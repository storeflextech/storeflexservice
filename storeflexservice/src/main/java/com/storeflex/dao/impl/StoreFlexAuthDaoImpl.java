package com.storeflex.dao.impl;

import java.util.Optional;

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
import com.storeflex.entities.UsersReg;
import com.storeflex.exceptions.StoreFlexServiceException;
import com.storeflex.repositories.StoreFlexUserRepository;
import com.storeflex.repositories.UserAuthRepository;

@Component
public class StoreFlexAuthDaoImpl implements StoreFlexAuthDao {

	private static final Logger log = LoggerFactory.getLogger(StoreFlexAuthDaoImpl.class);

	@Autowired
	UserAuthRepository userAuthRepository;
	@Autowired
	StoreFlexUserRepository storeFlexUserRepository;

	@Override
	public Object sllogin(TestAuthBean bean) throws StoreFlexServiceException {
		log.info("Starting method sllogin", this);
		ErrorCodeBean error = new ErrorCodeBean();
		StoreFlexUserBean userBean = new StoreFlexUserBean();
	   UsersReg usersReg = userAuthRepository.searchEmailExist(bean.getEmailId());
		if (null!=usersReg && null!=usersReg.getRegId()) {
			StoreFlexUsers user = storeFlexUserRepository.authorizeUser(bean.getEmailId());
			userBean.setFirstName(user.getFirstName());
			userBean.setMiddleName(user.getMiddleName());
			userBean.setLastName(user.getLastName());
			userBean.setEmail(usersReg.getEmail());
			userBean.setMobileNo(usersReg.getPhno());
			userBean.setRoleType(user.getRoleType());
			userBean.setStatus(usersReg.getEmail());
			userBean.setLoginType(usersReg.getUserType());
			userBean.setRedirectUrl("/storeflexuserdashboard");
		} else {
			error.setErrorCode("Password is not valid, Please try again::" + ErrorCodes.SL_INVALID_PASSWORD);
			error.setErrorMessage("Enter password is not valid");
			return error;
		}
		log.info("End method sllogin", this);
		return userBean;
	}
}
