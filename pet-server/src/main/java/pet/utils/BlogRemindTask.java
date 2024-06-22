package pet.utils;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import pet.dao.mapper.AdoptMapper;
import pet.dao.mapper.MessageMapper;
import pet.dao.model.Adopt;
import pet.dao.model.Message;

@Configuration
@EnableScheduling 
public class BlogRemindTask {
	
	@Autowired
	private AdoptMapper adoptMapper;
	@Autowired
	private MessageMapper msgMapper;

	@Scheduled(cron = "0 0 0 1 * ?")
    private void configureTasks() {
		//每月1日执行一次
        System.err.println("执行定时任务时间: " + LocalDateTime.now());
        Adopt record = new Adopt();
        record.setRemark("已通过");
		List<Adopt> adoptList = adoptMapper.select(record );
		for (Adopt adopt : adoptList) {
			Message msg = new Message();
			msg.setContent("您好，请定期发表宠物饲养状态，以保证我们能了解到宠物们的现状哦！");
			msg.setTitle("宠物动态提醒");
			msg.setIsRead(0);
			msg.setSender("SYS");
			msg.setSendTime(new Date());
			msg.setUserId(adopt.getAdoptUser());
			msgMapper.insertSelective(msg);
		}
    }
}
