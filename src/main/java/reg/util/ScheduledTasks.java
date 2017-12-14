package reg.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import reg.dao.MobCountRepository;


/**
 * @Scheduled(fixedRate = 5000) ：上一次开始执行时间点之后5秒再执行
 * @Scheduled(fixedDelay = 5000) ：上一次执行完毕时间点之后5秒再执行
 * @Scheduled(initialDelay=1000, fixedRate=5000)
 *                               ：第一次延迟1秒后执行，之后按fixedRate的规则每5秒执行一次
 * @Scheduled(cron="1/5 * * * * *") ：通过cron表达式定义规则
 */
@Component
public class ScheduledTasks {
	
	@Autowired
	MobCountRepository mcdao;
	
	@Scheduled(cron="0 0 6 * * *")
	public void updatePlotInfo(){
		mcdao.deleteAll();
	}
	
}
