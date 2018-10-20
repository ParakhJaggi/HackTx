package hacktx.site.security;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import hacktx.site.common.user.UserAuthenticationDto;
import hacktx.site.common.user.UserService;

/**
 * Created by jlutteringer on 1/15/18.
 */
@Service
public class HacktxUserDetailsService implements UserDetailsService {
	@Autowired
	private UserService userService;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserAuthenticationDto userAuthentication =
				userService.findUserAuthenticationByPrincipal(username).orElseThrow(() -> new UsernameNotFoundException(username + " not found"));

		return new User(
				userAuthentication.getUser().getPrincipal(),
				userAuthentication.getPassword(),
				userAuthentication.getUser().getRoles().stream()
						.map(SimpleGrantedAuthority::new)
						.collect(Collectors.toList()));
	}
}