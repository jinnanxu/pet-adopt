package pet.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

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
import pet.dao.mapper.PetNoticeMapper;
import pet.dao.mapper.UserMapper;
import pet.dao.model.Message;
import pet.dao.model.PetNotice;
import pet.dao.model.User;

/**
 * 寻宠数据接口
 * @author xujinnan
 *
 */
@RequestMapping("/search")
@RestController
public class SearchApiController extends ApiBaseController{
	
	@Autowired
	private UserMapper userMapper;
	@Autowired
	private PetMapper petMapper;
	@Autowired
	private PetNoticeMapper noticeMapper;
	@Autowired
	private MessageMapper msgMapper;

	@InitBinder
    public void initBinder(WebDataBinder binder, WebRequest request) {
        //转换日期格式
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        //注册自定义的编辑器
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}
	
	/**
	 * 寻宠启事列表，按发布时间倒序
	 * @param session
	 * @return
	 */
	@GetMapping("indexList")
	public JsonResult indexList(HttpSession session) {
		JsonResult result = new JsonResult();
		List<PetNotice> list = noticeMapper.selectListDesc();
		result.setData(list);
		return result;
	}
	
	@GetMapping("mysearch")
	public JsonResult mysearch(HttpSession session) {
		JsonResult result = new JsonResult();
		User user = getCurrUser(session);
		List<PetNotice> list = noticeMapper.selectMyListDesc(user.getUserId());
		result.setData(list);
		return result;
	}
	
	/**
	 * 获取寻宠启事详情
	 * @param noticeid
	 * @return
	 */
	@GetMapping("loadDetail")
	public JsonResult loadDetail(int noticeid) {
		JsonResult result = new JsonResult();
		PetNotice notice = noticeMapper.selectByPrimaryKey(noticeid);
		result.setData(notice);
		return result;
	}
	
	/**
	 * 提交寻宠表单
	 * @param req
	 * @param session
	 * @return
	 */
	@PostMapping("publishNotice")
	public JsonResult publishNotice(@RequestBody JSONObject req, HttpSession session) {
		PetNotice pet = JSONObject.parseObject(req.toJSONString(), PetNotice.class);
		System.out.println(req.toJSONString());
		User user = getCurrUser(session);
		if(user!=null) {
			pet.setPublishTime(new Date());
			pet.setUserId(user.getUserId());
			pet.setStatus("寻找中");
			try {
				noticeMapper.insertSelective(pet);
				return new JsonResult("0", "发布成功");
			} catch (Exception e) {
				e.printStackTrace();
				return new JsonResult("PUBLISH_ERROR", "发布寻宠失败");
			}
		}else {
			return new JsonResult("301", "用户未登录！");
		}
	}
	
	@PostMapping("publishClue")
	public JsonResult publishClue(@RequestBody JSONObject req, HttpSession session) {
		System.out.println(req.toJSONString());
		User user = getCurrUser(session);
		Integer noticeId = req.getInteger("noticeId");
		PetNotice notice = noticeMapper.selectByPrimaryKey(noticeId);
		if(user!=null) {
			Message msg = new Message();
			msg.setSender("USER:"+user.getUserId());
			msg.setUserId(notice.getUserId());
			msg.setIsRead(0);
			msg.setSendTime(new Date());
			msg.setTitle(req.getString("title"));
			msg.setContent(req.getString("content"));
			try {
				msgMapper.insertSelective(msg);
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
	 * 我发布的寻宠
	 * @param session
	 * @return
	 */
	@GetMapping("myPublish")
	public JsonResult myPublish(HttpSession session) {
		User user = getCurrUser(session);
		PetNotice record = new PetNotice();
		record.setUserId(user.getUserId());
		List<PetNotice> list = noticeMapper.select(record);
		JsonResult json = new JsonResult();
		json.setData(list);
		return json;
	}

	
}
