package com.storeflex.services.impl;

import java.io.IOException;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.storeflex.beans.StoreFlexBean;
import com.storeflex.beans.StoreFlexClientUsersBean;
import com.storeflex.beans.StoreFlexUserBean;
import com.storeflex.constants.StoreFlexConstants;
import com.storeflex.dao.StoreFlexDao;
import com.storeflex.exceptions.StoreFlexServiceException;
import com.storeflex.services.StoreFlexService;
@Service
public class StoreFlexServiceImpl implements StoreFlexService{
	private static final Logger log = LoggerFactory.getLogger(StoreFlexServiceImpl.class);

	@Autowired
	StoreFlexDao storeFlexDao;
	@Override
	@Transactional
	public Object createStoreFlex(StoreFlexBean storeFlexBean) throws StoreFlexServiceException {
		 log.info("Starting method createStoreFlex", this);
		return storeFlexDao.createStoreFlex(storeFlexBean);
	}
	@Override
	@Transactional
	public Object getStoreFlexCompyDetails() throws StoreFlexServiceException {
		 log.info("Starting method getStoreFlexCompyDetails", this);
		return storeFlexDao.getStoreFlexCompyDetails();
	}
	@Override
	@Transactional
	public Object storeFlexUser(StoreFlexUserBean req, String roleType)
			throws StoreFlexServiceException {
		 log.info("Starting method storeFlexUser", this);
		return storeFlexDao.storeFlexUser(req,roleType);
	}
	
	@Override
	@Transactional
	public Object storeFlexUserFinalize(StoreFlexUserBean req, String roleType,String clientCodes) throws StoreFlexServiceException {
		log.info("Starting method storeFlexUserFinalize", this);
		if(req.getLoginType().equalsIgnoreCase(StoreFlexConstants.SL_USER) && clientCodes.equalsIgnoreCase("SF-101")) {
			return storeFlexDao.storeFlexUserFinalizeSL(req,roleType,clientCodes);
		}
		if(req.getLoginType().equalsIgnoreCase(StoreFlexConstants.CL_USER)) {
			return storeFlexDao.storeFlexUserFinalizeCL(req,roleType,clientCodes);
		}
		if(req.getLoginType().equalsIgnoreCase(StoreFlexConstants.CU_USER)) {
			return storeFlexDao.storeFlexUserFinalizeCU(req,roleType);
		}
		return null;
	}
	
	@Override
	@Transactional
	public Object uploaduserpic(String userid, MultipartFile file) throws StoreFlexServiceException, IOException {
		 log.info("Starting method uploaduserpic", this);
		 return storeFlexDao.uploaduserpic(userid,file);
	}
	@Override
	@Transactional
	public Object getCity(String stateId) throws StoreFlexServiceException {
		 log.info("Starting method getCity", this);
	     return storeFlexDao.getCity(stateId);
	}
	@Override
	@Transactional
	public Object getState(String countyId) throws StoreFlexServiceException {
		 log.info("Starting method getState", this);
		 return storeFlexDao.getState(countyId);
	}
	@Override
	@Transactional
	public Object getRoles() throws StoreFlexServiceException {
		 log.info("Starting method getRoles", this);
		return storeFlexDao.getRoles();
	}
	@Override
	@Transactional
	public Object getStoreFlexUsersDetails(Pageable paging) throws StoreFlexServiceException {
		 log.info("Starting method getStoreFlexUsersDetails", this);
		return storeFlexDao.getStoreFlexUsersDetails(paging);
	}
	@Override
	@Transactional
	public Object getStoreFlexUserId(String userid) throws StoreFlexServiceException {
		 log.info("Starting method getStoreFlexUserId", this);
		return storeFlexDao.getStoreFlexUserId(userid);
	}
	@Override
	@Transactional
	public Object clientUsers(String clientId,Pageable paging,String status) throws StoreFlexServiceException {
		 log.info("Starting method clientUsers", this);
		return storeFlexDao.clientUsers(clientId,paging,status);
	}
	@Override
	@Transactional
	public Object updateclientusers(StoreFlexClientUsersBean requestBean) throws StoreFlexServiceException {
		 log.info("Starting method updateclientusers", this);
		 return storeFlexDao.updateclientusers(requestBean);
	}
	@Override
	public Object clientUserById(String userId) throws StoreFlexServiceException {
		log.info("Starting method clientUserById", this);
		 return storeFlexDao.clientUserById(userId);
	}
	
	
	 
}
