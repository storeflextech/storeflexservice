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
import org.springframework.web.bind.annotation.RestController;

import com.storeflex.beans.CustEnquiryBean;
import com.storeflex.exceptions.StoreFlexServiceException;
import com.storeflex.response.StoreFlexResponse;
import com.storeflex.response.StoreFlexResponse.Status;
import com.storeflex.services.StoreFlexCustEnquiryService;

import io.swagger.annotations.ApiOperation;

@RestController
@CrossOrigin(origins = "*")
public class StoreFlexCustEnquiryController {
	private static final Logger log = LoggerFactory.getLogger(StoreFlexCustEnquiryController.class);

	@Autowired
	StoreFlexCustEnquiryService  service;
	
	@PostMapping(value="/enquiry" , produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value="enquiry" , notes ="store flex enquiry" , nickname="enquiry")
	public StoreFlexResponse<Object> enquiry(@Validated @RequestBody CustEnquiryBean bean) throws MessagingException, IOException{
		log.info("Starting method enquiry", this);
		StoreFlexResponse<Object> response = new StoreFlexResponse<Object>();
		Object object = null;
		try {
			object = service.enquiry(bean);
			if(null!=object) {
				 service.enquiryMail(bean);
				 response.setStatus(Status.SUCCESS);
				 response.setStatusCode(Status.SUCCESS.getCode());
				 response.setMessage("Thanks for enquiry submission , StoreFlex sales team review your request and get back on soon.. ");
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
		log.info("End method enquiry", this);
		return null;
		
	}
}
