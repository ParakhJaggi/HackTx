package alloy.util;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map.Entry;

/**
 * Created by jlutteringer on 1/15/18.
 */
public interface Tuple {
	static <V1> Single<V1> single(V1 v1) {
		return of(v1);
	}

	static <V1, V2> Pair<V1, V2> pair(V1 v1, V2 v2) {
		return of(v1, v2);
	}

	static <V1, V2> Pair<V1, V2> pair(Entry<V1, V2> entry) {
		return of(entry.getKey(), entry.getValue());
	}

	static <V1, V2, V3> Triple<V1, V2, V3> triple(V1 v1, V2 v2, V3 v3) {
		return of(v1, v2, v3);
	}

	static <V1> Tuple1<V1> of(V1 v1) {
		return new Tuple1<>(v1);
	}

	static <V1, V2> Tuple2<V1, V2> of(V1 v1, V2 v2) {
		return new Tuple2<>(v1, v2);
	}

	static <V1, V2, V3> Tuple3<V1, V2, V3> of(V1 v1, V2 v2, V3 v3) {
		return new Tuple3<>(v1, v2, v3);
	}

	// Alias For Tuple1
	interface Single<V1> extends Iterable<Object> {
		V1 getFirst();
	}

	class Tuple1<V1> implements Single<V1>, Serializable {
		private static final long serialVersionUID = 4006475675164664408L;
		public final V1 _1;

		public Tuple1(V1 v1) {
			_1 = v1;
		}

		public V1 getFirst() {
			return _1;
		}

		@Override
		public String toString() {
			return String.format("Tuple1(%s)", _1);
		}

		@Override
		public Iterator<Object> iterator() {
			return Arrays.<Object> asList(_1).iterator();
		};

		@Override
		public boolean equals(Object o) {
			if (this == o) {
				return true;
			}
			if (o == null || getClass() != o.getClass()) {
				return false;
			}

			Tuple1<?> tuple1 = (Tuple1<?>) o;

			return _1 != null ? _1.equals(tuple1._1) : tuple1._1 == null;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 0;
			result = prime * result + ((getFirst() == null) ? 0 : getFirst().hashCode());
			return result;
		}
	}

	// Alias for Tuple2
	interface Pair<V1, V2> extends Single<V1>, Serializable {
		V2 getSecond();
	}

	class Tuple2<V1, V2> extends Tuple1<V1> implements Pair<V1, V2> {
		private static final long serialVersionUID = 7567525698416053358L;
		public final V2 _2;

		public Tuple2(V1 v1, V2 v2) {
			super(v1);
			_2 = v2;
		}

		public V2 getSecond() {
			return _2;
		}

		@Override
		public String toString() {
			return String.format("Tuple2(%s,%s)", _1, _2);
		}

		@Override
		public Iterator<Object> iterator() {
			return Arrays.asList(_1, _2).iterator();
		};

		@Override
		public boolean equals(Object o) {
			if (this == o) {
				return true;
			}
			if (o == null || getClass() != o.getClass()) {
				return false;
			}
			if (!super.equals(o)) {
				return false;
			}

			Tuple2<?, ?> tuple2 = (Tuple2<?, ?>) o;

			return _2 != null ? _2.equals(tuple2._2) : tuple2._2 == null;

		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = super.hashCode();
			result = prime * result + ((getSecond() == null) ? 0 : getSecond().hashCode());
			return result;
		}
	}

	// Alias for Tuple3
	interface Triple<V1, V2, V3> extends Pair<V1, V2>, Serializable {
		V3 getThird();
	}

	class Tuple3<V1, V2, V3> extends Tuple2<V1, V2> implements Triple<V1, V2, V3> {
		private static final long serialVersionUID = -6298410011629728265L;
		public final V3 _3;

		public Tuple3(V1 v1, V2 v2, V3 v3) {
			super(v1, v2);
			_3 = v3;
		}

		public V3 getThird() {
			return _3;
		}

		@Override
		public String toString() {
			return String.format("Tuple3(%s,%s,%s)", _1, _2, _3);
		};

		@Override
		public Iterator<Object> iterator() {
			return Arrays.<Object> asList(_1, _2, _3).iterator();
		};

		@Override
		public boolean equals(Object o) {
			if (this == o) {
				return true;
			}
			if (o == null || getClass() != o.getClass()) {
				return false;
			}
			if (!super.equals(o)) {
				return false;
			}

			Tuple3<?, ?, ?> tuple3 = (Tuple3<?, ?, ?>) o;

			return _3 != null ? _3.equals(tuple3._3) : tuple3._3 == null;

		}

		@Override
		public int hashCode() {
			int result = super.hashCode();
			result = 31 * result + (_3 != null ? _3.hashCode() : 0);
			return result;
		}
	}
}