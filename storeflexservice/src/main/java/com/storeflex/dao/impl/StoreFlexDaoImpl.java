package com.storeflex.dao.impl;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.storeflex.beans.ErrorCodeBean;
import com.storeflex.beans.StoreFlexAddressBean;
import com.storeflex.beans.StoreFlexBean;
import com.storeflex.beans.StoreFlexClientUsersBean;
import com.storeflex.beans.StoreFlexContactBean;
import com.storeflex.beans.StoreFlexUserBean;
import com.storeflex.constants.ErrorCodes;
import com.storeflex.constants.StoreFlexConstants;
import com.storeflex.dao.StoreFlexDao;
import com.storeflex.entities.City;
import com.storeflex.entities.ClientProfile;
import com.storeflex.entities.ClientUsers;
import com.storeflex.entities.GuestUsers;
import com.storeflex.entities.Role;
import com.storeflex.entities.State;
import com.storeflex.entities.StoreFlex;
import com.storeflex.entities.StoreFlexAddress;
import com.storeflex.entities.StoreFlexContact;
import com.storeflex.entities.StoreFlexUsers;
import com.storeflex.entities.UniqueId;
import com.storeflex.entities.UsersReg;
import com.storeflex.entities.Warehouse;
import com.storeflex.exceptions.StoreFlexServiceException;
import com.storeflex.helpers.StoreFlexHelper;
import com.storeflex.repositories.CityRepository;
import com.storeflex.repositories.ClientUsersRepository;
import com.storeflex.repositories.GuestUsersRepository;
import com.storeflex.repositories.RoleRepository;
import com.storeflex.repositories.StateRepository;
import com.storeflex.repositories.StoreFlexClientRepository;
import com.storeflex.repositories.StoreFlexRepository;
import com.storeflex.repositories.StoreFlexUserRepository;
import com.storeflex.repositories.UniquePrefixRepository;
import com.storeflex.repositories.UserAuthRepository;
import com.storeflex.utilities.ImageUtility;
@Component
public class StoreFlexDaoImpl implements StoreFlexDao{
	 private static final Logger log = LoggerFactory.getLogger(StoreFlexDaoImpl.class);

	@Autowired
	StoreFlexHelper helper;
	
	@Autowired
	StoreFlexRepository storeFlexRespository;
	
	@Autowired
	UniquePrefixRepository uniquePrefixRespository;
	
	@Autowired
	StoreFlexUserRepository storeFlexUserRespository;
	
	@Autowired
	StateRepository stateRepository;
	
	@Autowired
	CityRepository cityRepository;
	
	@Autowired
	RoleRepository roleRepository;
	
	@Autowired
	UserAuthRepository userAuthRepository;
	
	@Autowired
	StoreFlexClientRepository storeFlexClientRepository;
	
	@Autowired
	ClientUsersRepository clientUsersRepository;
	
	@Autowired
	GuestUsersRepository guestUsersRepository;
	

		
	@Override
	public Object createStoreFlex(StoreFlexBean storeFlexBean) throws StoreFlexServiceException {
		 log.info("Starting method createStoreFlex", this);
		List<UniqueId> prefixList = uniquePrefixRespository.findAll();
		UniqueId uniqueId= helper.getStoreFlexPrefixDetails(prefixList);
		
		StoreFlex storeFlex =  new StoreFlex();
		Set<StoreFlexAddress> addressSet = new HashSet<StoreFlexAddress>();
		Set<StoreFlexContact> contactSet = new HashSet<StoreFlexContact>();
		storeFlex =  helper.createStoreFlex(uniqueId, storeFlexBean, storeFlex);
		
		addressSet = helper.populateAddress(storeFlexBean,storeFlex,addressSet);
		contactSet =  helper.populatedContacts(storeFlexBean,storeFlex,contactSet);
		storeFlex.setStoreFlexAddress(addressSet);
		storeFlex.setStoreFlexContact(contactSet);
		storeFlex = storeFlexRespository.save(storeFlex);
		
		uniqueId.setNextReserveId(uniqueId.getNextReserveId()+1);
		uniquePrefixRespository.save(uniqueId);
		 log.info("End method createStoreFlex", this);
		return storeFlex;
	}

