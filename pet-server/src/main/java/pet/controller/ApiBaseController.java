package pet.controller;

import javax.servlet.http.HttpSession;

import pet.dao.model.User;

public class ApiBaseController {

	public User getCurrUser(HttpSession session) {
		if(session.getAttribute("CURR_USER")!=null) {
			User user = (User)session.getAttribute("CURR_USER");
			System.out.println("当前登录用户："+user.getNickName());
			return user;
		}else {
			return null;
		}
	}
	
	public void updateCurrUser(HttpSession session, User user) {
		session.setAttribute("CURR_USER", user);
	}
}
