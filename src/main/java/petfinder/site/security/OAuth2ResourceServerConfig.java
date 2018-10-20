package petfinder.site.security;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.web.util.matcher.RequestMatcher;

@Configuration
@EnableResourceServer
public class OAuth2ResourceServerConfig extends ResourceServerConfigurerAdapter {
	@Value("${security.oauth2.resource.id}")
	private String resourceId;

	@Autowired
	private DefaultTokenServices tokenServices;

	@Autowired
	private TokenStore tokenStore;

	// To allow the rResourceServerConfigurerAdapter to understand the token,
	// it must share the same characteristics with AuthorizationServerConfigurerAdapter.
	// So, we must wire it up the beans in the ResourceServerSecurityConfigurer.
	@Override
	public void configure(ResourceServerSecurityConfigurer resources) {
		resources
				.resourceId(resourceId)
				.tokenServices(tokenServices)
				.tokenStore(tokenStore);
	}

	@Override
	public void configure(HttpSecurity http) throws Exception {
		http
				.requestMatcher(new OAuthRequestMatcher())
				.csrf().disable()
				.anonymous().disable()
				.authorizeRequests()
				.antMatchers(HttpMethod.OPTIONS).permitAll()
				.antMatchers("/api/**").authenticated();
	}

	private static class OAuthRequestMatcher implements RequestMatcher {
		public boolean matches(HttpServletRequest request) {
			// Determine if the resource called is "/api/**"
			String path = request.getServletPath();

			if(request.getMethod().equals(HttpMethod.OPTIONS.toString())) {
				return false;
			}

			if(path.contains("/api/user/register")) {
				return false;
			}

			if ( path.length() >= 5 ) {
				path = path.substring(0, 5);
				return path.equals("/api/");
			} else {
				return false;
			}
		}
	}
}