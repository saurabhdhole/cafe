package com.mm.ms.rest;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mm.ms.BaseAbstractController;
import com.mm.ms.bean.UserBean;
import com.mm.ms.entity.User;
import com.mm.ms.util.Const;
import com.mm.ms.util.LogUtil;
import com.mm.ms.util.RespMsgUtil;
import com.mm.ms.util.ResponseMsg;

@RestController
@RequestMapping("/user")
public class UserController extends BaseAbstractController<User, Long>{	
	
	public static final String MS_CODE = "USER";
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
	UserBean userBean;
	
	@Autowired
	RespMsgUtil respMsgUtil;

	@Override
	public ResponseEntity<User> create(@RequestBody User inputentity) {
		ResponseEntity<User> userResponse;
		HttpHeaders headers = new HttpHeaders();
		
		User user = userBean.create(logUtil.getPreStr(LOGSTR_MS, loggedInUser), inputentity);
		
		if (null != user){
			headers.add(ResponseMsg.HTTP_HEADER_NAME, respMsgUtil.getStr(MS_CODE, ResponseMsg.HTTP_201, CREATE_SUCCESS) );
			userResponse = new ResponseEntity<User>(user, headers, HttpStatus.CREATED);
		} else{
			headers.add(ResponseMsg.HTTP_HEADER_NAME, respMsgUtil.getStr(MS_CODE, ResponseMsg.HTTP_500, CREATE_FAILED) );
			userResponse = new ResponseEntity<User>( inputentity, headers, HttpStatus.INTERNAL_SERVER_ERROR);
		}		
		return userResponse;
	}

	@Override
	@ExceptionHandler(Exception.class)
	public ResponseEntity<User> read(@PathVariable(value="id")Long id) throws Exception {
		//TODO Comment/Uncomment below line based on your requirement
		ResponseEntity<User> appUserResponse = null;
		HttpHeaders headers = new HttpHeaders();
		User appUser = null;
		try {
			appUser = userBean.read(logUtil.getPreStr(LOGSTR_MS, loggedInUser), id);

			if (null != appUser) {
				headers.add(ResponseMsg.HTTP_HEADER_NAME, respMsgUtil.getStr(MS_CODE, ResponseMsg.HTTP_200, RETRIEVE_SUCCESS));
				appUserResponse = new ResponseEntity<User>(appUser, headers, HttpStatus.OK);
			} 
//			else {
//				headers.add(ResponseMsg.HTTP_HEADER_NAME, respMsgUtil.getStr(MS_CODE, ResponseMsg.HTTP_404, RETRIEVE_FAILED));
//				appUserResponse = new ResponseEntity<User>(appUser, headers, HttpStatus.NOT_FOUND);
//			}
		} catch (Exception e) {
			headers.add(ResponseMsg.HTTP_HEADER_NAME, respMsgUtil.getStr(MS_CODE, ResponseMsg.HTTP_404, RETRIEVE_FAILED));
			appUserResponse = new ResponseEntity<User>(appUser, headers, HttpStatus.NOT_FOUND);
		}
		return appUserResponse;
	}

	@Override
	public ResponseEntity<Iterable<User>> readAll() {
		//TODO Comment/Uncomment below line based on your requirement
		ResponseEntity<Iterable<User>> appUserResponse;
		HttpHeaders headers = new HttpHeaders();
		
		Iterable<User> appUserRecords = userBean.readAll(logUtil.getPreStr(LOGSTR_MS, loggedInUser));
		
		if (null != appUserRecords){
			headers.add(ResponseMsg.HTTP_HEADER_NAME, respMsgUtil.getStr(MS_CODE, ResponseMsg.HTTP_200, RETRIEVE_SUCCESS) );
			appUserResponse = new ResponseEntity<Iterable<User>>(appUserRecords, headers, HttpStatus.OK);
		}else{
			headers.add(ResponseMsg.HTTP_HEADER_NAME, respMsgUtil.getStr(MS_CODE, ResponseMsg.HTTP_404, RETRIEVE_FAILED) );
			appUserResponse = new ResponseEntity<Iterable<User>>(appUserRecords, headers, HttpStatus.NOT_FOUND);
		}
		
		return appUserResponse;
	}

	@Override
	public ResponseEntity<Iterable<User>> readAllPageable(
			@PathParam("firstresult") Integer firstresult,
			@PathParam("maxresult") Integer maxresult,
			@PathParam("sortdir") String sortdir,
			@PathParam("sortfield") String sortfield) {
		//TODO Comment/Uncomment below line based on your requirement
		ResponseEntity<Iterable<User>> appUserResponse;
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
		
		Iterable<User> appUserRecords = userBean.readAll(logUtil.getPreStr(LOGSTR_MS, loggedInUser),pageable);
		
		if (null != appUserRecords){
			headers.add(ResponseMsg.HTTP_HEADER_NAME, respMsgUtil.getStr(MS_CODE, ResponseMsg.HTTP_200, RETRIEVE_SUCCESS) );
			appUserResponse = new ResponseEntity<Iterable<User>>(appUserRecords, headers, HttpStatus.OK);
		}else{
			headers.add(ResponseMsg.HTTP_HEADER_NAME, respMsgUtil.getStr(MS_CODE, ResponseMsg.HTTP_404, RETRIEVE_FAILED) );
			appUserResponse = new ResponseEntity<Iterable<User>>(appUserRecords, headers, HttpStatus.NOT_FOUND);
		}
		
		return appUserResponse;
	}

	@Override
	public ResponseEntity<User> update(@RequestBody User tobemerged) {
		ResponseEntity<User> appUserResponse;
		HttpHeaders headers = new HttpHeaders();
		
		User appUser = userBean.update(logUtil.getPreStr(LOGSTR_MS, loggedInUser), tobemerged);
		
		if (null != appUser){
			headers.add(ResponseMsg.HTTP_HEADER_NAME, respMsgUtil.getStr(MS_CODE, ResponseMsg.HTTP_201, UPDATE_SUCCESS) );
			appUserResponse = new ResponseEntity<User>(appUser, headers, HttpStatus.CREATED);
		} else{
			headers.add(ResponseMsg.HTTP_HEADER_NAME, respMsgUtil.getStr(MS_CODE, ResponseMsg.HTTP_500, UPDATE_FAILED) );
			appUserResponse = new ResponseEntity<User>( tobemerged, headers, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return appUserResponse;
	}

	@Override
	public ResponseEntity<User> delete(@PathVariable(value="id") Long id) {
		ResponseEntity<User> appUserResponse;
		HttpHeaders headers = new HttpHeaders();
		
		Boolean isdeleted = userBean.delete(logUtil.getPreStr(LOGSTR_MS, loggedInUser), id);
		
		if (isdeleted){
			headers.add(ResponseMsg.HTTP_HEADER_NAME, respMsgUtil.getStr(MS_CODE, ResponseMsg.HTTP_200, DELETE_SUCCESS) );
			appUserResponse = new ResponseEntity<User>(null, headers, HttpStatus.OK);
		}else{
			headers.add(ResponseMsg.HTTP_HEADER_NAME, respMsgUtil.getStr(MS_CODE, ResponseMsg.HTTP_404, DELETE_FAILED) );
			appUserResponse = new ResponseEntity<User>(null, headers, HttpStatus.NOT_FOUND);
		}
		
		return appUserResponse;
	}	
}