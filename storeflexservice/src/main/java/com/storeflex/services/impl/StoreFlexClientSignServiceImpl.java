package com.storeflex.services.impl;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.storeflex.dao.StoreFlexClientSignDao;
import com.storeflex.exceptions.StoreFlexServiceException;
import com.storeflex.services.StoreFlexClientService;
import com.storeflex.services.StoreFlexClientSignService;

@Service
public class StoreFlexClientSignServiceImpl implements StoreFlexClientSignService {

    private static final Logger log = LoggerFactory.getLogger(StoreFlexClientService.class);

    @Autowired
	StoreFlexClientSignDao dao;

    @Override
	@Transactional
    public void createClientSignInfo(String clientId, String requestId, String requestStatus) throws StoreFlexServiceException
    {
        log.info("Starting method updateClientSignInfo", this);
		dao.createClientSignInfo(clientId, requestId, requestStatus);
    }

    @Override
	@Transactional
    public void updateClientSignInfo(String requestId, String requestStatus) throws StoreFlexServiceException
    {
        log.info("Starting method updateClientSignInfo", this);
		dao.updateClientSignInfo(requestId, requestStatus);
    }
    
}
