package com.mm.ms.data.repo;


import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.mm.ms.entity.Foodorder;
import com.mm.ms.entity.OrderItem;

/**
 * 
 * @author prady
 *
 */
public interface OrderRepository extends PagingAndSortingRepository<Foodorder, Long> {

	public Foodorder findByStatus(String status);
	
	
	@Query("SELECT f from Foodorder f where DATE(f.orderdate)>=:date and f.status=:status")
	List<Foodorder> fetchByDateAndStatus(@Param("date") Date date, @Param("status") String status);
	
	@Query("SELECT f from Foodorder f where DATE(f.orderdate)=:date")
	List<Foodorder> fetchByDate(@Param("date") Date date);

}
