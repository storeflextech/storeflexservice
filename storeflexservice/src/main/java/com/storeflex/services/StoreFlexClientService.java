package com.storeflex.services;

import java.io.IOException;
import java.util.List;
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

	ClientProfileListBean getStoreFlexClients(Pageable paging,String status, String clientId, String gstno)throws StoreFlexServiceException;

	//Object updateStoreFlexClient(StoreFlexClientBean clientBean) throws StoreFlexServiceException;

	boolean deActivateClient(String clientId) throws StoreFlexServiceException;

	//Object updateClientAddress(StoreFlexClientAddBean clientBean) throws StoreFlexServiceException;

	//Object updateClientContacts(StoreFlexClientContactBean clientBean) throws StoreFlexServiceException;

    byte[] uploadClientProfilePic(String clientId, MultipartFile file)throws StoreFlexServiceException, IOException;

	Map<String, Boolean> deleteClientById(String clientId)throws StoreFlexServiceException;

	List<Map> clientDropList()throws StoreFlexServiceException;

	StoreFlexClientBean uploadClientProfilePic(String clientId)throws StoreFlexServiceException;

	boolean gstcheckavailability(String gst)throws StoreFlexServiceException;


}