	@Override
	public Object getStoreFlexCompyDetails() throws StoreFlexServiceException {
		log.info("Start method getStoreFlexCompyDetails", this);
		StoreFlexBean bean = new StoreFlexBean();
		Set<StoreFlexAddressBean> addressBeanSet= new HashSet<StoreFlexAddressBean>();
		Set<StoreFlexContactBean> contactBeanSet = new HashSet<StoreFlexContactBean>();
		List<StoreFlex> storeFlexList= storeFlexRespository.findAll();
		for(StoreFlex storeFlex: storeFlexList) {
			bean.setStoreFlexId(storeFlex.getStoreFlexId());
			bean.setAboutUs(storeFlex.getAboutUs());
			bean.setCompyDesc(storeFlex.getCompyDesc());
			bean.setCreateBy(storeFlex.getCreateBy());
			bean.setCreateDate(storeFlex.getCreateDate());
			bean.setLeaders(storeFlex.getLeaders());
			bean.setStatus(storeFlex.isStatus());
			bean.setPromotion(storeFlex.getPromotion());
			
			for(StoreFlexAddress address:storeFlex.getStoreFlexAddress()) {
				StoreFlexAddressBean addressBean = new StoreFlexAddressBean();
				addressBean.setAddressId(addressBean.getAddressId());
				addressBean.setAddressType(address.getAddressType());
				addressBean.setHouseNo(address.getHouseNo());
				addressBean.setPlotNo(address.getPlotNo());
				addressBean.setStreetDetails(address.getStreetDetails());
				addressBean.setCityCode(address.getCityCode());
				addressBean.setStateCode(address.getStateCode());
				addressBean.setCountryCode(address.getCountryCode());
				addressBean.setPincode(address.getPincode());
				addressBeanSet.add(addressBean);
			}
		 
		  for(StoreFlexContact contact:storeFlex.getStoreFlexContact()) {
			  StoreFlexContactBean contactBean =  new StoreFlexContactBean();
			  contactBean.setContactId(contact.getContactId());
			  contactBean.setContactName(contact.getContactName());
			  contactBean.setComplianceMail(contact.getComplianceMail());
			  contactBean.setCropMail(contact.getCropMail());
			  contactBean.setFinanceMail(contact.getFinanceMail());
			  contactBean.setLandLine(contact.getLandLine());
			  contactBean.setLandLineExt(contact.getLandLineExt());
			  contactBean.setMobileNo(contact.getMobileNo());
			  contactBean.setSaleContactNo(contact.getSaleContactNo());
			  contactBeanSet.add(contactBean);
		  }
		  bean.setStoreFlexAddress(addressBeanSet);
		  bean.setStoreFlexContact(contactBeanSet);
		}
		log.info("End method getStoreFlexCompyDetails", this);
		return bean;
	}

	@Override
	public Object storeFlexUser(StoreFlexUserBean req ,String roleType) throws StoreFlexServiceException {
		log.info("Start method storeFlexUser", this);
		//StoreFlexUsers users = new StoreFlexUsers();
		ErrorCodeBean error = new ErrorCodeBean();
		UsersReg userReg = new UsersReg();
		UsersReg usersReg =  userAuthRepository.searchEmailExist(req.getEmail());
		if(null==usersReg) {
			userReg.setCreateBy("ADMIN");
			userReg.setCreateDate(LocalDateTime.now());
			userReg.setEmail(req.getEmail());
			userReg.setPhno(req.getMobileNo());
			userReg.setStatus(StoreFlexConstants.ACTIVE_STATUS);
			userReg.setPswd(req.getLastName()+"_"+req.getMobileNo());
			if(req.getLoginType().equalsIgnoreCase(StoreFlexConstants.SL_USER)) {
				userReg.setUserType(StoreFlexConstants.SL_USER);
			}
			if(req.getLoginType().equalsIgnoreCase(StoreFlexConstants.CL_USER)) {
				userReg.setUserType(StoreFlexConstants.CL_USER);
			}
			//roleType will be GUEST only
			if(req.getLoginType().equalsIgnoreCase(StoreFlexConstants.CU_USER)) {
				userReg.setUserType(StoreFlexConstants.CU_USER);
			}
			userAuthRepository.save(userReg);	
		}else {
			error.setErrorCode(ErrorCodes.EMAIL_EXIST);
			error.setErrorMessage(req.getEmail()+" EMAIL already exist in storeflex system");
			return error;
		}
		return req;
	}

