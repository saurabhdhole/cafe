package com.mm.ms.rest;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.mm.ms.bean.OrderAndUserBean;
import com.mm.ms.dto.OrderDto;
import com.mm.ms.entity.Foodorder;
import com.mm.ms.util.Const;

@RestController
@RequestMapping("/ordertoday")
public class TodayOrderAndUserController {
	
	public static final String MS_CODE = "FoodItem";
	//TODO Change below line based on your Microservice if not correct
	public static final String LOGSTR_MS = Const.LOGSTR_FOMS;
	private String loggedInUser = Const.DEFAULT_USER;
		
	private static final String CREATE_SUCCESS = "Successfully created user.";
	private static final String CREATE_FAILED = "User creation failed.";
		
	private static final String RETRIEVE_SUCCESS = "Successfully retrieved user record/s.";
	private static final String RETRIEVE_FAILED = "User retrieval failed.";
		
	private static final String UPDATE_SUCCESS = "Successfully updated user.";
	private static final String UPDATE_FAILED = "User update failed.";
		
	private static final String DELETE_SUCCESS = "Successfully deleted user.";
	private static final String DELETE_FAILED = "User deletion failed.";
	
	@Autowired
	OrderAndUserBean orderUserBean;
	
	@RequestMapping(method = RequestMethod.GET)
	public OrderDto fetchODetail() 
	{
		
		OrderDto order = orderUserBean.findPrevOrder();
		
		return order;
	}
}