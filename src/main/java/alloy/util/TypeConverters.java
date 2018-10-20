package alloy.util;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

/**
 * Created by jlutteringer on 1/15/18.
 */
public class TypeConverters {
	private static volatile ObjectMapper GLOBAL_OBJECT_MAPPER = null;

	public static ObjectMapper getObjectMapper() {
		if(GLOBAL_OBJECT_MAPPER == null) {
			synchronized (Json.class) {
				if(GLOBAL_OBJECT_MAPPER == null) {
					ObjectMapper mapper = new ObjectMapper();
					configureObjectMapper(mapper);
					GLOBAL_OBJECT_MAPPER = mapper;
				}
			}
		}

		return GLOBAL_OBJECT_MAPPER;
	}

	public static void setGlobalObjectMapper(ObjectMapper mapper) {
		synchronized (Json.class) {
			GLOBAL_OBJECT_MAPPER = mapper;
		}
	}

	public static void configureObjectMapper(ObjectMapper mapper) {
		mapper.registerModule(new JavaTimeModule());
		mapper.registerModule(new Jdk8Module());
		mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
		mapper.disable(SerializationFeature.WRITE_DATE_TIMESTAMPS_AS_NANOSECONDS);
		mapper.disable(DeserializationFeature.READ_DATE_TIMESTAMPS_AS_NANOSECONDS);

		mapper.disable(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE);
		mapper.enable(SerializationFeature.WRITE_DATES_WITH_ZONE_ID);

		mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
		mapper.configure(JsonParser.Feature.ALLOW_NUMERIC_LEADING_ZEROS, true);
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		mapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);
		mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

		mapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
	}


	public static <T> Either<T, IllegalArgumentException> convert(Object object, TypeReference<T> clazz) {
		return convert(object, clazz, getObjectMapper());
	}

	public static <T> Either<T, IllegalArgumentException> convert(Object object, Class<T> clazz) {
		return convert(object, clazz, getObjectMapper());
	}

	@SuppressWarnings({"unchecked", "Duplicates"})
	public static <T> Either<T, IllegalArgumentException> convert(Object object, TypeReference<T> clazz, ObjectMapper mapper) {
		return _Exceptions.either(() -> {
			if(clazz.getType().equals(String.class) && object instanceof String) {
				return (T) object;
			}
			else if(clazz.getType().equals(String.class)) {
				try {
					return (T) mapper.writer().writeValueAsString(object);
				} catch (JsonProcessingException e) {
					throw new IllegalArgumentException(e);
				}
			}
			else if(object instanceof String) {
				try {
					return mapper.readValue((String) object, clazz);
				} catch (IOException e) {
					throw new IllegalArgumentException(e);
				}
			}
			else {
				return mapper.convertValue(object, clazz);
			}
		}, IllegalArgumentException.class);
	}

	@SuppressWarnings({"unchecked", "Duplicates"})
	public static <T> Either<T, IllegalArgumentException> convert(Object object, Class<T> clazz, ObjectMapper mapper) {
		return _Exceptions.either(() -> {
			if(clazz.equals(String.class) && object instanceof String) {
				return (T) object;
			}
			else if(clazz.equals(String.class)) {
				try {
					return (T) mapper.writer().writeValueAsString(object);
				} catch (JsonProcessingException e) {
					throw new IllegalArgumentException(e);
				}
			}
			else if(object instanceof String) {
				try {
					return mapper.readValue((String) object, clazz);
				} catch (IOException e) {
					throw new IllegalArgumentException(e);
				}
			}
			else {
				return mapper.convertValue(object, clazz);
			}
		}, IllegalArgumentException.class);
	}

	public static <T> Optional<T> convertOptional(Object object, TypeReference<T> clazz) {
		return convert(object, clazz).getLeft();
	}

	public static <T> Optional<T> convertOptional(Object object, TypeReference<T> clazz, ObjectMapper mapper) {
		return convert(object, clazz, mapper).getLeft();
	}

	public static <T> Optional<T> convertOptional(Object object, Class<T> clazz) {
		return convert(object, clazz).getLeft();
	}

	public static <T> Optional<T> convertOptional(Object object, Class<T> clazz, ObjectMapper mapper) {
		return convert(object, clazz, mapper).getLeft();
	}

	public static <T> T convertOrThrow(Object object, TypeReference<T> clazz) {
		return convert(object, clazz).map(Function.identity(), _Exceptions::propagate).getLeft().orElseThrow(IllegalStateException::new);
	}

	public static <T> T convertOrThrow(Object object, TypeReference<T> clazz, ObjectMapper mapper) {
		return convert(object, clazz, mapper).map(Function.identity(), _Exceptions::propagate).getLeft().orElseThrow(IllegalStateException::new);
	}

	public static <T> T convertOrThrow(Object object, Class<T> clazz) {
		return convert(object, clazz).map(Function.identity(), _Exceptions::propagate).getLeft().orElseThrow(IllegalStateException::new);
	}

	public static <T> T convertOrThrow(Object object, Class<T> clazz, ObjectMapper mapper) {
		return convert(object, clazz, mapper).map(Function.identity(), _Exceptions::propagate).getLeft().orElseThrow(IllegalStateException::new);
	}

	public static Map<String,Object> mapify(Object object) {
		return convertOrThrow(object, new TypeReference<Map<String, Object>>() {});
	}

	public static <T> Map<String, T> mapify(Object object, Class<T> clazz) {
		return convertOrThrow(object, new TypeReference<Map<String, T>>() {});
	}

	public static <T> Serializer<T> serializer(Class<T> clazz) {
		return new Serializer<T>() {
			@Override
			public Map<String, Object> serialize(T element) {
				return mapify(element);
			}

			@Override
			public T deserialize(Map<String, Object> map) {
				return convertOrThrow(map, clazz);
			}
		};
	}
}