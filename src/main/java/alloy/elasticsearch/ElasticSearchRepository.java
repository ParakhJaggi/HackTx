package alloy.elasticsearch;

import java.util.List;
import java.util.Optional;
import java.util.stream.DoubleStream;
import java.util.stream.Stream;

import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

import alloy.util.Momento;
import alloy.util.Momento.Momentizer;
import alloy.util.Repository;
import alloy.util.Serializer;
import alloy.util.TypeConverters;
import petfinder.site.common.user.UserAuthenticationDto;

/**
 * Created by jlutteringer on 1/16/18.
 */
public class ElasticSearchRepository<T, I> implements Repository<T, I>  {
	private ElasticSearchIndex index;
	private Serializer<T> serializer;
	private Momentizer<T, I> momentizer;

	public ElasticSearchRepository(ElasticSearchIndex index, Serializer<T> serializer, Momentizer<T, I> momentizer) {
		this.index = index;
		this.serializer = serializer;
		this.momentizer = momentizer;
	}

	@Override
	public Optional<T> find(I id) {
		return index.find(id, serializer);
	}

	@Override
	public T save(T item) {
		// FUTURE we shouldn't assume you can just toString the id of the element
		index.save(item, serializer, Momento.stringMomentizer(momentizer));
		return item;
	}

	public void delete(I id) {
		index.delete(id);
	}

	public List<T> search(SearchSourceBuilder searchSource) {
		return index.search(searchSource, serializer);
	}

	public static class ElasticSearchMomentoRepository<T extends Momento<I>, I> extends ElasticSearchRepository<T, I> {
		public ElasticSearchMomentoRepository(ElasticSearchIndex index, Serializer<T> serializer) {
			super(index, serializer, Momento::getMomento);
		}
	}

	public static class ElasticSearchJsonRepository<T extends Momento<I>, I> extends ElasticSearchMomentoRepository<T, I> {
		public ElasticSearchJsonRepository(ElasticSearchIndex index, Class<T> type) {
			super(index, TypeConverters.serializer(type));
		}
	}
}