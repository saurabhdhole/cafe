package com.mm.ms.bean;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;

import com.mm.ms.BaseAbstractBean;
import com.mm.ms.data.repo.OrderItemRepository;
import com.mm.ms.entity.OrderItem;

public class OrderItemBean extends BaseAbstractBean<OrderItem, Long> {

	private final Log logger = LogFactory.getLog(getClass());

	@Autowired
	OrderItemRepository orderItemRepository;

	@Override
	public OrderItem create(String logstr, OrderItem entity) {
		logger.debug(logstr + "create Order " + entity.fetchLogDetails());
		OrderItem order = orderItemRepository.save(entity);
		logger.debug(logstr + "created order " + entity.fetchLogDetails());
		return order;
	}

	@Override
	public OrderItem read(String logstr, Long id) {
		logger.debug(logstr + "get order at id " + id);
		OrderItem order =orderItemRepository.findOne(id);
		logger.debug(logstr + "got order " + order.fetchLogDetails());
		return order;
	}

	@Override
	public Iterable<OrderItem> readAll(String logstr) {
		logger.debug(logstr + "get all order records");
		Iterable<OrderItem> orderRecords = orderItemRepository.findAll();
		return orderRecords;
	}

	@Override
	public Iterable<OrderItem> readAll(String logstr, Pageable pageable) {
		logger.debug(logstr + "get all order records pageable " + logdetails(pageable));
		Iterable<OrderItem> orderRecords = orderItemRepository.findAll(pageable);
		return orderRecords;
	}

	@Override
	public OrderItem update(String logstr, OrderItem tobemerged) {
		logger.debug(logstr + "update order " + tobemerged.fetchLogDetails());
		OrderItem appUserOrig = orderItemRepository.findOne(tobemerged.getId());
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

}