package com.storeflex.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.storeflex.beans.ClientWareHousePhtBean;
import com.storeflex.beans.ClientWareHousesBean;
import com.storeflex.beans.WarehouseListBean;
import com.storeflex.beans.WarehouseRequestBean;
import com.storeflex.beans.WarehouseViewBeanList;
import com.storeflex.entities.Warehouse;
import com.storeflex.exceptions.StoreFlexServiceException;
import com.storeflex.response.StoreFlexResponse;
import com.storeflex.response.StoreFlexResponse.Status;
import com.storeflex.services.StoreFlexWarehouseService;

import io.swagger.annotations.ApiOperation;

@RestController
public class StoreFlexWarehouseController {
	private static final Logger log = LoggerFactory.getLogger(StoreFlexWarehouseController.class);
	@Autowired
	StoreFlexWarehouseService service;

	@PostMapping(value = "/warehouse", produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "warehouse", notes = "create storeflex warehouse", nickname = "warehouse")
	public StoreFlexResponse<Object> createWarehouse(@Validated @RequestBody ClientWareHousesBean request) {
		log.info("Starting method wareHouse", this);
		StoreFlexResponse<Object> response = new StoreFlexResponse<Object>();
		Object object = null;
		try {
			Warehouse warehouse = service.createWarehouse(request);
			object = service.createWarehouse(warehouse.getWarehouseId());
			if (null != object) {
				response.setStatus(Status.SUCCESS);
				response.setStatusCode(Status.SUCCESS.getCode());
				response.setMessage("Warehouse " + request.getWarehouseName() + " record submitted");
				response.setMethodReturnValue(object);
			} else {
				response.setStatus(Status.BUSENESS_ERROR);
				response.setStatusCode(Status.BUSENESS_ERROR.getCode());
				response.setMessage("System Error....");
			}
		} catch (StoreFlexServiceException e) {
			response.setStatus(Status.BUSENESS_ERROR);
			response.setStatusCode(Status.BUSENESS_ERROR.getCode());
			response.setMessage("System Error...." + e.getMessage());
		}
		log.info("End method wareHouse", this);
		return response;
	}

