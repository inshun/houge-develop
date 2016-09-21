package com.ysp.houge.utility;

import java.lang.reflect.Type;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.ysp.houge.app.AppException;
import com.ysp.houge.app.AppException.EnumException;

public class JsonParser {
	public static Gson gson = new Gson();

	public static <T> T deserializeByJson(String data, Type type)
			throws AppException {
		T t = null;
		if (!TextUtils.isEmpty(data)) {
			try {
				t = gson.fromJson(data, type);
			} catch (JsonSyntaxException e) {
				throw new AppException(EnumException.JsonSyntaxException,
						e.getMessage());
			}
		}
		return t;
	}

	public static <T> T deserializeByJson(String data, Class<T> clz)
			throws AppException {
		T t = null;
		if (!TextUtils.isEmpty(data)) {
			try {
				t = gson.fromJson(data, clz);
			} catch (JsonSyntaxException e) {
				throw new AppException(EnumException.JsonSyntaxException,
						e.getMessage());
			}
		}
		return t;
	}

	public static <T> String serializeToJson(T t) {
		if (t == null) {
			return "";
		}
		return gson.toJson(t);
	}

}