package com.mm.ms.rest;

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
import com.mm.ms.bean.ReconciliationBean;
import com.mm.ms.dto.ReconciliationDto;
import com.mm.ms.entity.Reconciliation;
import com.mm.ms.util.Const;
import com.mm.ms.util.LogUtil;
import com.mm.ms.util.RespMsgUtil;
import com.mm.ms.util.ResponseMsg;

@RestController
@RequestMapping("/reconcile")
public class ReconciliationController extends
		BaseAbstractController<Reconciliation, Long> {

	public static final String MS_CODE = "RECONCILE";
	// TODO Change below line based on your Microservice if not correct
	public static final String LOGSTR_MS = Const.LOGSTR_FOMS;
	private String loggedInUser = Const.DEFAULT_USER;

	private static final String CREATE_SUCCESS = "Successfully created reconcile.";
	private static final String CREATE_FAILED = "Reconciliation creation failed.";

	private static final String RETRIEVE_SUCCESS = "Successfully retrieved reconcile record/s.";
	private static final String RETRIEVE_FAILED = "Reconciliation retrieval failed.";

	private static final String UPDATE_SUCCESS = "Successfully updated reconcile.";
	private static final String UPDATE_FAILED = "Reconciliation update failed.";

	private static final String DELETE_SUCCESS = "Successfully deleted reconcile.";
	private static final String DELETE_FAILED = "Reconciliation deletion failed.";

	@Autowired
	LogUtil logUtil;

	@Autowired
	ReconciliationBean reconciliationBean;

	@Autowired
	RespMsgUtil respMsgUtil;

	@Override
	public ResponseEntity<Reconciliation> create(
			@RequestBody Reconciliation inputentity) {
		ResponseEntity<Reconciliation> reconciliationResponse;
		HttpHeaders headers = new HttpHeaders();

		Reconciliation reconcile = reconciliationBean.create(
				logUtil.getPreStr(LOGSTR_MS, loggedInUser), inputentity);

		if (null != reconcile) {
			headers.add(ResponseMsg.HTTP_HEADER_NAME, respMsgUtil.getStr(
					MS_CODE, ResponseMsg.HTTP_201, CREATE_SUCCESS));
			reconciliationResponse = new ResponseEntity<Reconciliation>(
					reconcile, headers, HttpStatus.CREATED);
		} else {
			headers.add(ResponseMsg.HTTP_HEADER_NAME, respMsgUtil.getStr(
					MS_CODE, ResponseMsg.HTTP_500, CREATE_FAILED));
			reconciliationResponse = new ResponseEntity<Reconciliation>(
					inputentity, headers, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return reconciliationResponse;
	}

	@Override
	public ResponseEntity<Reconciliation> read(
			@PathVariable(value = "id") Long id) {
		// TODO Comment/Uncomment below line based on your requirement
		ResponseEntity<Reconciliation> reconciliationResponse;
		HttpHeaders headers = new HttpHeaders();

		Reconciliation reconcile = null;
		try {
			reconcile = reconciliationBean.read(
					logUtil.getPreStr(LOGSTR_MS, loggedInUser), id);
			headers.add(ResponseMsg.HTTP_HEADER_NAME, respMsgUtil.getStr(
					MS_CODE, ResponseMsg.HTTP_200, RETRIEVE_SUCCESS));
			reconciliationResponse = new ResponseEntity<Reconciliation>(
					reconcile, headers, HttpStatus.OK);
		} catch (Exception e) {
			headers.add(ResponseMsg.HTTP_HEADER_NAME, respMsgUtil.getStr(
					MS_CODE, ResponseMsg.HTTP_404, RETRIEVE_FAILED));
			reconciliationResponse = new ResponseEntity<Reconciliation>(
					reconcile, headers, HttpStatus.NOT_FOUND);
		}
		return reconciliationResponse;
	}

	@Override
	public ResponseEntity<Iterable<Reconciliation>> readAll() {
		// TODO Comment/Uncomment below line based on your requirement
		ResponseEntity<Iterable<Reconciliation>> reconciliationResponse;
		HttpHeaders headers = new HttpHeaders();

		Iterable<Reconciliation> appUserRecords = null;
		try {
			appUserRecords = reconciliationBean
					.readAll(logUtil.getPreStr(LOGSTR_MS, loggedInUser));
			headers.add(ResponseMsg.HTTP_HEADER_NAME, respMsgUtil.getStr(
					MS_CODE, ResponseMsg.HTTP_200, RETRIEVE_SUCCESS));
			reconciliationResponse = new ResponseEntity<Iterable<Reconciliation>>(
					appUserRecords, headers, HttpStatus.OK);
		} catch (Exception e) {
			headers.add(ResponseMsg.HTTP_HEADER_NAME, respMsgUtil.getStr(
					MS_CODE, ResponseMsg.HTTP_404, RETRIEVE_FAILED));
			reconciliationResponse = new ResponseEntity<Iterable<Reconciliation>>(
					appUserRecords, headers, HttpStatus.NOT_FOUND);
		}
		return reconciliationResponse;
	}

	@Override
	public ResponseEntity<Iterable<Reconciliation>> readAllPageable(
			@PathParam("firstresult") Integer firstresult,
			@PathParam("maxresult") Integer maxresult,
			@PathParam("sortdir") String sortdir,
			@PathParam("sortfield") String sortfield) {
		// TODO Comment/Uncomment below line based on your requirement
		ResponseEntity<Iterable<Reconciliation>> reconciliationResponse;
		HttpHeaders headers = new HttpHeaders();

		// Default sort Direction and Field
		if (sortdir == null) {
			sortdir = "DESC";
		}
		if (sortfield == null) {
			// TODO Change it as per your requirement
			sortfield = "name";
		}
		Pageable pageable = new PageRequest(firstresult, maxresult,
				Sort.Direction.fromString(sortdir), sortfield);

		Iterable<Reconciliation> appUserRecords = null;
		try {
			appUserRecords = reconciliationBean.readAll(
					logUtil.getPreStr(LOGSTR_MS, loggedInUser), pageable);
			headers.add(ResponseMsg.HTTP_HEADER_NAME, respMsgUtil.getStr(
					MS_CODE, ResponseMsg.HTTP_200, RETRIEVE_SUCCESS));
			reconciliationResponse = new ResponseEntity<Iterable<Reconciliation>>(
					appUserRecords, headers, HttpStatus.OK);
		} catch (Exception e) {
			headers.add(ResponseMsg.HTTP_HEADER_NAME, respMsgUtil.getStr(
					MS_CODE, ResponseMsg.HTTP_404, RETRIEVE_FAILED));
			reconciliationResponse = new ResponseEntity<Iterable<Reconciliation>>(
					appUserRecords, headers, HttpStatus.NOT_FOUND);
		}
		return reconciliationResponse;
	}

	@Override
	public ResponseEntity<Reconciliation> update(
			@RequestBody Reconciliation tobemerged) {
		ResponseEntity<Reconciliation> reconciliationResponse;
		HttpHeaders headers = new HttpHeaders();

		Reconciliation reconcile;
		try {
			reconcile = reconciliationBean.update(
					logUtil.getPreStr(LOGSTR_MS, loggedInUser), tobemerged);
			headers.add(ResponseMsg.HTTP_HEADER_NAME, respMsgUtil.getStr(
					MS_CODE, ResponseMsg.HTTP_201, UPDATE_SUCCESS));
			reconciliationResponse = new ResponseEntity<Reconciliation>(
					reconcile, headers, HttpStatus.CREATED);
		} catch (Exception e) {
			headers.add(ResponseMsg.HTTP_HEADER_NAME, respMsgUtil.getStr(
					MS_CODE, ResponseMsg.HTTP_500, UPDATE_FAILED));
			reconciliationResponse = new ResponseEntity<Reconciliation>(
					tobemerged, headers, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return reconciliationResponse;
	}

	@Override
	public ResponseEntity<Reconciliation> delete(
			@PathVariable(value = "id") Long id) {
		ResponseEntity<Reconciliation> reconciliationResponse;
		HttpHeaders headers = new HttpHeaders();
		try{
		 reconciliationBean.delete(
				logUtil.getPreStr(LOGSTR_MS, loggedInUser), id);
			headers.add(ResponseMsg.HTTP_HEADER_NAME, respMsgUtil.getStr(
					MS_CODE, ResponseMsg.HTTP_200, DELETE_SUCCESS));
			reconciliationResponse = new ResponseEntity<Reconciliation>(null,
					headers, HttpStatus.OK);
		} catch(Exception e) {
			headers.add(ResponseMsg.HTTP_HEADER_NAME, respMsgUtil.getStr(
					MS_CODE, ResponseMsg.HTTP_404, DELETE_FAILED));
			reconciliationResponse = new ResponseEntity<Reconciliation>(null,
					headers, HttpStatus.NOT_FOUND);
		}

		return reconciliationResponse;
	}

	@RequestMapping(value = "orderid/{orderid}/uid/{uid}", method = RequestMethod.POST)
	public Reconciliation makereconcile(
			@PathVariable(value = "orderid") Long orderid,
			@PathVariable(value = "uid") Long uid, @RequestBody ReconciliationDto entity)
	{ // TODO Comment/Uncomment below line based on your requirement
		ResponseEntity<Reconciliation> reconciliationResponse;
		HttpHeaders headers = new HttpHeaders();
		// for testing purpost only
		Reconciliation inputentity = null;
		try {
			inputentity = reconciliationBean.calculateamt(
					logUtil.getPreStr(LOGSTR_MS, loggedInUser), entity);
			headers.add(ResponseMsg.HTTP_HEADER_NAME, respMsgUtil.getStr(
					MS_CODE, ResponseMsg.HTTP_200, RETRIEVE_SUCCESS));
			reconciliationResponse = new ResponseEntity<Reconciliation>(
					inputentity, headers, HttpStatus.OK);
		} catch (Exception e) {
			headers.add(ResponseMsg.HTTP_HEADER_NAME, respMsgUtil.getStr(
					MS_CODE, ResponseMsg.HTTP_404, RETRIEVE_FAILED));
			reconciliationResponse = new ResponseEntity<Reconciliation>(
					inputentity, headers, HttpStatus.NOT_FOUND);
		}
		return inputentity;
	}

	// Controller
	@RequestMapping(value = "oname/{ordername}", method = RequestMethod.GET)
	public ResponseEntity<List<ReconciliationDto>> readAllByOid(
			@PathVariable(value = "ordername") String ordername) {
		// TODO Comment/Uncomment below line based on your requirement
		ResponseEntity<List<ReconciliationDto>> reconciliationDtoResponse;
		HttpHeaders headers = new HttpHeaders();

		List<ReconciliationDto> reconciliationDtoRecords = null;
		try {
			reconciliationDtoRecords = reconciliationBean.getRecordsByOid(logUtil.getPreStr(LOGSTR_MS, loggedInUser),ordername);
			headers.add(ResponseMsg.HTTP_HEADER_NAME, respMsgUtil.getStr(
					MS_CODE, ResponseMsg.HTTP_200, RETRIEVE_SUCCESS));
			reconciliationDtoResponse = new ResponseEntity<List<ReconciliationDto>>(
					reconciliationDtoRecords, headers, HttpStatus.OK);
		} catch (Exception e) {
			headers.add(ResponseMsg.HTTP_HEADER_NAME, respMsgUtil.getStr(
					MS_CODE, ResponseMsg.HTTP_404, RETRIEVE_FAILED));
			reconciliationDtoResponse = new ResponseEntity<List<ReconciliationDto>>(
					reconciliationDtoRecords, headers, HttpStatus.NOT_FOUND);
		}

		return reconciliationDtoResponse;
	}
	
	
	// Controller to change the status
		@RequestMapping(value = "status/{userid}/{orderid}", method = RequestMethod.GET)
		public ResponseEntity<Reconciliation> changeStatusByOname(
				@PathVariable(value = "userid") Long userid,@PathVariable(value="orderid")Long orderid) {
			// TODO Comment/Uncomment below line based on your requirement
			ResponseEntity<Reconciliation> reconciliationResponse;
			HttpHeaders headers = new HttpHeaders();

			Reconciliation reconciliation = null;
			try {
				reconciliation = reconciliationBean.clearStatusByname(logUtil.getPreStr(LOGSTR_MS, loggedInUser),userid,orderid);
				headers.add(ResponseMsg.HTTP_HEADER_NAME, respMsgUtil.getStr(
						MS_CODE, ResponseMsg.HTTP_200, RETRIEVE_SUCCESS));
				reconciliationResponse = new ResponseEntity<Reconciliation>(
						reconciliation, headers, HttpStatus.OK);
			} catch (Exception e) {
				headers.add(ResponseMsg.HTTP_HEADER_NAME, respMsgUtil.getStr(
						MS_CODE, ResponseMsg.HTTP_404, RETRIEVE_FAILED));
				reconciliationResponse = new ResponseEntity<Reconciliation>(
						reconciliation, headers, HttpStatus.NOT_FOUND);
			}

			return reconciliationResponse;
		}

}