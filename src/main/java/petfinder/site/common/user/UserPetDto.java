package petfinder.site.common.user;

import java.util.UUID;

import alloy.util.Identifiable;

public class UserPetDto implements Identifiable {
	private Long id = UUID.randomUUID().getMostSignificantBits();
	private String userPrincipal;
	private Long petId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUserPrincipal() {
		return userPrincipal;
	}

	public void setUserPrincipal(String userPrincipal) {
		this.userPrincipal = userPrincipal;
	}

	public Long getPetId() {
		return petId;
	}

	public void setPetId(Long petId) {
		this.petId = petId;
	}
}