package pet.controller.vo;

import pet.dao.model.Pet;
import pet.dao.model.User;

public class PetVo {

	private Pet pet;
	private User user;
	
	public Pet getPet() {
		return pet;
	}
	public void setPet(Pet pet) {
		this.pet = pet;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	
}
