package com.storeflex.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.data.domain.Pageable;

import com.storeflex.beans.ClientProfileListBean;
import com.storeflex.beans.StoreFlexClientBean;
import com.storeflex.entities.ClientProfile;
import com.storeflex.exceptions.StoreFlexServiceException;
import com.storeflex.response.StoreFlexResponse;
import com.storeflex.response.StoreFlexResponse.Status;
import com.storeflex.services.StoreFlexClientService;

import io.swagger.annotations.ApiOperation;

@RestController
public class StoreFlexClientController {
	private static final Logger log = LoggerFactory.getLogger(StoreFlexClientController.class);

	@Autowired
	StoreFlexClientService service;
	
	@PostMapping(value="/client" , produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value="client" , notes ="create storeflex client" , nickname="client")
	public StoreFlexResponse<Object> storeFlexClient(@Validated @RequestBody StoreFlexClientBean request){
		StoreFlexResponse<Object> response = new StoreFlexResponse<Object>();
		Object object = null;
		try {
			ClientProfile clientProfile = service.createFlexClient(request);
			object = service.getStoreFlexClient(clientProfile.getClientId());
			 if(null!=object) {
				 response.setStatus(Status.SUCCESS);
				 response.setStatusCode(Status.SUCCESS.getCode());
				 response.setMessage("Client "+request.getCompyName()+" record submitted");
				 response.setMethodReturnValue(object);
			 }else {
				 response.setStatus(Status.BUSENESS_ERROR);
				 response.setStatusCode(Status.BUSENESS_ERROR.getCode()); 
				 response.setMessage("System Error....");
			 }
		}
		catch(StoreFlexServiceException e){
			 response.setStatus(Status.BUSENESS_ERROR);
			 response.setStatusCode(Status.BUSENESS_ERROR.getCode()); 
			 response.setMessage("System Error...."+e.getMessage());
		}
		
		return response;
		
	}

	@GetMapping(value="/client" , produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value="client" , notes ="get client details by its Id" , nickname="client")
	public StoreFlexResponse<Object> getStoreFlexClientById(@RequestParam String clientId){
		 StoreFlexResponse<Object> response = new StoreFlexResponse<Object>();
		 try {
			Object object= service.getStoreFlexClient(clientId);
			 if(null!=object) {
				 response.setStatus(Status.SUCCESS);
				 response.setStatusCode(Status.SUCCESS.getCode());
				 response.setMessage("Client details for Id"+clientId);
				 response.setMethodReturnValue(object);
			 }else {
				 response.setStatus(Status.BUSENESS_ERROR);
				 response.setStatusCode(Status.BUSENESS_ERROR.getCode()); 
				 response.setMessage("System Error....");
			 }
			 
		 }
		 catch(StoreFlexServiceException e) {
			 response.setStatus(Status.BUSENESS_ERROR);
			 response.setStatusCode(Status.BUSENESS_ERROR.getCode()); 
			 response.setMessage("System Error...."+e.getMessage()); 
		 }
		 
		return response;
		
	}
	
	@GetMapping(value="/clients" , produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value="clients" , notes ="get all storeflex client" , nickname="clients")
	public StoreFlexResponse<ClientProfileListBean> getStoreFlexClients(
			@RequestParam(defaultValue = "0") int page,
	        @RequestParam(defaultValue = "3") int size
			){
		 StoreFlexResponse<ClientProfileListBean> response = new StoreFlexResponse<ClientProfileListBean>();
		 Pageable paging = PageRequest.of(page, size);
		 try {
			ClientProfileListBean objectList = service.getStoreFlexClients(paging);
		 	 if(null!=objectList) {
				 response.setStatus(Status.SUCCESS);
				 response.setStatusCode(Status.SUCCESS.getCode());
				 response.setMethodReturnValue(objectList);
			 }else {
				 response.setStatus(Status.BUSENESS_ERROR);
				 response.setStatusCode(Status.BUSENESS_ERROR.getCode()); 
				 response.setMessage("No Records Found");
			 }
		}
		catch(StoreFlexServiceException e){
			 response.setStatus(Status.BUSENESS_ERROR);
			 response.setStatusCode(Status.BUSENESS_ERROR.getCode()); 
			 response.setMessage("System Error...."+e.getMessage());
		}
		return response;
		
	}
	
	


}

