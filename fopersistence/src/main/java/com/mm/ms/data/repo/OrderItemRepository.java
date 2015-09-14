package com.mm.ms.data.repo;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.mm.ms.entity.OrderItem;

public interface OrderItemRepository extends PagingAndSortingRepository<OrderItem, Long> {

	
}
