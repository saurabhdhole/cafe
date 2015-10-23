package com.mm.ms.data.repo;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.mm.ms.entity.Foodorder;
import com.mm.ms.entity.Reconciliation;

public interface ReconciliationRepository extends PagingAndSortingRepository<Reconciliation, Long> {

	public List<Reconciliation> findByOrderid(Long orderid);
	public Reconciliation findByUseridAndOrderid(Long uid,Long oid);
	
	
	public List<Reconciliation> findByOrderidAndStatus(Long orderid,String status);
	
	@Query("SELECT r from Reconciliation r order by r.id desc")
	public List<Reconciliation> fetchLast();
}
