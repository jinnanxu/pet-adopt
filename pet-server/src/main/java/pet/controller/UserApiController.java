package pet.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import com.alibaba.fastjson.JSONObject;

import pet.controller.vo.JsonResult;
import pet.dao.mapper.MessageMapper;
import pet.dao.mapper.PetMapper;
import pet.dao.mapper.UserMapper;
import pet.dao.model.Message;
import pet.dao.model.PetNotice;
import pet.dao.model.User;

@RequestMapping("/user")
@RestController
public class UserApiController extends ApiBaseController{
	
	@Autowired
	private UserMapper userMapper;
	@Autowired
	private PetMapper petMapper;
	@Autowired
	private MessageMapper msgMapper;

	@InitBinder
    public void initBinder(WebDataBinder binder, WebRequest request) {
        //转换日期格式
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        //注册自定义的编辑器
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}
	
	@PostMapping("login")
	public JsonResult login(@RequestBody  JSONObject json, HttpSession session) {
		JsonResult result = new JsonResult();
		User record = new User(json.getString("mobile"), json.getString("pwd"));
		List users = userMapper.select(record);
		if(users == null || users.size() == 0) {
			result.setCode("PWD_ERROR");
		}else {
			session.setAttribute("CURR_USER", users.get(0));
			result.setData(users.get(0));
		}
		return result;
	}
	
	@GetMapping("logout")
	public JsonResult logout(HttpSession session) {
		session.removeAttribute("CURR_USER");
		return JsonResult.success();
	}
	
	@PostMapping("reg")
	public JsonResult reg(@RequestBody JSONObject req) {
		User user = JSONObject.parseObject(req.toJSONString(), User.class);
		System.out.println(req.toJSONString());
		try {
			userMapper.insertSelective(user);
			return new JsonResult("0", "注册成功");
		} catch (Exception e) {
			e.printStackTrace();
			return new JsonResult("REG_ERROR", "用户注册失败");
		}
	}
	
	/**
	 * 我收到的消息
	 * @param session
	 * @return
	 */
	@GetMapping("myMsg")
	public JsonResult myMsg(HttpSession session) {
		User user = getCurrUser(session);
		List<Message> list = msgMapper.selectMyMsg(user.getUserId());
		JsonResult json = new JsonResult();
		json.setData(list);
		return json;
	}
	
	@GetMapping("loadNewMsg")
	public JsonResult loadNewMsg(HttpSession session) {
		User user = getCurrUser(session);
		Message msg = new Message();
		msg.setUserId(user.getUserId());
		msg.setIsRead(0);
		int count = msgMapper.selectCount(msg);
		JsonResult json = new JsonResult();
		json.setData(count);
		return json;
	}
	
	@GetMapping("msgDetail")
	public JsonResult msgDetail(int mid) {
		Message msg = msgMapper.selectByPrimaryKey(mid);
		msg.setIsRead(1);
		msgMapper.updateByPrimaryKeySelective(msg);
		JsonResult json = new JsonResult();
		json.setData(msg);
		return json;
	}
	
	@PostMapping("validate")
	public JsonResult validate(@RequestBody JSONObject req, HttpSession session) {
		User user = getCurrUser(session);
		user.setRealName(req.getString("realName"));
		user.setImg(req.getString("url"));
		user.setIsValidate(2);
		userMapper.updateByPrimaryKeySelective(user);
		return JsonResult.success();
	}
	
}
