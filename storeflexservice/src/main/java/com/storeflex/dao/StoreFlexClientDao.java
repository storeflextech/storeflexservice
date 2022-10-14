package com.storeflex.dao;

import org.springframework.data.domain.Pageable;

import com.storeflex.beans.ClientProfileListBean;
import com.storeflex.beans.StoreFlexClientAddBean;
import com.storeflex.beans.StoreFlexClientBean;
import com.storeflex.beans.StoreFlexClientContactBean;
import com.storeflex.entities.ClientProfile;
import com.storeflex.exceptions.StoreFlexServiceException;

public interface StoreFlexClientDao {

	ClientProfile createFlexClient(StoreFlexClientBean request)throws StoreFlexServiceException;

	Object getStoreFlexClient(String clientId)throws StoreFlexServiceException;

	ClientProfileListBean getStoreFlexClients(Pageable paging)throws StoreFlexServiceException;

	StoreFlexClientBean updateStoreFlexClient(StoreFlexClientBean clientBean) throws StoreFlexServiceException;

	StoreFlexClientBean deActivateClient(StoreFlexClientBean clientBean) throws StoreFlexServiceException;

	StoreFlexClientAddBean updateClientAddress(StoreFlexClientAddBean clientBean) throws StoreFlexServiceException;

	StoreFlexClientContactBean updateClientContacts(StoreFlexClientContactBean clientBean) throws StoreFlexServiceException;

	

}
