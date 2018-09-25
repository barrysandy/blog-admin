package com.xgb.org.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import com.xgb.org.domain.SystemUpdateLog;


@Repository
public interface SystemUpdateLogMapper {

	@Insert("INSERT INTO t_system_update_log(id,title,descM,createTime,updateTime,statuses) VALUES(#{id},#{title},#{descM},#{createTime},#{updateTime},#{statuses}) ")
	int save(SystemUpdateLog bean) throws Exception;
	
	@Update("UPDATE t_system_update_log SET title=#{title},descM=#{descM},createTime=#{createTime},updateTime=#{updateTime},statuses=#{statuses} WHERE id=#{id}")
	int update(SystemUpdateLog bean) throws Exception;
	
	@Delete("DELETE FROM t_system_update_log WHERE id = #{id} ")
	int deleteById(@Param("id") String id) throws Exception;
	
	@Select("SELECT id,title,descM,createTime,updateTime,statuses FROM t_system_update_log WHERE id=#{id} ")
	SystemUpdateLog getBeanById(@Param("id") String id) throws Exception;
	
	@Select("SELECT id,title,descM,createTime,updateTime,statuses FROM t_system_update_log ORDER BY createTime DESC limit #{index},#{pageSize}")
	List<SystemUpdateLog> getList(@Param("index") int index,@Param("pageSize") int pageSize) throws Exception;
	
	@Select("SELECT count(id) FROM t_system_update_log")
	int getCount() throws Exception;
}
