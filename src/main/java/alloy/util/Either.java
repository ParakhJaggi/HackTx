package alloy.util;

import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

import com.google.common.base.Preconditions;

import alloy.util.Equality.SymmetricEqualitor;


/**
 * Created by jlutteringer on 5/16/16.
 */
public final class Either<L, R> {
	public static <L, R> Either<L, R> left(L value) {
		return new Either<>(Optional.of(value), Optional.empty());
	}

	public static <L, R> Either<L, R> right(R value) {
		return new Either<>(Optional.empty(), Optional.of(value));
	}

	public static <L, R> Either<L, R> of(Optional<? extends L> left, Optional<? extends R> right) {
		if(left.isPresent() && right.isPresent()) {
			throw new IllegalArgumentException();
		}
		if(!left.isPresent() && !right.isPresent()) {
			throw new IllegalArgumentException();
		}

		if(left.isPresent()) {
			return left(left.get());
		}
		else {
			return right(right.get());
		}
	}

	// This should be marked as a false positive in Sonar
	public static <L, R> boolean equals(Either<L, R> first, Either<L, R> second,
	                                    SymmetricEqualitor<L> leftEquality, SymmetricEqualitor<R> rightEquality) {
		return Either.reduce(first, second, leftEquality, rightEquality, () -> false);
	}

	public static <L, R, T> T reduce(Either<L, R> first, Either<L, R> second,
	                                 BiFunction<L, L, T> leftReducer, BiFunction<R, R, T> rightReducer,
	                                 Supplier<T> defaultSupplier) {

		Either<T, ?> mergedResult = merge(first, second,
				(val1, val2) -> Either.left(leftReducer.apply(val1, val2)),
				(val1, val2) -> Either.left(rightReducer.apply(val1, val2)),
				(val1, val2) -> Either.left(defaultSupplier.get()));

		// We went all left, so we know its gonna be left
		Optional<T> left = mergedResult.getLeft();
		return left.orElse(null);
	}

	public static <L, R, NL, NR> Either<NL, NR> merge(Either<L, R> first, Either<L, R> second,
	                                                  BiFunction<L, L, Either<NL, NR>> leftHandler, BiFunction<R, R, Either<NL, NR>> rightHandler, BiFunction<L, R, Either<NL, NR>> mismatchHandler) {
		return first.either(
				left ->
						second.either(
								match -> leftHandler.apply(left, match),
								mismatch -> mismatchHandler.apply(left, mismatch)),
				right ->
						second.either(
								mismatch -> mismatchHandler.apply(mismatch, right),
								match -> rightHandler.apply(right, match)));
	}

	public static <L, R, T> Either<L, R> build(T val, Predicate<T> predicate, Function<T, L> lFunc, Function<T, R> rFunc) {
		if(predicate.test(val)) {
			return Either.left(lFunc.apply(val));
		}
		else {
			return Either.right(rFunc.apply(val));
		}
	}

	private final Optional<L> left;
	private final Optional<R> right;

	private Either(Optional<L> l, Optional<R> r) {
		Preconditions.checkNotNull(l);
		Preconditions.checkNotNull(r);

		left = l;
		right = r;
	}

	public <T> T either(
			Function<? super L, ? extends T> lFunc,
			Function<? super R, ? extends T> rFunc) {
		Optional<T> opt = left.map(lFunc);
		if(opt.isPresent()) {
			return opt.get();
		} else {
			Optional<T> rightMap = right.map(rFunc);
			if (rightMap.isPresent()) {
				return rightMap.get();
			}
			throw new IllegalStateException();
		}
	}

	public <NL, NR> Either<NL, NR> map(
			Function<? super L, ? extends NL> lFunc,
			Function<? super R, ? extends NR> rFunc) {
		if(left.isPresent()) {
			return Either.left(lFunc.apply(left.get()));
		}
		else if (right.isPresent()){
			return Either.right(rFunc.apply(right.get()));
		} else {
			throw new IllegalStateException();
		}
	}

	public <T> Either<T, R> mapLeft(Function<? super L, ? extends T> lFunc) {
		return new Either<>(left.map(lFunc), right);
	}

	public <T> Either<L, T> mapRight(Function<? super R, ? extends T> rFunc) {
		return new Either<>(left, right.map(rFunc));
	}

	public void apply(Consumer<? super L> lFunc, Consumer<? super R> rFunc) {
		left.ifPresent(lFunc);
		right.ifPresent(rFunc);
	}

	public boolean isLeft() {
		return getLeft().isPresent();
	}

	public boolean isRight() {
		return getRight().isPresent();
	}

	public Optional<L> getLeft() {
		return left;
	}

	public Optional<R> getRight() {
		return right;
	}
}