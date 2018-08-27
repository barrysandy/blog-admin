package com.xgb.org.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xgb.org.dao.SystemRoleMapper;
import com.xgb.org.domain.SystemRole;
import com.xgb.org.service.SystemRoleService;


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
 * @date 2018/8/25 15:39
 */
//@Transactional()
@Service("SystemRoleService")
public class SystemRoleServiceImpl implements SystemRoleService {

	@Autowired
	SystemRoleMapper SystemRoleMapper;

	@Override
	public int saveService(SystemRole bean) throws Exception {
		return SystemRoleMapper.save(bean);
	}

	@Override
	public int updateService(SystemRole bean) throws Exception {
		return SystemRoleMapper.update(bean);
	}

	@Override
	public int deleteByIdService(String id) throws Exception {
		return SystemRoleMapper.deleteById(id);
	}

	@Override
	public SystemRole getBeanByIdService(String id) throws Exception {
		return SystemRoleMapper.getBeanById(id);
	}

	@Override
	public List<SystemRole> getListService(int index, int pageSize, String search) throws Exception {
		return SystemRoleMapper.getList(index, pageSize, search);
	}

	@Override
	public int getCountService(String search) throws Exception {
		return SystemRoleMapper.getCount(search);
	}

}
