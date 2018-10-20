package hacktx.site.common.user;

import java.util.List;
import java.util.Map;

import org.codehaus.jackson.annotate.JsonIgnore;

import alloy.util.Momento;

/**
 * Created by jlutteringer on 8/23/17.
 */
public class UserDto implements Momento<String> {
	private String principal;

	double balance;


	private UserDto() {

	}

	public UserDto(String principal) {
		this.principal = principal;

	}

	public String getPrincipal() {
		return principal;
	}






	@JsonIgnore
	@Override
	public String getMomento() {
		return principal;
	}


}