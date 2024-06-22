package pet.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import pet.dao.model.PetNotice;
import pet.utils.MyMapper;

public interface PetNoticeMapper extends MyMapper<PetNotice> {
	
	List<PetNotice> selectListDesc();
	
	List<PetNotice> selectMyListDesc(@Param("userId") Integer userId);
}