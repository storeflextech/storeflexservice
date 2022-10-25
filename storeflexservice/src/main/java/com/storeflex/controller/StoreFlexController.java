package com.storeflex.controller;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.storeflex.response.StoreFlexResponse.Status;

import io.swagger.annotations.ApiOperation;

import com.storeflex.beans.StoreFlexBean;
import com.storeflex.beans.StoreFlexUserBean;
import com.storeflex.exceptions.StoreFlexServiceException;
import com.storeflex.services.StoreFlexService;
import com.storeflex.response.StoreFlexResponse;


@RestController
@CrossOrigin(origins = "*")
public class StoreFlexController {
	 private static final Logger log = LoggerFactory.getLogger(StoreFlexProfileController.class);
 
	 @Autowired
	 StoreFlexService service;
	 
	 @PostMapping(value="/storeflex" ,consumes = MediaType.APPLICATION_JSON_VALUE ,produces = MediaType.APPLICATION_JSON_VALUE)
	 @ApiOperation(value="storeflex" , notes ="Create Store flex company details" , nickname="storeflex")
	 public StoreFlexResponse<Object> createStoreFlex(@Validated @RequestBody StoreFlexBean storeFlexReq){
		 log.info("Starting method createStoreFlex", this);
		 StoreFlexResponse<Object> response = new StoreFlexResponse<Object>();
		 Object object;
		try {
			object = service.createStoreFlex(storeFlexReq);
			object = service.getStoreFlexCompyDetails();
			 if(null!=object) {
				 response.setStatus(Status.SUCCESS);
				 response.setStatusCode(Status.SUCCESS.getCode());
				 response.setMessage("User created successfully");
				 response.setMethodReturnValue(object);
			 }else {
				 response.setStatus(Status.BUSENESS_ERROR);
				 response.setStatusCode(Status.BUSENESS_ERROR.getCode()); 
				 response.setMessage("System Error....");
			 }
		} catch (StoreFlexServiceException e) {
			 response.setStatus(Status.BUSENESS_ERROR);
			 response.setStatusCode(Status.BUSENESS_ERROR.getCode()); 
			 response.setMessage("System Error...."+e.getMessage());
		}
		
		 return response;
	 }
	 
	 @GetMapping(value="/storeflex" ,produces = MediaType.APPLICATION_JSON_VALUE)
	 @ApiOperation(value="storeflex" , notes ="Get Store flex company details" , nickname="storeflex")
	 public  StoreFlexResponse<Object> getStoreFlexCompyDetails(){
		 log.info("Starting method createStoreFlex", this);
		 StoreFlexResponse<Object> response = new StoreFlexResponse<Object>();
		 Object object;
		 try {
				object = service.getStoreFlexCompyDetails();
				 if(null!=object) {
					 response.setStatus(Status.SUCCESS);
					 response.setStatusCode(Status.SUCCESS.getCode());
					 response.setMethodReturnValue(object);
				 }else {
					 response.setStatus(Status.BUSENESS_ERROR);
					 response.setStatusCode(Status.BUSENESS_ERROR.getCode()); 
					 response.setMessage("System Error....");
				 }
			} catch (StoreFlexServiceException e) {
				 response.setStatus(Status.BUSENESS_ERROR);
				 response.setStatusCode(Status.BUSENESS_ERROR.getCode()); 
				 response.setMessage("System Error...."+e.getMessage());
			}
			
			 return response;
	 }
	 
