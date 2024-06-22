package pet.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import pet.dao.model.Pet;
import pet.utils.MyMapper;

public interface PetMapper extends MyMapper<Pet> {
	
	List<Pet> selectIndexPets();
	
	List<Pet> selectMyAdoptPets(@Param("userId") Integer userId);
}