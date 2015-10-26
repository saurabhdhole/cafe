package com.mm.ms.bean;

import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;

import com.mm.ms.BaseAbstractBean;
import com.mm.ms.data.repo.OrderRepository;
import com.mm.ms.data.repo.ReconciliationRepository;
import com.mm.ms.dto.OrderDto;
import com.mm.ms.entity.Foodorder;
import com.mm.ms.entity.Reconciliation;

public class OrderBean extends BaseAbstractBean<Foodorder, Long> {

	private final Log logger = LogFactory.getLog(getClass());

	@Autowired
	OrderRepository orderRepository;

	@Autowired
	ReconciliationRepository reconciliationRepository; 
	
	
	@Override
	public Foodorder create(String logstr, Foodorder entity) {
		logger.debug(logstr + "create order " + entity.fetchLogDetails());
		Foodorder order = orderRepository.save(entity);
		logger.debug(logstr + "created order " + entity.fetchLogDetails());
		return order;
	}

	
	public Foodorder readByStatus(String logstr, String status) {
		logger.debug(logstr + "get order with status " + status);
		Foodorder order = orderRepository.findByStatus(status);
		logger.debug(logstr + "got order with status " + order.fetchLogDetails());
		return order;
	}
	
	@Override
	public Foodorder read(String logstr, Long id) throws Exception {
		logger.debug(logstr + "get order at id " + id);
		Foodorder order = orderRepository.findOne(id);
		if(null!=order)
		{
		logger.debug(logstr + "got order " + order.fetchLogDetails());
		return order;
		}else{
			logger.debug(logstr + "Cant Retrieve Order " + order.fetchLogDetails());
			throw new Exception();
		}
	}

	@Override
	public Iterable<Foodorder> readAll(String logstr) {
		logger.debug(logstr + "get all order records");
		Iterable<Foodorder> appUserRecords = orderRepository.findAll();
		return appUserRecords;
	}

	@Override
	public Iterable<Foodorder> readAll(String logstr, Pageable pageable) {
		logger.debug(logstr + "get all order records pageable "
				+ logdetails(pageable));
		Iterable<Foodorder> appUserRecords = orderRepository.findAll(pageable);
		return appUserRecords;
	}

	@Override
	public Foodorder update(String logstr, Foodorder tobemerged) {
		logger.debug(logstr + "update oreder " + tobemerged.fetchLogDetails());
		Foodorder appUserOrig = orderRepository.findOne(tobemerged.getId());
		Foodorder order = orderRepository.save(appUserOrig
				.mergeUpdates(tobemerged));
		logger.debug(logstr + "updated order " + order.fetchLogDetails());
		return order;
	}

	@Override
	public Boolean delete(String logstr, Long id) {
		logger.debug(logstr + "delete order at id " + id);
		orderRepository.delete(id);
		logger.debug(logstr + "deleted oreder at id " + id);
		return true;
	}
	//checking last order name
	public OrderDto checkLastOrder(String logstr, Date date) {
		logger.debug(logstr + "checking Today's  last order name");
		OrderDto order=new OrderDto();
		List<Foodorder> foodorders= orderRepository.fetchByDate(date);
		int cnt=foodorders.size();
		if(cnt==0)
			order.setOrdername("Noexist");
		else
		{
			if(cnt==2)
			{
				if(foodorders.get(0).getName().equals("Lunch")&&foodorders.get(1).getName().equals("Breakfast"))
				{
					order.setOrdername("orderLB");
				}
				if(foodorders.get(1).getName().equals("Lunch")&&foodorders.get(0).getName().equals("Breakfast"))
				{
					order.setOrdername("orderLB");
				}
				if(foodorders.get(0).getName().equals("Breakfast")&&foodorders.get(1).getName().equals("Dinner"))
				{
					order.setOrdername("orderBD");
				}
				if(foodorders.get(1).getName().equals("Breakfast")&&foodorders.get(0).getName().equals("Dinner"))
				{
					order.setOrdername("orderBD");
				}
				if(foodorders.get(0).getName().equals("Lunch")&&foodorders.get(1).getName().equals("Dinner"))
				{
					order.setOrdername("orderLD");
				}
				if(foodorders.get(1).getName().equals("Lunch")&&foodorders.get(0).getName().equals("Dinner"))
				{
					order.setOrdername("orderLD");
				}
			}
			if(cnt==3)
				order.setOrdername("orderLBD");
			if(cnt==1)
				order.setOrdername(foodorders.get(cnt-1).getName());
			
			order.setOrderdate(foodorders.get(cnt-1).getOrderdate());
			order.setOrderidL((long) 0);
			order.setOrderidB((long) 0);
			order.setOrderidD((long) 0);
			for(Foodorder food:foodorders)
			{
				List<Reconciliation> reconcileRecords = reconciliationRepository.findByOrderidAndStatus(food.getId(),"Closed");
				List<Reconciliation> reconcileRecords1 = reconciliationRepository.findByOrderidAndStatus(food.getId(),"processed");
				
				if(food.getName().equals("Lunch"))
				{
					order.setOrderidL(food.getId());
					if(reconcileRecords.size()>0 && reconcileRecords1.size()==0 )
						order.setLunchrec("true");
					else
						order.setLunchrec("false");
				}
				if(food.getName().equals("Breakfast"))
				{	
					order.setOrderidB(food.getId());
					if(reconcileRecords.size()>0 && reconcileRecords1.size()==0)
						order.setBreakfastrec("true");
					else
						order.setBreakfastrec("false");
				}
				if(food.getName().equals("Dinner"))
				{
					order.setOrderidD(food.getId());
					if(reconcileRecords.size()>0 && reconcileRecords1.size()==0)
						order.setDinnerrec("true");
					else
						order.setDinnerrec("false");
				}
			}
			
			order.setOrdercnt((long) cnt);
		}
		logger.debug(logstr + "Retrievd Today's last order name");
		return order;
	}
	
	
	
}
