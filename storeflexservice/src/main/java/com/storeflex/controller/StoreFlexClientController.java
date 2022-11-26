package com.storeflex.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.data.domain.Pageable;

import com.storeflex.beans.ClientProfileListBean;
import com.storeflex.beans.StoreFlexClientAddBean;
import com.storeflex.beans.StoreFlexClientBean;
import com.storeflex.beans.StoreFlexClientContactBean;
import com.storeflex.entities.ClientProfile;
import com.storeflex.exceptions.StoreFlexServiceException;
import com.storeflex.response.StoreFlexResponse;
import com.storeflex.response.StoreFlexResponse.Status;
import com.storeflex.services.StoreFlexClientService;

import io.swagger.annotations.ApiOperation;

@RestController
@CrossOrigin(origins = "*")
public class StoreFlexClientController {
	private static final Logger log = LoggerFactory.getLogger(StoreFlexClientController.class);

	@Autowired
	StoreFlexClientService service;
	
	@PostMapping(value="/client" , produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value="client" , notes ="create storeflex client" , nickname="client")
	public StoreFlexResponse<Object> storeFlexClient(@Validated @RequestBody StoreFlexClientBean request){
		log.info("Starting method storeFlexClient", this);
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
		log.info("End method storeFlexClient", this);
		return response;
		
	}
	
	@PostMapping(value="/uploadClientProfilePic" , produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value="uploadClientProfilePic" , notes ="upload storeflex client profile pic , input client Id , profile name and pciture" , nickname="uploadClientProfilePic")
	public StoreFlexResponse<Object>uploadClientProfilePic(@RequestParam String clientId,@RequestParam("clientPhoto") MultipartFile file) throws IOException{
		log.info("Starting method uploadClientProfilePic", this);
		StoreFlexResponse<Object> response = new StoreFlexResponse<Object>();
		Object object;
		try {
			object = service.uploadClientProfilePic(clientId,file);
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
		log.info("Starting method getStoreFlexClientById", this);
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
		 log.info("End method storeFlexClient", this);
		return response;
		
	}
	
	@DeleteMapping(value = "/client")
	@ApiOperation(value = "client", notes = "Delete client from bussniess", nickname = "Delete client from bussniess")
	public StoreFlexResponse<Map> deleteClientById(@RequestParam(value = "clientId") String clientId){
		log.info("Starting method deleteClientById", this);
		StoreFlexResponse<Map> response =  new StoreFlexResponse<Map>();
		Map<String, Boolean> mapObject;
		try {
			mapObject = service.deleteClientById(clientId);
			if (!CollectionUtils.isEmpty(mapObject)) {
				 response.setStatus(Status.SUCCESS);
				 response.setStatusCode(Status.SUCCESS.getCode());
				 response.setMessage("clientId" +clientId+" Successfully out from bussniess ");
				 response.setMethodReturnValue(mapObject);	
			}else {
				 response.setStatus(Status.BUSENESS_ERROR);
				 response.setStatusCode(Status.BUSENESS_ERROR.getCode());
				 response.setMessage("clientId" +clientId+" not found");
				 response.setMethodReturnValue(mapObject);	
			}
		}catch(StoreFlexServiceException e) {
			 response.setStatus(Status.BUSENESS_ERROR);
			 response.setStatusCode(Status.BUSENESS_ERROR.getCode());
			 response.setMessage("System error ..."+e.getMessage());
		}
		return response;
	}
	
	@GetMapping(value="/clients" , produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value="clients" , notes ="get all storeflex client" , nickname="clients")
	public StoreFlexResponse<ClientProfileListBean> getStoreFlexClients(
			@RequestParam(defaultValue = "0") int page,
	        @RequestParam(defaultValue = "3") int size
			){
		log.info("Starting method getStoreFlexClients", this);
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
		 log.info("End method storeFlexClient", this);
		return response;
		
	}
	
	@GetMapping(value="/clientDropList" , produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value="clientDropList" , notes ="Client Id and client name for drop down list" , nickname="clientDropList")
	public StoreFlexResponse<Object> clientDropList(){
		log.info("Starting method clientDropList", this);
		StoreFlexResponse<Object> response = new StoreFlexResponse<Object>();
		List<Map>  mapList = null;
		try {
			 mapList = service.clientDropList();
		 	 if(null!=mapList) {
				 response.setStatus(Status.SUCCESS);
				 response.setStatusCode(Status.SUCCESS.getCode());
				 response.setMethodReturnValue(mapList);
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
		log.info("End method clientDropList", this);
		return response;
	}
	
	@PostMapping(value="/clientUpdate" , produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value="clientUpdate" , notes ="Update Client Profile" , nickname="clientUpdate")
	public StoreFlexResponse<Object> clientUpdate(@Validated @RequestBody StoreFlexClientBean request){
		log.info("Starting method clientUpdate", this);
		StoreFlexResponse<Object> response = new StoreFlexResponse<Object>();
		Object object = null;
		try {
			request = (StoreFlexClientBean) service.updateStoreFlexClient(request);
			object = service.getStoreFlexClient(request.getClientId());
			 if(null!=object) {
				 response.setStatus(Status.SUCCESS);
				 response.setStatusCode(Status.SUCCESS.getCode());
				 response.setMessage("Client "+request.getCompyName()+" record updated");
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
		log.info("End method storeFlexClient", this);
		return response;
		
	}
	
	@PostMapping(value="/updateClientAddress" , produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value="updateClientAddress" , notes ="Update Client Address Profile" , nickname="updateClientAddress")
	public StoreFlexResponse<Object> updateClientAddress(@Validated @RequestBody StoreFlexClientAddBean request){
		log.info("Starting method updateClientAddress", this);
		StoreFlexResponse<Object> response = new StoreFlexResponse<Object>();
		try {
			request = (StoreFlexClientAddBean) service.updateClientAddress(request);
			 if(null!=request) {
				 response.setStatus(Status.SUCCESS);
				 response.setStatusCode(Status.SUCCESS.getCode());
				 response.setMessage("Client address updated..");
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
		log.info("End method updateClientAddress", this);
		return response;
		
	}
	
	@PostMapping(value="/updateClientContacts" , produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value="updateClientAddress" , notes ="Update Client Address Profile" , nickname="updateClientAddress")
	public StoreFlexResponse<Object> updateClientContacts(@Validated @RequestBody StoreFlexClientContactBean request){
		log.info("Starting method updateClientContacts", this);
		StoreFlexResponse<Object> response = new StoreFlexResponse<Object>();
		Object object = null;
		try {
			request = (StoreFlexClientContactBean) service.updateClientContacts(request);
			 if(null!=request) {
				 response.setStatus(Status.SUCCESS);
				 response.setStatusCode(Status.SUCCESS.getCode());
				 response.setMessage("Client Contact updated..");
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
		log.info("End method updateClientContacts", this);
		return response;
		
	}

	@PostMapping(value="/deActivateClient" , produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value="deActivateClient" , notes ="deactivate client" , nickname="deActivateClient")
	public StoreFlexResponse<Object> deActivateClient(@Validated @RequestBody StoreFlexClientBean request){
		log.info("Starting method deActivateClient", this);
		StoreFlexResponse<Object> response = new StoreFlexResponse<Object>();
		try {
			request = (StoreFlexClientBean) service.deActivateClient(request);
			 if(null!=request) {
				 response.setStatus(Status.SUCCESS);
				 response.setStatusCode(Status.SUCCESS.getCode());
				 response.setMessage("Client" +request.getCompyName()+" with Id "+request.getClientId()+" deactivated successfully..");
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
		log.info("End method deActivateClient", this);
		return response;
		
	}


}

