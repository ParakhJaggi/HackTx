package alloy.util;

/**
 * Created by jlutteringer on 1/15/18.
 */

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;

import java.util.*;

import org.springframework.util.MultiValueMap;

import com.google.common.collect.*;

import alloy.util.Tuple.Pair;


/**
 * Created by jlutteringer on 4/28/16.
 */
public interface _Maps {
	ImmutableMap<Object, Object> EMPTY_MAP = ImmutableMap.of();

	@SuppressWarnings("unchecked")
	static <T, N> ImmutableMap<T, N> map() {
		return (ImmutableMap<T, N>) EMPTY_MAP;
	}

	static <T, N> ImmutableMap<T, N> map(T k1, N v1) {
		return ImmutableMap.of(k1, v1);
	}

	static <T, N> ImmutableMap<T, N> map(T k1, N v1, T k2, N v2) {
		return ImmutableMap.of(k1, v1, k2, v2);
	}

	static <T, N> ImmutableMap<T, N> map(T k1, N v1, T k2, N v2, T k3, N v3) {
		return ImmutableMap.of(k1, v1, k2, v2, k3, v3);
	}

	@SafeVarargs
	static <T, N> ImmutableMap<T, N> mapPairs(Pair<T, N>... elements) {
		ImmutableMap.Builder<T, N> builder = ImmutableMap.builder();
		for(Pair<T, N> element : elements) {
			builder.put(element.getFirst(), element.getSecond());
		}
		return builder.build();
	}

	static <T, N> Map<T, N> map(Map<T, N> map) {
		if(map instanceof ImmutableMap) {
			return map;
		}

		return ImmutableMap.copyOf(map);
	}

	static <T, N> Map<T, N> mutableMap() {
		return Maps.newHashMap();
	}

	static <T, N> Map<T, N> mutableMap(Map<T, N> map) {
		return Maps.newHashMap(map);
	}

	@SafeVarargs
	static <T, N> Map<T, N> mutableMapPairs(Pair<T, N>... elements) {
		return _Lists.list(elements).stream().collect(_Collectors.toMap());
	}

	static Map<String, Object> map(Properties properties) {
		return properties.entrySet().stream().map(e -> Tuple.pair(e.getKey().toString(), e.getValue())).collect(_Collectors.toMap());
	}

	static <T, N> Multimap<T, N> multimap() {
		return ImmutableMultimap.of();
	}

	@SuppressWarnings("unchecked")
	static <T, N> Multimap<T, N> mutableMultimap() {
		return mutableMultimap(false);
	}

	@SuppressWarnings("unchecked")
	static <T, N> Multimap<T, N> mutableMultimap(boolean uniqueValues) {
		// this method is written this way to avoid a bug in the intellij compiler... don't refactor
		com.google.common.base.Supplier<? extends Collection<N>> factory = new com.google.common.base.Supplier<Collection<N>>() {
			@Override
			public Collection<N> get() {
				if(uniqueValues) {
					return _Sets.mutableSet();
				}
				else {
					return _Lists.mutableList();

				}
			}
		};

		return Multimaps.newMultimap(mutableMap(), factory);
	}

	@SafeVarargs
	static <T, N> Multimap<T, N> mutableCombine(Multimap<T, N>... maps) {
		Multimap<T, N> map = mutableMultimap();
		for(Multimap<T, N> component : maps) {
			map.putAll(component);
		}
		return map;
	}

	static <T, N> Multimap<T, N> multimap(Multimap<T, N> map) {
		if(map instanceof ImmutableMultimap) {
			return map;
		}

		return ImmutableMultimap.copyOf(map);
	}

	static <T, N> Multimap<T, N> multimap(MultiValueMap<T, N> map) {
		ImmutableMultimap.Builder<T, N> builder = ImmutableMultimap.builder();
		map.entrySet().forEach(entry -> builder.putAll(entry.getKey(), entry.getValue()));
		return builder.build();
	}

	static <T, N> Map<T, N> merge(Stream<Map<T, N>> stream) {
		return stream
				.map(Map::entrySet)
				.flatMap(Collection::stream)
				.collect(_Collectors.toMapEntry());
	}

	static <T, N> Map<T, N> append(Map<T, N> map, T key, N value) {
		return append(map, Tuple.pair(key, value));
	}

	@SafeVarargs
	static <T, N> Map<T, N> append(Map<T, N> map, Pair<T, N>... elements) {
		Map<T, N> result = Maps.newHashMap(map);
		for (Pair<T, N> element : elements) {
			result.put(element.getFirst(), element.getSecond());
		}

		return map(result);
	}

	// TODO all the methods here need to be refactored for better mutability/immutability consistency
	static <T, N> Map<T, N> mapValues(List<T> list, Function<T, N> f) {
		Map<T, N> map = Maps.newHashMap();
		for(T item : list) {
			map.put(item, f.apply(item));
		}
		return map;
	}

	static <T, N> Map<N, T> mapKeys(Iterable<T> list, Function<T, N> f) {
		Map<N, T> map = Maps.newHashMap();
		for(T item : list) {
			map.put(f.apply(item), item);
		}
		return map;
	}

	@SafeVarargs
	static <T, N> Map<T, N> combine(Map<T, N>... maps) {
		Map<T, N> val = Maps.newHashMap();
		for (Map<T, N> map : maps) {
			val.putAll(map);
		}
		return val;
	}

	@SafeVarargs
	static <T, N> Multimap<T, N> combine(Multimap<T, N>... maps) {
		Multimap<T, N> val = mutableMultimap();
		for (Multimap<T, N> map : maps) {
			val.putAll(map);
		}
		return val;
	}

	static <T, N> Multimap<T, N> multimap(Stream<Pair<T, N>> elementStream) {
		return elementStream.collect(_Collectors.toMultimap());
	}

	static <T, N> Multimap<N,T> reverse(Map<T, N> map) {
		return multimap(map.entrySet().stream()
				.map(entry -> Tuple.pair(entry.getValue(), entry.getKey())));
	}

	static <T, N> Map<T, N> defaultHashMap(Supplier<N> defaultSupplier) {
		return new DefaultHashMap<>(defaultSupplier);
	}

	static <T, N, S> Map<T, S> transformValues(Map<T, N> target, Function<N, S> transformer) {
		Map<T, S> map = Maps.newHashMap();
		for(Entry<T, N> entry : target.entrySet()) {
			map.put(entry.getKey(), transformer.apply(entry.getValue()));
		}
		return map;
	}

	static <T, N> Map<T, N> mutableMap(T key, N value) {
		return mutableMapPairs(Tuple.pair(key, value));
	}

	static <T, N> Stream<Pair<T, N>> stream(Map<T, N> map) {
		return map.entrySet().stream().map(entry -> Tuple.pair(entry.getKey(), entry.getValue()));
	}

	static <T, N> Map<T, N> remove(Map<T, N> map, T key) {
		Map<T, N> result = mutableMap(map);
		result.remove(key);
		return Collections.unmodifiableMap(result);
	}

	class DefaultHashMap<K, V> extends HashMap<K, V> {
		private static final long serialVersionUID = -4762508189017754826L;

		protected transient Supplier<V> defaultValueGenerator;

		public DefaultHashMap(Supplier<V> defaultValueGenerator) {
			this.defaultValueGenerator = defaultValueGenerator;
		}

		@SuppressWarnings("unchecked")
		@Override
		public V get(Object k) {
			if (!this.containsKey(k) && defaultValueGenerator != null) {
				this.put((K) k, this.defaultValueGenerator.get());
			}
			return super.get(k);
		}
	}
}