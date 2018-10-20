package alloy.util;

/**
 * Created by jlutteringer on 1/15/18.
 */

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.BiConsumer;
import java.util.function.Supplier;

import java.util.Collection;
import java.util.Set;
import java.util.function.BinaryOperator;
import java.util.stream.Collector;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multimaps;

import alloy.util.Tuple.Pair;

/**
 * Created by jlutteringer on 4/27/16.
 */
public interface _Collectors {
	static <T> Collector<T, ImmutableList.Builder<T>, List<T>> toList() {
		return Collector.<T, ImmutableList.Builder<T>, List<T>> of(ImmutableList.Builder::new, ImmutableList.Builder::add, new BinaryOperator<ImmutableList.Builder<T>>() {
			@Override
			public ImmutableList.Builder<T> apply(ImmutableList.Builder<T> first, ImmutableList.Builder<T> second) {
				first.addAll(second.build());
				return first;
			}
		}, ImmutableList.Builder::build);
	}

	static <T> Collector<T, ImmutableSet.Builder<T>, Set<T>> toSet() {
		return Collector.<T, ImmutableSet.Builder<T>, Set<T>> of(ImmutableSet.Builder::new, ImmutableSet.Builder::add, new BinaryOperator<ImmutableSet.Builder<T>>() {
			@Override
			public ImmutableSet.Builder<T> apply(ImmutableSet.Builder<T> first, ImmutableSet.Builder<T> second) {
				first.addAll(second.build());
				return first;
			}
		}, ImmutableSet.Builder::build);
	}

	static <T, N> Collector<Pair<? extends T, ? extends N>, ImmutableMap.Builder<T, N>, Map<T, N>> toMap() {
		return Collector.of(
				new Supplier<ImmutableMap.Builder<T, N>>() {
					@Override
					public ImmutableMap.Builder<T, N> get() {
						return ImmutableMap.builder();
					}
				}, // Supplier of initial value
				new BiConsumer<ImmutableMap.Builder<T, N>, Pair<? extends T, ? extends N>>() {
					@Override
					public void accept(ImmutableMap.Builder<T, N> tnBuilder, Pair<? extends T, ? extends N> objects) {
						tnBuilder.put(objects.getFirst(), objects.getSecond());
					}
				}, // Mapper of collector type to the data structure
				new BinaryOperator<ImmutableMap.Builder<T, N>>() {
					@Override
					public ImmutableMap.Builder<T, N> apply(ImmutableMap.Builder<T, N> tnBuilder, ImmutableMap.Builder<T, N> tnBuilder2) {
						tnBuilder.putAll(tnBuilder2.build());
						return tnBuilder;
					}
				}, ImmutableMap.Builder::build); // Combiner in the case of parallel collections
	}

	static <T, N> Collector<Entry<T, N>, ImmutableMap.Builder<T, N>, Map<T, N>> toMapEntry() {
		return Collector.of(
				new Supplier<ImmutableMap.Builder<T, N>>() {
					@Override
					public ImmutableMap.Builder<T, N> get() {
						return ImmutableMap.builder();
					}
				}, // Supplier of initial value
				new BiConsumer<ImmutableMap.Builder<T, N>, Entry<T, N>>() {
					@Override
					public void accept(ImmutableMap.Builder<T, N> tnBuilder, Entry<T, N> objects) {
						tnBuilder.put(objects.getKey(), objects.getValue());
					}
				}, // Mapper of collector type to the data structure
				new BinaryOperator<ImmutableMap.Builder<T, N>>() {
					@Override
					public ImmutableMap.Builder<T, N> apply(ImmutableMap.Builder<T, N> tnBuilder, ImmutableMap.Builder<T, N> tnBuilder2) {
						tnBuilder.putAll(tnBuilder2.build());
						return tnBuilder;
					}
				}, ImmutableMap.Builder::build); // Combiner in the case of parallel collections
	}


	static <T, N> Collector<Pair<T, N>, Multimap<T, N>, Multimap<T, N>> toMultimap() {
		return Collector.of(
				new Supplier<Multimap<T, N>>() {
					@Override
					public Multimap<T, N> get() {
						return Multimaps.newListMultimap(Maps.newLinkedHashMap(),
								new com.google.common.base.Supplier<List<N>>() {
									@Override
									public List<N> get() {
										return Lists.newArrayList();
									}
								});
					}
				}, // Supplier of initial value
				new BiConsumer<Multimap<T, N>, Pair<T, N>>() {
					@Override
					public void accept(Multimap<T, N> tnMultimap, Pair<T, N> objects) {
						tnMultimap.put(objects.getFirst(), objects.getSecond());
					}
				}, // Mapper of collector type to the data structure
				new BinaryOperator<Multimap<T, N>>() {
					@Override
					public Multimap<T, N> apply(Multimap<T, N> tnMultimap, Multimap<T, N> tnMultimap2) {
						return _Maps.mutableCombine(tnMultimap, tnMultimap2);
					}
				}); // Combiner in the case of parallel collections
	}

	static <T, N> Collector<Pair<T, ? extends Collection<N>>, Multimap<T, N>, Multimap<T, N>> flattenToMultimap() {
		return Collector.of(
				new Supplier<Multimap<T, N>>() {
					@Override
					public Multimap<T, N> get() {
						return Multimaps.newListMultimap(Maps.newLinkedHashMap(),
								new com.google.common.base.Supplier<List<N>>() {
									@Override
									public List<N> get() {
										return Lists.newArrayList();
									}
								});
					}
				}, // Supplier of initial value
				new BiConsumer<Multimap<T, N>, Pair<T, ? extends Collection<N>>>() {
					@Override
					public void accept(Multimap<T, N> tnMultimap, Pair<T, ? extends Collection<N>> objects) {
						tnMultimap.putAll(objects.getFirst(), objects.getSecond());
					}
				}, // Mapper of collector type to the data structure
				new BinaryOperator<Multimap<T, N>>() {
					@Override
					public Multimap<T, N> apply(Multimap<T, N> tnMultimap, Multimap<T, N> tnMultimap2) {
						return _Maps.mutableCombine(tnMultimap, tnMultimap2);
					}
				}); // Combiner in the case of parallel collections
	}
}