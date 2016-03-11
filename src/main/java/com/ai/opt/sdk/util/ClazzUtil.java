package com.ai.opt.sdk.util;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ai.opt.base.exception.SystemException;

public class ClazzUtil {

    private static final Logger LOG = LoggerFactory.getLogger(ClazzUtil.class);

    private ClazzUtil() {

    }

    /**
     * 根据接口名称获取类实例
     * 
     * @param interfaceName
     * @return
     * @throws Exception
     * @author zhangchao
     */
    public static Class<?> getInterfaceClazz(String interfaceName) throws Exception {
        Class<?> interfaceClass = null;
        try {
            interfaceClass = Class.forName(interfaceName, true, Thread.currentThread()
                    .getContextClassLoader());
        } catch (ClassNotFoundException e) {
            throw new SystemException("接口名[" + interfaceName + "]对应的类不存在，请检查", e);
        }
        if (interfaceClass == null) {
            throw new SystemException("根据接口名[" + interfaceName + "]获取到的接口实例对象为空，请检查");
        }
        if (!interfaceClass.isInterface()) {
            throw new SystemException("类[" + interfaceName + "]不是一个接口类，请检查定义");
        }
        return interfaceClass;
    }

    /**
     * 根据方法名称获取同名的方法对象
     * 
     * @param interfaceClass
     * @param methodName
     * @return
     * @author zhangchao
     * @throws SystemException 
     */
    public static List<java.lang.reflect.Method> getMethods(Class<?> interfaceClass,
            String methodName) throws SystemException {
        // 接口不能为空
        if (interfaceClass == null) {
            throw new SystemException("接口类不能为空");
        }
        // 检查接口类型必需为接口
        if (!interfaceClass.isInterface()) {
            throw new SystemException("类[" + interfaceClass.getName() + "]不是一个接口类，请检查定义");
        }
        // 检查方法是否在接口中存在
        List<java.lang.reflect.Method> methods = new ArrayList<java.lang.reflect.Method>();
        for (java.lang.reflect.Method method : interfaceClass.getMethods()) {
            if (method.getName().equals(methodName)) {
                methods.add(method);
            }
        }
        return methods;
    }

    /**
     * 获取一个类下的所有子类
     * 
     * @param clazz
     * @return
     * @throws Exception
     * @author zhangchao
     */
    public static List<Class<?>> getAllAssignedClass(Class<?> clazz) throws Exception {
        if (clazz == null) {
            return null;
        }
        if (clazz.getPackage() == null) {
            return null;
        }
        List<Class<?>> classes = new ArrayList<Class<?>>();
        String pkg = clazz.getPackage().getName();
        List<Class<?>> list = getAllClasses(pkg, true, null);
        for (Class<?> c : list) {
            if (clazz.isAssignableFrom(c) && !clazz.equals(c)) {
                classes.add(c);
            }
        }
        return classes;
    }

    /**
     * 获取指定包路径下的所有类
     * 
     * @param pkgName
     * @param isRecursive
     * @param annotation
     * @return
     * @author zhangchao
     */
    private static List<Class<?>> getAllClasses(String pkgName, boolean isRecursive,
            Class<? extends Annotation> annotation) throws Exception {
        List<Class<?>> classList = new ArrayList<Class<?>>();
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        try {
            String strFile = pkgName.replaceAll("\\.", "/");
            Enumeration<URL> urls = loader.getResources(strFile);
            while (urls.hasMoreElements()) {
                URL url = urls.nextElement();
                if (url != null) {
                    String protocol = url.getProtocol();
                    String pkgPath = url.getPath();
                    if ("file".equals(protocol)) {
                        // 本地自己可见的代码
                        findClassName(classList, pkgName, pkgPath, isRecursive, annotation);
                    } else if ("jar".equals(protocol)) {
                        // 引用第三方jar的代码
                        findClassName(classList, pkgName, url, isRecursive, annotation);
                    }
                }
            }
        } catch (IOException e) {
            LOG.error("查找类列表错误", e);
        }
        return classList;
    }