	 @PostMapping(value="/storeflexuser" ,consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
	 @ApiOperation(value="storeflexuser" , notes ="Create Store flex users" , nickname="storeflexuser")
	 public StoreFlexResponse<Object> storeFlexUser(@Validated @RequestBody StoreFlexUserBean req,@RequestParam String roleType,@RequestParam String compyCode){
		 StoreFlexResponse<Object> response = new StoreFlexResponse<Object>();
		 Object object;
		 try {
				object = service.storeFlexUser(req,roleType,compyCode); 
				 if(null!=object) {
					 response.setStatus(Status.SUCCESS);
					 response.setStatusCode(Status.SUCCESS.getCode());
					 response.setMethodReturnValue(object);
				 }else {
					 response.setStatus(Status.BUSENESS_ERROR);
					 response.setStatusCode(Status.BUSENESS_ERROR.getCode()); 
					 response.setMessage("System Error....");
				 }
			} catch (StoreFlexServiceException e) {
				 response.setStatus(Status.BUSENESS_ERROR);
				 response.setStatusCode(Status.BUSENESS_ERROR.getCode()); 
				 response.setMessage("System Error...."+e.getMessage());
			}
			 return response;
		
	 }
	 
	 @PutMapping(value="/uploaduserpic" ,consumes = MediaType.MULTIPART_FORM_DATA_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
	 @ApiOperation(value="storeflexuser" , notes ="Upload Store flex users profile pic" , nickname="storeflexuser")
	 public StoreFlexResponse<Object> uploaduserpic(@RequestParam String userid,@RequestParam("userPhoto") MultipartFile file) throws StoreFlexServiceException,IOException{
		 StoreFlexResponse<Object> response = new StoreFlexResponse<Object>();
		 Object object;
		 try {
				object = service.uploaduserpic(userid,file);
				 if(null!=object) {
					 response.setStatus(Status.SUCCESS);
					 response.setStatusCode(Status.SUCCESS.getCode());
					 response.setMethodReturnValue(object);
				 }else {
					 response.setStatus(Status.BUSENESS_ERROR);
					 response.setStatusCode(Status.BUSENESS_ERROR.getCode()); 
					 response.setMessage("System Error....");
				 }
			} catch (StoreFlexServiceException|IOException e) {
				 response.setStatus(Status.BUSENESS_ERROR);
				 response.setStatusCode(Status.BUSENESS_ERROR.getCode()); 
				 response.setMessage("System Error...."+e.getMessage());
			}
			 return response;
		
	 }
	 
	 @GetMapping(value="/city" ,produces = MediaType.APPLICATION_JSON_VALUE)
	 @ApiOperation(value="city" , notes ="get city list by stateId" , nickname="city")
	 public StoreFlexResponse<Object> getCity(@RequestParam String stateCode)throws StoreFlexServiceException{
		 log.info("Starting method city", this);
		 StoreFlexResponse<Object> response = new StoreFlexResponse<Object>();
		 try {
			 Object object= service.getCity(stateCode);
			 if(null!=object) {
				 response.setStatus(Status.SUCCESS);
				 response.setStatusCode(Status.SUCCESS.getCode());
				 response.setMethodReturnValue(object);
			 }else {
				 response.setStatus(Status.BUSENESS_ERROR);
				 response.setStatusCode(Status.BUSENESS_ERROR.getCode()); 
				 response.setMessage("System Error....");
			 }
		 }
		 catch (StoreFlexServiceException e) {
			 response.setStatus(Status.BUSENESS_ERROR);
			 response.setStatusCode(Status.BUSENESS_ERROR.getCode()); 
			 response.setMessage("System Error...."+e.getMessage());
		}
		 return response;
	 }
	 
	 @GetMapping(value="/state" ,produces = MediaType.APPLICATION_JSON_VALUE)
	 @ApiOperation(value="state" , notes ="get state list by countryId" , nickname="state")
	 public StoreFlexResponse<Object> getState(@RequestParam String countryId)throws StoreFlexServiceException{
		 log.info("Starting method state", this);
		 StoreFlexResponse<Object> response = new StoreFlexResponse<Object>();
		 try {
			 Object object= service.getState(countryId);
			 if(null!=object) {
				 response.setStatus(Status.SUCCESS);
				 response.setStatusCode(Status.SUCCESS.getCode());
				 response.setMethodReturnValue(object);
			 }else {
				 response.setStatus(Status.BUSENESS_ERROR);
				 response.setStatusCode(Status.BUSENESS_ERROR.getCode()); 
				 response.setMessage("System Error....");
			 }
		 }
		 catch (StoreFlexServiceException e) {
			 response.setStatus(Status.BUSENESS_ERROR);
			 response.setStatusCode(Status.BUSENESS_ERROR.getCode()); 
			 response.setMessage("System Error...."+e.getMessage());
		}
		 return response;
	 }
	 
	 @GetMapping(value="/roles" ,produces = MediaType.APPLICATION_JSON_VALUE)
	 @ApiOperation(value="roles" , notes ="get roles list" , nickname="roles")
	 public StoreFlexResponse<Object> getRoles() throws StoreFlexServiceException{
		 StoreFlexResponse<Object> response = new StoreFlexResponse<Object>();
		 try {
			 Object object= service.getRoles();
			 if(null!=object) {
				 response.setStatus(Status.SUCCESS);
				 response.setStatusCode(Status.SUCCESS.getCode());
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
	 
	 
}
