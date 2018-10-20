package hacktx.site.common.user;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.annotate.JsonIgnore;

import alloy.util.Momento;

/**
 * Created by jlutteringer on 8/23/17.
 */
public class UserDto implements Momento<String> {
	private String principal;

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	double balance;


	private UserDto() {

	}

	public UserDto(String principal) {
		this.principal = principal;

	}

	public UserDto(String principal, List<String> roles) {
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

    public List<String> getRoles() {
	    return new ArrayList<String>();
    }

    public void setRoles(List<String> roles) {
    }

    public enum UserType {
		OWNER, SITTER
	}

}