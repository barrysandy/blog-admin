package com.xgb.org.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import com.xgb.org.domain.Admin;

@Repository
public interface AdminMapper {

	int save(Admin bean);
	
	int update(Admin bean);
	
	int deleteById(@Param("id") String id);
	
//	@Select("select id,name,password,descM from t_user_temp where id = #{id}")
	Admin getBeanById(@Param("id") String id);
	
	@Select("select id,name,password,descM from t_admin where name = #{name} and password = #{password}")
	Admin getBeanByNameAndPassword(@Param("name") String name,@Param("password") String password);
	
	List<Admin> getList(@Param("search") String search);
	
	int getCount(@Param("search") String search);
}
