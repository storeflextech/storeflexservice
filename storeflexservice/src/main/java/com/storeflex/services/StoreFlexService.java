package com.storeflex.services;

import java.io.IOException;

import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import com.storeflex.beans.StoreFlexBean;
import com.storeflex.beans.StoreFlexClientUsersBean;
import com.storeflex.beans.StoreFlexUserBean;
import com.storeflex.exceptions.StoreFlexServiceException;

public interface StoreFlexService {
public Object createStoreFlex(StoreFlexBean storeFlexBean) throws StoreFlexServiceException;

public Object getStoreFlexCompyDetails()throws StoreFlexServiceException;

public Object storeFlexUser(StoreFlexUserBean req, String roleType)throws StoreFlexServiceException;

public Object uploaduserpic(String userid, MultipartFile file)throws StoreFlexServiceException, IOException;

public Object getCity(String stateId)throws StoreFlexServiceException;

public Object getState(String countyId)throws StoreFlexServiceException;

public Object getRoles()throws StoreFlexServiceException;

public Object storeFlexUserFinalize(StoreFlexUserBean req, String roleType,String clientCodes)throws StoreFlexServiceException;

public Object getStoreFlexUsersDetails(Pageable paging)throws StoreFlexServiceException;

public Object getStoreFlexUserId(String userid)throws StoreFlexServiceException;

public Object clientUsers(String clientId,Pageable paging,String status)throws StoreFlexServiceException;

public Object updateclientusers(StoreFlexClientUsersBean requestBean)throws StoreFlexServiceException;

public Object clientUserById(String userId)throws StoreFlexServiceException;


}