	@Override
	public Object storeFlexUserFinalizeSL(StoreFlexUserBean req, String roleType,String clientCodes) throws StoreFlexServiceException {
		log.info("Start method storeFlexUserFinalize", this);
		StoreFlexUsers users = new StoreFlexUsers();
		ErrorCodeBean error = new ErrorCodeBean();
		clientCodes = "SF-101";
		if(null!=req) {
			Optional<StoreFlex> storeOp =  storeFlexRespository.findById(clientCodes);
			if(storeOp.isPresent()) {
				UsersReg usersReg =  userAuthRepository.searchEmailExist(req.getEmail());
				StoreFlex storeflex = storeOp.get();
				if(null==req.getUserId()) {
					users.setCreateBy("ADMIN");
					users.setCreateDate(LocalDateTime.now());
				}else {
					users.setUpdatedBy("ADMIN");
					users.setUpdateDate(LocalDateTime.now());
				}
				users.setFirstName(req.getFirstName());
				users.setMiddleName(req.getMiddleName());
				users.setLastName(req.getLastName());
				users.setUserPhoto(req.getUserPhoto());
				users.setPhotoName(req.getPhotoName());
				users.setMobileNo(req.getMobileNo());//need to update
				users.setEmail(req.getEmail());//need to update
				users.setRoleType(roleType);
				users.setHouseNo(req.getHouseNo());
				users.setAddress(req.getAddress());
				users.setCity(req.getCity());
				users.setState(req.getState());
				users.setCountry(req.getCountry());
				users.setPinCode(req.getPincode());
				users.setStatus(StoreFlexConstants.ACTIVE_STATUS);//need to update
				users.setUserReg(usersReg);
				users.setStoreflex(storeflex);
				users=storeFlexUserRespository.saveAndFlush(users);
				users.setUserId(users.getUserId());
			}else {
				if(null==req.getUserId()) {
					error.setErrorCode(ErrorCodes.USER_NOT_REGISTER);
					error.setErrorMessage("Registration issue on storeflex system1");
					return error;
				}else {
					error.setErrorCode(ErrorCodes.USER_RECORDS_NOT_UPDATED);
					error.setErrorMessage("User records not updates "+ErrorCodes.USER_RECORDS_NOT_UPDATED);
					return error;
				}
				
			}
		return users;
		}
		error.setErrorCode(ErrorCodes.USER_NOT_REGISTER);
		error.setErrorMessage("Registration issue on storeflex system2");
		return error;

	}
	
	@Override
	public Object storeFlexUserFinalizeCU(StoreFlexUserBean req, String roleType) {
		log.info("Start method storeFlexUserFinalizeCU", this);
		GuestUsers users = new GuestUsers();
		ErrorCodeBean error = new ErrorCodeBean();
		if (null != req) {
        	UsersReg usersReg = userAuthRepository.searchEmailExist(req.getEmail());
			users.setCreateBy("ADMIN");
		    users.setCreateDate(LocalDateTime.now());
			users.setFirstName(req.getFirstName());
			users.setMiddleName(req.getMiddleName());
			users.setLastName(req.getLastName());
			users.setMobileNo(req.getMobileNo());// need to update
			users.setEmail(req.getEmail());// need to update
			users.setRoleType(roleType);
			users.setHouseNo(req.getHouseNo());
			users.setAddress(req.getAddress());
			users.setCity(req.getCity());
			users.setState(req.getState());
			users.setCountry(req.getCountry());
			users.setPincode(req.getPincode());
			users.setStatus(StoreFlexConstants.ACTIVE_STATUS);// need to update
			users.setUserReg(usersReg);
			users = guestUsersRepository.saveAndFlush(users);
			users.setUserId(users.getUserId());
		}else {
			error.setErrorCode(ErrorCodes.USER_NOT_REGISTER);
			error.setErrorMessage("Guest user issue on registration "+ErrorCodes.USER_NOT_REGISTER);
			return error;
		}
		return users;
	}
	
