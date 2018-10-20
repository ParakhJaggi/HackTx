package petfinder.site.elasticsearch;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import alloy.elasticsearch.ElasticSearchClientProvider;
import alloy.elasticsearch.ElasticSearchIndex;
import alloy.elasticsearch.ElasticSearchRepository.ElasticSearchJsonRepository;
import petfinder.site.common.user.UserAuthenticationDto;
import petfinder.site.common.user.UserPetDto;

@Service
public class UserPetElasticsearchRepository extends ElasticSearchJsonRepository<UserPetDto, Long> {
	@Autowired
	public UserPetElasticsearchRepository(ElasticSearchClientProvider provider) {
		super(new ElasticSearchIndex(provider, "petfinder-user-pets"), UserPetDto.class);
	}
}