    /**
     * 从当前工程查找类
     * 
     * @param classes
     * @param pkgName
     * @param pkgPath
     * @param isRecursive
     * @param annotation
     * @author zhangchao
     */
    private static void findClassName(List<Class<?>> classes, String pkgName, String pkgPath,
            boolean isRecursive, Class<? extends Annotation> annotation) {
        if (CollectionUtil.isEmpty(classes)) {
            return;
        }
        /* 1.获取指定包下的.class文件或文件夹 */
        File[] files = filterClassFiles(pkgPath);
        /* 2.如果没有检索到文件，则不处理 */
        if (CollectionUtil.isEmpty(files)) {
            return;
        }
        /* 3.依次处理文件 */
        for (File f : files) {
            String fileName = f.getName();
            if (f.isFile()) {
                /* 3.1 如果是单个.class文件 */
                String clazzName = getClassFullName(pkgName, fileName);
                addClassName(classes, clazzName, annotation);
            } else {
                /* 3.2 如果是文件夹，根据是否递归，来进行处理 */
                if (isRecursive) {
                    String subPkgName = pkgName + "." + fileName;
                    String subPkgPath = pkgPath + "/" + fileName;
                    findClassName(classes, subPkgName, subPkgPath, true, annotation);
                }
            }
        }
    }

    /**
     * 从JAR包查找类
     * 
     * @param clazzList
     * @param pkgName
     * @param url
     * @param isRecursive
     * @param annotation
     * @throws Exception
     * @author zhangchao
     */
    private static void findClassName(List<Class<?>> clazzList, String pkgName, URL url,
            boolean isRecursive, Class<? extends Annotation> annotation) throws Exception {
        JarURLConnection jarURLConnection = (JarURLConnection) url.openConnection();
        JarFile jarFile = jarURLConnection.getJarFile();
        Enumeration<JarEntry> jarEntries = jarFile.entries();
        while (jarEntries.hasMoreElements()) {
            JarEntry jarEntry = jarEntries.nextElement();
            String jarEntryName = jarEntry.getName();
            String clazzName = jarEntryName.replace("/", ".");
            int endIndex = clazzName.lastIndexOf(".");
            String prefix = null;
            String prefix_name = null;
            if (endIndex > 0) {
                prefix_name = clazzName.substring(0, endIndex);
                endIndex = prefix_name.lastIndexOf(".");
                if (endIndex > 0) {
                    prefix = prefix_name.substring(0, endIndex);
                }
            }
            if (prefix != null && jarEntryName.endsWith(".class")) {
                if (prefix.equals(pkgName)) {
                    addClassName(clazzList, prefix_name, annotation);
                } else if (isRecursive && prefix.startsWith(pkgName)) {
                    addClassName(clazzList, clazzName, annotation);
                }
            }
        }
    }

    /**
     * 获取路径下.class文件或文件夹
     * 
     * @param pkgPath
     * @return
     * @author zhangchao
     */
    private static File[] filterClassFiles(String pkgPath) {
        if (pkgPath == null) {
            return null;
        }
        return new File(pkgPath).listFiles(new FileFilter() {
            @Override
            public boolean accept(File file) {
                return (file.isFile() && file.getName().endsWith(".class")) || file.isDirectory();
            }
        });
    }

    /**
     * 获取类的全路径名
     * 
     * @param pkgName
     * @param fileName
     * @return
     * @author zhangchao
     */
    private static String getClassFullName(String pkgName, String fileName) {
        int endIndex = fileName.lastIndexOf(".");
        String clazz = null;
        if (endIndex >= 0) {
            clazz = fileName.substring(0, endIndex);
        }
        String clazzName = null;
        if (clazz != null) {
            clazzName = pkgName + "." + clazz;
        }
        return clazzName;
    }

    /**
     * 加入到类集合中
     * 
     * @param clazzList
     * @param clazzName
     * @param annotation
     * @author zhangchao
     */
    private static void addClassName(List<Class<?>> clazzList, String clazzName,
            Class<? extends Annotation> annotation) {
        if (clazzList != null && clazzName != null) {
            Class<?> clazz = null;
            try {
                clazz = Class.forName(clazzName);
            } catch (ClassNotFoundException e) {
                LOG.error("class not found", e);
            }
            if (clazz != null) {
                if (annotation == null) {
                    clazzList.add(clazz);
                } else if (clazz.isAnnotationPresent(annotation)) {
                    clazzList.add(clazz);
                }
            }
        }
    }
}
