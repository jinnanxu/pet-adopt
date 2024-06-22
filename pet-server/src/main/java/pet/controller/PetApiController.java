package pet.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.time.DateUtils;
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

import pet.controller.vo.AdoptVo;
import pet.controller.vo.JsonResult;
import pet.dao.mapper.AccusationMapper;
import pet.dao.mapper.AdoptMapper;
import pet.dao.mapper.MessageMapper;
import pet.dao.mapper.PetMapper;
import pet.dao.mapper.UserMapper;
import pet.dao.model.Accusation;
import pet.dao.model.Adopt;
import pet.dao.model.Message;
import pet.dao.model.Pet;
import pet.dao.model.PetNotice;
import pet.dao.model.User;
import pet.utils.ImageRecognitionUtil;

@RequestMapping("/pet")
@RestController
public class PetApiController extends ApiBaseController {
	
	@Autowired
	private UserMapper userMapper;
	@Autowired
	private PetMapper petMapper;
	@Autowired
	private AdoptMapper adoptMapper;
	@Autowired
	private AccusationMapper accMapper;
	@Autowired
	private MessageMapper msgMapper;
//	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");

	@InitBinder
    public void initBinder(WebDataBinder binder, WebRequest request) {
        //转换日期格式
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        //注册自定义的编辑器
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}
	
	@GetMapping("indexList")
	public JsonResult indexList(HttpSession session) {
		JsonResult result = new JsonResult();
		List<Pet> list = petMapper.selectIndexPets();
		for (Pet pet : list) {
			//pet.setRemark(sdf.format(pet.getPublishTime()));
			pet.setRemark(userMapper.selectByPrimaryKey(pet.getUserId()).getNickName());
		}
		result.setData(list);
		return result;
	}
	
	@GetMapping("myPetList")
	public JsonResult myPetList(HttpSession session) {
		JsonResult result = new JsonResult();
		User user = getCurrUser(session);
		Pet record = new Pet();
		record.setUserId(user.getUserId());
		List<Pet> list = petMapper.select(record );
		for (Pet pet : list) {
			//pet.setRemark(sdf.format(pet.getPublishTime()));
			pet.setRemark(userMapper.selectByPrimaryKey(pet.getUserId()).getNickName());
		}
		result.setData(list);
		return result;
	}
	
	@GetMapping("loadDetail")
	public JsonResult loadDetail(int petid) {
		JsonResult result = new JsonResult();
		Pet pet = petMapper.selectByPrimaryKey(petid);
		result.setData(pet);
		return result;
	}
	
	@PostMapping("publishAdopt")
	public JsonResult publishAdopt(@RequestBody JSONObject req, HttpSession session) {
		Pet pet = JSONObject.parseObject(req.toJSONString(), Pet.class);
		System.out.println(req.toJSONString());
		if(session.getAttribute("CURR_USER")!=null) {
			User user = getCurrUser(session);
			pet.setPublishTime(new Date());
			pet.setUserId(user.getUserId());
			pet.setStatus("待领养");
			//通过图片识别宠物的具体品种，不允许用户输入，防止作弊
			String realCategory = ImageRecognitionUtil.animal(pet.getPic());
			pet.setSubCategory(realCategory);
			try {
				petMapper.insertSelective(pet);
				return new JsonResult("0", "发布成功");
			} catch (Exception e) {
				e.printStackTrace();
				return new JsonResult("PET_ERROR", "发布送养失败");
			}
		}else {
			return new JsonResult("301", "用户未登录！");
		}
	}
	
