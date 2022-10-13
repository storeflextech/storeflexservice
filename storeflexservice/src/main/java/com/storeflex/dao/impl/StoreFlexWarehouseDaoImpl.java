package com.storeflex.dao.impl;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import com.storeflex.beans.ClientWareHouseAddrBean;
import com.storeflex.beans.ClientWareHousePhtBean;
import com.storeflex.beans.ClientWareHousesBean;
import com.storeflex.beans.WarehouseListBean;
import com.storeflex.beans.WarehouseRequestBean;
import com.storeflex.dao.StoreFlexWarehouseDao;
import com.storeflex.entities.ClientProfile;
import com.storeflex.entities.UniqueId;
import com.storeflex.entities.WareHousePhoto;
import com.storeflex.entities.Warehouse;
import com.storeflex.entities.WarehouseAddress;
import com.storeflex.exceptions.StoreFlexServiceException;
import com.storeflex.helpers.StoreFlexHelper;
import com.storeflex.helpers.StoreFlexWarehouseHelper;
import com.storeflex.repositories.CityRepository;
import com.storeflex.repositories.StoreFlexClientRepository;
import com.storeflex.repositories.UniquePrefixRepository;
import com.storeflex.repositories.WarehouseAddressRepository;
import com.storeflex.repositories.WarehousePhotosRepository;
import com.storeflex.repositories.WarehouseRepository;

@Component
public class StoreFlexWarehouseDaoImpl implements StoreFlexWarehouseDao{

	private static final Logger log = LoggerFactory.getLogger(StoreFlexWarehouseDaoImpl.class);

	@Autowired
	WarehouseRepository warehouseRepository;
	@Autowired
	WarehouseAddressRepository warehouseAddresRepository;
	@Autowired
	CityRepository cityRepository;
	@Autowired
	StoreFlexClientRepository clientRepository;
	@Autowired
	WarehousePhotosRepository warehousePhotosRepository;
	@Autowired
	StoreFlexWarehouseHelper helper;
	@Autowired
	StoreFlexHelper storeflexhelper;
	@Autowired
	UniquePrefixRepository uniquePrefixRespository;
	
	@Override
	public Warehouse createWarehouse(ClientWareHousesBean request) throws StoreFlexServiceException {
		 log.info("Starting method createWarehouse", this);
		 
		 List<UniqueId> prefixList = uniquePrefixRespository.findAll();
		 UniqueId uniqueId= storeflexhelper.getWareHouseUniqueId(prefixList);
		 
		 Warehouse warehouse= new Warehouse();
		 if(null!=request.getClientId()) {
		 Optional<ClientProfile> clientProfileOpt =  clientRepository.findById(request.getClientId());
			 if(clientProfileOpt.isPresent()) {
				 WarehouseAddress address = new WarehouseAddress();
				 Set<WarehouseAddress> addressSet = new HashSet<WarehouseAddress>();
				 warehouse.setWarehouseId(uniqueId.getPrex()+uniqueId.getNextReserveId());
				 warehouse.setClientId(request.getClientId());
				 warehouse.setCreateBy("ADMIN");
				 warehouse.setCreateDate(LocalDateTime.now());
				 warehouse.setWarehouseName(request.getWarehouseName());
				 warehouse.setDescp(request.getDescp());
				 warehouse.setStatus(true);
				 warehouse.setWarehouseTaxId(request.getWarehouseTaxId());
				 warehouse.setProfilePhotoName(request.getProfilePhotoName());
				 warehouse.setProfilePhoto(request.getProfilePhoto());
				 
				 addressSet= helper.populateAddress(addressSet,address,request,warehouse);
				 if(!CollectionUtils.isEmpty(addressSet)) {
					 warehouse.setAddress(addressSet); 
				 }
				 warehouse = warehouseRepository.save(warehouse);
				 
				 //increase the count of Clinet ReserveId
				 uniqueId.setNextReserveId(uniqueId.getNextReserveId()+1);
				 uniquePrefixRespository.save(uniqueId);
			 }else {
				 log.error("client id is not is not valid", this ); 
				 return null;
			 }
		 }else {
			 log.error("client id is not is request", this );
			 return null;
		 }
		return warehouse;
	}


