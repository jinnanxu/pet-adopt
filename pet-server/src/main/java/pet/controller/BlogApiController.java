package pet.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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

import pet.controller.vo.BlogVo;
import pet.controller.vo.JsonResult;
import pet.dao.mapper.AdoptMapper;
import pet.dao.mapper.BlogMapper;
import pet.dao.mapper.MessageMapper;
import pet.dao.mapper.PetMapper;
import pet.dao.mapper.PetNoticeMapper;
import pet.dao.mapper.UserMapper;
import pet.dao.model.Adopt;
import pet.dao.model.Blog;
import pet.dao.model.Message;
import pet.dao.model.Pet;
import pet.dao.model.PetNotice;
import pet.dao.model.User;
import pet.utils.ImageRecognitionUtil;

/**
 * 动态信息数据接口
 * @author xujinnan
 *
 */
@RequestMapping("/blog")
@RestController
public class BlogApiController extends ApiBaseController{
	
	@Autowired
	private UserMapper userMapper;
	@Autowired
	private AdoptMapper adoptMapper;
	@Autowired
	private PetMapper petMapper;
	@Autowired
	private BlogMapper blogMapper;
	@Autowired
	private PetNoticeMapper noticeMapper;
	@Autowired
	private MessageMapper msgMapper;
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	@InitBinder
    public void initBinder(WebDataBinder binder, WebRequest request) {
        //转换日期格式
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        //注册自定义的编辑器
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}
	
	/**
	 * 动态列表，按发布时间倒序
	 * @param session
	 * @return
	 */
	@GetMapping("indexList")
	public JsonResult indexList(HttpSession session) {
		JsonResult result = new JsonResult();
		List<Blog> list = blogMapper.selectListDesc();
		List<BlogVo> voList = new ArrayList<>();
		for (Blog blog : list) {
			BlogVo vo = new BlogVo();
			vo.setPet(petMapper.selectByPrimaryKey(blog.getPetId()));
			vo.setBlog(blog);
			vo.setUser(userMapper.selectByPrimaryKey(blog.getUserId()));
			Adopt record = new Adopt();
			record.setPetId(vo.getPet().getPetId());
			record.setAdoptUser(blog.getUserId());
			try {
				Adopt adopt = adoptMapper.select(record).get(0);
				vo.setAdoptDate(adopt.getAdoptDate());
			} catch (Exception e) {
				vo.setAdoptDate(new Date());
				e.printStackTrace();
			}
			voList.add(vo);
		}
		result.setData(voList);
		return result;
	}
	
	@GetMapping("myblog")
	public JsonResult myblog(HttpSession session) {
		JsonResult result = new JsonResult();
		User user = getCurrUser(session);
		List<Blog> list = blogMapper.selectMyListDesc(user.getUserId());
		List<BlogVo> voList = new ArrayList<>();
		for (Blog blog : list) {
			BlogVo vo = new BlogVo();
			vo.setPet(petMapper.selectByPrimaryKey(blog.getPetId()));
			vo.setBlog(blog);
			vo.setUser(user);//(userMapper.selectByPrimaryKey(blog.getUserId()));
			Adopt record = new Adopt();
			record.setPetId(vo.getPet().getPetId());
			record.setAdoptUser(blog.getUserId());
			try {
				Adopt adopt = adoptMapper.select(record).get(0);
				vo.setAdoptDate(adopt.getAdoptDate());
			} catch (Exception e) {
				vo.setAdoptDate(new Date());
				e.printStackTrace();
			}
			voList.add(vo);
		}
		result.setData(voList);
		return result;
	}
	
	/**
	 * 查询我领养过的宠物，用于填充下拉框
	 * @param session
	 * @return
	 */
	@GetMapping("loadMyAdopt")
	public JsonResult myAdopt(HttpSession session) {
		JsonResult result = new JsonResult();
		User user = getCurrUser(session);
		List<Pet> pets = petMapper.selectMyAdoptPets(user.getUserId());
		result.setData(pets);
		return result;
	}
	
	/**
	 * 查询博客详情
	 * @param session
	 * @return
	 */
	@GetMapping("loadDetail")
	public JsonResult loadDetail(int blogid) {
		JsonResult result = new JsonResult();
		Blog blog = blogMapper.selectByPrimaryKey(blogid);
		BlogVo vo = new BlogVo();
		vo.setPet(petMapper.selectByPrimaryKey(blog.getPetId()));
		vo.setBlog(blog);
		vo.setUser(userMapper.selectByPrimaryKey(blog.getUserId()));
		Adopt record = new Adopt();
		record.setPetId(vo.getPet().getPetId());
		record.setAdoptUser(blog.getUserId());
		try {
			Adopt adopt = adoptMapper.select(record).get(0);
			vo.setAdoptDateStr(sdf.format(adopt.getAdoptDate()));
		} catch (Exception e) {
			vo.setAdoptDate(new Date());
			e.printStackTrace();
		}
		result.setData(vo);
		return result;
	}
	
	/**
	 * 提交动态表单
	 * @param req
	 * @param session
	 * @return
	 */
	@PostMapping("publishBlog")
	public JsonResult publishNotice(@RequestBody JSONObject req, HttpSession session) {
		Blog blog = JSONObject.parseObject(req.toJSONString(), Blog.class);
		System.out.println(req.toJSONString());
		Integer petId = blog.getPetId();
		//查找宠物信息，匹配图片是否符合
		Pet pet = petMapper.selectByPrimaryKey(petId);
		String picUrl = blog.getPic();//pet.getPic();
		String realCategory = ImageRecognitionUtil.animal(picUrl);
		if(pet.getSubCategory().contains(realCategory) || realCategory.contains(pet.getSubCategory())) {
			//匹配通过！此次发表的动态中的宠物与领养时的是同一个类别
			System.out.println("匹配通过！");
		}else {
			//匹配不通过！发出警告信息！
			System.out.println("匹配失败！");
			return new JsonResult("MATCH_ERROR", "发布动态失败");
		}
		User user = getCurrUser(session);
		if(user!=null) {
			blog.setPublishTime(new Date());
			blog.setUserId(user.getUserId());
			try {
				blogMapper.insertSelective(blog);
				return new JsonResult("0", "发布成功");
			} catch (Exception e) {
				e.printStackTrace();
				return new JsonResult("PUBLISH_ERROR", "发布动态失败");
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
