package hacktx.site.elasticsearch;

import hacktx.site.common.pet.PetDto;
import org.springframework.stereotype.Service;

import alloy.elasticsearch.ElasticSearchClientProvider;
import alloy.elasticsearch.ElasticSearchIndex;
import alloy.elasticsearch.ElasticSearchRepository.ElasticSearchJsonRepository;

/**
 * Created by jlutteringer on 2/7/18.
 */
@Service
public class PetElasticsearchRepository extends ElasticSearchJsonRepository<PetDto, Long> {
	public PetElasticsearchRepository(ElasticSearchClientProvider provider) {
		super(new ElasticSearchIndex(provider, "hacktx-pets"), PetDto.class);
	}
}