package hacktx.site.common.user;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import hacktx.site.elasticsearch.UserElasticSearchRepository;
import hacktx.site.elasticsearch.UserPetElasticsearchRepository;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * Created by jlutteringer on 8/23/17.
 */
@Repository
public class UserDao {
	@Autowired
	private UserElasticSearchRepository userRepository;

	@Autowired
	private UserPetElasticsearchRepository userPetRepository;



	public Optional<UserAuthenticationDto> findUserByPrincipal(String principal) {
		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

		String queryString = String.format("user.principal=\"%s\"", principal.replace("\"", ""));
		searchSourceBuilder.query(QueryBuilders.queryStringQuery(queryString));

		return userRepository.search(searchSourceBuilder).stream().findFirst();
	}

	public void save(UserAuthenticationDto userAuthentication) {

		userRepository.save(userAuthentication);
	}



	public UserPetDto save(UserPetDto userPetDto) {
		return userPetRepository.save(userPetDto);
	}
}