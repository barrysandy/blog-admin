package com.xgb.org.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.xgb.org.common.DateUtils;
import com.xgb.org.common.StringUtils;
import com.xgb.org.common.VerifyFileTypeUtils;
import com.xgb.org.dao.ArtMapper;
import com.xgb.org.domain.Art;
import com.xgb.org.domain.Material;
import com.xgb.org.service.ArtService;
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
 * @date 2018/7/16 16:20
 */
//@Transactional()
@Service("artService")
public class ArtServiceImpl implements ArtService {

	@Autowired
	ArtMapper artMapper;
	
	@Autowired private MaterialService materialService;

	@Override
	public int saveService(Art bean,String diskPath) throws Exception {
		int result = artMapper.save(bean);
//		throw new RuntimeException("程序运行异常");
		String fileType = VerifyFileTypeUtils.getFileType(diskPath);
		Material material = new Material(StringUtils.getUUID(), bean.getTitle(), bean.getImage(), 
				diskPath, fileType, 1, DateUtils.getStringDate(DateUtils.simpleMinute),"");
		materialService.saveService(material);
		return result;
	}

	@Override
	public int updateService(Art bean) {
		//判断资源是否改变
		//改变则 删除旧资源
		Art oldBean = artMapper.getBeanById(bean.getId());
		if(oldBean.getImage() != null && bean.getImage() != null) {
			if(!oldBean.getImage().equals(bean.getImage())) {
				try {
					materialService.deleteByIdService(oldBean.getImage());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		
		return artMapper.update(bean);
	}

	@Override
	public int deleteByIdService(String id) {
		return artMapper.deleteById(id);
	}

	@Transactional(readOnly=true)
	@Override
	public Art getBeanByIdService(String id) {
		return artMapper.getBeanById(id);
	}

	@Override
//	@Transactional(readOnly=true)
	public List<Art> getListService(int index, int pageSize,String search) {
		PageHelper.startPage(index, pageSize);
		List<Art> list = artMapper.getList(search);
		return list;
	}

	@Override
//	@Transactional(readOnly=true)
	public int getCountService(String search) {
		return artMapper.getCount(search);
	}


}
