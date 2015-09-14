package com.mm.ms.bean;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;

import com.mm.ms.BaseAbstractBean;
import com.mm.ms.data.repo.OrderRepository;
import com.mm.ms.entity.Foodorder;

public class OrderBean extends BaseAbstractBean<Foodorder, Long> {

	private final Log logger = LogFactory.getLog(getClass());

	@Autowired
	OrderRepository orderRepository;

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
	public Foodorder read(String logstr, Long id) {
		logger.debug(logstr + "get order at id " + id);
		Foodorder order = orderRepository.findOne(id);
		logger.debug(logstr + "got order " + order.fetchLogDetails());
		return order;
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
}
