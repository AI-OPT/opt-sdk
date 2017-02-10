package com.ai.opt.sdk.components.mds.constants;

/**
 * 发送MDS常量
 * Date: 2017年2月10日 <br>
 * Copyright (c) 2017 asiainfo.com <br>
 * 
 * @author
 */
public class MDSSenderConstants {
	/**
	 * metadata.broker.list=10.1.130.84:39091
	 */
	public static String METADATA_BROKER_LIST="metadata.broker.list";
	/**
	 * serializer.class=kafka.serializer.DefaultEncoder
	 */
	public static String SERIALIZER_CLASS="serializer.class";
	/**
	 * key.serializer.class=kafka.serializer.StringEncoder
	 */
	public static String KEY_SERIAL_CLASS="key.serializer.class";
	/**
	 * partitioner.class=com.ai.paas.ipaas.mds.impl.sender.ModPartitioner
	 */
	public static String PARTITIONER_CLASS="partitioner.class";
	/**
	 * request.required.acks=1
	 */
	public static String REQUEST_REQUIRED_ACKS="request.required.acks";
	/**
	 * queue.buffering.max.messages=1048576
	 */
	public static String QUEUE_BUFFERING_MAX_MESSAGES="queue.buffering.max.messages";
	/**
	 * producer.type=sync
	 */
	public static String PRODUCER_TYPE="producer.type";
	/**
	 * message.send.max.retries=3
	 */
	public static String MESSAGE_SEND_MAX_RETRIES="message.send.max.retries";
	/**
	 * compression.codec=none
	 */
	public static String COMPRESSION_CODEC="compression.codec";
	/**
	 * request.timeout.ms=20000
	 */
	public static String REQUEST_TIMEOUT_MS="request.timeout.ms";
	/**
	 * batch.num.messages=64000
	 */
	public static String BATCH_NUM_MESSAGES="batch.num.messages";
	/**
	 * send.buffer.bytes=67108864
	 */
	public static String SEND_BUFFER_BYTES="send.buffer.bytes";
	/**
	 * maxProducer=5
	 */
	public static String MAXPRODUCER="maxProducer";
	/**
	 * mds.topic=bmc_kafka
	 */
	public static String MDS_TOPIC="mds.topic";

}
