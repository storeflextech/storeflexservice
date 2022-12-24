package com.storeflex.services.impl;

import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.storeflex.beans.ClientWareHousePhtBean;
import com.storeflex.beans.ClientWareHousesBean;
import com.storeflex.beans.WarehouseCategoriesBean;
import com.storeflex.beans.WarehouseListBean;
import com.storeflex.beans.WarehouseRequestBean;
import com.storeflex.beans.WarehouseViewBeanList;
import com.storeflex.dao.StoreFlexWarehouseDao;
import com.storeflex.entities.Warehouse;
import com.storeflex.exceptions.StoreFlexServiceException;
import com.storeflex.services.StoreFlexWarehouseService;

@Service
public class StoreFlexWarehouseServiceImpl implements StoreFlexWarehouseService {
	private static final Logger log = LoggerFactory.getLogger(StoreFlexWarehouseServiceImpl.class);
	
	@Autowired
	StoreFlexWarehouseDao dao;
	
	@Override
	@Transactional
	public Warehouse createWarehouse(ClientWareHousesBean request) throws StoreFlexServiceException {
		 log.info("Starting method createWarehouse", this);
		return dao.createWarehouse(request);
	}

	@Override
	@Transactional
	public Object getWarehouseById(String warehouseId) throws StoreFlexServiceException {
		 log.info("Starting method createWarehouse", this);
		return dao.getWarehouseById(warehouseId);
	}

	@Override
	@Transactional
	public void upload(MultipartFile file,String clientId,String warehouseId) throws StoreFlexServiceException, IOException {
		 log.info("Starting method upload", this);
		 dao.upload(file,clientId,warehouseId);
	}

	@Override
	@Transactional
	public WarehouseListBean getWarehouseList(String clientId, Pageable paging) throws StoreFlexServiceException {
		log.info("Starting method getWarehouseList", this);
		return dao.getWarehouseList(clientId,paging);
	}

	@Override
	@Transactional
	public Set<ClientWareHousePhtBean> getWarehousePics(String warehouseId) throws StoreFlexServiceException {
		log.info("Starting method getWarehousePics", this);
		return dao.getWarehousePics(warehouseId);
	}

	@Override
	@Transactional
	public WarehouseViewBeanList getWarehouseSearch(WarehouseRequestBean build, int page, int size)
			throws StoreFlexServiceException {
		log.info("Starting method getWarehouseSearch", this);
		return dao.getWarehouseSearch(build,page,size);
	}

	@Override
	@Transactional
	public byte[] uploadWareHouseProfilePic(String warehouseId, MultipartFile file) throws StoreFlexServiceException, IOException {
		log.info("Starting method uploadWareHouseProfilePic", this);
		return dao.uploadWareHouseProfilePic(warehouseId,file);
	}

	@Override
	@Transactional
	public Map<String, Boolean> deleteWarehouseById(String warehouseId) throws StoreFlexServiceException {
		log.info("Starting method deleteWarehouseById", this);
		return dao.deleteWarehouseById(warehouseId);
	}

	@Override
	@Transactional
	public WarehouseViewBeanList getAllWarehouses(int page, int size,String status) throws StoreFlexServiceException {
		log.info("Starting method getAllWarehouses", this);
		return dao.getAllWarehouses(page,size,status);
	}

	@Override
	@Transactional
	public WarehouseCategoriesBean getWareshouseCategories() throws StoreFlexServiceException {
		log.info("Starting method getWareshouseCategories", this);
		return dao.getWareshouseCategories();
	}

}
