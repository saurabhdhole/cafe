package com.mm.ms;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.mm.ms.bean.FoodItemBean;
import com.mm.ms.bean.OrderBean;
import com.mm.ms.bean.OrderItemBean;
import com.mm.ms.bean.ReconciliationBean;
import com.mm.ms.bean.OrderAndUserBean;
import com.mm.ms.bean.UserBean;
import com.mm.ms.util.LogUtil;
import com.mm.ms.util.RespMsgUtil;

@Configuration
public class FoCoreConfig {

	@Bean
	public OrderItemBean orderItemBean() {
		OrderItemBean orderItemBean = new OrderItemBean();
		return orderItemBean;
	}

	@Bean
	public ReconciliationBean reconciliationBean() {
		ReconciliationBean reconciliationBean = new ReconciliationBean();
		return reconciliationBean;
	}

	@Bean
	public OrderBean orderBean() {
		OrderBean orderBean = new OrderBean();
		return orderBean;
	}

	@Bean
	public UserBean userBean() {
		UserBean userBean = new UserBean();
		return userBean;
	}

	@Bean
	public LogUtil logUtil() {
		LogUtil logUtil = new LogUtil();
		return logUtil;
	}

	@Bean
	public RespMsgUtil respMsgUtil() {
		RespMsgUtil respMsgUtil = new RespMsgUtil();
		return respMsgUtil;
	}

	@Bean
	public FoodItemBean foodItemBean() {
		FoodItemBean foodItemBean = new FoodItemBean();
		return foodItemBean;
	}
	
@Bean
public OrderAndUserBean todayOrderAndUserBean()
{
	OrderAndUserBean todayOrderAndUserBean=new OrderAndUserBean();
	
	return todayOrderAndUserBean;
}
	
	
	
}
