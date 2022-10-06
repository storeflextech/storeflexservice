package com.storeflex.dao;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import com.storeflex.beans.StoreFlexBean;
import com.storeflex.beans.StoreFlexUserBean;
import com.storeflex.exceptions.StoreFlexServiceException;

public interface StoreFlexDao {

	Object createStoreFlex(StoreFlexBean storeFlexBean) throws StoreFlexServiceException;

	Object getStoreFlexCompyDetails()throws StoreFlexServiceException;

	Object storeFlexUser(StoreFlexUserBean req, String roleType, String compyCode)throws StoreFlexServiceException;

	Object uploaduserpic(String userid,MultipartFile file)throws StoreFlexServiceException, IOException;

	Object getCity(String stateId)throws StoreFlexServiceException;

	Object getState(String countyId)throws StoreFlexServiceException;

	Object getRoles()throws StoreFlexServiceException;

}
