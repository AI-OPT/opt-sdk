package com.ai.opt.sdk.components.mcs.constants;

public class MCSConstants {
	/**
	 * mcs对应的redis主机
	 * mcs.host=127.0.0.1:6379
	 * mcs.host=192.168.0.21:6379;192.168.0.22:6379;192.168.0.23:6379
	 * 
	 */
	public static String MCS_HOST="mcs.host";
	/**
	 * mcs.maxtotal=200
	 */
	public static String MCS_MAXTOTAL="mcs.maxtotal";
	/**
	 * mcs.maxIdle=10
	 */
	public static String MCS_MAXIDLE="mcs.maxIdle";
	/**
	 * mcs.minIdle=5
	 */
	public static String MCS_MINIDLE="mcs.minIdle";
	/**
	 * mcs.testOnBorrow=true
	 */
	public static String MCS_TESTONBORROW="mcs.testOnBorrow";
	/**
	 * mcs.password=123456
	 */
	public static String MCS_PASSWORD="mcs.password";

}
