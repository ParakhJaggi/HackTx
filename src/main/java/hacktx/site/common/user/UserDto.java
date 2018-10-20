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
	private List<String> roles;
	private UserType type;
	private Map<String, Object> attributes;
	private String myNewField;

	private UserDto() {

	}

	public UserDto(String principal, List<String> roles, UserType type, Map<String, Object> attributes) {
		this.principal = principal;
		this.roles = roles;
		this.attributes = attributes;
	}

	public String getPrincipal() {
		return principal;
	}

	public List<String> getRoles() {
		return roles;
	}

	public Map<String, Object> getAttributes() {
		return attributes;
	}

	public UserType getType() {
		return type;
	}

	public String getMyNewField() {
		return myNewField;
	}

	public void setMyNewField(String myNewField) {
		this.myNewField = myNewField;
	}

	@JsonIgnore
	@Override
	public String getMomento() {
		return principal;
	}

	public enum UserType {
		OWNER, SITTER
	}
}