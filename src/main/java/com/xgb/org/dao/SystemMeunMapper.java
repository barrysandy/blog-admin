package com.xgb.org.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import com.xgb.org.domain.SystemMeun;

@Repository
public interface SystemMeunMapper {

	@Insert("insert into t_system_meun (id,name,url,descM,statuses,sort,createTime,updateTime,parentId,icon) "
			+ "values (#{id},#{name},#{url},#{descM},#{statuses},#{sort},#{createTime},#{updateTime},#{parentId},#{icon} )")
	int save(SystemMeun bean) throws Exception;
	
	@Update("update t_system_meun set id =#{id},name =#{name},url =#{url},descM =#{descM},statuses =#{statuses},"
			+ "sort =#{sort},createTime =#{createTime},updateTime =#{updateTime},parentId =#{parentId},icon=#{icon} where id = #{id}")
	int update(SystemMeun bean) throws Exception;
	
	@Delete("delete from t_system_meun where id = #{id}")
	int deleteById(@Param("id") String id) throws Exception;
	
	@Select("SELECT id,name,url,descM,statuses,sort,createTime,updateTime,parentId,icon FROM t_system_meun WHERE id = #{id} ")
	SystemMeun getBeanById(@Param("id") String id) throws Exception;
	
	List<SystemMeun> getList(@Param("index") int index,@Param("pageSize") int pageSize,@Param("search") String search) throws Exception;
	
	int getCount(@Param("search") String search) throws Exception;
	
	@Select("SELECT id,name,url,descM,statuses,sort,createTime,updateTime,parentId,icon FROM t_system_meun WHERE parentId = #{parentId} ORDER BY sort")
	List<SystemMeun> getMenuTree(@Param("parentId") String parentId) throws Exception;
}
