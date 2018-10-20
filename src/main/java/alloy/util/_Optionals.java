package alloy.util;

import java.util.Collection;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Stream;

/**
 * Created by jlutteringer on 1/15/18.
 */
public class _Optionals {
	public static <T> Optional<T> firstSome(Collection<Optional<T>> optionals) {
		return firstSome(optionals.stream());
	}

	public static <T> Optional<T> firstSome(Stream<Optional<T>> optionals) {
		return optionals.filter(Optional::isPresent).map(Optional::get).findFirst();
	}

	public static <T> Supplier<Optional<T>> lift(Supplier<T> operation) {
		return () -> Optional.ofNullable(operation.get());
	}
}