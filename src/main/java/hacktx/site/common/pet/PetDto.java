package petfinder.site.common.pet;

import java.util.UUID;

import alloy.util.Identifiable;

/**
 * Created by jlutteringer on 8/23/17.
 */
public class PetDto implements Identifiable {
	private Long id;
	private String name;
	private String type;

	public PetDto() {
		this.id = UUID.randomUUID().getMostSignificantBits();
	}

	@Override
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}