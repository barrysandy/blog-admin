package com.xgb.org.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.xgb.org.domain.Schedules;

@Repository
public interface SchedulesMapper {
	
	int save(Schedules bean) throws Exception;
	
	int update(Schedules bean) throws Exception;
	
	int deleteById(@Param("id") Integer id) throws Exception;
	
	Schedules getBeanById(@Param("id") Integer id) throws Exception;
	
	/**
	 * 按照开始和结束时间以及类型查询排期集合
	 * @param beginTime 开始时间
	 * @param endTime 结束时间
	 * @param typese 类型(非必须条件)
	 * @param adminId 管理员id
	 * @return 返回排期集合
	 * @throws Exception
	 */
	List<Schedules> getList(@Param("beginTime") String beginTime,@Param("endTime") String endTime,@Param("typese") String typese,@Param("adminId") String adminId) throws Exception;
	
	/**
	 * 统计排期数量
	 * @param beginTime
	 * @param endTime
	 * @param typese
	 * @param adminId
	 * @return
	 * @throws Exception
	 */
	int getCount(@Param("beginTime") String beginTime,@Param("endTime") String endTime,@Param("typese") String typese,@Param("adminId") String adminId) throws Exception;
}
