package com.mm.ms.rest;

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
import com.mm.ms.bean.OrderBean;
import com.mm.ms.entity.Foodorder;
import com.mm.ms.util.Const;
import com.mm.ms.util.LogUtil;
import com.mm.ms.util.RespMsgUtil;
import com.mm.ms.util.ResponseMsg;

@RestController
@RequestMapping("/order")
public class OrderController extends BaseAbstractController<Foodorder, Long>{	
	
	public static final String MS_CODE = "ORDER";
	//TODO Change below line based on your Microservice if not correct
	public static final String LOGSTR_MS = Const.LOGSTR_FOMS;
	private String loggedInUser = Const.DEFAULT_USER;
		
	private static final String CREATE_SUCCESS = "Successfully created order.";
	private static final String CREATE_FAILED = "Foodorder creation failed.";
		
	private static final String RETRIEVE_SUCCESS = "Successfully retrieved order record/s.";
	private static final String RETRIEVE_FAILED = "Foodorder retrieval failed.";
		
	private static final String UPDATE_SUCCESS = "Successfully updated order.";
	private static final String UPDATE_FAILED = "Foodorder update failed.";
		
	private static final String DELETE_SUCCESS = "Successfully deleted order.";
	private static final String DELETE_FAILED = "Foodorder deletion failed.";
	
	@Autowired
	LogUtil logUtil;
	
	@Autowired
	OrderBean orderBean;
	
	@Autowired
	RespMsgUtil respMsgUtil;

	@Override
	public ResponseEntity<Foodorder> create(@RequestBody Foodorder inputentity) {
		ResponseEntity<Foodorder> userResponse;
		HttpHeaders headers = new HttpHeaders();
		
		Foodorder order = orderBean.create(logUtil.getPreStr(LOGSTR_MS, loggedInUser), inputentity);
		
		if (null != order){
			headers.add(ResponseMsg.HTTP_HEADER_NAME, respMsgUtil.getStr(MS_CODE, ResponseMsg.HTTP_201, CREATE_SUCCESS) );
			userResponse = new ResponseEntity<Foodorder>(order, headers, HttpStatus.CREATED);
		} else{
			headers.add(ResponseMsg.HTTP_HEADER_NAME, respMsgUtil.getStr(MS_CODE, ResponseMsg.HTTP_500, CREATE_FAILED) );
			userResponse = new ResponseEntity<Foodorder>( inputentity, headers, HttpStatus.INTERNAL_SERVER_ERROR);
		}		
		return userResponse;
	}

	@Override
	public ResponseEntity<Foodorder> read(@PathVariable(value="id")Long id) {
		//TODO Comment/Uncomment below line based on your requirement
		ResponseEntity<Foodorder> appUserResponse;
		HttpHeaders headers = new HttpHeaders();
		
		Foodorder appUser = orderBean.read(logUtil.getPreStr(LOGSTR_MS, loggedInUser), id);
		
		if (null != appUser){
			headers.add(ResponseMsg.HTTP_HEADER_NAME, respMsgUtil.getStr(MS_CODE, ResponseMsg.HTTP_200, RETRIEVE_SUCCESS) );
			appUserResponse = new ResponseEntity<Foodorder>(appUser, headers, HttpStatus.OK);
		}else{
			headers.add(ResponseMsg.HTTP_HEADER_NAME, respMsgUtil.getStr(MS_CODE, ResponseMsg.HTTP_404, RETRIEVE_FAILED) );
			appUserResponse = new ResponseEntity<Foodorder>(appUser, headers, HttpStatus.NOT_FOUND);
		}
		
		return appUserResponse;
	}

	
	
	
	
	@RequestMapping(value="status/{status}", method = RequestMethod.GET)
	public ResponseEntity<Foodorder> readByStatus(@PathVariable(value="status")String status) {
		//TODO Comment/Uncomment below line based on your requirement
		ResponseEntity<Foodorder> orderResponse;
		HttpHeaders headers = new HttpHeaders();
		
		Foodorder foodorder = orderBean.readByStatus(logUtil.getPreStr(LOGSTR_MS, loggedInUser), status);
		
		if (null != foodorder){
			headers.add(ResponseMsg.HTTP_HEADER_NAME, respMsgUtil.getStr(MS_CODE, ResponseMsg.HTTP_200, RETRIEVE_SUCCESS) );
			orderResponse = new ResponseEntity<Foodorder>(foodorder, headers, HttpStatus.OK);
		}else{
			headers.add(ResponseMsg.HTTP_HEADER_NAME, respMsgUtil.getStr(MS_CODE, ResponseMsg.HTTP_404, RETRIEVE_FAILED) );
			orderResponse = new ResponseEntity<Foodorder>(foodorder, headers, HttpStatus.NOT_FOUND);
		}
		
		return orderResponse;
	}
	
	
	
	
	
