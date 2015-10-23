package com.mm.ms.bean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;

import com.mm.ms.BaseAbstractBean;
import com.mm.ms.data.repo.FoodItemRepository;
import com.mm.ms.data.repo.OrderItemRepository;
import com.mm.ms.data.repo.OrderRepository;
import com.mm.ms.dto.OrderItemDto;
import com.mm.ms.entity.FoodItem;
import com.mm.ms.entity.Foodorder;
import com.mm.ms.entity.OrderItem;



public class OrderItemBean extends BaseAbstractBean<OrderItem, Long> {

	private final Log logger = LogFactory.getLog(getClass());

	@Autowired
	OrderItemRepository orderItemRepository;

	@Autowired
	OrderRepository orderRepository;
	
	@Autowired
	UserBean userBean;
	
	@Autowired
	FoodItemBean foodItemBean;

	private OrderItemDto orderItemDto;
	
	@Override
	public OrderItem create(String logstr, OrderItem entity) throws Exception {
		logger.debug(logstr + "create Order " + entity.fetchLogDetails());
		OrderItem order = orderItemRepository.save(entity);
		if(order==null)
		{
			logger.debug(logstr + "Order Item not created");
			throw new Exception();
		}
		logger.debug(logstr + "created order " + entity.fetchLogDetails());
		return order;
	}

	@Override
	public OrderItem read(String logstr, Long id) throws Exception {
		logger.debug(logstr + "get order at id " + id);
		OrderItem order =orderItemRepository.findOne(id);
//		if(order==null)
//		{
//			logger.debug(logstr + "Food Item does not exist");
//			throw new Exception();
//		}
		logger.debug(logstr + "got order " + order.fetchLogDetails());
		return order;
	}

	@Override
	public Iterable<OrderItem> readAll(String logstr) throws Exception {
		logger.debug(logstr + "get all order records");
		Iterable<OrderItem> orderRecords = orderItemRepository.findAll();
		if(orderRecords==null)
		{
			logger.debug(logstr + "Food Item empty");
			throw new Exception();
		}
		return orderRecords;
	}

	//Retrieve Records by the orderid and userid
	public List<OrderItemDto> readAllByOidAndUid(String logstr,Long oid,Long uid) throws Exception {
		logger.debug(logstr + "get all order records");
		List<OrderItem> orderRecords = orderItemRepository.findByUidAndOid(uid, oid);
		List<OrderItemDto> orderItemDtoRecords = new ArrayList<OrderItemDto>();
		
		FoodItem foodItem;
		try {
		for(OrderItem orderItem:orderRecords)
		{	
			orderItemDto = new OrderItemDto();
				orderItemDto.setOrderid(oid);
				orderItemDto.setUsername((userBean.read(logstr, orderItem.getUid())).getName());
				foodItem =foodItemBean.read(logstr, orderItem.getItem_id());
				orderItemDto.setItemname(foodItem.getName());
				orderItemDto.setQty(orderItem.getQty());
				orderItemDto.setPrice(foodItem.getPrice());
				orderItemDtoRecords.add(orderItemDto);
		}
		logger.debug(logstr + "OrderItem data succesfully fetched by orderid and userid ");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.debug(logstr + "Failed to retrive OrderItem data by orderid and userid ");
			throw new Exception();
		}
		return orderItemDtoRecords;
	}
	
	@Override
	public Iterable<OrderItem> readAll(String logstr, Pageable pageable) throws Exception {
		logger.debug(logstr + "get all order records pageable " + logdetails(pageable));
		Iterable<OrderItem> orderRecords = orderItemRepository.findAll(pageable);
		if(orderRecords==null)
		{
			logger.debug(logstr + "Food Item empty");
			throw new Exception();
		}
		return orderRecords;
	}

	@Override
	public OrderItem update(String logstr, OrderItem tobemerged) throws Exception {
		logger.debug(logstr + "update order " + tobemerged.fetchLogDetails());
		OrderItem appUserOrig = orderItemRepository.findOne(tobemerged.getId());
		if(appUserOrig==null)
		{
			logger.debug(logstr + "Food Item record does not exist");
			throw new Exception();
		}
		OrderItem order = orderItemRepository.save(appUserOrig.mergeUpdates(tobemerged));
		logger.debug(logstr + "updated order " + order.fetchLogDetails());
		return order;
	}

	@Override
	public Boolean delete(String logstr, Long id) {
		logger.debug(logstr + "delete order at id " + id);
		orderItemRepository.delete(id);
		logger.debug(logstr + "deleted appUser at id " + id);
		return true;
	}

	public List<OrderItemDto> getOrderDetail(String logstr, Long orderid ) throws Exception {
		
		OrderItemDto orderItemDto;
		List<OrderItem> orderRecords = orderItemRepository.findByOid(orderid);
		List<OrderItemDto> orderItems = new ArrayList<OrderItemDto>();
		Integer qty;
		FoodItem foodItem;
		if(orderRecords.isEmpty())
			throw new Exception();
		for(OrderItem orderItem:orderRecords)
		{
			orderItemDto = new OrderItemDto();
			orderItemDto.setOrderid(orderid);
			orderItemDto.setUsername((userBean.read(logstr, orderItem.getUid())).getName());
			foodItem =foodItemBean.read(logstr, orderItem.getItem_id());
			orderItemDto.setItemname(foodItem.getName());
			qty = orderItem.getQty();
			orderItemDto.setQty(qty);
			orderItemDto.setPrice(foodItem.getPrice()*qty);
			orderItems.add(orderItemDto);
		}
		return orderItems;
	}
	
	//get orderitem records by date
public List<OrderItemDto> getOrderDetailbyDate(String logstr,Date date ) throws Exception {
		
	List<Foodorder> orderRecords =orderRepository.fetchByDate(date);
	if(orderRecords.size()==0)
		throw new Exception();
	
	List<OrderItemDto> orderItemsM = new ArrayList<OrderItemDto>();
	
	for(Foodorder f:orderRecords)
	{
		List<OrderItemDto> orderItems = new ArrayList<OrderItemDto>();
		Long orderid=f.getId();
		orderItems=getOrderDetail(logstr, orderid);
		orderItemsM.addAll(orderItems);
		
	}
	return orderItemsM;
	}
	
}
