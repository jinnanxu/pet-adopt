package pet.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import pet.controller.vo.AccVo;
import pet.controller.vo.AdoptVo;
import pet.controller.vo.BlogVo;
import pet.controller.vo.PetVo;
import pet.dao.mapper.AccusationMapper;
import pet.dao.mapper.AdoptMapper;
import pet.dao.mapper.BlogMapper;
import pet.dao.mapper.MessageMapper;
import pet.dao.mapper.PetMapper;
import pet.dao.mapper.UserMapper;
import pet.dao.model.Accusation;
import pet.dao.model.Adopt;
import pet.dao.model.Blog;
import pet.dao.model.Message;
import pet.dao.model.Pet;
import pet.dao.model.User;
import pet.utils.PageView;

@Controller
@RequestMapping("/admin")
public class AdminController {
	
	@Autowired
	private UserMapper userMapper;
	@Autowired
	private PetMapper petMapper;
	@Autowired
	private AccusationMapper accMapper;
	@Autowired
	private AdoptMapper adoptMapper;
	@Autowired
	private BlogMapper blogMapper;
	@Autowired
	private MessageMapper msgMapper;
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");

	@RequestMapping("login")
	public String loginPage() {
		return "admin/login";
	}
	
	@RequestMapping("logout")
	public String logOut(HttpSession session) {
		session.removeAttribute("currAdmin");
		return "admin/login";
	}
	
	@RequestMapping("login-form")
	public ModelAndView loginForm(String username, String pwd, HttpSession session) {
		ModelAndView mav = new ModelAndView("redirect:home");
		if(username.equals("admin") && pwd.equals("123456")) {
			session.setAttribute("currAdmin", "admin");
		}else {
			mav.addObject("msg", "用户名或密码错误");
			mav.setViewName("admin/login");
		}
		return mav;
	}
	
	@RequestMapping("home")
	public String home() {
		return "admin/home";
	}
	
	@InitBinder
    public void initBinder(WebDataBinder binder, WebRequest request) {
        //转换日期格式
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        //注册自定义的编辑器
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}
	
	/* *********************用户信息管理**************************/
	@RequestMapping("toadduser")
	public String toAddUser() {
		return "admin/user-add";
	}
	
	@RequestMapping("saveUser")
	public String saveUser(User user) {
		userMapper.insert(user);
		return "redirect:usermgr";
	}
	
	@RequestMapping("usermgr")
	public ModelAndView userList(PageView<User> page) {
		ModelAndView mav = new ModelAndView("admin/user-list");
		List<User> list = userMapper.selectByRowBounds(new User(), new RowBounds(page.getFirstResult(), page.getMaxresult())); //userService.findUsers(page.getFirstResult(), page.getMaxresult());
		page.setRecords(list);
		page.setTotalrecord(userMapper.selectCount(new User()));
		mav.addObject("page", page);
		return mav;
	}
	
	@RequestMapping("toupdateuser")
	public ModelAndView toUserModify(Integer uid) {
		ModelAndView mav = new ModelAndView("admin/user-validate");
		User user = userMapper.selectByPrimaryKey(uid);
		mav.addObject("user", user);
		return mav;
	}
	
	@RequestMapping("updateuser")
	public String updateUser(User user) {
		userMapper.updateByPrimaryKey(user);
		return "redirect:usermgr";
	}
	
	@RequestMapping("validateUser")
	public String validateUser(Integer validate, Integer userid) {
		User user = userMapper.selectByPrimaryKey(userid);
		user.setIsValidate(validate);
		userMapper.updateByPrimaryKey(user);
		return "redirect:usermgr";
	}
	

	/**
	 * 解除黑名单
	 * @param userId
	 * @return
	 */
	@RequestMapping("updateuser2")
	public String updateUser2(Integer userId) {
		User user = userMapper.selectByPrimaryKey(userId);
		int orgStatus = user.getIsValidate() - 4;
		user.setIsValidate(orgStatus);
		userMapper.updateByPrimaryKeySelective(user);
		return "redirect:usermgr";
	}
	
	/**
	 * 注销用户
	 * @param uid
	 * @return
	 */
	@RequestMapping("deleteuser")
	public String deleteUser(Integer uid) {
		User user = userMapper.selectByPrimaryKey(uid);
		userMapper.updateByPrimaryKeySelective(user);
		return "redirect:usermgr";
	}
	/* *********************用户信息管理END**************************/

	@RequestMapping("petmgr")
	public ModelAndView petList(PageView<PetVo> page) {
		ModelAndView mav = new ModelAndView("admin/pet-list");
		List<Pet> list = petMapper.selectByRowBounds(new Pet(), new RowBounds(page.getFirstResult(), page.getMaxresult())); //userService.findUsers(page.getFirstResult(), page.getMaxresult());
		List<PetVo> voList = new ArrayList<>();
		for (Pet pet : list) {
			PetVo vo = new PetVo();
			vo.setPet(pet);
			vo.setUser(userMapper.selectByPrimaryKey(pet.getUserId()));
			voList.add(vo);
		}
		page.setRecords(voList);
		page.setTotalrecord(petMapper.selectCount(new Pet()));
		mav.addObject("page", page);
		return mav;
	}
	
	@RequestMapping("updatepet")
	public String updatePet(Integer petid) {
		Pet pet = petMapper.selectByPrimaryKey(petid);
		pet.setStatus("已屏蔽");
		petMapper.updateByPrimaryKeySelective(pet);
		return "redirect:petmgr";
	}
	
	@RequestMapping("updatepet2")
	public String updatePet2(Integer petid) {
		Pet pet = petMapper.selectByPrimaryKey(petid);
		pet.setStatus("待领养");
		petMapper.updateByPrimaryKeySelective(pet);
		return "redirect:petmgr";
	}
	
