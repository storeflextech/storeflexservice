package com.storeflex.services;

import java.io.IOException;
import java.util.Map;

import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

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

	Object uploadClientProfilePic(String clientId, MultipartFile file)throws StoreFlexServiceException, IOException;

	Map<String, Boolean> deleteClientById(String clientId)throws StoreFlexServiceException;

	Map<String, String> clientDropList()throws StoreFlexServiceException;;
}
