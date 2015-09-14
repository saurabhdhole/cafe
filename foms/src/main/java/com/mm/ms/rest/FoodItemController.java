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
import org.springframework.web.bind.annotation.RestController;

import com.mm.ms.BaseAbstractController;
import com.mm.ms.bean.FoodItemBean;
import com.mm.ms.entity.FoodItem;
import com.mm.ms.util.Const;
import com.mm.ms.util.LogUtil;
import com.mm.ms.util.RespMsgUtil;
import com.mm.ms.util.ResponseMsg;

@RestController
@RequestMapping("/fooditem")
public class FoodItemController extends BaseAbstractController<FoodItem, Long>{	
	
	public static final String MS_CODE = "FoodItem";
	//TODO Change below line based on your Microservice if not correct
	public static final String LOGSTR_MS = Const.LOGSTR_FOMS;
	private String loggedInUser = Const.DEFAULT_USER;
		
	private static final String CREATE_SUCCESS = "Successfully created user.";
	private static final String CREATE_FAILED = "User creation failed.";
		
	private static final String RETRIEVE_SUCCESS = "Successfully retrieved user record/s.";
	private static final String RETRIEVE_FAILED = "User retrieval failed.";
		
	private static final String UPDATE_SUCCESS = "Successfully updated user.";
	private static final String UPDATE_FAILED = "User update failed.";
		
	private static final String DELETE_SUCCESS = "Successfully deleted user.";
	private static final String DELETE_FAILED = "User deletion failed.";
	
	@Autowired
	LogUtil logUtil;
	
	@Autowired
	FoodItemBean foodIemBean;
	
	@Autowired
	RespMsgUtil respMsgUtil;
	
	
	
	
	@Override
	public ResponseEntity<FoodItem> create(@RequestBody FoodItem inputentity) {
		ResponseEntity<FoodItem> fooditemResponse;
		HttpHeaders headers = new HttpHeaders();
		
		FoodItem foodItem = foodIemBean.create(logUtil.getPreStr(LOGSTR_MS, loggedInUser), inputentity);
		
		if (null != foodIemBean){
			headers.add(ResponseMsg.HTTP_HEADER_NAME, respMsgUtil.getStr(MS_CODE, ResponseMsg.HTTP_201, CREATE_SUCCESS) );
			fooditemResponse = new ResponseEntity<FoodItem>(foodItem, headers, HttpStatus.CREATED);
		} else{
			headers.add(ResponseMsg.HTTP_HEADER_NAME, respMsgUtil.getStr(MS_CODE, ResponseMsg.HTTP_500, CREATE_FAILED) );
			fooditemResponse = new ResponseEntity<FoodItem>( inputentity, headers, HttpStatus.INTERNAL_SERVER_ERROR);
		}		
		return fooditemResponse;
	}
	 
	
	

	@Override
	public ResponseEntity<FoodItem> read(@PathVariable(value="id")Long id) {
		//TODO Comment/Uncomment below line based on your requirement
		ResponseEntity<FoodItem> fooditemResponse;
		HttpHeaders headers = new HttpHeaders();
		
		FoodItem foodItem = foodIemBean.read(logUtil.getPreStr(LOGSTR_MS, loggedInUser), id);
		
		if (null != foodItem){
			headers.add(ResponseMsg.HTTP_HEADER_NAME, respMsgUtil.getStr(MS_CODE, ResponseMsg.HTTP_200, RETRIEVE_SUCCESS) );
			fooditemResponse = new ResponseEntity<FoodItem>(foodItem, headers, HttpStatus.OK);
		}else{
			headers.add(ResponseMsg.HTTP_HEADER_NAME, respMsgUtil.getStr(MS_CODE, ResponseMsg.HTTP_404, RETRIEVE_FAILED) );
			fooditemResponse = new ResponseEntity<FoodItem>(foodItem, headers, HttpStatus.NOT_FOUND);
		}
		
		return fooditemResponse;
	}

