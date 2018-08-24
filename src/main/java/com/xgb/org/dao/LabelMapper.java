package com.xgb.org.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import com.xgb.org.domain.Label;

@Repository
public interface LabelMapper {

	int save(Label bean);
	
	int update(Label bean);
	
	int deleteById(@Param("id") String id);
	
	Label getBeanById(@Param("id") String id);
	
	List<Label> getList(@Param("search") String search);
	
	@Select("SELECT id,name,sort FROM t_label")
	List<Label> getListAll();
	
	@Select("SELECT id,name,sort FROM t_label WHERE id in(SELECT label_id FROM t_art_label WHERE art_id = #{artId} )")
	List<Label> getListByArtId(@Param("artId") String artId);
	
	int getCount(@Param("search") String search);
	
	@Select("SELECT COUNT(*) FROM t_art_label WHERE art_id = #{aid} AND label_id = #{lid} ")
	int existenceData(@Param("lid") String lid, @Param("aid") String aid);
	
	@Insert("INSERT INTO t_art_label(id,art_id,label_id) VALUES(#{id},#{aid},#{lid}) ")
	int saveArtLabel(@Param("id") String id, @Param("aid") String aid, @Param("lid") String lid);
	
	@Delete("DELETE FROM t_art_label WHERE art_id = #{aid} ")
	int deleteByArtId(@Param("aid") String aid);
}
