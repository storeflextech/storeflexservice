package com.storeflex.services.impl;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.storeflex.beans.ErrorCodeBean;
import com.storeflex.beans.StoreFlexUserBean;
import com.storeflex.beans.TestAuthBean;
import com.storeflex.constants.ErrorCodes;
import com.storeflex.dao.StoreFlexAuthDao;
import com.storeflex.entities.StoreFlexUsers;
import com.storeflex.exceptions.StoreFlexServiceException;
import com.storeflex.repositories.StoreFlexUserRepository;
import com.storeflex.services.StoreFlexAuthService;
@Component
public class StoreFlexAuthServiceImpl implements StoreFlexAuthService{
	private static final Logger log = LoggerFactory.getLogger(StoreFlexAuthServiceImpl.class);

	@Autowired
	StoreFlexAuthDao storeFlexAuthDao;
	
	
	@Override
	@Transactional
	public Object sllogin(TestAuthBean bean) throws StoreFlexServiceException {
		 log.info("Starting method sllogin", this);
		 return storeFlexAuthDao.sllogin(bean);
	}


	@Override
	@Transactional
	public Object login(TestAuthBean bean) throws StoreFlexServiceException {
		 log.info("Starting method login", this);
		 return storeFlexAuthDao.login(bean);
	}

}
