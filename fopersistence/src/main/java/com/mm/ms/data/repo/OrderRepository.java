package com.mm.ms.data.repo;


import org.springframework.data.repository.PagingAndSortingRepository;

import com.mm.ms.entity.Foodorder;

/**
 * 
 * @author prady
 *
 */
public interface OrderRepository extends PagingAndSortingRepository<Foodorder, Long> {

}
