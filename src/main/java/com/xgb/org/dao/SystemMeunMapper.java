package com.xgb.org.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import com.xgb.org.domain.SystemMeun;

@Repository
public interface SystemMeunMapper {

	int save(SystemMeun bean) throws Exception;
	
	int update(SystemMeun bean) throws Exception;
	
	@Delete("delete from t_system_meun where id = #{id}")
	int deleteById(@Param("id") String id) throws Exception;
	
	SystemMeun getBeanById(@Param("id") String id) throws Exception;
	
	List<SystemMeun> getList(@Param("index") int index,@Param("pageSize") int pageSize,@Param("search") String search) throws Exception;
	
	int getCount(@Param("search") String search) throws Exception;
	
	@Select("SELECT id,name,url,descM,statuses,sort,createTime,updateTime,parentId FROM t_system_meun WHERE parentId = #{parentId} ")
	List<SystemMeun> getMenuTree(@Param("parentId") String parentId) throws Exception;
}
