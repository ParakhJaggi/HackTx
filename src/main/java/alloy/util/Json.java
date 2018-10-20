package alloy.util;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Created by jlutteringer on 1/15/18.
 */
public class Json {
	public static String marshall(Object o) {
		return marshall(o, TypeConverters.getObjectMapper());
	}

	public static String marshall(Object o, ObjectMapper mapper) {
		if (o == null) {
			return "";
		}

		return TypeConverters.convertOrThrow(o, String.class, mapper);
	}

	public static <T> T unMarshall(String json, Class<T> clazz) {
		return unMarshall(json, clazz, TypeConverters.getObjectMapper());
	}

	public static <T> T unMarshall(String json, TypeReference<T> typeReference) {
		return unMarshall(json, typeReference, TypeConverters.getObjectMapper());
	}

	public static <T> T unMarshall(String json, Class<T> clazz, ObjectMapper mapper) {
		return TypeConverters.convertOrThrow(json, clazz, mapper);
	}

	public static <T> T unMarshall(String json, TypeReference<T> typeReference, ObjectMapper mapper) {
		return TypeConverters.convertOrThrow(json, typeReference, mapper);
	}

	public static class JsonModel extends HashMap<String, Object> {
		public String toJson() {
			return marshall(this);
		}
	}

	public static JsonModel model() {
		return new JsonModel();
	}
}