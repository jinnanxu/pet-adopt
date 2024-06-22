package pet.controller.vo;

import java.util.Date;

import pet.dao.model.Blog;
import pet.dao.model.Pet;
import pet.dao.model.User;

public class BlogVo {

	private Blog blog;
	private Pet pet;
	private User user;
	private Date adoptDate;
	private String adoptDateStr;
	
	public Blog getBlog() {
		return blog;
	}
	public void setBlog(Blog blog) {
		this.blog = blog;
	}
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
	public Date getAdoptDate() {
		return adoptDate;
	}
	public void setAdoptDate(Date adoptDate) {
		this.adoptDate = adoptDate;
	}
	public String getAdoptDateStr() {
		return adoptDateStr;
	}
	public void setAdoptDateStr(String adoptDateStr) {
		this.adoptDateStr = adoptDateStr;
	}
	
}
