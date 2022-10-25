package com.storeflex.services.impl;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.storeflex.beans.ClientProfileListBean;
import com.storeflex.beans.StoreFlexClientAddBean;
import com.storeflex.beans.StoreFlexClientBean;
import com.storeflex.beans.StoreFlexClientContactBean;
import com.storeflex.dao.StoreFlexClientDao;
import com.storeflex.entities.ClientProfile;
import com.storeflex.exceptions.StoreFlexServiceException;
import com.storeflex.services.StoreFlexClientService;

@Service
public class StoreFlexClientImpl implements StoreFlexClientService{

	private static final Logger log = LoggerFactory.getLogger(StoreFlexClientService.class);

	@Autowired
	StoreFlexClientDao dao;
		
	@Override
	@Transactional
	public ClientProfile createFlexClient(StoreFlexClientBean request) throws StoreFlexServiceException {
		 log.info("Starting method createFlexClient", this);
		return dao.createFlexClient(request);
	}

	@Override
	@Transactional
	public Object getStoreFlexClient(String clientId) throws StoreFlexServiceException {
	    log.info("Starting method getStoreFlexClient", this);
	    return dao.getStoreFlexClient(clientId);
	}

	@Override
	@Transactional
	public ClientProfileListBean getStoreFlexClients(Pageable paging) throws StoreFlexServiceException {
		log.info("Starting method getStoreFlexClients", this);
		return dao.getStoreFlexClients(paging);
	}

	@Override
	@Transactional	
	public StoreFlexClientBean updateStoreFlexClient(StoreFlexClientBean clientBean) throws StoreFlexServiceException {
		 log.info("Starting method updateStoreFlexClient", this);
		return dao.updateStoreFlexClient(clientBean);
	}

	@Override
	@Transactional
	public StoreFlexClientBean deActivateClient(StoreFlexClientBean clientBean) throws StoreFlexServiceException {
		 log.info("Starting method deActivateClient", this);
		return dao.deActivateClient(clientBean);
	}

	@Override
	@Transactional
	public StoreFlexClientAddBean updateClientAddress(StoreFlexClientAddBean clientBean) throws StoreFlexServiceException {
		 log.info("Starting method updateClientAddress", this);
		return dao.updateClientAddress(clientBean);
	}

	@Override
	@Transactional
	public StoreFlexClientContactBean updateClientContacts(StoreFlexClientContactBean clientBean) throws StoreFlexServiceException {
		 log.info("Starting method updateClientContacts", this);
		return dao.updateClientContacts(clientBean);
	}

}
