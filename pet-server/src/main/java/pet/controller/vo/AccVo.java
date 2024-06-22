package pet.controller.vo;

import pet.dao.model.Accusation;
import pet.dao.model.Pet;
import pet.dao.model.User;

public class AccVo {

	private Accusation acc;
	private User jbr;
	private User beijbr;
	private Pet pet;
	
	public Accusation getAcc() {
		return acc;
	}
	public void setAcc(Accusation acc) {
		this.acc = acc;
	}
	public User getJbr() {
		return jbr;
	}
	public void setJbr(User jbr) {
		this.jbr = jbr;
	}
	public User getBeijbr() {
		return beijbr;
	}
	public void setBeijbr(User beijbr) {
		this.beijbr = beijbr;
	}
	public Pet getPet() {
		return pet;
	}
	public void setPet(Pet pet) {
		this.pet = pet;
	}
	
}
