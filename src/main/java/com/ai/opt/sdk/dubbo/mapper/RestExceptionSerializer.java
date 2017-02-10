package com.ai.opt.sdk.dubbo.mapper;

import java.lang.reflect.Type;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

/**
 * RestException
 * Date: 2017年2月10日 <br>
 * Copyright (c) 2017 asiainfo.com <br>
 * 
 * @author
 */
public class RestExceptionSerializer implements JsonSerializer<Throwable> {
	@Override
	public JsonElement serialize(Throwable src, Type typeOfSrc,
			JsonSerializationContext context) {
		JsonObject jsonObject = new JsonObject();
		jsonObject.add("cause",
				new JsonPrimitive(String.valueOf(src.getCause())));
		jsonObject.add("message", new JsonPrimitive(src.getMessage()));
		return jsonObject;
	}
}
