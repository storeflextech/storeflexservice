package com.storeflex.controller;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
import com.storeflex.beans.StoreFlexClientUsersBean;
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
	 
	 @GetMapping(value = "/healthCheck")
		@ApiOperation(value = "healthCheck", notes = "Check Deployment Status", nickname = "healthCheck")
		public StoreFlexResponse<Object> healthCheck() {
			log.info("Starting method", this);
			StoreFlexResponse<Object> caseMgntServiceResponse = new StoreFlexResponse<Object>();
			caseMgntServiceResponse.setStatus(Status.SUCCESS);
			caseMgntServiceResponse.setStatusCode(Status.SUCCESS.getCode());
			caseMgntServiceResponse.setMessage("healthCheck status good");
			return caseMgntServiceResponse;
		}
	 
	 
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
	 
	 @GetMapping(value="/storeflexusers" ,produces = MediaType.APPLICATION_JSON_VALUE)
	 @ApiOperation(value="storeflex" , notes ="Get Store flex Users list details" , nickname="storeflex")
	 public  StoreFlexResponse<Object> getStoreFlexUsers(
			 @RequestParam(defaultValue = "0") int page,
		     @RequestParam(defaultValue = "3") int size
			 ){
		 log.info("Starting method getStoreFlexUsers", this);
		 StoreFlexResponse<Object> response = new StoreFlexResponse<Object>();
		 Object object;
		 Pageable paging = PageRequest.of(page, size);
		 try {
				object = service.getStoreFlexUsersDetails(paging);
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
		 log.info("End method getStoreFlexUsers", this);
		return response;
	 }
	 
	 @GetMapping(value="/storeflexuser" ,produces = MediaType.APPLICATION_JSON_VALUE)
	 @ApiOperation(value="storeflex" , notes ="Get Store flex User details by Id " , nickname="storeflex")
	 public  StoreFlexResponse<Object> getStoreFlexUserId(
			 @RequestParam String userid
		     ){
		 log.info("Starting method getStoreFlexUsers", this);
		 StoreFlexResponse<Object> response = new StoreFlexResponse<Object>();
		 Object object;
		 try {
				object = service.getStoreFlexUserId(userid);
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
		 log.info("End method getStoreFlexUserId", this);
		return response;
	 }
	
	 /*
	 @PostMapping(value="/storeflexuser" ,consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
	 @ApiOperation(value="storeflexuser" , notes ="Create Store flex users" , nickname="storeflexuser")
	 public StoreFlexResponse<Object> storeFlexUser(@Validated @RequestBody StoreFlexUserBean req,@RequestParam String roleType){
		 StoreFlexResponse<Object> response = new StoreFlexResponse<Object>();
		 Object object;
		 try {
			 if(null==req.getUserId()) {//new user
				 object = service.storeFlexUser(req,roleType);
					if(null!=object) {
						object = service.storeFlexUserFinalize(req,roleType);
						if(null!=object) {
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
				 object = service.storeFlexUserFinalize(req,roleType);
			 }
			} catch (StoreFlexServiceException e) {
				 response.setStatus(Status.BUSENESS_ERROR);
				 response.setStatusCode(Status.BUSENESS_ERROR.getCode()); 
				 response.setMessage("System Error...."+e.getMessage());
			}
			 return response;
		
	 }*/
	 
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
					 response.setMessage("Profile picture upload successfully");
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
	 
	
	 @GetMapping(value="/clientusers" ,produces = MediaType.APPLICATION_JSON_VALUE)
	 @ApiOperation(value="clientusers" , notes ="Get client users" , nickname="clientusers")
	 public StoreFlexResponse<Object> clientUsers(@RequestParam String clientId,@RequestParam String status,
			 @RequestParam(defaultValue = "0") int page,
			 @RequestParam(defaultValue = "3") int size)throws StoreFlexServiceException{
		 log.info("Starting method clientUsers", this);
		 StoreFlexResponse<Object> response = new StoreFlexResponse<Object>();
		 try {
			 Pageable paging = PageRequest.of(page, size);
			 Object object= service.clientUsers(clientId,paging,status);
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
	 
	 @PostMapping(value="/clientuser" ,produces = MediaType.APPLICATION_JSON_VALUE)
	 @ApiOperation(value="clientuser" , notes ="Update client user" , nickname="clientuser")
	 public StoreFlexResponse<Object> clientusers(@Validated @RequestBody StoreFlexClientUsersBean requestBean)throws StoreFlexServiceException{
		 log.info("Starting method clientusers", this);
		 StoreFlexResponse<Object> response = new StoreFlexResponse<Object>();
		 try {
			Object object= service.updateclientusers(requestBean);
			 if(null!=object) {
				 object=service.clientUserById(requestBean.getUserId().toString());
				 response.setStatus(Status.SUCCESS);
				 response.setStatusCode(Status.SUCCESS.getCode());
				 response.setMethodReturnValue(object);
				 response.setMessage("Record updated");
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
		 log.info("End method clientuser", this);
		 return response;
	 }
	 
	 @GetMapping(value="/clientuser" ,produces = MediaType.APPLICATION_JSON_VALUE)
	 @ApiOperation(value="clientuser" , notes ="Get Client by USER ID" , nickname="clientuser")
	 public StoreFlexResponse<Object> clientUserById(@RequestParam String userId)throws StoreFlexServiceException{
		 log.info("Starting method clientuser", this);
		 StoreFlexResponse<Object> response = new StoreFlexResponse<Object>();
		 try {
			Object object= service.clientUserById(userId);
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
		 log.info("End method clientuser", this);
		 return response;
	 }
}
