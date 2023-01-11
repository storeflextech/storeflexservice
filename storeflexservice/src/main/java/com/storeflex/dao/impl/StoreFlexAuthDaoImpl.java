package com.storeflex.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.storeflex.beans.ErrorCodeBean;
import com.storeflex.beans.StoreFlexClientUsersBean;
import com.storeflex.beans.StoreFlexUserBean;
import com.storeflex.beans.TestAuthBean;
import com.storeflex.constants.ErrorCodes;
import com.storeflex.dao.StoreFlexAuthDao;
import com.storeflex.entities.ClientUsers;
import com.storeflex.entities.GuestUsers;
import com.storeflex.entities.StoreFlexUsers;
import com.storeflex.entities.UsersReg;
import com.storeflex.exceptions.StoreFlexServiceException;
import com.storeflex.repositories.ClientUsersRepository;
import com.storeflex.repositories.GuestUsersRepository;
import com.storeflex.repositories.StoreFlexUserRepository;
import com.storeflex.repositories.UserAuthRepository;

@Component
public class StoreFlexAuthDaoImpl implements StoreFlexAuthDao {

	private static final Logger log = LoggerFactory.getLogger(StoreFlexAuthDaoImpl.class);

	@Autowired
	UserAuthRepository userAuthRepository;
	@Autowired
	StoreFlexUserRepository storeFlexUserRepository;
	@Autowired
	ClientUsersRepository clientUsersRepository;
	@Autowired
	GuestUsersRepository guestUserRepository;

	@Override
	public Object sllogin(TestAuthBean bean) throws StoreFlexServiceException {
		log.info("Starting method sllogin", this);
		ErrorCodeBean error = new ErrorCodeBean();
		StoreFlexUserBean userBean = new StoreFlexUserBean();
		UsersReg usersReg = userAuthRepository.validateUser(bean.getEmailId(),bean.getPassword());
		if (null != usersReg && null != usersReg.getRegId()) {
			StoreFlexUsers user = storeFlexUserRepository.authorizeUser(bean.getEmailId());
			if (null != user) {
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
				error.setErrorCode("Invalid login credentail, Please try again::" + ErrorCodes.SL_INVALID_PASSWORD);
				error.setErrorMessage("Check your enter email and password");
				return error;
			}

		} else {
			error.setErrorCode("Invalid login credentail, Please try again::" + ErrorCodes.SL_INVALID_PASSWORD);
			error.setErrorMessage("Check your enter email and password");
			return error;
		}
		log.info("End method sllogin", this);
		return userBean;
	}

	@Override
	public Object login(TestAuthBean bean) throws StoreFlexServiceException {
		log.info("Starting method login", this);
		ErrorCodeBean error = new ErrorCodeBean();
		StoreFlexClientUsersBean userBean = new StoreFlexClientUsersBean();
		UsersReg usersReg = userAuthRepository.validateUser(bean.getEmailId(),bean.getPassword());
		if (null != usersReg && null != usersReg.getRegId() && usersReg.getUserType().equalsIgnoreCase("CL")) {
			ClientUsers user = clientUsersRepository.authorizeUser(bean.getEmailId());
			if (null != user) {
				userBean.setFirstName(user.getFirstName());
				userBean.setMiddleName(user.getMiddleName());
				userBean.setLastName(user.getLastName());
				userBean.setEmail(usersReg.getEmail());
				userBean.setMobileNo(usersReg.getPhno());
				userBean.setRoleType(user.getRoleType());
				userBean.setStatus(usersReg.getEmail());
				userBean.setLoginType(usersReg.getUserType());
				userBean.setRedirectUrl("/storeflexclientdashboard");
			}else {
				error.setErrorCode("Invalid login credentail, Please try again::" + ErrorCodes.SL_INVALID_PASSWORD);
				error.setErrorMessage("Check your enter email and password");
				return error;
			}
		}
		if (null != usersReg && null != usersReg.getRegId() && usersReg.getUserType().equalsIgnoreCase("CU")) {
			GuestUsers user = guestUserRepository.authorizeUser(bean.getEmailId());
			if (null != user) {
				userBean.setFirstName(user.getFirstName());
				userBean.setMiddleName(user.getMiddleName());
				userBean.setLastName(user.getLastName());
				userBean.setEmail(usersReg.getEmail());
				userBean.setMobileNo(usersReg.getPhno());
				userBean.setRoleType(user.getRoleType());
				userBean.setStatus(usersReg.getEmail());
				userBean.setLoginType(usersReg.getUserType());
				userBean.setRedirectUrl("/storeflexcustdashboard");
			}else {
				error.setErrorCode("Invalid login credentail, Please try again::" + ErrorCodes.SL_INVALID_PASSWORD);
				error.setErrorMessage("Check your enter email and password");
				return error;
			}

		}
		if (null != usersReg && null != usersReg.getRegId() && usersReg.getUserType().equalsIgnoreCase("CL|CU")) {
			ClientUsers user = clientUsersRepository.authorizeUser(bean.getEmailId());
			if (null != user) {
				userBean.setFirstName(user.getFirstName());
				userBean.setMiddleName(user.getMiddleName());
				userBean.setLastName(user.getLastName());
				userBean.setEmail(usersReg.getEmail());
				userBean.setMobileNo(usersReg.getPhno());
				userBean.setRoleType(user.getRoleType());
				userBean.setStatus(usersReg.getEmail());
				userBean.setLoginType(usersReg.getUserType());
				userBean.setIntent(true);
			}else {
				error.setErrorCode("Invalid login credentail, Please try again::" + ErrorCodes.SL_INVALID_PASSWORD);
				error.setErrorMessage("Check your enter email and password");
				return error;
			}

		} 
		return userBean;
	}
}
