package petfinder.site.common.pet;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by jlutteringer on 8/23/17.
 */
@Service
public class PetService {
	@Autowired
	private PetDao petDao;

	public Optional<PetDto> findPet(Long id) {
		return petDao.findPet(id);
	}

	public void save(PetDto pet) {
		petDao.save(pet);
	}
}