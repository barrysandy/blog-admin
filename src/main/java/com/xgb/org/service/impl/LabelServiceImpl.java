package com.xgb.org.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.xgb.org.dao.LabelMapper;
import com.xgb.org.domain.Label;
import com.xgb.org.service.LabelService;


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
@Service("labelService")
public class LabelServiceImpl implements LabelService {

	@Autowired
	LabelMapper labelMapper;

	@Override
	public int saveService(Label bean) {
		int result = labelMapper.save(bean);
//		throw new RuntimeException("程序运行异常");
		return result;
	}

	@Override
	public int updateService(Label bean) {
		return labelMapper.update(bean);
	}

	@Override
	public int deleteByIdService(String id) {
		return labelMapper.deleteById(id);
	}

	@Transactional(readOnly=true)
	@Override
	public Label getBeanByIdService(String id) {
		return labelMapper.getBeanById(id);
	}

	@Override
//	@Transactional(readOnly=true)
	public List<Label> getListService(int index, int pageSize,String search) {
		PageHelper.startPage(index, pageSize);
		List<Label> list = labelMapper.getList(search);
		return list;
	}

	@Override
//	@Transactional(readOnly=true)
	public int getCountService(String search) {
		return labelMapper.getCount(search);
	}

	@Override
	public List<Label> getListByArtId(String artId) throws Exception {
		return labelMapper.getListByArtId(artId);
	}

	@Override
	public List<Label> getListAll() throws Exception {
		return labelMapper.getListAll();
	}

	@Override
	public int existenceData(String lid, String aid) throws Exception {
		return labelMapper.existenceData(lid, aid);
	}

	@Override
	public int saveArtLabelService(String id, String aid, String lid) throws Exception {
		return labelMapper.saveArtLabel(id, aid, lid);
	}

	@Override
	public int deleteByArtIdService(String aid) throws Exception {
		return labelMapper.deleteByArtId(aid);
	}


}
