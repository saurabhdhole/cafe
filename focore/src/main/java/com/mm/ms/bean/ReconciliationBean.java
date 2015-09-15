package com.mm.ms.bean;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;

import com.mm.ms.BaseAbstractBean;
import com.mm.ms.data.repo.FoodItemRepository;
import com.mm.ms.data.repo.OrderItemRepository;
import com.mm.ms.data.repo.ReconciliationRepository;
import com.mm.ms.entity.FoodItem;
import com.mm.ms.entity.OrderItem;
import com.mm.ms.entity.Reconciliation;
import com.mm.ms.exception.ReconcileException;

public class ReconciliationBean extends BaseAbstractBean<Reconciliation, Long> {

	private final Log logger = LogFactory.getLog(getClass());

	@Autowired
	ReconciliationRepository reconciliationRepository;

	@Autowired
	FoodItemRepository foodItemRepository;

	@Autowired
	OrderItemRepository orderItemRepository;

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
		if(null!=reconciliation)
		{
		logger.debug(logstr + "got reconciliation entry "+ reconciliation.fetchLogDetails());
		return reconciliation;
		}else{
			logger.debug(logstr + "got reconciliation entry "+ reconciliation.fetchLogDetails());
			throw new Exception();
		}
	}

	@Override
	public Iterable<Reconciliation> readAll(String logstr) {
		// TODO Auto-generated method stub
		logger.debug(logstr + "get all reconciliation records");
		Iterable<Reconciliation> reconciliatonRecords = reconciliationRepository
				.findAll();
		//reconciliatonRecords.iterator().next();
		return reconciliatonRecords;
	}

	@Override
	public Iterable<Reconciliation> readAll(String logstr, Pageable pagable) {
		// TODO Auto-generated method stub
		logger.debug(logstr + "get all reconciliation records pageable "
				+ logdetails(pagable));
		Iterable<Reconciliation> reconciliatonRecords = reconciliationRepository
				.findAll(pagable);
		return reconciliatonRecords;
	}

	@Override
	public Reconciliation update(String logstr, Reconciliation tobemerged) {
		// TODO Auto-generated method stub
		logger.debug(logstr + "update reconciliation entry"
				+ tobemerged.fetchLogDetails());
		Reconciliation reconciliationOrig = reconciliationRepository
				.findOne(tobemerged.getId());
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

	// calculate amount
	public Reconciliation calculateamt(String logstr, OrderItem tobemerged)throws ReconcileException {
		// TODO Auto-generated method stub
		int qty;
		double fprice,amt;
		//Date date;
		if(tobemerged.getOid()>0)
			throw new ReconcileException("Order item list is empty");
		
		logger.debug(logstr + "create reconciliation entry"
				+ tobemerged.fetchLogDetails());
		
		//find food item and its price;
		FoodItem foodItem=foodItemRepository.findOne(tobemerged.getItem_id());
		OrderItem orderItem=orderItemRepository.findOne(tobemerged.getOid());
		
		amt=foodItem.getPrice()*tobemerged.getQty();
		//tobemerged item should be POJO 
				
		
		
		//Create Reconsile entity
		Reconciliation reconciliation=new Reconciliation();
		//reconciliation.setDate(getDate);
		reconciliation.setOrderid(tobemerged.getOid());
		reconciliation.setOrdercost(amt);
		//reconciliation.setAmountpaid(tobemerged.get);
		reconciliation.setUserid(tobemerged.getUid());
		
		Reconciliation entity=create(logstr, reconciliation);
		
		logger.debug(logstr + "created reconciliation entry "
				+ reconciliation.fetchLogDetails());
		return entity;
	}
}
