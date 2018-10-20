package alloy.util;

import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Deque;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiFunction;
import java.util.stream.Collector;
import java.util.stream.Stream;

/**
 * Created by jlutteringer on 1/15/18.
 */
public class _Streams {
	public static <T, N> Stream<N> mapIndexed(Stream<T> iterable, BiFunction<T, Integer, N> mapper) {
		AtomicInteger i = new AtomicInteger(0);
		return iterable.map(v -> mapper.apply(v, i.getAndIncrement()));
	}

	public static <T, N> Stream<N> mapIndexed(Collection<T> iterable, BiFunction<T, Integer, N> mapper) {
		return mapIndexed(iterable.stream(), mapper);
	}

	public static <T> Stream<T> reverse(Stream<T> stream) {
		Deque<T> output =
				stream.collect(Collector.of(
						ArrayDeque::new,
						ArrayDeque::addFirst,
						(d1, d2) -> { d2.addAll(d1); return d2; }));

		return output.stream();
	}

	public static <T> Stream<T> stream(T element) {
		return _Lists.list(element).stream();
	}
}