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
import com.mm.ms.data.repo.ReconciliationRepository;
import com.mm.ms.data.repo.UserRepository;
import com.mm.ms.dto.OrderItemDto;
import com.mm.ms.dto.ReconciliationDto;
import com.mm.ms.entity.FoodItem;
import com.mm.ms.entity.Foodorder;
import com.mm.ms.entity.OrderItem;
import com.mm.ms.entity.Reconciliation;
import com.mm.ms.entity.User;
import com.mm.ms.exception.ReconcileException;

public class ReconciliationBean extends BaseAbstractBean<Reconciliation, Long> {

	private final Log logger = LogFactory.getLog(getClass());

	@Autowired
	ReconciliationRepository reconciliationRepository;

	@Autowired
	FoodItemRepository foodItemRepository;

	@Autowired
	OrderItemRepository orderItemRepository;

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	OrderRepository orderRepository;

	@Override
	public Reconciliation create(String logstr, Reconciliation entity) {
		// TODO Auto-generated method stub
		logger.debug(logstr + "create reconcialtion entry "
				+ entity.fetchLogDetails());
		Reconciliation reconciliation = reconciliationRepository.save(entity);
		logger.debug(logstr + "created reconcialtion entry "
				+ entity.fetchLogDetails());
		return reconciliation;
	}

	@Override
	public Reconciliation read(String logstr, Long id) throws Exception {
		// TODO Auto-generated method stub
		logger.debug(logstr + "get reconciliation entry at id " + id);
		Reconciliation reconciliation = reconciliationRepository.findOne(id);
		if(reconciliation==null)
		{
			logger.debug(logstr + "Reconciliation record does not exist");
			throw new Exception();
		}
		logger.debug(logstr + "got reconciliation entry "
				+ reconciliation.fetchLogDetails());
		return reconciliation;
	}

	@Override
	public Iterable<Reconciliation> readAll(String logstr) throws Exception {
		// TODO Auto-generated method stub
		logger.debug(logstr + "get all reconciliation records");
		Iterable<Reconciliation> reconciliatonRecords = reconciliationRepository
				.findAll();
		if(reconciliatonRecords==null)
		{
			logger.debug(logstr + "Reconciliation records does not exist");
			throw new Exception();
		}
		// reconciliatonRecords.iterator().next();
		return reconciliatonRecords;
	}

	@Override
	public Iterable<Reconciliation> readAll(String logstr, Pageable pagable) throws Exception {
		// TODO Auto-generated method stub
		logger.debug(logstr + "get all reconciliation records pageable "
				+ logdetails(pagable));
		Iterable<Reconciliation> reconciliatonRecords = reconciliationRepository
				.findAll(pagable);
		if(reconciliatonRecords==null)
		{
			logger.debug(logstr + "Reconciliation records does not exist");
			throw new Exception();
		}
		return reconciliatonRecords;
	}

	@Override
	public Reconciliation update(String logstr, Reconciliation tobemerged) throws Exception {
		// TODO Auto-generated method stub
		logger.debug(logstr + "update reconciliation entry"
				+ tobemerged.fetchLogDetails());
		Reconciliation reconciliationOrig = reconciliationRepository.findOne(tobemerged.getId());
		if(reconciliationOrig==null)
		{
			logger.debug(logstr + "Reconciliation record does not exist");
			throw new Exception();
		}
		Reconciliation reconciliation = reconciliationRepository
				.save(reconciliationOrig.mergeUpdates(tobemerged));
		logger.debug(logstr + "updated reconciliation entry "
				+ reconciliation.fetchLogDetails());
		return reconciliation;
	}

	@Override
	public Boolean delete(String logstr, Long id) {
		// TODO Auto-generated method stub
		logger.debug(logstr + "delete reconciliation at id " + id);
		reconciliationRepository.delete(id);
		logger.debug(logstr + "deleted appUser at id " + id);
		return true;
	}
	
