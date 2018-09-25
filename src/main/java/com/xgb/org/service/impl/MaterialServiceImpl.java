package com.xgb.org.service.impl;


import java.io.File;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xgb.org.dao.MaterialMapper;
import com.xgb.org.domain.Material;
import com.xgb.org.service.MaterialService;


/**
 * SpringBoot 事务配置
 * <p>Title: 事务配置</p>
 * <p>Description: </p>
 *	第一种方式使用注解：
 *		第一步在启动类添加注解开启事务支持（@EnableTransactionManagement），
 *  	第二步在实现接口添加事务注解（@Transactional()[类级别配置表示对其下所有方法都进行配置事务]，@Transactional(readOnly=true)[方法级别配置表示本方法进行配置事务]）
 *  
 *  第二种方式使用AOP全局事务配置切面（参考切面类com.xgb.org.config.TransationAdviceConfig）
 *  	参考网页：https://segmentfault.com/q/1010000015095590/a-1020000015097236
 *  	
 * @author XuGongBin
 * @version 1.0.0
 * @date 2018/8/24 23:16
 */
//@Transactional()
@Service("materialService")
public class MaterialServiceImpl implements MaterialService {
	
	private static Logger LOGGER = LoggerFactory.getLogger(MaterialService.class);

	@Autowired
	MaterialMapper materialMapper;

	@Override
	public int saveService(Material bean) throws Exception {
		if(bean != null) {
			Date time = new Date();
			bean.setMaterialId(String.valueOf(time.getTime()));
		}
		return materialMapper.save(bean);
	}

	@Override
	public int updateService(Material bean) throws Exception {
		return materialMapper.update(bean);
	}

	@Override
	public int deleteByIdService(String url) throws Exception {
		int rusult = 0;
		Material material = getBeanByUrlService(url);
		if(material != null) {
			File file = new File(material.getDiskPath());
			if(file.exists()) {
				file.delete();
				LOGGER.info("Log----Info : " + material.getDiskPath() + " has delete!! ");
			}
			rusult = materialMapper.deleteById(material.getId());
		}
		
		
		return rusult;
	}

	@Override
	public Material getBeanByIdService(String id) throws Exception {
		return materialMapper.getBeanById(id);
	}

	@Override
	public Material getBeanByUrlService(String url) throws Exception {
		return materialMapper.getBeanByUrl(url);
	}

	@Override
	public List<Material> getListService(int index, int pageSize, String type, String search) throws Exception {
		return materialMapper.getList(index, pageSize, type, search);
	}

	@Override
	public int getCountService(String type,String search) throws Exception {
		return materialMapper.getCount(type, search);
	}

	@Override
	public synchronized Integer getMaxMaterialIdService() throws Exception {
		return materialMapper.getMaxMaterialId();
	}

	@Override
	public int deleteByMaterialIdService(String materialId) throws Exception {
		int rusult = 0;
		Material material = getBeanByMaterialIdService(materialId);
		if(material != null) {
			File file = new File(material.getDiskPath());
			if(file.exists()) {
				file.delete();
				LOGGER.info("Log----Info : " + material.getDiskPath() + " has delete!! ");
			}
			rusult = materialMapper.deleteByMaterialId(materialId);
		}
		
		return rusult;
	}

	@Override
	public Material getBeanByMaterialIdService(String materialId) throws Exception {
		return materialMapper.getBeanByMaterialIdService(materialId);
	}

}