	@Override
	public Object createWareHouse(String warehouseId) throws StoreFlexServiceException {
		 log.info("Starting method createWarehouse", this);
	     Optional<Warehouse> warehouseOpt= warehouseRepository.findById(warehouseId);
	     if(warehouseOpt.isPresent()) {
	    	 Warehouse warehouse =  warehouseOpt.get();
	    	 ClientWareHousesBean warehousebean =  new ClientWareHousesBean();
	    	 ClientWareHouseAddrBean warehouseAddreBean =  new ClientWareHouseAddrBean();
	    	 warehousebean.setClientId(warehouse.getClientId());
	    	 warehousebean.setWarehouseId(warehouse.getWarehouseId());
	    	 warehousebean.setCreateBy(warehouse.getCreateBy());
	    	 warehousebean.setCreateDate(warehouse.getCreateDate());
	    	 warehousebean.setDescp(warehouse.getDescp());
	    	 warehousebean.setProfilePhotoName(warehouse.getProfilePhotoName());
	    	 warehousebean.setProfilePhoto(warehouse.getProfilePhoto());
	    	 warehousebean.setStatus(warehouse.isStatus());
	    	 warehousebean.setWarehouseTaxId(warehouse.getWarehouseTaxId());
	    	 Set<ClientWareHouseAddrBean> addressSet   = new HashSet<ClientWareHouseAddrBean>();
	    	 for(WarehouseAddress address :warehouse.getAddress()) {
	    		 warehouseAddreBean.setAddressId(address.getAddressId());
	    		 warehouseAddreBean.setAddressType(address.getAddressType());
	    		 warehouseAddreBean.setHouseNo(address.getHouseNo());
	    		 warehouseAddreBean.setPlotNo(address.getPlotNo());
	    		 warehouseAddreBean.setCity(address.getCityId());
	    		 warehouseAddreBean.setState(address.getState());
	    		 warehouseAddreBean.setCountry(address.getCountryId());
	    		 warehouseAddreBean.setPincode(address.getPincode());
	    		 warehouseAddreBean.setCreateBy(address.getCreateBy());
	    		 warehouseAddreBean.setCreateDate(address.getCreateDate());
	    		 addressSet.add(warehouseAddreBean);
	    	 }
	    	 warehousebean.setAddress(addressSet);
	    	 
	    	 return warehousebean;
	     }
		return null;
	}


	@Override
	public void upload(MultipartFile file,String clientId,String warehouseId) throws StoreFlexServiceException, IOException {
		 log.info("Starting method upload", this);
		 Optional<ClientProfile> clientProfileOpt =  clientRepository.findById(clientId);
		 if(clientProfileOpt.isPresent()) {
			  Optional<Warehouse> warehouseOpt= warehouseRepository.findById(warehouseId);
			  if(warehouseOpt.isPresent()) {
				  Warehouse warehouse = warehouseOpt.get();
				  WareHousePhoto phts =  new WareHousePhoto();
				  phts.setCreateBy("Admin");
				  phts.setCreateDate(LocalDateTime.now());
				  phts.setPhts(file.getBytes());
				  phts.setPhtDescp(file.getOriginalFilename());
				  phts.setPhtName(file.getOriginalFilename());
				  phts.setWarehouse(warehouse);
				  warehousePhotosRepository.save(phts);
			  }
			  
		 }
	}


