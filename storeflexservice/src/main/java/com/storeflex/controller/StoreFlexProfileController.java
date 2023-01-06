package com.storeflex.controller;

import java.io.IOException;

import javax.mail.MessagingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.storeflex.beans.StoreFlexUserBean;
import com.storeflex.exceptions.StoreFlexServiceException;
import com.storeflex.helpers.StoreFlexClientHelper;
import com.storeflex.response.StoreFlexResponse;
import com.storeflex.response.StoreFlexResponse.Status;
import com.storeflex.services.StoreFlexService;

import io.swagger.annotations.ApiOperation;

@RestController
@CrossOrigin(origins = "*")
public class StoreFlexProfileController {
private static final Logger log = LoggerFactory.getLogger(StoreFlexProfileController.class);

@Autowired
StoreFlexService service;
@Autowired
StoreFlexClientHelper helper;

@PostMapping(value="/storeflexuser" ,consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
@ApiOperation(value="storeflexuser" , notes ="Create Store flex users" , nickname="storeflexuser")
public StoreFlexResponse<Object> storeFlexUser(@Validated @RequestBody StoreFlexUserBean req,@RequestParam String roleType,String clientCodes) throws MessagingException, IOException{
	 StoreFlexResponse<Object> response = new StoreFlexResponse<Object>();
	 Object object;
	 try {
		 if(null==req.getUserId()) {//new user
			 object = service.storeFlexUser(req,roleType);
				if(null!=object) {
					object = service.storeFlexUserFinalize(req,roleType,clientCodes);
					if(null!=object) {
						 helper.onboardUser(req);
						 response.setStatus(Status.SUCCESS);
						 response.setStatusCode(Status.SUCCESS.getCode());
						 response.setMethodReturnValue(object);
					 }else {
						 response.setStatus(Status.BUSENESS_ERROR);
						 response.setStatusCode(Status.BUSENESS_ERROR.getCode()); 
						 response.setMessage("System Error....");
					 }
				}else {
					 response.setStatus(Status.BUSENESS_ERROR);
					 response.setStatusCode(Status.BUSENESS_ERROR.getCode()); 
					 response.setMessage("StoreFlex User Registration failure");
				} 
		 }else {
			 object = service.storeFlexUserFinalize(req,roleType,clientCodes);
		 }
		} catch (StoreFlexServiceException e) {
			 response.setStatus(Status.BUSENESS_ERROR);
			 response.setStatusCode(Status.BUSENESS_ERROR.getCode()); 
			 response.setMessage("System Error...."+e.getMessage());
		}
		 return response;
	
}
}
