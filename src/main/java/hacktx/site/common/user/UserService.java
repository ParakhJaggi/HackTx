package hacktx.site.common.user;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import alloy.util._Lists;

/**
 * Created by jlutteringer on 8/23/17.
 */
@Service
public class UserService {
	@Autowired
	private UserDao userDao;

	@Autowired
	private PasswordEncoder passwordEncoder;

	public Optional<UserDto> findUserByPrincipal(String principal) {
		return userDao.findUserByPrincipal(principal).map(UserAuthenticationDto::getUser);
	}

	public Optional<UserAuthenticationDto> findUserAuthenticationByPrincipal(String principal) {
		return userDao.findUserByPrincipal(principal);
	}

	public static class RegistrationRequest {
		private String principal;
		private String password;
		private Double balance;

		public String getPrincipal() {
			return principal;
		}

		public void setPrincipal(String principal) {
			this.principal = principal;
		}

		public String getPassword() {
			return password;
		}

		public void setPassword(String password) {
			this.password = password;
		}

		public Double getBalance() {
			return balance;
		}

		public void setBalance(Double balance) {
			this.balance = balance;
		}
	}

	public UserDto register(RegistrationRequest request) {
		UserAuthenticationDto userAuthentication = new UserAuthenticationDto(
				new UserDto(request.getPrincipal(), request.getBalance()), passwordEncoder.encode(request.getPassword()));

		userDao.save(userAuthentication);
		return userAuthentication.getUser();
	}



	public void updateBalance(UserDto user){
		userDao.save(new UserAuthenticationDto(user, null));
	}
}