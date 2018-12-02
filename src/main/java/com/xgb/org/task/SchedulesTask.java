package com.xgb.org.task;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.xgb.org.common.DateUtils;
import com.xgb.org.common.SendEmailUtils;
import com.xgb.org.domain.Schedules;
import com.xgb.org.service.SchedulesService;

@Component
/**
 * @Scheduled所支持的参数： 
 * 1. cron：cron表达式，指定任务在特定时间执行； 
 * 2. fixedDelay：表示上一次任务执行完成后多久再次执行，参数类型为long，单位ms； 
 * 3. fixedDelayString：与fixedDelay含义一样，只是参数类型变为String； 
 * 4. fixedRate：表示按一定的频率执行任务，参数类型为long，单位ms； 
 * 5. fixedRateString: 与fixedRate的含义一样，只是将参数类型变为String； 
 * 6. initialDelay：表示延迟多久再第一次执行任务，参数类型为long，单位ms； 
 * 7. initialDelayString：与initialDelay的含义一样，只是将参数类型变为String； 
 * 8. zone：时区，默认为当前时区，一般没有用到。
--------------------- 
*/
public class SchedulesTask {
	@Autowired
	private SchedulesService schedulesService;
	
	private int count=0;

	@Scheduled(cron = "0 0/1 * * * ? ")
    private void process() throws Exception{
        //System.out.println("[" + Thread.currentThread().getName() + "]" + "this is scheduler task runing  "+(count++));
        String bTime = DateUtils.getStringDate(DateUtils.simpleMinute);
        //System.out.println("bTime: " + bTime + " eTime: " + bTime);
        //当前排期数据
        List<Schedules> list = schedulesService.getListCurrentHourService(bTime, bTime, "");
        if(list != null) {
        	if(list.size() > 0) {
        		for (int i = 0; i < list.size(); i++) {
        			SendEmailUtils.sendEmial(list.get(i).getAdmin().getEmail(), "[" + list.get(i).getAdmin().getAliasName() + "]--[排期提醒]" + bTime , list.get(i).getTitle());
        		}	
        	}
        }
    }
}
