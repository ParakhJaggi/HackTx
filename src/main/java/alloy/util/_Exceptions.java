package alloy.util;

import java.io.IOException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;
import java.util.function.Supplier;

import com.google.common.base.Throwables;
import com.google.common.collect.Sets;

import static com.google.common.base.Preconditions.checkNotNull;

public class _Exceptions {
	public static final Set<Class<? extends Throwable>> RETRYABLE_EXCEPTIONS = _Sets.set(
			SQLException.class,
			SocketException.class,
			SocketTimeoutException.class,
			IOException.class
	);

	public static <T, N> Either<T, N> either(Supplier<T> supplier, Class<N> exceptionType) {
		return either(supplier).mapRight(e -> castOrPropogate(e, exceptionType));
	}

	private static <T> T castOrPropogate(Exception e, Class<T> exceptionType) {
		if(exceptionType.isAssignableFrom(e.getClass())) {
			return (T) e;
		}
		else {
			throw new RuntimeException(e);
		}
	}

	public static <T> Either<T, Exception> either(Supplier<T> supplier) {
		try {
			return Either.left(supplier.get());
		}
		catch (Exception e) {
			return Either.right(e);
		}
	}

	public static void propagate(ExceptionalOperation<?> operation) {
		try {
			operation.apply();
		} catch (Throwable e) {
			throw new RuntimeException(e);
		}
	}

	public static <T> T propagate(ExceptionalSupplier<T, Throwable> operation) {
		try {
			return operation.next();
		} catch (Throwable e) {
			throw new RuntimeException(e);
		}
	}

	public static void ignore(ExceptionalOperation<?> operation) {
		try {
			operation.apply();
		} catch (Throwable e) {
		}
	}

	public static <T> Optional<T> ignoreRuntime(Supplier<T> operation) {
		return ignoreRuntimeOptional(_Optionals.lift(operation));
	}

	public static <T> Optional<T> ignoreRuntimeOptional(Supplier<Optional<T>> operation) {
		return ignoreOptional(operation::get);
	}

	public static <T> Optional<T> ignore(ExceptionalSupplier<T, Exception> operation) {
		try {
			return Optional.of(operation.next());
		} catch (Exception e) {
			return Optional.empty();
		}
	}

	public static <T> Optional<T> ignore(ExceptionalSupplier<T, Exception> operation, Predicate<Exception> predicate) {
		try {
			return Optional.of(operation.next());
		} catch (Exception e) {
			if(predicate.test(e)) {
				return Optional.empty();
			} else {
				throw propagate(e);
			}
		}
	}

	public static <T> Optional<T> ignore(ExceptionalSupplier<T, Exception> operation, Class<? extends Exception> clazz) {
		return ignore(operation, (exception) -> exception.getClass().isAssignableFrom(clazz));
	}

	public static <T> Optional<T> ignoreOptional(ExceptionalSupplier<Optional<T>, Exception> operation) {
		try {
			return operation.next();
		} catch (Exception e) {
			return Optional.empty();
		}
	}

	private static HashSet<Class<? extends Exception>>
			INTERMITTENT_EXCEPTIONS = Sets.newHashSet();

	public static boolean isIntermittent(Exception exception) {
		return INTERMITTENT_EXCEPTIONS.contains(exception.getClass());
	}

	@SuppressWarnings("unchecked")
	public static <T> Optional<T> getCause(Throwable exception, Class<T> exceptionType) {
		List<Throwable> throwables = _Lists.list(exception, Throwables.getCausalChain(exception));

		return throwables.stream()
				.filter(throwable -> exceptionType.isAssignableFrom(throwable.getClass()))
				.map(throwable -> (T) throwable)
				.findFirst();
	}

	@FunctionalInterface
	public interface ExceptionalOperation<T extends Throwable> {
		void apply() throws T;
	}

	@FunctionalInterface
	public interface ExceptionalSupplier<T, N extends Throwable> {
		T next() throws N;

		static <N> ExceptionalSupplier<N, Exception> wrap(Supplier<N> f) {
			return f::get;
		}
	}

	public static class NotYetImplementedException extends RuntimeException {

	}

	public static RuntimeException propagate(Throwable throwable) {
		throwIfUnchecked(throwable);

		throw new RuntimeException(throwable);
	}

	private static void throwIfUnchecked(Throwable throwable) {
		checkNotNull(throwable);

		if (throwable instanceof RuntimeException) {
			throw (RuntimeException) throwable;
		}
		if (throwable instanceof Error) {
			throw (Error) throwable;
		}
	}
}