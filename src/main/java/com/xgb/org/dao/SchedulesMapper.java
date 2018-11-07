package com.xgb.org.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import com.xgb.org.domain.Schedules;

@Repository
public interface SchedulesMapper {
	
	@Insert("Insert into t_schedules(title,typese,sendEmail,sendMessage,runTime,createTime,updateTime,statuses,adminId) "
			+ "values(#{title},#{typese},#{sendEmail},#{sendMessage},#{runTime},#{createTime},#{updateTime},#{statuses},#{adminId} )")
	int save(Schedules bean) throws Exception;
	
	@Update("update t_schedules set title =#{title},typese =#{typese},sendEmail =#{sendEmail},sendMessage =#{sendMessage},runTime =#{runTime},"
			+ "updateTime =#{updateTime},statuses =#{statuses},adminId =#{adminId} where id = #{id}")
	int update(Schedules bean) throws Exception;
	
	@Delete("DELETE FROM t_schedules WHERE id = #{id} ")
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
	
	/**
	 * 按照开始和结束时间以及类型查询排期集合
	 * @param beginTime
	 * @param endTime
	 * @param typese
	 * @return 返回排期集合
	 * @throws Exception
	 */
	List<Schedules> getListCurrentHour(@Param("beginTime") String beginTime,@Param("endTime") String endTime,@Param("typese") String typese) throws Exception;
	
}
