package com.xgb.org.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import com.xgb.org.domain.Material;

@Repository
public interface MaterialMapper {

	
	@Insert("Insert into t_material(id,name,url,diskPath,type,status,createTime,updateTime,materialId) "
			+ "values(#{id},#{name},#{url},#{diskPath},#{type},#{status},#{createTime},#{updateTime},#{materialId} )")
	int save(Material bean) throws Exception;
	
	int update(Material bean) throws Exception;
	
	@Delete("DELETE FROM t_material WHERE id = #{id} ")
	int deleteById(@Param("id") String id) throws Exception;
	
	@Delete("DELETE FROM t_material WHERE materialId = #{materialId} ")
	int deleteByMaterialId(@Param("materialId") String materialId) throws Exception;
	
	@Select("SELECT id,name,url,diskPath,type,status,createTime,updateTime,materialId FROM t_material WHERE id = #{id} ")
	Material getBeanById(@Param("id") String id) throws Exception;
	
	@Select("SELECT id,name,url,diskPath,type,status,createTime,updateTime,materialId FROM t_material WHERE url = #{url} LIMIT 1")
	Material getBeanByUrl(@Param("url") String url) throws Exception;
	
	@Select("SELECT id,name,url,diskPath,type,status,createTime,updateTime,materialId FROM t_material WHERE materialId = #{materialId} LIMIT 1")
	Material getBeanByMaterialIdService(@Param("materialId") String materialId) throws Exception;
	
	List<Material> getList(@Param("index") int index,@Param("pageSize") int pageSize,@Param("type") String type,@Param("search") String search) throws Exception;
	
	int getCount(@Param("type") String type,@Param("search") String search) throws Exception;
	
	@Select("SELECT max(materialId) FROM t_material")
	Integer getMaxMaterialId() throws Exception;
}
