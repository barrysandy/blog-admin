package com.xgb.org.dao;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import com.xgb.org.domain.Material;

@Repository
public interface MaterialMapper {

	
	@Insert("Insert into t_material(id,name,url,diskPath,type,status,createTime,updateTime) "
			+ "values(#{id},#{name},#{url},#{diskPath},#{type},#{status},#{createTime},#{updateTime})")
	int save(Material bean) throws Exception;
	
	int update(Material bean) throws Exception;
	
	@Delete("DELETE FROM t_material WHERE id = #{id} ")
	int deleteById(String id) throws Exception;
	
	@Select("SELECT id,name,url,diskPath,type,status,createTime,updateTime FROM t_material WHERE id = #{id} ")
	Material getBeanById(String id) throws Exception;
	
	@Select("SELECT id,name,url,diskPath,type,status,createTime,updateTime FROM t_material WHERE url = #{url} ")
	Material getBeanByUrl(String url) throws Exception;
}
