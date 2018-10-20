package alloy.elasticsearch;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.support.WriteRequest.RefreshPolicy;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.engine.Engine.Delete;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import alloy.util.Identifiable;
import alloy.util.Json;
import alloy.util.Momento;
import alloy.util.Momento.Momentizer;
import alloy.util.Serializer;
import alloy.util._Exceptions;
import alloy.util._Lists;
import petfinder.site.common.user.UserDto;

/**
 * Created by jlutteringer on 1/15/18.
 */
public class ElasticSearchIndex {
	private ElasticSearchClientProvider elasticSearchClientProvider;
	private String indexName;

	public ElasticSearchIndex(ElasticSearchClientProvider elasticSearchClientProvider, String indexName) {
		this.elasticSearchClientProvider = elasticSearchClientProvider;
		this.indexName = indexName;
	}

	public <T> Optional<T> find(Object key, Serializer<T> serializer) {
		GetRequest request = new GetRequest(indexName, "doc", key.toString());
		GetResponse response = _Exceptions.propagate(() -> elasticSearchClientProvider.getClient().get(request));
		if (!response.isExists()) {
			return Optional.empty();
		}

		return Optional.of(serializer.deserialize(response.getSourceAsMap()));
	}

	public <T> void save(T document, Serializer<T> serializer, Momentizer<T, String> momentizer) {
		this.save(_Lists.list(document), serializer, momentizer);
	}

	public <T> void save(Collection<T> documents, Serializer<T> serializer, Momentizer<T, String> momentizer) {
		documents.forEach(document -> {
			IndexRequest request = new IndexRequest(indexName, "doc", momentizer.getMomento(document));
			// JOHN this should be configurable somehow
			request.setRefreshPolicy(RefreshPolicy.WAIT_UNTIL);

			Map<String, Object> serializedDocument = serializer.serialize(document);
			String stringifiedDocument = Json.marshall(serializedDocument);
			request.source(stringifiedDocument, XContentType.JSON);

			_Exceptions.propagate(() -> elasticSearchClientProvider.getClient().index(request));
		});
	}

	public <T> List<T> search(SearchSourceBuilder searchSource, Serializer<T> serializer) {
		SearchRequest searchRequest = new SearchRequest(indexName);
		searchRequest.types("doc");
		searchRequest.source(searchSource);

		List<T> results = _Lists.mutableList();
		for(SearchHit hit: _Exceptions.propagate(() -> elasticSearchClientProvider.getClient().search(searchRequest)).getHits()) {
			results.add(serializer.deserialize(hit.getSourceAsMap()));
		}

		return results;
	}

	public void delete(Object key) {
		DeleteRequest request = new DeleteRequest(indexName, "doc", key.toString());
		_Exceptions.propagate(() -> elasticSearchClientProvider.getClient().delete(request));
	}
}