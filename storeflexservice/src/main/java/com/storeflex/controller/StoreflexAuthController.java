package com.storeflex.controller;

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

import com.storeflex.beans.LoginBean;
import com.storeflex.beans.TestAuthBean;
import com.storeflex.config.AppConfiguration;
import com.storeflex.exceptions.StoreFlexServiceException;
import com.storeflex.response.StoreFlexResponse;
import com.storeflex.response.StoreFlexResponse.Status;
import com.storeflex.services.StoreFlexAuthService;

import io.swagger.annotations.ApiOperation;

@RestController
@CrossOrigin(origins = "*")
public class StoreflexAuthController {
	private static final Logger log = LoggerFactory.getLogger(StoreflexAuthController.class);
     
	@Autowired
	AppConfiguration config;
	@Autowired
	LoginBean loginBean;
	@Autowired
	StoreFlexAuthService service;
	
	@PostMapping(value = "/logintest", produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "logintest", notes = "logintest test , passed username and password", nickname = "logintest")
	public StoreFlexResponse<Object> testLogin(@Validated @RequestBody TestAuthBean bean){
		log.info("Starting method testLogin", this);	
		StoreFlexResponse<Object> response = new StoreFlexResponse<Object>();
		if(bean.getUsername().trim().equals(config.getTest_user().trim()) && bean.getPassword().trim().equals(config.getTest_password().trim())) {
			loginBean.setRedirect("/storeflexhome");
			loginBean.setUsername(bean.getUsername());
			response.setStatus(Status.SUCCESS);
			response.setStatusCode(Status.SUCCESS.getCode());
			response.setMessage("Login Success");
			response.setMethodReturnValue(loginBean);
		}
		else
		if(bean.getUsername().trim().equals(config.getSl_user().trim()) && bean.getPassword().trim().equals(config.getSl_password().trim())) {
				loginBean.setRedirect("/storeflexuserdashboard");
				loginBean.setUsername(bean.getUsername());
				response.setStatus(Status.SUCCESS);
				response.setStatusCode(Status.SUCCESS.getCode());
				response.setMessage("Login Success");
				response.setMethodReturnValue(loginBean);
			}
		else
			if(bean.getUsername().trim().equals(config.getCl_user().trim()) && bean.getPassword().trim().equals(config.getCl_password().trim())) {
				loginBean.setRedirect("/storeflexclientdashboard");
				loginBean.setUsername(bean.getUsername());
				response.setStatus(Status.SUCCESS);
				response.setStatusCode(Status.SUCCESS.getCode());
				response.setMessage("Login Success");
				response.setMethodReturnValue(loginBean);
			}
		else
		if(bean.getUsername().trim().equals(config.getCust_user().trim()) && bean.getPassword().trim().equals(config.getCust_password().trim())) {
			loginBean.setRedirect("/storeflexcustdashboard");
			loginBean.setUsername(bean.getUsername());
			response.setStatus(Status.SUCCESS);
			response.setStatusCode(Status.SUCCESS.getCode());
			response.setMessage("Login Success");
			response.setMethodReturnValue(loginBean);
		}
		else {
			loginBean.setRedirect("/errorPage");
			loginBean.setUsername(bean.getUsername());
			response.setStatus(Status.BUSENESS_ERROR);
			response.setStatusCode(Status.BUSENESS_ERROR.getCode());
			response.setMessage("Login failure");
			response.setMethodReturnValue(loginBean);
		}
		return response;
	}

	
	@PostMapping(value = "/sllogin", produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "sllogin", notes = "Storeflex sllogin  , passed username and password", nickname = "sllogin")
	public StoreFlexResponse<Object> sllogin(@Validated @RequestBody TestAuthBean bean){
		log.info("Starting method testLogin", this);	
		StoreFlexResponse<Object> response = new StoreFlexResponse<Object>();
		Object object=null;
		try {
			object=service.sllogin(bean);
			 if(null!=object) {
				 response.setStatus(Status.SUCCESS);
				 response.setStatusCode(Status.SUCCESS.getCode());
				 response.setMessage("Login Success");
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
	
	
	@PostMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "login", notes = "Storeflex Customer login  , passed email id and password , userType will be SL, CL , CU", nickname = "login")
	public StoreFlexResponse<Object> login(@RequestParam String userType,@Validated @RequestBody TestAuthBean bean){
		log.info("Starting method testLogin", this);	
		StoreFlexResponse<Object> response = new StoreFlexResponse<Object>();
		Object object=null;
		try {
			if(userType.equalsIgnoreCase("SL")) {
				object=service.sllogin(bean);	
			}else {
			 //CL or CU
			 object=service.login(bean);
			}
			 if(null!=object) {
				 response.setStatus(Status.SUCCESS);
				 response.setStatusCode(Status.SUCCESS.getCode());
				 response.setMessage("Login Success");
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
}
