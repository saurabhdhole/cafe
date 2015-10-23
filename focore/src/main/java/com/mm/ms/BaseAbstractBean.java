package com.mm.ms;

import java.util.Iterator;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Order;


public abstract class BaseAbstractBean<T, ID> {

	public abstract T create(String logstr, T entity) throws Exception;
	
	public abstract T read(String logstr, ID id) throws Exception;
	
	public abstract Iterable<T> readAll(String logstr) throws Exception;
	
	public abstract Iterable<T> readAll(String logstr, Pageable pagable) throws Exception;
	
	public abstract T update(String logstr, T tobemerged) throws Exception;
	
	public abstract Boolean delete(String logstr, ID id);
	
	public String logdetails(Pageable pageable){
		String pageableDetails = " ";
		if (null != pageable) {
			pageableDetails = pageableDetails + "page number = " + pageable.getPageNumber();
			pageableDetails = pageableDetails + " page offset = " + pageable.getOffset();
			pageableDetails = pageableDetails + " page size = " + pageable.getPageSize();
			pageableDetails = pageableDetails + " sorting = ";
			Iterator<Order> orders = pageable.getSort().iterator();
			Order order = null;
			while(orders.hasNext()) {
				 order = orders.next();
				 pageableDetails = pageableDetails + order.getProperty() + "-" + order.getDirection().toString() + " ";
		     }			
		}
		return pageableDetails;
	}
	
}
