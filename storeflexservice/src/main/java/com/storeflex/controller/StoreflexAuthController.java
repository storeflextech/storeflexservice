package com.storeflex.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.storeflex.beans.LoginBean;
import com.storeflex.beans.TestAuthBean;
import com.storeflex.config.AppConfiguration;
import com.storeflex.response.StoreFlexResponse;
import com.storeflex.response.StoreFlexResponse.Status;

import io.swagger.annotations.ApiOperation;

@RestController
public class StoreflexAuthController {
	private static final Logger log = LoggerFactory.getLogger(StoreflexAuthController.class);
     
	@Autowired
	AppConfiguration config;
	@Autowired
	LoginBean loginBean;
	
	@PostMapping(value = "/logintest", produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "logintest", notes = "logintest test , passed username and password", nickname = "logintest")
	public StoreFlexResponse<Object> testLogin(@Validated @RequestBody TestAuthBean bean){
		log.info("Starting method testLogin", this);	
		StoreFlexResponse<Object> response = new StoreFlexResponse<Object>();
		if(bean.getUsername().trim().equals(config.getTestuser().trim()) && bean.getPassword().trim().equals(config.getPassword().trim())) {
			loginBean.setRedirect("/storeflexhome");
			loginBean.setUsername(bean.getUsername());
			response.setStatus(Status.SUCCESS);
			response.setStatusCode(Status.SUCCESS.getCode());
			response.setMessage("Login Success");
			response.setMethodReturnValue(loginBean);
		}else {
			loginBean.setRedirect("/errorPage");
			loginBean.setUsername(bean.getUsername());
			response.setStatus(Status.BUSENESS_ERROR);
			response.setStatusCode(Status.BUSENESS_ERROR.getCode());
			response.setMessage("Login failure");
			response.setMethodReturnValue(loginBean);
		}
		return response;
	}

}
