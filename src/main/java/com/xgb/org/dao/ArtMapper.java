package com.xgb.org.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.xgb.org.domain.Art;

@Repository
public interface ArtMapper {

	int save(Art bean);
	
	int update(Art bean);
	
	int deleteById(@Param("id") String id);
	
	Art getBeanById(@Param("id") String id);
	
	List<Art> getList(@Param("search") String search);
	
	int getCount(@Param("search") String search);
}
