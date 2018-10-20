package alloy.util;

import java.util.Comparator;
import java.util.function.BiFunction;

/**
 * Created by jlutteringer on 1/15/18.
 */
public class Equality {
	public interface Equalitor<T, N> extends BiFunction<T, N, Boolean> {

	}

	public interface SymmetricEqualitor<T> extends Equalitor<T, T> {

	}

	public static <T, N> boolean equals(T o1, N o2, Equalitor<T, N> equalitor) {
		return equalitor.apply(o1, o2);
	}

	public static <T> int biDirectionalCompare(Comparator<T> comparator, T first, T second) {
		int firstResult = comparator.compare(first, second);
		int secondResult = comparator.compare(second, first);

		if(firstResult > 0 && secondResult > 0 || firstResult < 0 && secondResult < 0) {
			throw new RuntimeException();
		}

		if(firstResult < 0) {
			return -1;
		}

		if(secondResult < 0) {
			return 1;
		}

		return 0;
	}

	public static <T> Comparator<T> biDirectionalComparator(Comparator<T> comparator) {
		return (first, second) -> biDirectionalCompare(comparator, first, second);
	}
}