package com.mm.ms.data.repo;


import org.springframework.data.repository.PagingAndSortingRepository;

import com.mm.ms.entity.User;

/**
 * 
 * @author prady
 *
 */
public interface UserRepository extends PagingAndSortingRepository<User, Long> {

}
