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
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import com.storeflex.beans.ErrorCodeBean;
import com.storeflex.beans.StoreFlexAddressBean;
import com.storeflex.beans.StoreFlexBean;
import com.storeflex.beans.StoreFlexContactBean;
import com.storeflex.beans.StoreFlexUserBean;
import com.storeflex.constants.ErrorCodes;
import com.storeflex.constants.StoreFlexConstants;
import com.storeflex.dao.StoreFlexDao;
import com.storeflex.entities.City;
import com.storeflex.entities.Role;
import com.storeflex.entities.State;
import com.storeflex.entities.StoreFlex;
import com.storeflex.entities.StoreFlexAddress;
import com.storeflex.entities.StoreFlexContact;
import com.storeflex.entities.StoreFlexUsers;
import com.storeflex.entities.UniqueId;
import com.storeflex.entities.UsersReg;
import com.storeflex.exceptions.StoreFlexServiceException;
import com.storeflex.helpers.StoreFlexHelper;
import com.storeflex.repositories.CityRepository;
import com.storeflex.repositories.RoleRepository;
import com.storeflex.repositories.StateRepository;
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
		StoreFlexUsers users = new StoreFlexUsers();
		ErrorCodeBean error = new ErrorCodeBean();
		UsersReg userReg = new UsersReg();
		UsersReg usersReg =  userAuthRepository.searchEmailExist(req.getEmail());
		if(null==usersReg) {
			userReg.setCreateBy("ADMIN");
			userReg.setCreateDate(LocalDateTime.now());
			userReg.setEmail(req.getEmail());
			userReg.setPhno(req.getMobileNo());
			userReg.setStatus(StoreFlexConstants.ACTIVE_STATUS);
			userReg.setPswd("store@2022");
			userReg.setUserType(StoreFlexConstants.SL_USER);
			userAuthRepository.save(userReg);	
		}else {
			error.setErrorCode(ErrorCodes.EMAIL_EXIST);
			error.setErrorMessage(req.getEmail()+" EMAIL already exist in storeflex system");
			return error;
		}
		return req;
	}

	@Override
	public Object storeFlexUserFinalize(StoreFlexUserBean req, String roleType) throws StoreFlexServiceException {
		log.info("Start method storeFlexUserFinalize", this);
		StoreFlexUsers users = new StoreFlexUsers();
		ErrorCodeBean error = new ErrorCodeBean();
		if(null!=req) {
			Optional<StoreFlex> storeOp =  storeFlexRespository.findById("SF-101");
			if(storeOp.isPresent()) {
				UsersReg usersReg =  userAuthRepository.searchEmailExist(req.getEmail());
				StoreFlex storeflex = storeOp.get();
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
				error.setErrorCode(ErrorCodes.USER_NOT_REGISTER);
				error.setErrorMessage("Registration issue on storeflex system1");
				return error;
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
			user.setPhotoName(file.getOriginalFilename()+"."+file.getContentType());
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

	
}