	@Override
	public Object storeFlexUserFinalizeCL(StoreFlexUserBean req, String roleType,String clientCodes) throws StoreFlexServiceException {
		log.info("Start method storeFlexUserFinalize", this);
		ClientUsers users = new ClientUsers();
		ErrorCodeBean error = new ErrorCodeBean();
		if(null!=req) {
			Optional<ClientProfile> clientProfileOp =  storeFlexClientRepository.findById(clientCodes);
			if(clientProfileOp.isPresent()) {
				UsersReg usersReg =  userAuthRepository.searchEmailExist(req.getEmail());
				ClientProfile clientProfile = clientProfileOp.get();
				if(null==req.getUserId()) {
					users.setCreateBy("ADMIN");
					users.setCreateDate(LocalDateTime.now());
				}else {
					users.setUpdatedBy("ADMIN");
					users.setUpdateDate(LocalDateTime.now());
				}
				users.setFirstName(req.getFirstName());
				users.setMiddleName(req.getMiddleName());
				users.setLastName(req.getLastName());
				users.setUserPhoto(req.getUserPhoto());
				users.setPhotoName(req.getPhotoName());
				users.setMobileNo(req.getMobileNo());//need to update
				users.setEmail(req.getEmail());//need to update
				users.setRoleType(roleType);
				users.setHouseNo(req.getHouseNo());
				users.setAddress(req.getAddress());
				users.setCity(req.getCity());
				users.setState(req.getState());
				users.setCountry(req.getCountry());
				users.setPincode(req.getPincode());
				users.setStatus(StoreFlexConstants.ACTIVE_STATUS);//need to update
				users.setUserReg(usersReg);
				users.setClientProfile(clientProfile);
				users=clientUsersRepository.saveAndFlush(users);
				users.setUserId(users.getUserId());
			}else {
				if(null==req.getUserId()) {
					error.setErrorCode(ErrorCodes.USER_NOT_REGISTER);
					error.setErrorMessage("Registration issue on storeflex system1");
					return error;
				}else {
					error.setErrorCode(ErrorCodes.USER_RECORDS_NOT_UPDATED);
					error.setErrorMessage("User records not updates "+ErrorCodes.USER_RECORDS_NOT_UPDATED);
					return error;
				}
				
			}
		return users;
		}
		error.setErrorCode(ErrorCodes.USER_NOT_REGISTER);
		error.setErrorMessage("Registration issue on storeflex system2");
		return error;

	}
	
	@Override
	public Object uploaduserpic(String userid,MultipartFile file) throws StoreFlexServiceException, IOException {
		log.info("Start method uploaduserpic", this);
		Optional<StoreFlexUsers> storeFlexUserOp = storeFlexUserRespository.findById(UUID.fromString(userid));
		StoreFlexUsers user =null;
		if(storeFlexUserOp.isPresent()) {
			user= storeFlexUserOp.get();
			user.setPhotoName(file.getOriginalFilename());
			user.setUserPhoto(ImageUtility.compressImage(file.getBytes()));
			user=storeFlexUserRespository.saveAndFlush(user);
		}
		return user;
	}

	@Override
	public Object getCity(String stateCode) throws StoreFlexServiceException {
		log.info("Start method getCity", this);
		List<Map> list =  new ArrayList<Map>();
		List<City> cityList= cityRepository.getCityByStateId(stateCode);
		if(!CollectionUtils.isEmpty(cityList)) {
			for(City city:cityList) {
				HashMap<String,String> cityMap =  new HashMap<String,String>();
				cityMap.put(city.getCityCode(), city.getCityName());
				list.add(cityMap);
			}
			return list;
		}
		return null;
	}

