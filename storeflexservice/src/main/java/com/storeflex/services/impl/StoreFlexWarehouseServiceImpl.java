package com.storeflex.services.impl;

import java.io.IOException;
import java.util.UUID;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.storeflex.beans.ClientWareHousesBean;
import com.storeflex.beans.WarehouseListBean;
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
	public Object createWarehouse(String warehouseId) throws StoreFlexServiceException {
		 log.info("Starting method createWarehouse", this);
		return dao.createWareHouse(warehouseId);
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

}