	@Override
	public ResponseEntity<Iterable<Foodorder>> readAll() {
		//TODO Comment/Uncomment below line based on your requirement
		ResponseEntity<Iterable<Foodorder>> appUserResponse;
		HttpHeaders headers = new HttpHeaders();
		
		Iterable<Foodorder> appUserRecords = orderBean.readAll(logUtil.getPreStr(LOGSTR_MS, loggedInUser));
		
		if (null != appUserRecords){
			headers.add(ResponseMsg.HTTP_HEADER_NAME, respMsgUtil.getStr(MS_CODE, ResponseMsg.HTTP_200, RETRIEVE_SUCCESS) );
			appUserResponse = new ResponseEntity<Iterable<Foodorder>>(appUserRecords, headers, HttpStatus.OK);
		}else{
			headers.add(ResponseMsg.HTTP_HEADER_NAME, respMsgUtil.getStr(MS_CODE, ResponseMsg.HTTP_404, RETRIEVE_FAILED) );
			appUserResponse = new ResponseEntity<Iterable<Foodorder>>(appUserRecords, headers, HttpStatus.NOT_FOUND);
		}
		
		return appUserResponse;
	}

	@Override
	public ResponseEntity<Iterable<Foodorder>> readAllPageable(
			@PathParam("firstresult") Integer firstresult,
			@PathParam("maxresult") Integer maxresult,
			@PathParam("sortdir") String sortdir,
			@PathParam("sortfield") String sortfield) {
		//TODO Comment/Uncomment below line based on your requirement
		ResponseEntity<Iterable<Foodorder>> appUserResponse;
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
		
		Iterable<Foodorder> appUserRecords = orderBean.readAll(logUtil.getPreStr(LOGSTR_MS, loggedInUser),pageable);
		
		if (null != appUserRecords){
			headers.add(ResponseMsg.HTTP_HEADER_NAME, respMsgUtil.getStr(MS_CODE, ResponseMsg.HTTP_200, RETRIEVE_SUCCESS) );
			appUserResponse = new ResponseEntity<Iterable<Foodorder>>(appUserRecords, headers, HttpStatus.OK);
		}else{
			headers.add(ResponseMsg.HTTP_HEADER_NAME, respMsgUtil.getStr(MS_CODE, ResponseMsg.HTTP_404, RETRIEVE_FAILED) );
			appUserResponse = new ResponseEntity<Iterable<Foodorder>>(appUserRecords, headers, HttpStatus.NOT_FOUND);
		}
		
		return appUserResponse;
	}

	@Override
	public ResponseEntity<Foodorder> update(@RequestBody Foodorder tobemerged) {
		ResponseEntity<Foodorder> appUserResponse;
		HttpHeaders headers = new HttpHeaders();
		
		Foodorder appUser = orderBean.update(logUtil.getPreStr(LOGSTR_MS, loggedInUser), tobemerged);
		
		if (null != appUser){
			headers.add(ResponseMsg.HTTP_HEADER_NAME, respMsgUtil.getStr(MS_CODE, ResponseMsg.HTTP_201, UPDATE_SUCCESS) );
			appUserResponse = new ResponseEntity<Foodorder>(appUser, headers, HttpStatus.CREATED);
		} else{
			headers.add(ResponseMsg.HTTP_HEADER_NAME, respMsgUtil.getStr(MS_CODE, ResponseMsg.HTTP_500, UPDATE_FAILED) );
			appUserResponse = new ResponseEntity<Foodorder>( tobemerged, headers, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return appUserResponse;
	}

	@Override
	public ResponseEntity<Foodorder> delete(@PathVariable(value="id") Long id) {
		ResponseEntity<Foodorder> appUserResponse;
		HttpHeaders headers = new HttpHeaders();
		
		Boolean isdeleted = orderBean.delete(logUtil.getPreStr(LOGSTR_MS, loggedInUser), id);
		
		if (isdeleted){
			headers.add(ResponseMsg.HTTP_HEADER_NAME, respMsgUtil.getStr(MS_CODE, ResponseMsg.HTTP_200, DELETE_SUCCESS) );
			appUserResponse = new ResponseEntity<Foodorder>(null, headers, HttpStatus.OK);
		}else{
			headers.add(ResponseMsg.HTTP_HEADER_NAME, respMsgUtil.getStr(MS_CODE, ResponseMsg.HTTP_404, DELETE_FAILED) );
			appUserResponse = new ResponseEntity<Foodorder>(null, headers, HttpStatus.NOT_FOUND);
		}
		
		return appUserResponse;
	}	
}