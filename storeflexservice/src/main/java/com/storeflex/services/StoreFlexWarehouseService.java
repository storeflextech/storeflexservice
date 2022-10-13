package com.storeflex.services;

import java.io.IOException;
import java.util.Set;

import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import com.storeflex.beans.ClientWareHousePhtBean;
import com.storeflex.beans.ClientWareHousesBean;
import com.storeflex.beans.WarehouseListBean;
import com.storeflex.beans.WarehouseRequestBean;
import com.storeflex.entities.Warehouse;
import com.storeflex.exceptions.StoreFlexServiceException;

public interface StoreFlexWarehouseService {

	Warehouse createWarehouse(ClientWareHousesBean request)throws StoreFlexServiceException;

	Object createWarehouse(String warehouseId)throws StoreFlexServiceException;

	void upload(MultipartFile file, String clientId,String warehouseId) throws StoreFlexServiceException, IOException;

	WarehouseListBean getWarehouseList(String clientId, Pageable paging) throws StoreFlexServiceException;

	Set<ClientWareHousePhtBean> getWarehousePics(String warehouseId)throws StoreFlexServiceException;

	WarehouseListBean getWarehouseSearch(WarehouseRequestBean build, int page, int size)throws StoreFlexServiceException;



}
