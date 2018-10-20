package petfinder.site.common.user;

import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import alloy.util.AlloyAuthentication;
import alloy.util.Wait;
import alloy.util._Lists;
import alloy.util._Maps;
import petfinder.site.common.pet.PetDto;
import petfinder.site.common.user.UserDto.UserType;

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
		private String myNewField;
		private Map<String, Object> attributes;

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

		public Map<String, Object> getAttributes() {
			return attributes;
		}

		public void setAttributes(Map<String, Object> attributes) {
			this.attributes = attributes;
		}

		public String getMyNewField() {
			return myNewField;
		}

		public void setMyNewField(String myNewField) {
			this.myNewField = myNewField;
		}
	}

	public UserDto register(RegistrationRequest request) {
		UserAuthenticationDto userAuthentication = new UserAuthenticationDto(
				new UserDto(request.getPrincipal(), _Lists.list("ROLE_USER"), UserType.OWNER, request.getAttributes()), passwordEncoder.encode(request.getPassword()));

		userDao.save(userAuthentication);
		return userAuthentication.getUser();
	}

	public UserPetDto save(UserPetDto userPetDto) {
		return userDao.save(userPetDto);
	}

	public List<PetDto> findPets(UserDto user) {
		return userDao.findPets(user);
	}
}