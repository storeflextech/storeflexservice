package com.storeflex.dao.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.storeflex.beans.StoreFlexClientBean;
import com.storeflex.beans.ClientProfileListBean;
import com.storeflex.beans.StoreFlexClientAddBean;
import com.storeflex.beans.StoreFlexClientContactBean;
import com.storeflex.dao.StoreFlexClientDao;
import com.storeflex.entities.ClientAddress;
import com.storeflex.entities.ClientContacts;
import com.storeflex.entities.ClientProfile;
import com.storeflex.entities.UniqueId;
import com.storeflex.exceptions.StoreFlexServiceException;
import com.storeflex.helpers.StoreFlexClientHelper;
import com.storeflex.helpers.StoreFlexHelper;
import com.storeflex.repositories.StoreFlexClientRepository;
import com.storeflex.repositories.UniquePrefixRepository;

@Component
public class StoreFlexClientDaoImpl implements StoreFlexClientDao{
	private static final Logger log = LoggerFactory.getLogger(StoreFlexClientDao.class);
	
	@Autowired
	StoreFlexClientRepository storeFlexClientRepository;
	@Autowired
	StoreFlexHelper helper;
	@Autowired
	StoreFlexClientHelper clientHelper;
	@Autowired
	UniquePrefixRepository uniquePrefixRespository;
	
	@Override
	public ClientProfile createFlexClient(StoreFlexClientBean request) throws StoreFlexServiceException {
		 log.info("Starting method createFlexClient", this);
		 
		 List<UniqueId> prefixList = uniquePrefixRespository.findAll();
		 UniqueId uniqueId= helper.getStoreFlexClintPrefixDetails(prefixList);
		 
		 ClientProfile clientProfile = new ClientProfile();
		 Set<ClientAddress> clientAddressSet =  new HashSet<ClientAddress>();
		 Set<ClientContacts> clientContactSet =  new HashSet<ClientContacts>();
		 if(null!=request) {
			 clientProfile.setClientId(uniqueId.getPrex()+uniqueId.getNextReserveId());
			 clientProfile.setCompyDesc(request.getCompyDesc());
			 clientProfile.setCompyName(request.getCompyName());
			 clientProfile.setCreateBy("ADMIN");
			 clientProfile.setCreateDate(LocalDateTime.now());
			 clientProfile.setPhotoName(request.getPhotoName());
			 clientProfile.setPhoto(request.getPhoto());
		     clientProfile.setUrl(request.getUrl());
			 clientAddressSet = clientHelper.populateClientAddress(request, clientProfile, clientAddressSet);
			 clientContactSet =clientHelper.populateClientContact(request,clientProfile,clientContactSet);
			 clientProfile.setAddresses(clientAddressSet);
			 clientProfile.setContact(clientContactSet);
			 clientProfile = storeFlexClientRepository.save(clientProfile);
			 
			 //increase the count of Clinet ReserveId
			 uniqueId.setNextReserveId(uniqueId.getNextReserveId()+1);
			 uniquePrefixRespository.save(uniqueId);
		 }
		return clientProfile;
	}

	@Override
	public Object getStoreFlexClient(String clientId) throws StoreFlexServiceException {
		 log.info("Starting method getStoreFlexClient", this);
		Optional<ClientProfile> clientProfileOpt = storeFlexClientRepository.findById(clientId);
		if(clientProfileOpt.isPresent()) {
			ClientProfile clientProfile = clientProfileOpt.get();
			StoreFlexClientBean clientBean = new StoreFlexClientBean();
			clientBean = clientHelper.populateClientList(clientProfile,clientBean);
			return clientBean;
		}
		return null;
	}

	@Override
	public ClientProfileListBean getStoreFlexClients(Pageable paging) throws StoreFlexServiceException {
		 log.info("Starting method getStoreFlexClients", this);
		 ClientProfileListBean listBean = new ClientProfileListBean();
		 List<ClientProfile> clientList =  new ArrayList<ClientProfile>();
		List<StoreFlexClientBean> clientBeanList = new ArrayList<StoreFlexClientBean>();
		Page<ClientProfile> clientListPageable = storeFlexClientRepository.findAll(paging);
		clientList = clientListPageable.getContent();
		if(!CollectionUtils.isEmpty(clientList)) {
			
			for(ClientProfile client : clientList) {
				StoreFlexClientBean clientBean = new StoreFlexClientBean();
				clientBean = clientHelper.populateClientList(client, clientBean);
				clientBeanList.add(clientBean);
			}
		};
		listBean.setTotalRecords(clientListPageable.getTotalElements());
		listBean.setClientList(clientBeanList);
		return listBean;
	}

}
