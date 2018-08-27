package com.xgb.org.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.xgb.org.domain.SystemRole;

@Repository
public interface SystemRoleMapper {

	int save(SystemRole bean) throws Exception;
	
	int update(SystemRole bean) throws Exception;
	
	int deleteById(@Param("id") String id) throws Exception;
	
	SystemRole getBeanById(@Param("id") String id) throws Exception;
	
	List<SystemRole> getList(@Param("index") int index,@Param("pageSize") int pageSize,@Param("search") String search) throws Exception;
	
	int getCount(@Param("search") String search) throws Exception;
}
