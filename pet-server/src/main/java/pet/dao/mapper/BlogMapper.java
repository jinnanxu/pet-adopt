package pet.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import pet.dao.model.Blog;
import pet.utils.MyMapper;

public interface BlogMapper extends MyMapper<Blog> {
	
	List<Blog> selectListDesc();
	
	List<Blog> selectMyListDesc(@Param("userId") Integer userId);
}