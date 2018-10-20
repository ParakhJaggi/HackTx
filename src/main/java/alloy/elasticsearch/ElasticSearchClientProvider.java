package alloy.elasticsearch;

import org.elasticsearch.client.RestHighLevelClient;

/**
 * Created by jlutteringer on 1/15/18.
 */
public interface ElasticSearchClientProvider {
	RestHighLevelClient getClient();
}