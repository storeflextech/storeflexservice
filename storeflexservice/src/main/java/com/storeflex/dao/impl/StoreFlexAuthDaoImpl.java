package com.storeflex.dao.impl;

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

@Component
public class StoreFlexAuthDaoImpl implements StoreFlexAuthDao{

	private static final Logger log = LoggerFactory.getLogger(StoreFlexAuthDaoImpl.class);
	 
	@Autowired
	StoreFlexUserRepository storeFlexUserRepository;
	@Autowired
	StoreFlexUserBean userBean;
	@Override
	public Object sllogin(TestAuthBean bean) throws StoreFlexServiceException {
		 log.info("Starting method sllogin", this);
		 ErrorCodeBean error = new ErrorCodeBean();
	     StoreFlexUsers userDetails =  storeFlexUserRepository.authorizeUser(bean.getEmailId());
	     if(null!=userDetails && null!=userDetails.getEmail() && null!=userDetails.getPwd()) {
	    	 if(bean.getPassword().equals(userDetails.getPwd())) {
	    		 userBean.setFirstName(userDetails.getFirstName());
	    		 userBean.setMiddleName(userDetails.getMiddleName());
	    		 userBean.setLastName(userDetails.getLastName());
	    		 userBean.setEmail(userDetails.getEmail());
	    		 userBean.setMobileNo(userDetails.getMobileNo());
	    		 userBean.setRoleType(userDetails.getRoleType());
	    		 userBean.setStatus(userDetails.getState());
	    		 userBean.setRedirectUrl("/storeflexuserdashboard");
	    		 return userBean;
	    	 }else {
	    		 error.setErrorCode("Password is not valid, Please try again::"+ErrorCodes.SL_INVALID_PASSWORD);
	    		 error.setErrorMessage("Enter password is not valid");
	    		 return error;
	    	 }
	     }else {
	    	 error.setErrorCode("Enter email is not found on storeflex record ::"+ErrorCodes.SL_INVALID_EMAIL);
    		 error.setErrorMessage("Enter email is not found on storeflex record");
    		 return error; 
	     }
	}

}
