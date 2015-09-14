package com.mm.ms.data.repo;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.mm.ms.entity.FoodItem;

public interface FoodItemRepository extends PagingAndSortingRepository<FoodItem, Long>{

}