	@Override
	public WarehouseListBean getWarehouseList(String clientId, Pageable paging) throws StoreFlexServiceException {
		 log.info("Starting method getWarehouseList", this);
		 WarehouseListBean beanlist = new WarehouseListBean();
		 List<ClientWareHousesBean> warehouseBeanList = new ArrayList<ClientWareHousesBean>();
		 Page<Warehouse> warehousePageList = warehouseRepository.findByClientId(clientId,paging);
		// List<Warehouse> warehousePageList =  warehouseRepository.getWarehouseListByClient(clientId);
		  for(Warehouse warehouse: warehousePageList) {
			  ClientWareHousesBean warehouseBean = new ClientWareHousesBean();
			  warehouseBean.setClientId(warehouse.getClientId());
			  warehouseBean.setWarehouseId(warehouse.getWarehouseId());
			  warehouseBean.setWarehouseName(warehouse.getWarehouseName());
			  warehouseBean.setCreateBy(warehouse.getCreateBy());
			  warehouseBean.setCreateDate(warehouse.getCreateDate());
			  warehouseBean.setDescp(warehouse.getDescp());
			  warehouseBean.setStatus(warehouse.isStatus());
			  warehouseBean.setUpdatedBy(warehouse.getUpdatedBy());
			  warehouseBean.setUpdateDate(warehouse.getUpdateDate());
			  warehouseBean.setWarehouseTaxId(warehouse.getWarehouseTaxId());
			  
			  ClientWareHouseAddrBean addressBean = new ClientWareHouseAddrBean();
			  Set<ClientWareHouseAddrBean> addressBeanList =  new HashSet<ClientWareHouseAddrBean>();
			  for(WarehouseAddress address:warehouse.getAddress()) {
				  addressBean.setAddressId(address.getAddressId());
				  addressBean.setAddressType(address.getAddressType());
				  addressBean.setPlotNo(address.getPlotNo());
				  addressBean.setHouseNo(address.getHouseNo());
				  addressBean.setStreetDetails(address.getStreetDetails());
				  addressBean.setCity(address.getCityId());
				  addressBean.setCountry(address.getCountryId());
				  addressBean.setCreateBy(address.getCreateBy());
				  addressBean.setCreateDate(address.getCreateDate());
				  addressBean.setPincode(address.getPincode());
				  addressBean.setUpdatedBy(address.getUpdatedBy());
				  addressBean.setUpdateDate(address.getUpdateDate());
				  addressBeanList.add(addressBean);
			  }
			  warehouseBean.setAddress(addressBeanList);
			 /* 
			  ClientWareHousePhtBean phtBean = new ClientWareHousePhtBean();
			  Set<ClientWareHousePhtBean> phtBeanList =  new HashSet<ClientWareHousePhtBean>();
		      for(WareHousePhoto pht: warehouse.getPhotos()) {
		    	  phtBean.setCreateBy(pht.getCreateBy());
		    	  phtBean.setCreateDate(pht.getCreateDate());
		    	  phtBean.setPhotoId( pht.getPhotoId());
		    	  phtBean.setPhtDescp(pht.getPhtDescp());
		    	  phtBean.setPhtName(pht.getPhtName());
		    	  phtBean.setPhts(pht.getPhts());
		    	  phtBean.setUpdatedBy(pht.getUpdatedBy());
		    	  phtBean.setUpdateDate(pht.getUpdateDate());
		    	  phtBeanList.add(phtBean);
		      }*/
		      
		      warehouseBeanList.add(warehouseBean);
		  }
		  beanlist.setTotalRecords(warehousePageList.getTotalElements());
		  beanlist.setWarehouseList(warehouseBeanList); 
		 return beanlist;
	}


	@Override
	public Set<ClientWareHousePhtBean> getWarehousePics(String warehouseId) throws StoreFlexServiceException {
		log.info("Starting method getWarehousePics", this);
		List<WareHousePhoto> warehousePhotoList = warehousePhotosRepository
					.findByWarehouseId(warehouseId);
			Set<ClientWareHousePhtBean> phtBeanList = new HashSet<ClientWareHousePhtBean>();
			for (WareHousePhoto pht : warehousePhotoList) {
				ClientWareHousePhtBean phtBean = new ClientWareHousePhtBean();
				phtBean.setCreateBy(pht.getCreateBy());
				phtBean.setCreateDate(pht.getCreateDate());
				phtBean.setPhotoId(pht.getPhotoId());
				phtBean.setPhtDescp(pht.getPhtDescp());
				phtBean.setPhtName(pht.getPhtName());
				phtBean.setPhts(pht.getPhts());
				phtBean.setUpdatedBy(pht.getUpdatedBy());
				phtBean.setUpdateDate(pht.getUpdateDate());
				phtBeanList.add(phtBean);

			}
			
		log.info("Starting method getWarehousePics", this);
		return phtBeanList;
	}


	@Override
	public WarehouseListBean getWarehouseSearch(WarehouseRequestBean build, int page, int size)
			throws StoreFlexServiceException {
		log.info("Starting method getWarehouseSearch", this);
		if(null!=build.getCity()) {
			String cityCode = cityRepository.getCityCode(build.getCity());
			List<WarehouseAddress> addressList = warehouseAddresRepository.getWarehouseByCity(cityCode);
			for(WarehouseAddress address:addressList) {
				address.getWarehouse().getWarehouseId();
				address.getWarehouse().getClientId();
				address.getWarehouse().getDescp();
				address.getWarehouse().getCreateBy();
				address.getWarehouse().getCreateDate();
				address.getWarehouse().getDescp();
				address.getWarehouse().getProfilePhoto();
				address.getWarehouse().getProfilePhotoName();
				address.getWarehouse().getWarehouseName();
				address.getAddressType();
				address.getHouseNo();
				address.getPlotNo();
				address.getStreetDetails();
				address.getCityId();
				address.getState();
				address.getCountryId();
				
				
			}
			
		}
		
		 
		return null;
	}
}