	@Override
	public Object getState(String countyCode) throws StoreFlexServiceException {
		log.info("Start method getState", this);
		List<Map> list =  new ArrayList<Map>();
	    List<State> stateList= stateRepository.getStateByCountryId(Long.parseLong(countyCode));
		if(!CollectionUtils.isEmpty(stateList)) {
			for(State state:stateList) {
				HashMap<String,String> stateMap =  new HashMap<String,String>();
				stateMap.put(state.getStateCode(), state.getStateName());
				list.add(stateMap);
			}
			return list;
		}
		return null;
	}

	@Override
	public Object getRoles() throws StoreFlexServiceException {
		log.info("Start method getRoles", this);
		HashMap<Long,String> roleMap =  new HashMap<Long,String>();
		List<Role> roleList=roleRepository.findAll();
		for(Role role:roleList) {
			roleMap.put(role.getRoleId(), role.getRoleType());
		}
		return roleMap;
	}

	@Override
	public Object getStoreFlexUsersDetails(Pageable paging) throws StoreFlexServiceException {
		log.info("Start method getStoreFlexUsersDetails", this);
		 Page<StoreFlexUsers> storeFlexUserList =	storeFlexUserRespository.findAll(paging);
		 List<StoreFlexUserBean> userList =  new ArrayList<StoreFlexUserBean>();
		 for(StoreFlexUsers user : storeFlexUserList) {
			 StoreFlexUserBean userBean =  new StoreFlexUserBean();
			 userBean.setUserId(user.getUserId());
			 userBean.setFirstName(user.getFirstName());
			 userBean.setMiddleName(user.getMiddleName());
			 userBean.setLastName(user.getLastName());
			 userBean.setEmail(user.getEmail());
			 userBean.setMobileNo(user.getMobileNo());
			 userBean.setPhotoName(user.getPhotoName());
			 userBean.setUserPhoto(user.getUserPhoto());
			 userBean.setHouseNo(user.getHouseNo());
			 userBean.setAddress(user.getAddress());
			 userBean.setCity(user.getCity());
			 userBean.setCountry(user.getCountry());
			 userBean.setPincode(user.getPinCode());
			 userBean.setRoleType(user.getRoleType());
			 userBean.setStatus(user.getStatus());
			 userList.add(userBean);
			
		 }
		return userList;
	}

	@Override
	public Object getStoreFlexUserId(String userid) throws StoreFlexServiceException {
		log.info("Start method getStoreFlexUserId", this);
	    Optional<StoreFlexUsers> storeFlexUserOpt= storeFlexUserRespository.findById(UUID.fromString(userid));
	    ErrorCodeBean error = new ErrorCodeBean();
	    if(storeFlexUserOpt.isPresent()) {
	    	StoreFlexUsers user =storeFlexUserOpt.get();
	    	StoreFlexUserBean userBean =  new StoreFlexUserBean();
	    	userBean.setUserId(userBean.getUserId());
			 userBean.setFirstName(user.getFirstName());
			 userBean.setMiddleName(user.getMiddleName());
			 userBean.setLastName(user.getLastName());
			 userBean.setEmail(user.getEmail());
			 userBean.setMobileNo(user.getMobileNo());
			 userBean.setPhotoName(user.getPhotoName());
			 userBean.setUserPhoto(user.getUserPhoto());
			 userBean.setHouseNo(user.getHouseNo());
			 userBean.setAddress(user.getAddress());
			 userBean.setCity(user.getCity());
			 userBean.setCountry(user.getCountry());
			 userBean.setPincode(user.getPinCode());
			 userBean.setRoleType(user.getRoleType());
			 userBean.setStatus(user.getStatus());
			 log.info("End method getStoreFlexUserId", this);
			 return userBean;
	    }else {
	    	log.info("Error on method getStoreFlexUserId"+ErrorCodes.USER_NOT_REGISTER, this);
	    	error.setErrorCode(ErrorCodes.USER_NOT_REGISTER);
	    	error.setErrorMessage("User not exist on Storeflex System"+ErrorCodes.USER_NOT_REGISTER);
	    	return error;
	    }
		}