	@RequestMapping("accmgr")
	public ModelAndView accList(PageView<AccVo> page) {
		ModelAndView mav = new ModelAndView("admin/acc-list");
		List<Accusation> list = accMapper.selectByRowBounds(new Accusation(), new RowBounds(page.getFirstResult(), page.getMaxresult())); //userService.findUsers(page.getFirstResult(), page.getMaxresult());
		List<AccVo> voList = new ArrayList<>();
		for (Accusation acc : list) {
			AccVo vo = new AccVo();
			vo.setAcc(acc);
			vo.setBeijbr(userMapper.selectByPrimaryKey(acc.getUserId()));
			vo.setJbr(userMapper.selectByPrimaryKey(acc.getAccusater()));
			vo.setPet(petMapper.selectByPrimaryKey(acc.getPetId()));
			voList.add(vo);
		}
		page.setRecords(voList);
		page.setTotalrecord(accMapper.selectCount(new Accusation()));
		mav.addObject("page", page);
		return mav;
	}
	
	@RequestMapping("toupdateacc")
	public ModelAndView toModifyAcc(Integer accid) {
		ModelAndView mav = new ModelAndView("admin/acc-modify");
		Accusation acc = accMapper.selectByPrimaryKey(accid);
		AccVo vo = new AccVo();
		vo.setAcc(acc);
		vo.setBeijbr(userMapper.selectByPrimaryKey(acc.getUserId()));
		vo.setJbr(userMapper.selectByPrimaryKey(acc.getAccusater()));
		vo.setPet(petMapper.selectByPrimaryKey(acc.getPetId()));
		mav.addObject("acc", vo);
		return mav;
	}
	
	@RequestMapping("updateAcc")
	public String updateAcc(Integer accid, String detail, Integer isbl) {
		Accusation acc = accMapper.selectByPrimaryKey(accid);
		Pet pet = petMapper.selectByPrimaryKey(acc.getPetId());
		acc.setStatus("已处理");
		//分别发送通知举报人与被举报人
		Message msg = new Message();
		msg.setContent("您好，您于"+sdf.format(acc.getAccTime())+"举报的宠物领养信息\""+pet.getTitle()+"\"，管理员已经处理，处理结果："+detail);
		msg.setIsRead(0);
		msg.setSender("SYS");
		msg.setSendTime(new Date());
		msg.setTitle("系统消息：举报反馈");
		msg.setUserId(acc.getAccusater());
		msgMapper.insertSelective(msg);
		
		Message msg2 = new Message();
		msg2.setContent("您好，您于"+sdf.format(pet.getPublishTime())+"发布的宠物领养信息\""+pet.getTitle()+"\"，被举报，管理员处理结果为："+detail);
		msg2.setIsRead(0);
		msg2.setSender("SYS");
		msg2.setSendTime(new Date());
		msg2.setTitle("系统消息：举报反馈");
		msg2.setUserId(acc.getUserId());
		msgMapper.insertSelective(msg2);
		
		//拉黑
		if(isbl == 1) {
			User bjbr = userMapper.selectByPrimaryKey(acc.getUserId());
			Integer newStatus = bjbr.getIsValidate() + 4;
			bjbr.setIsValidate(newStatus );
			userMapper.updateByPrimaryKeySelective(bjbr);
		}
		
		accMapper.updateByPrimaryKeySelective(acc);
		
		return "redirect:accmgr";
	}
	
	/**
	 * 领养记录列表
	 * @param page
	 * @return
	 */
	@RequestMapping("adoptmgr")
	public ModelAndView adoptmgr(PageView<AdoptVo> page) {
		ModelAndView mav = new ModelAndView("admin/adopt-list");
		List<Adopt> list = adoptMapper.selectByRowBounds(new Adopt(), new RowBounds(page.getFirstResult(), page.getMaxresult())); //userService.findUsers(page.getFirstResult(), page.getMaxresult());
		List<AdoptVo> voList = new ArrayList<>();
		for (Adopt adp : list) {
			AdoptVo vo = new AdoptVo();
			vo.setAdopt(adp);
			vo.setAdopter(userMapper.selectByPrimaryKey(adp.getAdoptUser()));
			vo.setPublisher(userMapper.selectByPrimaryKey(adp.getPublishUser()));
			vo.setPet(petMapper.selectByPrimaryKey(adp.getPetId()));
			voList.add(vo);
		}
		page.setRecords(voList);
		page.setTotalrecord(adoptMapper.selectCount(new Adopt()));
		mav.addObject("page", page);
		return mav;
	}
	
	/**
	 * 动态信息管理 
	 * @param page
	 * @return
	 */
	@RequestMapping("blogmgr")
	public ModelAndView blogMgr(PageView<BlogVo> page) {
		ModelAndView mav = new ModelAndView("admin/blog-list");
		List<Blog> list = blogMapper.selectByRowBounds(new Blog(), new RowBounds(page.getFirstResult(), page.getMaxresult())); //userService.findUsers(page.getFirstResult(), page.getMaxresult());
		List<BlogVo> voList = new ArrayList<>();
		for (Blog blog : list) {
			BlogVo vo = new BlogVo();
			vo.setPet(petMapper.selectByPrimaryKey(blog.getPetId()));
			vo.setUser(userMapper.selectByPrimaryKey(blog.getUserId()));
			vo.setBlog(blog);
			voList.add(vo);
		}
		page.setRecords(voList);
		page.setTotalrecord(blogMapper.selectCount(new Blog()));
		mav.addObject("page", page);
		return mav;
	}
	
	@RequestMapping("deleteblog")
	public String updateBlog(Integer bid) {
		blogMapper.deleteByPrimaryKey(bid);
		return "redirect:blogmgr";
	}
}
