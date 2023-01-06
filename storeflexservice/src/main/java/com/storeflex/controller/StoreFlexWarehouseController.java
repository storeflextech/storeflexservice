package com.storeflex.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
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
@CrossOrigin(origins = "*")
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
			object = service.getWarehouseById(warehouse.getWarehouseId());
			if (null != object) {
				response.setStatus(Status.SUCCESS);
				response.setStatusCode(Status.SUCCESS.getCode());
				response.setMessage("Warehouse record submitted");
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
	
	@PostMapping(value="/uploadWareHouseProfilePic" , produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value="uploadWareHouseProfilePic" , notes ="upload storeflex WareHouse profile pic , input wareHouseId , profile name and pciture" , nickname="uploadWareHouseProfilePic")
	public ResponseEntity<?> uploadWareHouseProfilePic(@RequestParam("warehouseId") String warehouseId,@RequestParam("wareHousePhoto") MultipartFile file) throws IOException, StoreFlexServiceException{
		log.info("Starting method uploadClientProfilePic", this);
		byte[] object;
		object = service.uploadWareHouseProfilePic(warehouseId,file);
		return ResponseEntity.status(HttpStatus.OK)
					.contentType(MediaType.valueOf(file.getContentType()))
					.body(object);
			
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
						response.setStatus(Status.BUSENESS_ERROR);
						response.setStatusCode(Status.BUSENESS_ERROR.getCode());
						response.setMessage("System Error...." +"Input is not valid "+e.getMessage());	
					} catch (IOException e) {
						response.setStatus(Status.BUSENESS_ERROR);
						response.setStatusCode(Status.BUSENESS_ERROR.getCode());
						response.setMessage("System Error...." +"Input is not valid "+e.getMessage());	
					}
					filesNames.add(file.getOriginalFilename());
				});
				message = "Uploaded the files successfully" + filesNames;
				response.setStatus(Status.SUCCESS);
				response.setStatusCode(Status.SUCCESS.getCode());
				response.setMessage(message);
				
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

	
	@GetMapping(value = "/warehouseByClientId", produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "warehouse", notes = "get storeflex warehouse for client Id", nickname = "warehouse")
	public StoreFlexResponse<WarehouseListBean> warehouseByClientId(@RequestParam String clientId,
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
	
	@GetMapping(value = "/warehouseById", produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "warehouseById", notes = "get storeflex warehouse by Id", nickname = "warehouseById")
	public StoreFlexResponse<Object> getWarehouseById(@RequestParam String warehouseId) {
		log.info("Starting method warehouseById", this);
		StoreFlexResponse<Object> response = new StoreFlexResponse<Object>();
		Object object= null;
		try {
			object = service.getWarehouseById(warehouseId);
			if (null != object) {
				response.setStatus(Status.SUCCESS);
				response.setStatusCode(Status.SUCCESS.getCode());
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
	
	@DeleteMapping(value = "/warehouse")
	@ApiOperation(value = "warehouseId", notes = "Delete warehouse from bussniess", nickname = "Delete warehouse from bussniess")
	public StoreFlexResponse<Map> deleteWarehouseById(@RequestParam(value = "warehouseId") String warehouseId){
		log.info("Starting method deleteWarehouseById", this);
		StoreFlexResponse<Map> response =  new StoreFlexResponse<Map>();
		Map<String, Boolean> mapObject;
		try {
			mapObject = service.deleteWarehouseById(warehouseId);
			if (!CollectionUtils.isEmpty(mapObject)) {
				 response.setStatus(Status.SUCCESS);
				 response.setStatusCode(Status.SUCCESS.getCode());
				 response.setMessage("WarehouseId" +warehouseId+" Successfully out from bussniess ");
				 response.setMethodReturnValue(mapObject);	
			}else {
				 response.setStatus(Status.BUSENESS_ERROR);
				 response.setStatusCode(Status.BUSENESS_ERROR.getCode());
				 response.setMessage("Warehouse" +warehouseId+" not found");
				 response.setMethodReturnValue(mapObject);	
			}
		}catch(StoreFlexServiceException e) {
			 response.setStatus(Status.BUSENESS_ERROR);
			 response.setStatusCode(Status.BUSENESS_ERROR.getCode());
			 response.setMessage("System error ..."+e.getMessage());
		}
		log.info("End method deleteWarehouseById", this);
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
	
	@GetMapping(value = "/warehouses", produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "warehouses", notes = "get all warehouses", nickname = "warehouses")
	public StoreFlexResponse<WarehouseViewBeanList> getAllWarehouses(
			@RequestParam(defaultValue = "0") int page,
	        @RequestParam(defaultValue = "3") int size,
	        @RequestParam(value = "status") String status){
		log.info("Starting method wareHouse", this);
		StoreFlexResponse<WarehouseViewBeanList> response = new StoreFlexResponse<WarehouseViewBeanList>();
		try {
			WarehouseViewBeanList warehouseViewBeanList = service.getAllWarehouses(page,size,status);
			if (null != warehouseViewBeanList && null!=warehouseViewBeanList.getWarehouseViewBean() && warehouseViewBeanList.getWarehouseViewBean().size()>0) {
				response.setStatus(Status.SUCCESS);
				response.setStatusCode(Status.SUCCESS.getCode());
				response.setMethodReturnValue(warehouseViewBeanList);
			}else
			if(!StringUtils.isEmpty(warehouseViewBeanList.getErrorCode().getErrorCode())) {
				response.setStatus(Status.BUSENESS_ERROR);
				response.setStatusCode(Status.BUSENESS_ERROR.getCode());
				response.setMethodReturnValue(warehouseViewBeanList);	
			}
			else {
				response.setStatus(Status.BUSENESS_ERROR);
				response.setStatusCode(Status.BUSENESS_ERROR.getCode());
				response.setMessage("System Error....");
			}
			
		}
		catch(StoreFlexServiceException e) {
			response.setStatus(Status.BUSENESS_ERROR);
			response.setStatusCode(Status.BUSENESS_ERROR.getCode());
			response.setMessage("System Error...." + e.getMessage());
		}
		
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
	
	@GetMapping(value = "/categories", produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "categories", notes = "List of categoies of warehouse", nickname = "List of categoies of warehouse")
	public StoreFlexResponse<Object> getWareshouseCategories(){
		log.info("Starting method getWareshouseCategories", this);
		StoreFlexResponse<Object> response = new StoreFlexResponse<Object>();
		Object object =null;
		try {
			object = service.getWareshouseCategories();
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
			response.setMessage("System Error...." + e.getMessage());
		}
		return response;
	}
	
	@GetMapping(value="/gstwarehouseavailability" , produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value="warehouse" , notes ="find gst avalibility" , nickname="warehouse")
    public StoreFlexResponse<Object> gstcheckavailability(@RequestParam String gst){
		log.info("Start method gstcheckavailability", this);
		StoreFlexResponse<Object> response = new StoreFlexResponse<Object>();
		try {
			boolean flag  = service.gstcheckavailability(gst);
			 if(flag) {
				 response.setStatus(Status.SUCCESS);
				 response.setStatusCode(Status.SUCCESS.getCode());
				 response.setMessage("GST "+gst+" available");
			 }else {
				 response.setStatus(Status.BUSENESS_ERROR);
				 response.setStatusCode(Status.BUSENESS_ERROR.getCode()); 
				 response.setMessage("GST "+gst+" not available");
			 }
		}
		catch(StoreFlexServiceException e){
			 response.setStatus(Status.BUSENESS_ERROR);
			 response.setStatusCode(Status.BUSENESS_ERROR.getCode()); 
			 response.setMessage("System Error...."+e.getMessage());
		}
		log.info("End method gstcheckavailability", this);
		return response;
    } 
	
}
