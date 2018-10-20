package hacktx.site.elasticsearch;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import alloy.elasticsearch.ElasticSearchClientProvider;
import alloy.elasticsearch.ElasticSearchIndex;
import alloy.elasticsearch.ElasticSearchRepository.ElasticSearchJsonRepository;
import hacktx.site.common.user.UserAuthenticationDto;

/**
 * Created by jlutteringer on 1/16/18.
 */
@Service
public class UserElasticSearchRepository extends ElasticSearchJsonRepository<UserAuthenticationDto, String> {
	@Autowired
	public UserElasticSearchRepository(ElasticSearchClientProvider provider) {
		super(new ElasticSearchIndex(provider, "hacktx-users"), UserAuthenticationDto.class);
	}
}