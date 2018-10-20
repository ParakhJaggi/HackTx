package alloy.util;

import java.util.Optional;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * Created by jlutteringer on 1/15/18.
 */
public class AlloyAuthentication {
	private String principal;
	private String password;

	public AlloyAuthentication(String principal, String password) {
		this.principal = principal;
		this.password = password;
	}

	public String getPrincipal() {
		return principal;
	}

	public String getPassword() {
		return password;
	}
}