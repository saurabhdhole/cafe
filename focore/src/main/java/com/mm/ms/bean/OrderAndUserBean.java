package com.mm.ms.bean;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.datetime.DateFormatter;

import com.mm.ms.data.repo.OrderRepository;
import com.mm.ms.data.repo.ReconciliationRepository;
import com.mm.ms.dto.OrderDto;
import com.mm.ms.entity.Foodorder;
import com.mm.ms.entity.Reconciliation;

import java.text.ParseException;
public class OrderAndUserBean {
	
	

	@Autowired
	OrderRepository orderRepository;
	
	@Autowired 
	ReconciliationRepository reconciliationRepository;

	public OrderDto findPrevOrder() {
		
		//fetch last record of reconcile
		List<Reconciliation> reconcile = reconciliationRepository.fetchLast();
		int flag=0;
		OrderDto orderdto =new OrderDto();
		
		if(reconcile!=null)
		{
			//fetch last record of order by oid
			Foodorder order = orderRepository.findOne(reconcile.get(0).getOrderid());
		
			//	fetch the record of order by
			List<Foodorder> Forders = orderRepository.fetchByDate(order.getOrderdate());
		
			Date date=new Date();
			
//			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//			Date date1;
//			Date date2;
//			try {
//				System.out.println(date.toString());
//				System.out.println(order.getOrderdate().toString());
//				date1 = sdf.parse(date.toString());
//				 date2 = sdf.parse(order.getOrderdate().toString());
//				 System.out.println(date1);
//					System.out.println(date2);
//				
//			} catch (ParseException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
        	int checkToday;
			if(date.getDay()==order.getOrderdate().getDay()&&date.getMonth()==order.getOrderdate().getMonth()&&date.getYear()==order.getOrderdate().getYear())
				checkToday=1;
			else
				checkToday=0;
			if(checkToday==1)//if all records reconcile
			{
				if(Forders.size()==3)
				{
					List<Reconciliation> reconcileRecords1 = reconciliationRepository.findByOrderidAndStatus(Forders.get(0).getId(),"Closed");
					List<Reconciliation> reconcileRecords2 = reconciliationRepository.findByOrderidAndStatus(Forders.get(1).getId(),"Closed");
					List<Reconciliation> reconcileRecords3 = reconciliationRepository.findByOrderidAndStatus(Forders.get(2).getId(),"Closed");
					if(reconcileRecords1.size()>0 && reconcileRecords2.size()>0&& reconcileRecords3.size()>0)
					{
						orderdto.setOrdername("done");
						flag=1;
					}
				}
			}
			
			if(checkToday==0 && Forders.size()>0)
			{
				for(Foodorder f:Forders)
				{
					List<Reconciliation> reconcileRecords = reconciliationRepository.findByOrderidAndStatus(f.getId(),"processed");
					if(reconcileRecords.size()>0)
					{		
						orderdto.setOrdername("recExist");
						orderdto.setOrderdate(order.getOrderdate());
						String str=reconcileRecords.get(0).getStatus();
						if(str.equals("processed"))
						{	flag=1;
							break;
						}
					}
			
				}
			}
		}
		if(flag==0)
		{
			orderdto.setOrdername("recClear");
			//orderdto.setOrderdate(order.getOrderdate());
		}
		return orderdto;
	}
	
}