	@Override
	public Object clientUsers(String clientId,Pageable paging,String status) throws StoreFlexServiceException {
		log.info("Start method clientUsers", this);
		List<ClientUsers>  clientUsersList =null;
		if(!StringUtils.isEmpty(status)) {
			clientUsersList =  clientUsersRepository.findByClient_Id(clientId,status,paging);
		}else {
			clientUsersList =  clientUsersRepository.findByClient_Id(clientId,paging);
		}
		
		 List<StoreFlexClientUsersBean> userList =  new ArrayList<StoreFlexClientUsersBean>();
		 for(ClientUsers user : clientUsersList) {
			 StoreFlexClientUsersBean userBean =  new StoreFlexClientUsersBean();
			 userBean.setUserId(user.getUserId());
			 userBean.setFirstName(user.getFirstName());
			 userBean.setMiddleName(user.getMiddleName());
			 userBean.setLastName(user.getLastName());
			 userBean.setMobileNo(user.getMobileNo());
			 userBean.setEmail(user.getEmail());
			 userBean.setEmergenyPhno(user.getEmergenyPhno());
			 userBean.setHouseNo(user.getHouseNo());
			 userBean.setAddress(user.getAddress());
			 userBean.setCity(user.getCity());
			 userBean.setState(user.getState());
			 userBean.setCountry(user.getCountry());
			 userBean.setCreateBy(user.getCreateBy());
			 userBean.setCreateDate(user.getCreateDate());
			 userBean.setUpdatedBy(user.getUpdatedBy());
			 userBean.setUpdateDate(user.getUpdateDate());
			 userList.add(userBean);
		 }
		return userList;
	}

	@Override
	public Object updateclientusers(StoreFlexClientUsersBean requestBean) throws StoreFlexServiceException {
		log.info("Start method updateclientusers", this);
		 ClientUsers users =null;
		 if(null!=requestBean.getUserId()) {
		Optional<ClientUsers> clientUsersOpt = clientUsersRepository.findById(requestBean.getUserId());
		 if(clientUsersOpt.isPresent()) {
			 users=	clientUsersOpt.get();
			 users.setAddress(requestBean.getAddress());
			 users.setCity(requestBean.getCity());
			 users.setState(requestBean.getState());
			 users.setMobileNo(requestBean.getMobileNo());
			 users.setEmergenyPhno(requestBean.getEmergenyPhno());
			 users.setEmail(requestBean.getEmail());
			 users.setUpdateDate(LocalDateTime.now());
			 users.setUpdatedBy("ADMIN");
			 users.setFirstName(requestBean.getFirstName());
			 users.setMiddleName(requestBean.getMiddleName());
			 users.setLastName(requestBean.getLastName());
			 users= clientUsersRepository.save(users);
		 }
		}
		 log.info("End method updateclientusers", this);
		return users;
	}

	@Override
	public Object clientUserById(String userId) throws StoreFlexServiceException {
		log.info("Start method clientUserById", this);
		StoreFlexClientUsersBean bean =  new StoreFlexClientUsersBean();
		Optional<ClientUsers> clientUsersOpt = clientUsersRepository.findById(UUID.fromString(userId));
		 if(clientUsersOpt.isPresent()) {
			 ClientUsers users=	clientUsersOpt.get(); 
			 bean.setUserId(users.getUserId());
			 bean.setFirstName(users.getFirstName()); 
			 bean.setMiddleName(users.getMiddleName());
			 bean.setLastName(users.getLastName());
			 bean.setAddress(users.getAddress());
			 bean.setCity(users.getCity());
			 bean.setState(users.getState());
			 bean.setCountry(users.getCountry());
			 bean.setMobileNo(users.getMobileNo());
			 bean.setEmergenyPhno(users.getEmergenyPhno());
			 bean.setEmail(users.getEmail());
			 bean.setCreateBy(users.getCreateBy());
			 bean.setCreateDate(users.getCreateDate());
			 bean.setUpdatedBy(users.getUpdatedBy());
			 bean.setUpdateDate(users.getUpdateDate());
			 bean.setStatus(users.getStatus());
		 }
		return bean;
	}



	
}
