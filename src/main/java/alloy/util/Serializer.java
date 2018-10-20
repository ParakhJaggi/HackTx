package alloy.util;

import java.util.Map;

import alloy.util.Momento.Momentizer;

/**
 * Created by jlutteringer on 1/16/18.
 */
public interface Serializer<T> {
	Map<String, Object> serialize(T element);

	T deserialize(Map<String, Object> map);
}