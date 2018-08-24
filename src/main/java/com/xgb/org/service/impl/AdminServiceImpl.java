package com.xgb.org.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.xgb.org.dao.AdminMapper;
import com.xgb.org.domain.Admin;
import com.xgb.org.service.AdminService;


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
@Service("adminService")
public class AdminServiceImpl implements AdminService {

	@Autowired
	AdminMapper adminMapper;

	@Override
	public int saveService(Admin bean) {
		int result = adminMapper.save(bean);
//		throw new RuntimeException("程序运行异常");
		return result;
	}

	@Override
	public int updateService(Admin bean) {
		return adminMapper.update(bean);
	}

	@Override
	public int deleteByIdService(String id) {
		return adminMapper.deleteById(id);
	}

	@Transactional(readOnly=true)
	@Override
	public Admin getBeanByIdService(String id) {
		return adminMapper.getBeanById(id);
	}

	@Override
//	@Transactional(readOnly=true)
	public List<Admin> getListService(int index, int pageSize,String search) {
		PageHelper.startPage(index, pageSize);
		List<Admin> list = adminMapper.getList(search);
		return list;
	}

	@Override
//	@Transactional(readOnly=true)
	public int getCountService(String search) {
		return adminMapper.getCount(search);
	}

	@Override
	public Admin getBeanByNameAndPassword(String name, String password) throws Exception {
		return adminMapper.getBeanByNameAndPassword(name, password);
	}

}
