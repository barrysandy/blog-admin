package com.xgb.org.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xgb.org.common.DateUtils;
import com.xgb.org.dao.SchedulesMapper;
import com.xgb.org.domain.Schedules;
import com.xgb.org.service.SchedulesService;
import com.xgb.org.vo.VSchedules;

/**
 * SpringBoot 事务配置
 * <p>Title: 事务配置</p>
 * <p>Description: </p>
 *	第一种方式使用注解：
 *		第一步在启动类添加注解开启事务支持（@EnableTransactionManagement），
 *  	第二步在实现接口添加事务注解（@Transactional()[类级别配置表示对其下所有方法都进行配置事务]，@Transactional(readOnly=true)[方法级别配置表示本方法进行配置事务]）
 *  
 *  第二种方式使用AOP全局事务配置切面（参考切面类com.xgb.org.config.TransationAdviceConfig）
 *  	参考网页：https://segmentfault.com/q/1010000015095590/a-1020000015097236
 *  	
 * @author XuGongBin
 * @version 1.0.0
 * @date 2018/10/15 21:54
 */
//@Transactional()
@Service("schedulesService")
public class SchedulesServiceImpl implements SchedulesService 
{

	@Autowired
	SchedulesMapper mapper;

	@Override
	public int saveService(Schedules bean) throws Exception 
	{
		return mapper.save(bean);
	}

	@Override
	public int updateService(Schedules bean) throws Exception 
	{
		return mapper.update(bean);
	}

	@Override
	public int deleteByIdService(Integer id) throws Exception 
	{
		return mapper.deleteById(id);
	}

	@Override
	public Schedules getBeanByIdService(Integer id) throws Exception 
	{
		
		return mapper.getBeanById(id);
	}

	@Override
	public List<VSchedules> getListService(String beginTime, String endTime, String typese, String adminId) throws Exception 
	{
		List<VSchedules> list = new ArrayList<>();
		List<Schedules> listSchedules = mapper.getList(beginTime, endTime, typese, adminId);
		long time1 = System.currentTimeMillis();
		if(listSchedules != null) {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(new Date());
			int year = calendar.get(Calendar.YEAR);//获取年份
	        int month = calendar.get(Calendar.MONTH) + 1;//获取月份
			for (int i = 1; i <= DateUtils.getMonthLastDay(year, month); i++) {
				VSchedules vSchedules = new VSchedules();
				vSchedules.setDay(i);
				List<Schedules> temp = new ArrayList<>();
				for (int j = 0; j < listSchedules.size(); j++) {
				   Schedules obj = listSchedules.get(j);
					if(obj != null) {
						String day = obj.getRunTime().split("-")[2];
						day = day.split(" ")[0];
						if(Integer.parseInt(day) == i) {
							temp.add(obj);
						}
					}
				}
				vSchedules.setTotal(temp.size());
				vSchedules.setSchedules(temp);
				list.add(vSchedules);
			}
		}
		long time2 = System.currentTimeMillis();
		System.err.println("排期数据加工时间：" + (time2 - time1));
		return list;
	}

	@Override
	public int getCountService(String beginTime, String endTime, String typese, String adminId) throws Exception 
	{
		return mapper.getCount(beginTime, endTime, typese, adminId);
	}

	@Override
	public List<Schedules> getListCurrentHourService(String beginTime, String endTime, String typese)
			throws Exception {
		return mapper.getListCurrentHour(beginTime, endTime, typese);
	}


}
