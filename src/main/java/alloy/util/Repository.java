package alloy.util;

import java.util.Optional;

import petfinder.site.common.user.UserAuthenticationDto;

/**
 * Created by jlutteringer on 1/16/18.
 */
public interface Repository<T, I> {
	Optional<T> find(I id);

	T save(T item);
}