	@PostMapping(value = "/uploadwarehousepics", produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "uploadwarehousepics", notes = "upload warehouse pics", nickname = "uploadwarehousepics")
	public StoreFlexResponse<Object> uploadfilewarehousepic(@RequestParam String clientId,@RequestParam String warehouseId, @RequestParam("files") MultipartFile[] files)
			throws IOException, StoreFlexServiceException {
		String message = "";
		StoreFlexResponse<Object> response = new StoreFlexResponse<Object>();
		try {
			List<String> filesNames = new ArrayList<String>();
			if (null != files) {
				Arrays.asList(files).stream().forEach(file -> {
					try {
						service.upload(file, clientId,warehouseId);
					} catch (StoreFlexServiceException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					filesNames.add(file.getOriginalFilename());
				});
				message = "Uploaded the files successfully" + filesNames;
				response.setStatus(Status.SUCCESS);
				response.setStatusCode(Status.SUCCESS.getCode());
				response.setMessage(message);
				response.setMethodReturnValue(message);
			}else {
				response.setStatus(Status.BUSENESS_ERROR);
				response.setStatusCode(Status.BUSENESS_ERROR.getCode());
				response.setMessage("System Error...." +"Input is not valid");	
			}

			
		} catch (Exception e) {
			response.setStatus(Status.BUSENESS_ERROR);
			response.setStatusCode(Status.BUSENESS_ERROR.getCode());
			response.setMessage("System Error...." + e.getMessage());
		}
		return response;
	}

	
	@GetMapping(value = "/warehouse", produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "warehouse", notes = "get storeflex warehouse for client store", nickname = "warehouse")
	public StoreFlexResponse<WarehouseListBean> getWarehouse(@RequestParam String clientId,
			@RequestParam(defaultValue = "0") int page,
	        @RequestParam(defaultValue = "3") int size) {
		log.info("Starting method wareHouse", this);
		StoreFlexResponse<WarehouseListBean> response = new StoreFlexResponse<WarehouseListBean>();
		 Pageable paging = PageRequest.of(page, size);
		try {
			WarehouseListBean warehouseListBean = service.getWarehouseList(clientId,paging);
			if (null != warehouseListBean && warehouseListBean.getWarehouseList().size()>0) {
				response.setStatus(Status.SUCCESS);
				response.setStatusCode(Status.SUCCESS.getCode());
				response.setMethodReturnValue(warehouseListBean);
			} else {
				response.setStatus(Status.BUSENESS_ERROR);
				response.setStatusCode(Status.BUSENESS_ERROR.getCode());
				response.setMessage("System Error....");
			}
		} catch (StoreFlexServiceException e) {
			response.setStatus(Status.BUSENESS_ERROR);
			response.setStatusCode(Status.BUSENESS_ERROR.getCode());
			response.setMessage("System Error...." + e.getMessage());
		}
		log.info("End method wareHouse", this);
		return response;
	}
	
	@GetMapping(value = "/warehousepics", produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "warehousepics", notes = "get warehouse pics", nickname = "warehousepics")
	public StoreFlexResponse<Set<ClientWareHousePhtBean>> getWarehousePics(@RequestParam String warehouseId) {
		log.info("Starting method getWarehousePics", this);
		StoreFlexResponse<Set<ClientWareHousePhtBean>> response = new StoreFlexResponse<Set<ClientWareHousePhtBean>>();
		try {
			Set<ClientWareHousePhtBean> setList= service.getWarehousePics(warehouseId);
			if (!CollectionUtils.isEmpty(setList)) {
				response.setStatus(Status.SUCCESS);
				response.setStatusCode(Status.SUCCESS.getCode());
				response.setMethodReturnValue(setList);
			} else {
				response.setStatus(Status.BUSENESS_ERROR);
				response.setStatusCode(Status.BUSENESS_ERROR.getCode());
				response.setMessage("System Error....");
			}
		} catch (StoreFlexServiceException e) {
			response.setStatus(Status.BUSENESS_ERROR);
			response.setStatusCode(Status.BUSENESS_ERROR.getCode());
			response.setMessage("System Error...." + e.getMessage());
		}
		log.info("End method getWarehousePics", this);
		return response;
	}
	
	
	@GetMapping(value = "/searchwarehouse", produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "searchwarehouse", notes = "Search waharehouse", nickname = "searchwarehouse")
	public StoreFlexResponse<WarehouseViewBeanList> getWarehouseSearch(
			@RequestParam(required=false) String city,
			@RequestParam(required=true) String pincode,
			@RequestParam(required=false) String state,
			@RequestParam(defaultValue = "0") int page,
	        @RequestParam(defaultValue = "3") int size) {
		log.info("Starting method wareHouse", this);
		StoreFlexResponse<WarehouseViewBeanList> response = new StoreFlexResponse<WarehouseViewBeanList>();
		try {
			WarehouseViewBeanList warehouseViewBeanList = service.getWarehouseSearch(
					WarehouseRequestBean.builder().city(city).pincode(pincode).state(state).build(),page,size
					);
			if (null != warehouseViewBeanList && null!=warehouseViewBeanList.getWarehouseViewBean() && warehouseViewBeanList.getWarehouseViewBean().size()>0) {
				response.setStatus(Status.SUCCESS);
				response.setStatusCode(Status.SUCCESS.getCode());
				response.setMethodReturnValue(warehouseViewBeanList);
			}else
			if(!StringUtils.isEmpty(warehouseViewBeanList.getErrorCode().getErrorCode())) {
				response.setStatus(Status.SUCCESS);
				response.setStatusCode(Status.SUCCESS.getCode());
				response.setMethodReturnValue(warehouseViewBeanList);	
			}
			else {
				response.setStatus(Status.BUSENESS_ERROR);
				response.setStatusCode(Status.BUSENESS_ERROR.getCode());
				response.setMessage("System Error....");
			}
		} catch (StoreFlexServiceException e) {
			response.setStatus(Status.BUSENESS_ERROR);
			response.setStatusCode(Status.BUSENESS_ERROR.getCode());
			response.setMessage("System Error...." + e.getMessage());
		}
		log.info("End method wareHouse", this);
		return response;
	}
	
	
	
	
}
