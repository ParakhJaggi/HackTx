package hacktx.site.endpoint;

import java.util.Optional;

import hacktx.site.common.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

	@GetMapping(value = "/get_balance")
	public double getBalance() {
		String principal = SecurityContextHolder.getContext().getAuthentication().getName();
		UserDto user = userService.findUserByPrincipal(principal).get();

		return user.getBalance();
	}

	@PostMapping(value = "/update_balance/{balance}")
	public UserDto updateBalance(double balance) {
		String principal = SecurityContextHolder.getContext().getAuthentication().getName();
		UserDto user = userService.findUserByPrincipal(principal).get();
		user.setBalance(balance);
		userService.updateBalance(user);


		return user;
	}

}