package com.mm.ms.bean;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;

import com.mm.ms.BaseAbstractBean;
import com.mm.ms.data.repo.FoodItemRepository;
import com.mm.ms.entity.FoodItem;

public class FoodItemBean extends BaseAbstractBean<FoodItem, Long> {

	private final Log logger = LogFactory.getLog(getClass());

	@Autowired
	FoodItemRepository FoodItemRepository;

	@Override
	public FoodItem create(String logstr, FoodItem entity) {
		logger.debug(logstr + "create Food Item " + entity.fetchLogDetails());
		FoodItem foodItem = FoodItemRepository.save(entity);
		logger.debug(logstr + "created Food Item " + entity.fetchLogDetails());
		return foodItem;
	}

	@Override
	public FoodItem read(String logstr, Long id) {
		logger.debug(logstr + "get foodItem at id " + id);
		FoodItem foodItem = FoodItemRepository.findOne(id);
		logger.debug(logstr + "got fooditem " + foodItem.fetchLogDetails());
		return foodItem;
	}

	@Override
	public Iterable<FoodItem> readAll(String logstr) {
		logger.debug(logstr + "get all fooditem records");
		Iterable<FoodItem> foodRecords = FoodItemRepository.findAll();
		return foodRecords;
	}

	@Override
	public Iterable<FoodItem> readAll(String logstr, Pageable pageable) {
		logger.debug(logstr + "get all fooditem records pageable "
				+ logdetails(pageable));
		Iterable<FoodItem> foodsRecords = FoodItemRepository.findAll(pageable);
		return foodsRecords;
	}

	@Override
	public FoodItem update(String logstr, FoodItem tobemerged) {
		logger.debug(logstr + "update appUser " + tobemerged.fetchLogDetails());
		FoodItem foodFind = FoodItemRepository.findOne(tobemerged.getId());
		FoodItem mergeFood = FoodItemRepository.save(foodFind
				.mergeUpdates(tobemerged));
		logger.debug(logstr + "updated appUser " + mergeFood.fetchLogDetails());
		return mergeFood;
	}

	@Override
	public Boolean delete(String logstr, Long id) {
		logger.debug(logstr + "delete fooditem at id " + id);
		FoodItemRepository.delete(id);
		logger.debug(logstr + "deleted fooditem at id " + id);
		return true;
	}
}
