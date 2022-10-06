package com.storeflex.helpers;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.storeflex.beans.StoreFlexClientBean;
import com.storeflex.beans.StoreFlexClientAddBean;
import com.storeflex.beans.StoreFlexClientContactBean;
import com.storeflex.entities.ClientAddress;
import com.storeflex.entities.ClientContacts;
import com.storeflex.entities.ClientProfile;


@Component
public class StoreFlexClientHelper {
	private static final Logger log = LoggerFactory.getLogger(StoreFlexClientHelper.class);

	public Set<ClientAddress> populateClientAddress(StoreFlexClientBean request,ClientProfile clientProfile,Set<ClientAddress> clientAddressSet){
		 log.info("Starting method populateClientAddress", this);
		for(StoreFlexClientAddBean clientAddressReq : request.getAddresses()) {
			 ClientAddress address = new ClientAddress();
			 address.setAddressType(clientAddressReq.getAddressType());
             address.setStreetDetails(clientAddressReq.getStreetDetails());
			 address.setCity(clientAddressReq.getPlotNo());
			 address.setState(clientAddressReq.getCity());
			 address.setCountry(clientAddressReq.getCountry());
			 address.setPincode(clientAddressReq.getPincode());
			 address.setCreateBy("ADMIN");
			 address.setCreateDate(LocalDateTime.now());
			 address.setClientProfile(clientProfile);
			 clientAddressSet.add(address);
		 }
		 log.info("Ending method populateClientAddress", this);
		return clientAddressSet;
	}
	
	public Set<ClientContacts> populateClientContact(StoreFlexClientBean request,ClientProfile clientProfile, Set<ClientContacts> clientContactSet){
		 log.info("Starting method populateClientContact", this);
		for(StoreFlexClientContactBean clientContactReq : request.getContact()) {
			 ClientContacts contacts = new ClientContacts();
			 contacts.setContactName(clientContactReq.getContactName());
			 contacts.setCreateBy("ADMIN");
			 contacts.setCreateDate(LocalDateTime.now());
			 contacts.setEmailId(clientContactReq.getEmailId());
			 contacts.setLandLineExt(clientContactReq.getLandLineExt());
			 contacts.setLandLine(clientContactReq.getLandLine());
			 contacts.setMobileNo(clientContactReq.getMobileNo());
			 contacts.setClientProfile(clientProfile);
			 clientContactSet.add(contacts);
		 }
		 log.info("Ending method populateClientContact", this);
		return clientContactSet;
	}
	
	public StoreFlexClientBean populateClientList(ClientProfile clientProfile,StoreFlexClientBean clientBean) {
		log.info("Starting method populateClientList", this);
		Set<StoreFlexClientAddBean> addressSet =  new HashSet<StoreFlexClientAddBean>();
		Set<StoreFlexClientContactBean> contactSet =  new HashSet<StoreFlexClientContactBean>();
		clientBean.setClientId(clientProfile.getClientId());
		clientBean.setCompyDesc(clientProfile.getCompyDesc());
		clientBean.setCompyName(clientProfile.getCompyName());
	    clientBean.setCreateBy(clientProfile.getCreateBy());
	    clientBean.setCreateDate(clientProfile.getCreateDate());
	    clientBean.setUrl(clientProfile.getUrl());
	    clientBean.setPhotoName(clientProfile.getPhotoName());
	    clientBean.setPhoto(clientProfile.getPhoto());
	    
	    Set<ClientAddress> clientAddressSet= clientProfile.getAddresses();
	    if(!CollectionUtils.isEmpty(clientAddressSet)) {
	    	 for(ClientAddress clientAdd :clientAddressSet) {
	    		 StoreFlexClientAddBean address = new StoreFlexClientAddBean();	
	    		 address.setAddressId(clientAdd.getAddressId()); 
	    		 address.setAddressType(clientAdd.getAddressType());
	    		 address.setPlotNo(clientAdd.getPlotNo());
	    		 address.setStreetDetails(clientAdd.getStreetDetails());
	    		 address.setCity(clientAdd.getCity());
	    		 address.setState(clientAdd.getState());
	    		 address.setPincode(clientAdd.getPincode());
	    		 address.setCountry(clientAdd.getCountry());
	    		 addressSet.add(address);
	    	 }
	    }
	    clientBean.setAddresses(addressSet);
	    
	    Set<ClientContacts> clientContactSet =  clientProfile.getContact();
	    if(!CollectionUtils.isEmpty(clientContactSet)) {
	    	for(ClientContacts clientContacts : clientContactSet) {
		    	StoreFlexClientContactBean contacts =  new StoreFlexClientContactBean();
		    	contacts.setContactId(clientContacts.getContactId());
		    	contacts.setContactName(clientContacts.getEmailId());
		    	contacts.setCreateBy(clientContacts.getCreateBy());
		    	contacts.setCreateDate(clientContacts.getCreateDate());
		    	contacts.setLandLineExt(clientContacts.getLandLineExt());
		    	contacts.setLandLine(clientContacts.getLandLine());
		    	contacts.setMobileNo(clientContacts.getMobileNo());
		    	contactSet.add(contacts);
	    	}
	    }
	    clientBean.setContact(contactSet);
	    log.info("Ending method populateClientList", this);
		return clientBean;
	}
}
