package com.mm.ms;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.mm.ms.bean.FoodItemBean;
import com.mm.ms.bean.UserBean;
import com.mm.ms.util.LogUtil;
import com.mm.ms.util.RespMsgUtil;

@Configuration
public class FoCoreConfig {
	
	@Bean
	public UserBean userBean() {
		UserBean userBean = new UserBean();		
		return userBean;
	}
			
	@Bean
	public LogUtil logUtil(){
		LogUtil logUtil = new LogUtil();
		return logUtil;
	}
	
	@Bean
	public RespMsgUtil respMsgUtil(){
		RespMsgUtil respMsgUtil = new RespMsgUtil();
		return respMsgUtil;
	}
	
	@Bean
	public FoodItemBean foodItemBean() {
	FoodItemBean foodItemBean = new FoodItemBean();
	return foodItemBean;
	}
}