	//Get all records by orderid
	public List<ReconciliationDto> getRecordsByOid(String logstr,String ordername) throws Exception {
		// TODO Auto-generated method stub
		logger.debug(logstr + "get all order records");
		//fetch last record of reconciliation
		List<Reconciliation> recRecords=reconciliationRepository.fetchLast();
		if(recRecords.size()==0)
			return null;
		Foodorder foodorder=orderRepository.findOne(recRecords.get(0).getOrderid());
		
		
		List<Foodorder> foodorderrecords=orderRepository.fetchByDate(foodorder.getOrderdate());
		Long orderid = null;
		//find the orderid by the ordername
		for(Foodorder food:foodorderrecords)
		{
			if(ordername.equals(food.getName()))
			{
				orderid=food.getId();
			}
		}
		if(orderid==null)
			return null;
		List<Reconciliation> reconciliationrecords = reconciliationRepository.findByOrderid(orderid);
		List<ReconciliationDto> reconciliationDtoRecords = new ArrayList<ReconciliationDto>();
		try
		{
			
		for(Reconciliation reconciliation:reconciliationrecords)
		{
			ReconciliationDto reconciliationDto = new ReconciliationDto();
			reconciliationDto.setOrderid(orderid);
			reconciliationDto.setAmtpaid(reconciliation.getAmountpaid());
			reconciliationDto.setCost(reconciliation.getOrdercost());
			reconciliationDto.setUserid(reconciliation.getUserid());
			User user=userRepository.findOne(reconciliation.getUserid());
			reconciliationDto.setUsername(user.getName());
			reconciliationDto.setStatus(reconciliation.getStatus());
			//reconciliationDto.setUserid(reconciliation.getUserid());
			
			reconciliationDtoRecords.add(reconciliationDto);
		}
		logger.debug(logstr + "Records retrived by orderid");
		}
		catch(Exception e)
		{
			throw new Exception();
		}
		return reconciliationDtoRecords;
	}
	/////////

	// calculate amount
	public Reconciliation calculateamt(String logstr, ReconciliationDto tobemerged) throws Exception
	{
		// TODO Auto-generated method stub
		int qty;
		double fprice, amt = 0;
		Date date = new Date();
		//if (tobemerged.getOrderid() == null)
	//		throw new ReconcileException("Order item list is empty");

		// Finding all order to the perticular user
		List<OrderItem> orderRecords = orderItemRepository.findByUidAndOid(
				tobemerged.getUserid(), tobemerged.getOrderid());
		if(orderRecords==null)
		{
			logger.debug(logstr + "Reconciliation record does not exist according to oid and uid");
			throw new Exception();
		}
		
		for (OrderItem orderItem : orderRecords) {
			FoodItem foodItem = foodItemRepository.findOne(orderItem
					.getItem_id());
			amt = amt + orderItem.getQty() * foodItem.getPrice();
		}

		// Create Reconcile entity
		Reconciliation reconciliation = new Reconciliation();

		logger.debug(logstr + "create reconciliation entry"
				+ reconciliation.fetchLogDetails());
		// reconciliation.setDate(getDate);
		reconciliation.setOrderid(tobemerged.getOrderid());
		reconciliation.setOrdercost(amt);
		reconciliation.setAmountpaid(tobemerged.getAmtpaid());
		reconciliation.setUserid(tobemerged.getUserid());
		reconciliation.setStatus("processed");
		reconciliation.setDate(date);
		
		//new update for another update by same user
		Reconciliation recRecords = reconciliationRepository.findByUseridAndOrderid(tobemerged.getUserid(),tobemerged.getOrderid());
		if(recRecords!=null)
		{
			reconciliation.setId(recRecords.getId());
			reconciliation.setAmountpaid(tobemerged.getAmtpaid()+recRecords.getAmountpaid());
			update(logstr, reconciliation);
		}
		else
		{	
			reconciliation = reconciliationRepository.save(reconciliation);
		}
		logger.debug(logstr + "created reconciliation entry "
				+ reconciliation.fetchLogDetails());
		return reconciliation;
	}
	
	public Reconciliation clearStatusByname(String logstr,Long userid,Long orderid) throws Exception {
		// TODO Auto-generated method stub
		
		Reconciliation rec=reconciliationRepository.findByUseridAndOrderid(userid, orderid);
		logger.debug(logstr + "Fetch reconciliation entry"
				+ rec.fetchLogDetails());
		try
		{
			rec.setStatus("Closed");
			rec.setAmountpaid(rec.getOrdercost());
			rec=update(logstr,rec);
		}
		catch(Exception e)
		{
			throw new Exception();
		}
		return rec;
	}
	
}
