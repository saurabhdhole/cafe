package com.mm.ms.rest;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.mm.ms.BaseAbstractController;
import com.mm.ms.bean.OrderItemBean;
import com.mm.ms.dto.OrderItemDto;
import com.mm.ms.entity.OrderItem;
import com.mm.ms.util.Const;
import com.mm.ms.util.LogUtil;
import com.mm.ms.util.RespMsgUtil;
import com.mm.ms.util.ResponseMsg;

@RestController
@RequestMapping("/orderItem")
public class OrderItemController extends BaseAbstractController<OrderItem, Long> {
	
	public static final String MS_CODE = "ORDER";
	//TODO Change below line based on your Microservice if not correct
	public static final String LOGSTR_MS = Const.LOGSTR_FOMS;
	private String loggedInUser = Const.DEFAULT_USER;
		
	private static final String CREATE_SUCCESS = "Successfully created order.";
	private static final String CREATE_FAILED = "Order creation failed.";
		
	private static final String RETRIEVE_SUCCESS = "Successfully retrieved order record/s.";
	private static final String RETRIEVE_FAILED = "Order retrieval failed.";
		
	private static final String UPDATE_SUCCESS = "Successfully updated order.";
	private static final String UPDATE_FAILED = "Order update failed.";
		
	private static final String DELETE_SUCCESS = "Successfully deleted order.";
	private static final String DELETE_FAILED = "Order deletion failed.";
	
	@Autowired
	LogUtil logUtil;
	
	@Autowired
	OrderItemBean orderItemBean;
	
	@Autowired
	RespMsgUtil respMsgUtil;

