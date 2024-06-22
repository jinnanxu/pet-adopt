package pet.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import pet.dao.model.Message;
import pet.utils.MyMapper;

public interface MessageMapper extends MyMapper<Message> {
	
	List<Message> selectMyMsg(@Param("userId") Integer userId);
}