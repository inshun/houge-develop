package com.ysp.houge.utility.volley;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.ysp.houge.app.AppException;
import com.ysp.houge.model.entity.base.BaseHttpResultEntity;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Json工具类
 * 
 * @author tyn
 * @version 1.0, 2015年3月13日 下午5:51:49
 */
public abstract class JsonUtils {

	private static final String NODE_RESULT = "result";
	private static final String NODE_MSG = "msg";
	public static boolean isPrintException = true;
	private static Gson gson = new Gson();

	public static <T> BaseHttpResultEntity<T> reloveJsonToBean(String json, String dataKey, Class<T> clazz)
			throws JsonSyntaxException {
		BaseHttpResultEntity<T> resultEntity = null;
		if (json != null) {
			JsonParser parser = new JsonParser();
			JsonElement element = parser.parse(json);
			if (element.isJsonObject()) {
				resultEntity = new BaseHttpResultEntity<T>();
				JsonObject rootJsonObject = element.getAsJsonObject();
				if (rootJsonObject.has(NODE_RESULT)) {
					resultEntity.setResult(rootJsonObject.get(NODE_RESULT).getAsBoolean());
				}
				if (rootJsonObject.has(dataKey)) {
					JsonElement businessElement = rootJsonObject.get(dataKey);
					if (businessElement.isJsonObject()) {
						JsonObject jsonObject = businessElement.getAsJsonObject();
						T resultT;
						try {
							resultT = com.ysp.houge.utility.JsonParser.deserializeByJson(jsonObject.toString(), clazz);
							resultEntity.setData(resultT);
						} catch (AppException e) {
							e.printStackTrace();
						}
					}
				}
				if (rootJsonObject.has(NODE_MSG)) {
					resultEntity.setMsg(rootJsonObject.get(NODE_MSG).getAsString());
				}
			}
		}
		return resultEntity;
	}

	public static <T> BaseHttpResultEntity<List<T>> reloveJsonToListBean(String json, String dataKey, Class<T> clazz)
			throws JsonSyntaxException {
		BaseHttpResultEntity<List<T>> resultEntity = null;
		if (json != null) {
			JsonParser parser = new JsonParser();
			JsonElement element = parser.parse(json);

			if (element.isJsonObject()) {
				resultEntity = new BaseHttpResultEntity<List<T>>();
				JsonObject rootJsonObject = element.getAsJsonObject();
				if (rootJsonObject.has(NODE_RESULT)) {
					resultEntity.setResult(rootJsonObject.get(NODE_RESULT).getAsBoolean());
				}
				if (rootJsonObject.has(dataKey)) {
					JsonElement businessElement = rootJsonObject.get(dataKey);
					if (businessElement.isJsonArray()) {
						JsonArray jsonArray = businessElement.getAsJsonArray();
						List<T> objList = new ArrayList<T>();
						JsonElement element3;
						T resultT;
						for (int i = 0, count = jsonArray.size(); i < count; i++) {
							element3 = jsonArray.get(i);
							resultT = gson.fromJson(element3, clazz);
							objList.add(resultT);
						}
						resultEntity.setData(objList);
					}
				}
				if (rootJsonObject.has(NODE_MSG)) {
					resultEntity.setMsg(rootJsonObject.get(NODE_MSG).getAsString());
				}
			}
		}
		return resultEntity;
	}

	public static BaseHttpResultEntity<JsonElement> reloveJsonToJsonElement(String json, String dataKey) {
		BaseHttpResultEntity<JsonElement> resultEntity = null;
		if (json != null) {
			JsonParser parser = new JsonParser();
			JsonElement element = parser.parse(json);

			if (element.isJsonObject()) {
				resultEntity = new BaseHttpResultEntity<JsonElement>();
				JsonObject rootJsonObject = element.getAsJsonObject();
				if (rootJsonObject.has(NODE_RESULT)) {
					resultEntity.setResult(rootJsonObject.get(NODE_RESULT).getAsBoolean());
				}
				if (rootJsonObject.has(dataKey)) {
					JsonElement businessElement = rootJsonObject.get(dataKey);
					resultEntity.setData(businessElement);
				}
				if (rootJsonObject.has(NODE_MSG)) {
					resultEntity.setMsg(rootJsonObject.get(NODE_MSG).getAsString());
				}
			}
		}
		return resultEntity;
	}

	public static BaseHttpResultEntity<String> reloveJsonToString(String json, String dataKey) {
		BaseHttpResultEntity<String> resultEntity = null;
		if (json != null) {
			JsonParser parser = new JsonParser();
			JsonElement element = parser.parse(json);
			if (element.isJsonObject()) {
				resultEntity = new BaseHttpResultEntity<String>();
				JsonObject rootJsonObject = element.getAsJsonObject();
				if (rootJsonObject.has(NODE_RESULT)) {
					resultEntity.setResult(rootJsonObject.get(NODE_RESULT).getAsBoolean());
				}
				// 此时返回的是当前的datakey所对应的value
				if (rootJsonObject.has(dataKey)) {
					resultEntity.setData(rootJsonObject.get(dataKey).getAsString());
				}
				if (rootJsonObject.has(NODE_MSG)) {
					resultEntity.setMsg(rootJsonObject.get(NODE_MSG).getAsString());
				}
			}
		}
		return resultEntity;
	}

	public static Map<String, Object> reloveJsonToToMap(String jsonStr) throws JsonSyntaxException {
		Map<String, Object> objMap = null;
		Type type = new TypeToken<Map<String, ?>>() {
		}.getType();
		try {
			objMap = com.ysp.houge.utility.JsonParser.deserializeByJson(jsonStr, type);
		} catch (AppException e) {
			e.printStackTrace();
		}
		return objMap;
	}

}