	@Override
	public ResponseEntity<OrderItem> create(@RequestBody OrderItem inputentity) {
		ResponseEntity<OrderItem> userResponse;
		HttpHeaders headers = new HttpHeaders();
		
		OrderItem order;
		try {
			order = orderItemBean.create(logUtil.getPreStr(LOGSTR_MS, loggedInUser), inputentity);
			headers.add(ResponseMsg.HTTP_HEADER_NAME, respMsgUtil.getStr(MS_CODE, ResponseMsg.HTTP_201, CREATE_SUCCESS) );
			userResponse = new ResponseEntity<OrderItem>(order, headers, HttpStatus.CREATED);
		} catch (Exception e) {
			headers.add(ResponseMsg.HTTP_HEADER_NAME, respMsgUtil.getStr(MS_CODE, ResponseMsg.HTTP_500, CREATE_FAILED) );
			userResponse = new ResponseEntity<OrderItem>( inputentity, headers, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return userResponse;
	}

	@Override
	public ResponseEntity<OrderItem> read(@PathVariable(value="id")Long id) {
		//TODO Comment/Uncomment below line based on your requirement
		ResponseEntity<OrderItem> appUserResponse;
		HttpHeaders headers = new HttpHeaders();
		
		OrderItem orderUser = null;
		try {
			orderUser = orderItemBean.read(logUtil.getPreStr(LOGSTR_MS, loggedInUser), id);
			headers.add(ResponseMsg.HTTP_HEADER_NAME, respMsgUtil.getStr(MS_CODE, ResponseMsg.HTTP_200, RETRIEVE_SUCCESS) );
			appUserResponse = new ResponseEntity<OrderItem>(orderUser, headers, HttpStatus.OK);
		}catch(SQLException sq)
		{
			headers.add(ResponseMsg.HTTP_HEADER_NAME, respMsgUtil.getStr(MS_CODE,ResponseMsg.HTTP_503, "SERVER_ERROR") );
			appUserResponse = new ResponseEntity<OrderItem>(orderUser, headers, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		catch (Exception e) {
			headers.add(ResponseMsg.HTTP_HEADER_NAME, respMsgUtil.getStr(MS_CODE, ResponseMsg.HTTP_404, RETRIEVE_FAILED) );
			appUserResponse = new ResponseEntity<OrderItem>(orderUser, headers, HttpStatus.NOT_FOUND);
		}
		return appUserResponse;
	}

	@Override
	public ResponseEntity<Iterable<OrderItem>> readAll() {
		//TODO Comment/Uncomment below line based on your requirement
		ResponseEntity<Iterable<OrderItem>> appUserResponse;
		HttpHeaders headers = new HttpHeaders();
		
		Iterable<OrderItem> appUserRecords = null;
		try {
			appUserRecords = orderItemBean.readAll(logUtil.getPreStr(LOGSTR_MS, loggedInUser));
			headers.add(ResponseMsg.HTTP_HEADER_NAME, respMsgUtil.getStr(MS_CODE, ResponseMsg.HTTP_200, RETRIEVE_SUCCESS) );
			appUserResponse = new ResponseEntity<Iterable<OrderItem>>(appUserRecords, headers, HttpStatus.OK);
		} catch (Exception e) {
			headers.add(ResponseMsg.HTTP_HEADER_NAME, respMsgUtil.getStr(MS_CODE, ResponseMsg.HTTP_404, RETRIEVE_FAILED) );
			appUserResponse = new ResponseEntity<Iterable<OrderItem>>(appUserRecords, headers, HttpStatus.NOT_FOUND);
		}
		return appUserResponse;
	}

	@Override
	public ResponseEntity<Iterable<OrderItem>> readAllPageable(
			@PathParam("firstresult") Integer firstresult,
			@PathParam("maxresult") Integer maxresult,
			@PathParam("sortdir") String sortdir,
			@PathParam("sortfield") String sortfield) {
		//TODO Comment/Uncomment below line based on your requirement
		ResponseEntity<Iterable<OrderItem>> appUserResponse;
		HttpHeaders headers = new HttpHeaders();
		
		// Default sort Direction and Field
		if (sortdir == null){
			sortdir = "DESC";
		}
		if (sortfield == null){
			//TODO Change it as per your requirement
			sortfield = "name";
		}
		Pageable pageable = new PageRequest(firstresult, maxresult,
				Sort.Direction.fromString(sortdir), sortfield);
		
		Iterable<OrderItem> appUserRecords = null;
		try {
			appUserRecords = orderItemBean.readAll(logUtil.getPreStr(LOGSTR_MS, loggedInUser),pageable);
			headers.add(ResponseMsg.HTTP_HEADER_NAME, respMsgUtil.getStr(MS_CODE, ResponseMsg.HTTP_200, RETRIEVE_SUCCESS) );
			appUserResponse = new ResponseEntity<Iterable<OrderItem>>(appUserRecords, headers, HttpStatus.OK);
		} catch (Exception e) {
			headers.add(ResponseMsg.HTTP_HEADER_NAME, respMsgUtil.getStr(MS_CODE, ResponseMsg.HTTP_404, RETRIEVE_FAILED) );
			appUserResponse = new ResponseEntity<Iterable<OrderItem>>(appUserRecords, headers, HttpStatus.NOT_FOUND);
		}
		return appUserResponse;
	}

	@Override
	public ResponseEntity<OrderItem> update(@RequestBody OrderItem tobemerged) {
		ResponseEntity<OrderItem> appUserResponse;
		HttpHeaders headers = new HttpHeaders();
		
		OrderItem orderUser;
		try {
			orderUser = orderItemBean.update(logUtil.getPreStr(LOGSTR_MS, loggedInUser), tobemerged);
			headers.add(ResponseMsg.HTTP_HEADER_NAME, respMsgUtil.getStr(MS_CODE, ResponseMsg.HTTP_201, UPDATE_SUCCESS) );
			appUserResponse = new ResponseEntity<OrderItem>(orderUser, headers, HttpStatus.CREATED);
		} catch (Exception e) {
			headers.add(ResponseMsg.HTTP_HEADER_NAME, respMsgUtil.getStr(MS_CODE, ResponseMsg.HTTP_500, UPDATE_FAILED) );
			appUserResponse = new ResponseEntity<OrderItem>( tobemerged, headers, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return appUserResponse;
	}

	@Override
	public ResponseEntity<OrderItem> delete(@PathVariable(value="id") Long id) {
		ResponseEntity<OrderItem> appUserResponse;
		HttpHeaders headers = new HttpHeaders();
		try{
		 orderItemBean.delete(logUtil.getPreStr(LOGSTR_MS, loggedInUser), id);
		
		
			headers.add(ResponseMsg.HTTP_HEADER_NAME, respMsgUtil.getStr(MS_CODE, ResponseMsg.HTTP_200, DELETE_SUCCESS) );
			appUserResponse = new ResponseEntity<OrderItem>(null, headers, HttpStatus.OK);
		}catch(Exception e){
			headers.add(ResponseMsg.HTTP_HEADER_NAME, respMsgUtil.getStr(MS_CODE, ResponseMsg.HTTP_404, DELETE_FAILED) );
			appUserResponse = new ResponseEntity<OrderItem>(null, headers, HttpStatus.NOT_FOUND);
		}
		
		return appUserResponse;
	}	

	@RequestMapping(value="orderid/{orderid}", method = RequestMethod.GET)
	public ResponseEntity<List<OrderItemDto>> readAllOrderItemsByid(@PathVariable(value="orderid")Long orderid) {
		//TODO Comment/Uncomment below line based on your requirement
		ResponseEntity<List<OrderItemDto>> appUserResponse;
		HttpHeaders headers = new HttpHeaders();
		
		List<OrderItemDto> appUserRecords = null;
		try {
			appUserRecords = orderItemBean.getOrderDetail(logUtil.getPreStr(LOGSTR_MS, loggedInUser),orderid);
			headers.add(ResponseMsg.HTTP_HEADER_NAME, respMsgUtil.getStr(MS_CODE, ResponseMsg.HTTP_200, RETRIEVE_SUCCESS) );
			appUserResponse = new ResponseEntity<List<OrderItemDto>>(appUserRecords, headers, HttpStatus.OK);
		} catch (Exception e) {
			headers.add(ResponseMsg.HTTP_HEADER_NAME, respMsgUtil.getStr(MS_CODE, ResponseMsg.HTTP_404, RETRIEVE_FAILED) );
			appUserResponse = new ResponseEntity<List<OrderItemDto>>(appUserRecords, headers, HttpStatus.NOT_FOUND);
		}

		
		return appUserResponse;
	}
	
	//controller for read by oid and uid
	@RequestMapping(value = "oid/{oid}/uid/{uid}", method = RequestMethod.GET)
	public ResponseEntity<List<OrderItemDto>> readAllByOidAndUid(@PathVariable(value = "oid") Long orderid,@PathVariable(value = "uid") Long userid) {
		//TODO Comment/Uncomment below line based on your requirement
		ResponseEntity<List<OrderItemDto>> appUserResponse;
		HttpHeaders headers = new HttpHeaders();
		
		List<OrderItemDto> orderItemRecords = null;
		try {
			orderItemRecords = orderItemBean.readAllByOidAndUid(logUtil.getPreStr(LOGSTR_MS, loggedInUser),orderid,userid);
			headers.add(ResponseMsg.HTTP_HEADER_NAME, respMsgUtil.getStr(MS_CODE, ResponseMsg.HTTP_200, RETRIEVE_SUCCESS) );
			appUserResponse = new ResponseEntity<List<OrderItemDto>>(orderItemRecords, headers, HttpStatus.OK);
		} catch (Exception e) {
			headers.add(ResponseMsg.HTTP_HEADER_NAME, respMsgUtil.getStr(MS_CODE, ResponseMsg.HTTP_404, RETRIEVE_FAILED) );
			appUserResponse = new ResponseEntity<List<OrderItemDto>>(orderItemRecords, headers, HttpStatus.NOT_FOUND);
		}
		return appUserResponse;
	}
	
	//read Order by date 
	@RequestMapping(value="date/{date}", method = RequestMethod.GET)
	public ResponseEntity<List<OrderItemDto>> readAllOrderItemsByDate(@PathVariable(value="date")Date date) {
		//TODO Comment/Uncomment below line based on your requirement
		ResponseEntity<List<OrderItemDto>> appUserResponse;
		HttpHeaders headers = new HttpHeaders();
		
		List<OrderItemDto> appUserRecords = null;
		try {
			appUserRecords = orderItemBean.getOrderDetailbyDate(logUtil.getPreStr(LOGSTR_MS, loggedInUser),date);
			headers.add(ResponseMsg.HTTP_HEADER_NAME, respMsgUtil.getStr(MS_CODE, ResponseMsg.HTTP_200, RETRIEVE_SUCCESS) );
			appUserResponse = new ResponseEntity<List<OrderItemDto>>(appUserRecords, headers, HttpStatus.OK);
		} catch (Exception e) {
			headers.add(ResponseMsg.HTTP_HEADER_NAME, respMsgUtil.getStr(MS_CODE, ResponseMsg.HTTP_404, RETRIEVE_FAILED) );
			appUserResponse = new ResponseEntity<List<OrderItemDto>>(appUserRecords, headers, HttpStatus.NOT_FOUND);
		}

		
		return appUserResponse;
	}
	
}