	@Override
	public ResponseEntity<Iterable<FoodItem>> readAll()  {
		//TODO Comment/Uncomment below line based on your requirement
		ResponseEntity<Iterable<FoodItem>> fooditemResponse;
		HttpHeaders headers = new HttpHeaders();
		
		Iterable<FoodItem> foodItemRecords = foodIemBean.readAll(logUtil.getPreStr(LOGSTR_MS, loggedInUser));
		
		if (null != foodItemRecords){
			headers.add(ResponseMsg.HTTP_HEADER_NAME, respMsgUtil.getStr(MS_CODE, ResponseMsg.HTTP_200, RETRIEVE_SUCCESS) );
			fooditemResponse = new ResponseEntity<Iterable<FoodItem>>(foodItemRecords, headers, HttpStatus.OK);
		}else{
			headers.add(ResponseMsg.HTTP_HEADER_NAME, respMsgUtil.getStr(MS_CODE, ResponseMsg.HTTP_404, RETRIEVE_FAILED) );
			fooditemResponse = new ResponseEntity<Iterable<FoodItem>>(foodItemRecords, headers, HttpStatus.NOT_FOUND);
		}
		
		return fooditemResponse;
	}

	@Override
	public ResponseEntity<Iterable<FoodItem>> readAllPageable(
			@PathParam("firstresult") Integer firstresult,
			@PathParam("maxresult") Integer maxresult,
			@PathParam("sortdir") String sortdir,
			@PathParam("sortfield") String sortfield) {
		//TODO Comment/Uncomment below line based on your requirement
		ResponseEntity<Iterable<FoodItem>> fooditemResponse;
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
		
		Iterable<FoodItem> foodItemRecords = foodIemBean.readAll(logUtil.getPreStr(LOGSTR_MS, loggedInUser),pageable);
		
		if (null != foodItemRecords){
			headers.add(ResponseMsg.HTTP_HEADER_NAME, respMsgUtil.getStr(MS_CODE, ResponseMsg.HTTP_200, RETRIEVE_SUCCESS) );
			fooditemResponse = new ResponseEntity<Iterable<FoodItem>>(foodItemRecords, headers, HttpStatus.OK);
		}else{
			headers.add(ResponseMsg.HTTP_HEADER_NAME, respMsgUtil.getStr(MS_CODE, ResponseMsg.HTTP_404, RETRIEVE_FAILED) );
			fooditemResponse = new ResponseEntity<Iterable<FoodItem>>(foodItemRecords, headers, HttpStatus.NOT_FOUND);
		}
		
		return fooditemResponse;
	}

	@Override
	public ResponseEntity<FoodItem> update(@RequestBody FoodItem tobemerged) {
		ResponseEntity<FoodItem> fooditemResponse;
		HttpHeaders headers = new HttpHeaders();
		
		FoodItem foodItem = foodIemBean.update(logUtil.getPreStr(LOGSTR_MS, loggedInUser), tobemerged);
		
		if (null != foodItem){
			headers.add(ResponseMsg.HTTP_HEADER_NAME, respMsgUtil.getStr(MS_CODE, ResponseMsg.HTTP_201, UPDATE_SUCCESS) );
			fooditemResponse = new ResponseEntity<FoodItem>(foodItem, headers, HttpStatus.CREATED);
		} else{
			headers.add(ResponseMsg.HTTP_HEADER_NAME, respMsgUtil.getStr(MS_CODE, ResponseMsg.HTTP_500, UPDATE_FAILED) );
			fooditemResponse = new ResponseEntity<FoodItem>( tobemerged, headers, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return fooditemResponse;
	}

	@Override
	public ResponseEntity<FoodItem> delete(@PathVariable(value="id") Long id) {
		ResponseEntity<FoodItem> fooditemResponse;
		HttpHeaders headers = new HttpHeaders();
		
		Boolean isdeleted = foodIemBean.delete(logUtil.getPreStr(LOGSTR_MS, loggedInUser), id);
		
		if (isdeleted){
			headers.add(ResponseMsg.HTTP_HEADER_NAME, respMsgUtil.getStr(MS_CODE, ResponseMsg.HTTP_200, DELETE_SUCCESS) );
			fooditemResponse = new ResponseEntity<FoodItem>(null, headers, HttpStatus.OK);
		}else{
			headers.add(ResponseMsg.HTTP_HEADER_NAME, respMsgUtil.getStr(MS_CODE, ResponseMsg.HTTP_404, DELETE_FAILED) );
			fooditemResponse = new ResponseEntity<FoodItem>(null, headers, HttpStatus.NOT_FOUND);
		}
		
		return fooditemResponse;
	}	
}