package pet.controller.vo;

import pet.dao.model.Adopt;
import pet.dao.model.Pet;
import pet.dao.model.User;

public class AdoptVo {

	private Adopt adopt;
	private Pet pet;
	private User adopter;
	private User publisher;
	
	public Adopt getAdopt() {
		return adopt;
	}
	public void setAdopt(Adopt adopt) {
		this.adopt = adopt;
	}
	public Pet getPet() {
		return pet;
	}
	public void setPet(Pet pet) {
		this.pet = pet;
	}
	
	public User getAdopter() {
		return adopter;
	}
	public void setAdopter(User adopter) {
		this.adopter = adopter;
	}
	public User getPublisher() {
		return publisher;
	}
	public void setPublisher(User publisher) {
		this.publisher = publisher;
	}
	
}
