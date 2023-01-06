package com.storeflex.dao;

import java.io.IOException;

import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import com.storeflex.beans.StoreFlexBean;
import com.storeflex.beans.StoreFlexClientUsersBean;
import com.storeflex.beans.StoreFlexUserBean;
import com.storeflex.exceptions.StoreFlexServiceException;

public interface StoreFlexDao {

	Object createStoreFlex(StoreFlexBean storeFlexBean) throws StoreFlexServiceException;

	Object getStoreFlexCompyDetails()throws StoreFlexServiceException;

	Object storeFlexUser(StoreFlexUserBean req, String roleType)throws StoreFlexServiceException;

	Object uploaduserpic(String userid,MultipartFile file)throws StoreFlexServiceException, IOException;

	Object getCity(String stateId)throws StoreFlexServiceException;

	Object getState(String countyId)throws StoreFlexServiceException;

	Object getRoles()throws StoreFlexServiceException;

	Object storeFlexUserFinalizeSL(StoreFlexUserBean req, String roleType,String clientCodes)throws StoreFlexServiceException;
	
	Object storeFlexUserFinalizeCL(StoreFlexUserBean req, String roleType,String clientCodes)throws StoreFlexServiceException;
	
	Object storeFlexUserFinalizeCU(StoreFlexUserBean req, String roleType)throws StoreFlexServiceException;

	Object getStoreFlexUsersDetails(Pageable paging)throws StoreFlexServiceException;

	Object getStoreFlexUserId(String userid)throws StoreFlexServiceException;

	Object clientUsers(String clientId,Pageable paging,String status)throws StoreFlexServiceException;

	Object updateclientusers(StoreFlexClientUsersBean requestBean)throws StoreFlexServiceException;

	Object clientUserById(String userId)throws StoreFlexServiceException;

}