	/**
	 * 发布举报
	 * @param req
	 * @param session
	 * @return
	 */
	@PostMapping("publishAcc")
	public JsonResult publishAcc(@RequestBody JSONObject req, HttpSession session) {
		System.out.println(req.toJSONString());
		User user = getCurrUser(session);
		Integer petId = req.getInteger("petId");
		String reason = req.getString("reason");
		Accusation acc = new Accusation();
		Pet pet = petMapper.selectByPrimaryKey(petId);
		acc.setPetId(petId);
		acc.setAccTime(new Date());
		if(user!=null) {
			acc.setAccusater(user.getUserId());
			acc.setReason(reason);
			acc.setStatus("待审核");
			try {
				Adopt record = new Adopt();
				record.setPetId(petId);
				record.setRemark("已领养");
				List<Adopt> rets = adoptMapper.select(record);
				if(rets != null && rets.size() > 0) {			
					Adopt adopt = adoptMapper.select(record).get(0);
					acc.setUserId(adopt.getAdoptUser());	
				}else {
					acc.setUserId(pet.getUserId());
				}
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			try {
				accMapper.insertSelective(acc);
				return new JsonResult("0", "发布成功");
			} catch (Exception e) {
				e.printStackTrace();
				return new JsonResult("PUBLISH_ERROR", "提供线索失败");
			}
		}else {
			return new JsonResult("301", "用户未登录！");
		}
	}
	
	/**
	 * 我发布的宠物送养
	 * @param session
	 * @return
	 */
	@GetMapping("myPublish")
	public JsonResult myPublish(HttpSession session) {
		User user = getCurrUser(session);
		Pet record = new Pet();
		record.setUserId(user.getUserId());
		List<Pet> list = petMapper.select(record);
		JsonResult json = new JsonResult();
		json.setData(list);
		return json;
	}

	/**
	 * 提交领养宠物申请
	 * @param req
	 * @param session
	 * @return
	 */
	@PostMapping("adopt")
	public JsonResult adopt(@RequestBody JSONObject req, HttpSession session) {
		User user = getCurrUser(session);
		Integer petId = req.getInteger("petId");
		JsonResult json = new JsonResult();
		Adopt record = new Adopt();
		record.setAdoptUser(user.getUserId());
		record.setPetId(petId);
		List<Adopt> list = adoptMapper.select(record);
		if(list != null && list.size() >0) {
			return new JsonResult("ADOPT_ERR", "您已申请领养过该宠物，请耐心等待审核。");
		}
		
		if(user.getIsValidate() != 1) {
			JsonResult err = new JsonResult();
			if(user.getIsValidate() == -1) {
				err.setMsg("您还不是实名认证用户，请完成实名认证后再申请领养。");
			}else if(user.getIsValidate() == 2) {
				err.setMsg("实名认证待审核中，无法申请领养");
			}else if(user.getIsValidate() >= 3) {
				err.setMsg("您是黑名单用户，无法申请领养");
			}
			return err;
		}
		
		Adopt adopt = new Adopt();
		adopt.setAdoptDate(new Date());
		adopt.setAdoptUser(user.getUserId());
		adopt.setPublishUser(req.getInteger("publishUser"));
		adopt.setRemark("待审核");
		adopt.setPetId(petId);
		adoptMapper.insertSelective(adopt);
//		List<AdoptVo> voList = getMyAdopt(user);
//		json.setData(voList);
		json.setCode("OK");
		return json;
	}
	
	@GetMapping("myadopt")
	public JsonResult myadopt(Integer type, HttpSession session) {
		User user = getCurrUser(session);
		List<List<AdoptVo>> data = new ArrayList<>();
		Adopt adopt = new Adopt();
		//1.我申请的
		adopt.setAdoptUser(user.getUserId());
		List<Adopt> adopts = adoptMapper.select(adopt);
		List<AdoptVo> voList = new ArrayList<>();
		for (Adopt adp : adopts) {
			AdoptVo vo = new AdoptVo();
			vo.setAdopt(adp);
			vo.setAdopter(user);
			vo.setPublisher(userMapper.selectByPrimaryKey(adp.getPublishUser()));
			vo.setPet(petMapper.selectByPrimaryKey(adp.getPetId()));
			voList.add(vo);
		}
		data.add(voList);
		//2.我收到的申请
		Adopt adopt2 = new Adopt();
		adopt2.setPublishUser(user.getUserId());
		List<Adopt> adopts2 = adoptMapper.select(adopt2);
		List<AdoptVo> voList2 = new ArrayList<>();
		for (Adopt adp : adopts2) {
			AdoptVo vo = new AdoptVo();
			vo.setAdopt(adp);
			vo.setAdopter(userMapper.selectByPrimaryKey(adp.getAdoptUser()));
			vo.setPublisher(user);
			vo.setPet(petMapper.selectByPrimaryKey(adp.getPetId()));
			voList2.add(vo);
		}
		data.add(voList2);
		
		JsonResult json = new JsonResult();
		json.setData(data);
		return json;
	}
	
	/**
	 * 同意领养申请
	 * @param adoptId
	 * @param session
	 * @return
	 */
	@GetMapping("appAdopt")
	public JsonResult appAdopt(Integer adoptId, HttpSession session) {
		Adopt adopt = adoptMapper.selectByPrimaryKey(adoptId);
		Pet pet = petMapper.selectByPrimaryKey(adopt.getPetId());
		adopt.setRemark("已通过");
		adoptMapper.updateByPrimaryKeySelective(adopt);
		//发送系统消息，通知领养人审核通过
		Message msg0 = new Message();
		msg0.setIsRead(0);
		msg0.setSender("SYS");
		msg0.setTitle("宠物领养审核结果");
		msg0.setContent("您的领养申请【"+pet.getTitle()+"】已通过送养人的审核，请与送养人联系沟通。");
		msg0.setUserId(adopt.getAdoptUser());
		msg0.setSendTime(new Date());
		msgMapper.insertSelective(msg0);
		Adopt record = new Adopt();
		record.setPetId(adopt.getPetId());
		List<Adopt> list = adoptMapper.select(record);
		//将其他人的领养申请状态置为已拒绝，并且发送系统消息
		Iterator<Adopt> itr = list.iterator();
		while(itr.hasNext()) {
			Message msg = new Message();
			msg.setIsRead(0);
			Adopt ado = itr.next();
			if(ado.getAdoptUser() == adopt.getAdoptUser()) {
				itr.remove();
			}else {
				msg.setContent("您的领养申请【"+pet.getTitle()+"】未能通过送养人的审核，感谢您的支持。");
				ado.setRemark("已拒绝");
				adoptMapper.updateByPrimaryKeySelective(ado);
				msg.setSender("SYS");
				msg.setTitle("宠物领养审核结果");
				msg.setUserId(ado.getAdoptUser());
				msg.setSendTime(new Date());
				msgMapper.insertSelective(msg);
			}
		}
		//将宠物的状态设置为已领养
		pet.setStatus("已领养");
		petMapper.updateByPrimaryKeySelective(pet);
		return JsonResult.success();
	}
	
	/**
	 * 获取我的认领申请
	 * @param user 当前登录用户
	 * @return
	 */
	private List<AdoptVo> getMyAdopt(User user){
		Adopt record = new Adopt();
		record.setAdoptUser(user.getUserId());
		List<Adopt> list = adoptMapper.select(record );
		List<AdoptVo> voList = new ArrayList<>();
		for (Adopt adp : list) {
			AdoptVo vo = new AdoptVo();
			vo.setAdopt(adp);
			vo.setAdopter(user);
			vo.setPublisher(userMapper.selectByPrimaryKey(adp.getPublishUser()));
			vo.setPet(petMapper.selectByPrimaryKey(adp.getPetId()));
			voList.add(vo);
		}
		return voList;
	}
	
	/**
	 * 获取我收到的领养申请
	 * @param user
	 * @return
	 */
	private List<AdoptVo> getMyPublishAdopt(User user){
		Adopt record = new Adopt();
		record.setPublishUser(user.getUserId());
		List<Adopt> list = adoptMapper.select(record );
		List<AdoptVo> voList = new ArrayList<>();
		for (Adopt adp : list) {
			AdoptVo vo = new AdoptVo();
			vo.setAdopt(adp);
			vo.setAdopter(userMapper.selectByPrimaryKey(adp.getAdoptUser()));
			vo.setPublisher(user);
			vo.setPet(petMapper.selectByPrimaryKey(adp.getPetId()));
			voList.add(vo);
		}
		return voList;
	}
}
