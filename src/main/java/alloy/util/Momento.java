package alloy.util;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;
import java.util.stream.Stream;

import org.codehaus.jackson.annotate.JsonIgnore;

/**
 * Created by jlutteringer on 1/15/18.
 */
public interface Momento<T> extends Serializable {
	T getMomento();

	static <T, R extends Momento<T>> Map<T, R> mapify(Collection<R> list) {
		return mapify(list.stream());
	}

	static <T, R extends Momento<T>> Map<T, R> mapify(Stream<R> stream) {
		return stream.map(element -> Tuple.pair(element.getMomento(), element)).collect(_Collectors.toMap());
	}

	static <T, I> Momentizer<T, String> stringMomentizer(Momentizer<T, I> momentizer) {
		return object -> momentizer.getMomento(object).toString();
	}

	interface Momentizer<O, T> {
		T getMomento(O object);
	}
}