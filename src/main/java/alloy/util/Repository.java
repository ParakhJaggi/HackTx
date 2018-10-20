package alloy.util;

import java.util.Optional;

/**
 * Created by jlutteringer on 1/16/18.
 */
public interface Repository<T, I> {
	Optional<T> find(I id);

	T save(T item);
}