package com.ai.opt.sdk.components.mds.constants;

/**
 * MDS消费者常量
 * Date: 2017年2月10日 <br>
 * Copyright (c) 2017 asiainfo.com <br>
 * 
 * @author
 */
public class MDSConsumerConstants {
	/**
	 * kafka.zookeeper.hosts=127.0.0.1:2181
	 */
	public static String KAFKA_ZOOKEEPER_HOSTS="kafka.zookeeper.hosts";
	/**
	 * kafka.zookeeper.broker.path=/brokers
	 */
	public static String KAFKA_ZOOKEEPER_BROKER_PATH="kafka.zookeeper.broker.path";
	/**
	 * kafka.zookeeper.user=
	 */
	public static String KAFKA_ZOOKEEPER_USER="kafka.zookeeper.user";
	/**
	 * kafka.zookeeper.user.passwd=
	 */
	public static String KAFKA_ZOOKEEPER_USER_PASSWD="kafka.zookeeper.user.passwd";
	/**
	 * kafka.consumer.id=123456xxxx
	 */
	public static String KAFKA_CONSUMER_ID="kafka.consumer.id";
	/**
	 * mds.partition.runninglock.path=/baas/MDS/MDS001/consumer/partitions
	 */
	public static String MDS_PARTITION_RUNNINGLOCK_PATH="mds.partition.runninglock.path";
	/**
	 * mds.partition.pauselock.path=/baas/MDS/MDS001/consumer/partitions
	 */
	public static String MDS_PARTITION_PAUSELOCK_PATH="mds.partition.pauselock.path";
	/**
	 * mds.partition.offset.basepath=/baas/MDS/MDS001/consumer/offsets
	 */
	public static String MDS_PARTITION_OFFSET_BASEPATH="mds.partition.offset.basepath";
	/**
	 * mds.consumer.base.path=/baas/MDS/MDS001
	 */
	public static String MDS_CONSUMER_BASE_PATH="mds.consumer.base.path";
	/**
	 * mds.zookeeper.hosts=127.0.0.1:2181
	 */
	public static String MDS_ZOOKEEPER_HOSTS="mds.zookeeper.hosts";
	/**
	 * mds.topic=bmc_kafka
	 */
	public static String MDS_TOPIC="mds.topic";

}
