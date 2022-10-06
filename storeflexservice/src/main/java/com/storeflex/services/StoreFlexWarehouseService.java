package com.storeflex.services;

import java.io.IOException;

import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import com.storeflex.beans.ClientWareHousesBean;
import com.storeflex.beans.WarehouseListBean;
import com.storeflex.entities.Warehouse;
import com.storeflex.exceptions.StoreFlexServiceException;

public interface StoreFlexWarehouseService {

	Warehouse createWarehouse(ClientWareHousesBean request)throws StoreFlexServiceException;

	Object createWarehouse(String warehouseId)throws StoreFlexServiceException;

	void upload(MultipartFile file, String clientId,String warehouseId) throws StoreFlexServiceException, IOException;

	WarehouseListBean getWarehouseList(String clientId, Pageable paging) throws StoreFlexServiceException;



}
