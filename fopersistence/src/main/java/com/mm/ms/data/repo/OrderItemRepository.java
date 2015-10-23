package com.mm.ms.data.repo;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.mm.ms.entity.OrderItem;

public interface OrderItemRepository extends PagingAndSortingRepository<OrderItem, Long> {

	public List<OrderItem> findByOid(Long oid);
	public List<OrderItem> findByUidAndOid(Long uid,Long oid);
	
}
