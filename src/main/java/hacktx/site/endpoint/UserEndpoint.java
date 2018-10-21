package hacktx.site.endpoint;

import java.util.Optional;

import hacktx.site.common.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import hacktx.site.common.user.UserDto;

/**
 * Created by jlutteringer on 8/23/17.
 */
@RestController
@RequestMapping(value = "/api/user")
public class UserEndpoint {
	@Autowired
	private UserService userService;

	@GetMapping(value = "", produces = "application/json")
	public Optional<UserDto> getUserDetails() {
		String principal = SecurityContextHolder.getContext().getAuthentication().getName();
		return userService.findUserByPrincipal(principal);
	}

	@PostMapping(value = "/register")
	public UserDto register(@RequestBody UserService.RegistrationRequest request) {
		return userService.register(request);
	}

	@PostMapping(value = "/update_balance/{balance}")
	public UserDto updateBalance(@PathVariable("balance") Double val) {
		String principal = SecurityContextHolder.getContext().getAuthentication().getName();
		UserDto user = userService.findUserByPrincipal(principal).get();
		user.setBalance(user.getBalance() + val);
		userService.updateBalance(user);
		return user;
	}

}