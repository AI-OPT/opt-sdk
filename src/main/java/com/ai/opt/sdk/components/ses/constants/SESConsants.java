package com.ai.opt.sdk.components.ses.constants;

public class SESConsants {
	/**
	 * elasticsearch主机
	 * eshosts=10.1.xxx.xxx:9300,10.1.xxx.xxx:9300
	 */
	public static String ESHOSTS="eshosts";
	/**
	 * 索引名称
	 * indexname=user-detail-index
	 */
	public static String INDEXNAME="indexname";
	/**
	 * 索引mapping的主键
	 * mappingid=userid
	 */
	public static String MAPPINGID="mappingid";
	/**
	 * 索引mapping
	 * mapping=
	 * 	     {
	 *				"userInfo" : {
	 *						"properties" : {
	 *						    "userId" :  {"type" : "string", "store" : "yes","index": "not_analyzed"},
	 *						      "name" : {"type" : "string", "store" : "yes","analyzer":"ik_max_word"},
	 *						      "age" : {"type" : "integer"},"
	 *						      "created" : {"type" : "date", "format" : "strict_date_optional_time||epoch_millis"}
	 *						 }   
	 *				}  
	 *		}
	 */
	public static String MAPPING="mapping";
	
	/**
	 * 分片数
	 * shards=2
	 */
	public static String SHARDS="shards";
	/**
	 * 副本数量
	 * replicas=1
	 */
	public static String REPLICAS="replicas";
}
