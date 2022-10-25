package com.storeflex.services;

import org.springframework.data.domain.Pageable;

import com.storeflex.beans.ClientProfileListBean;
import com.storeflex.beans.StoreFlexClientAddBean;
import com.storeflex.beans.StoreFlexClientBean;
import com.storeflex.beans.StoreFlexClientContactBean;
import com.storeflex.entities.ClientProfile;
import com.storeflex.exceptions.StoreFlexServiceException;

public interface StoreFlexClientService {

	ClientProfile createFlexClient(StoreFlexClientBean request)throws StoreFlexServiceException;

	Object getStoreFlexClient(String clientId)throws StoreFlexServiceException;

	ClientProfileListBean getStoreFlexClients(Pageable paging)throws StoreFlexServiceException;

	Object updateStoreFlexClient(StoreFlexClientBean clientBean) throws StoreFlexServiceException;

	Object deActivateClient(StoreFlexClientBean clientBean) throws StoreFlexServiceException;

	Object updateClientAddress(StoreFlexClientAddBean clientBean) throws StoreFlexServiceException;

	Object updateClientContacts(StoreFlexClientContactBean clientBean) throws StoreFlexServiceException;
}
