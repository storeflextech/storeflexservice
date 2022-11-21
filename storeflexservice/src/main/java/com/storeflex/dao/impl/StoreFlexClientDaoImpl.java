package com.storeflex.dao.impl;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

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
import com.storeflex.repositories.StoreFlexClientAddrsRepository;
import com.storeflex.repositories.StoreFlexClientContactsRepository;
import com.storeflex.repositories.StoreFlexClientRepository;
import com.storeflex.repositories.UniquePrefixRepository;
import com.storeflex.utilities.ImageUtility;

@Component
public class StoreFlexClientDaoImpl implements StoreFlexClientDao{
	private static final Logger log = LoggerFactory.getLogger(StoreFlexClientDao.class);
	
	@Autowired
	StoreFlexClientRepository storeFlexClientRepository;
	@Autowired
	StoreFlexClientAddrsRepository storeFlexClientAddrsRepository;
	@Autowired
	StoreFlexClientContactsRepository storeFlexClientContactsRepository;
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
			 if(null==request.getClientId()) {
				 clientProfile.setClientId(uniqueId.getPrex()+uniqueId.getNextReserveId());
				 clientProfile.setCreateBy("ADMIN");
				 clientProfile.setCreateDate(LocalDateTime.now());
			 }
			 else {
				 clientProfile.setClientId(request.getClientId());
				 clientProfile.setUpdatedBy("ADMIN");
				 clientProfile.setUpdatedate(LocalDateTime.now());
			 }
			 clientProfile.setCompyDesc(request.getCompyDesc());
			 clientProfile.setCompyName(request.getCompyName());
		     clientProfile.setUrl(request.getUrl());
		     clientProfile.setGstNo(request.getGstNo());
			 clientAddressSet = clientHelper.populateClientAddress(request, clientProfile, clientAddressSet);
			 clientContactSet =clientHelper.populateClientContact(request,clientProfile,clientContactSet);
			 clientProfile.setAddresses(clientAddressSet);
			 clientProfile.setContact(clientContactSet);
			 clientProfile.setStatus(true);
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
	public StoreFlexClientBean updateStoreFlexClient(StoreFlexClientBean clientBean) throws StoreFlexServiceException {
		 log.info("Starting method getStoreFlexClient", this);
		ClientProfile client =null;
		Optional<ClientProfile> clientProfileOpt = storeFlexClientRepository.findById(clientBean.getClientId());
		if(clientProfileOpt.isPresent()) {
			client = clientProfileOpt.get();
			Optional.ofNullable(clientBean.getCompyName()).ifPresent(client::setCompyName);
			Optional.ofNullable(clientBean.getCompyDesc()).ifPresent(client::setCompyDesc);
			client.setUpdatedate(LocalDateTime.now());
			client.setUpdatedBy("ADMIN");
			/*
			 * Optional.ofNullable(clientBean.getPhoto()).ifPresent(client::setPhoto);
			 * Optional.ofNullable(clientBean.getPhotoName()).ifPresent(client::setPhotoName
			 * );
			 */
			Optional.ofNullable(clientBean.getUrl()).ifPresent(client::setUrl);
			client = storeFlexClientRepository.save(client);
		}
		return clientBean;
	}
	
	@Override
	public StoreFlexClientAddBean updateClientAddress(StoreFlexClientAddBean clientBean) throws StoreFlexServiceException {
		log.info("Starting method getStoreFlexClient", this);
		ClientProfile client = null;
		Optional<ClientAddress> addressOpt = storeFlexClientAddrsRepository
				.findById(clientBean.getAddressId());
		if (addressOpt.isPresent()) {
			ClientAddress address = addressOpt.get();
			Optional.ofNullable(clientBean.getAddressType()).ifPresent(address::setAddressType);
			Optional.ofNullable(clientBean.getPlotNo()).ifPresent(address::setPlotNo);
			Optional.ofNullable(clientBean.getHouseNo()).ifPresent(address::setHouseNo);
			Optional.ofNullable(clientBean.getStreetDetails()).ifPresent(address::setStreetDetails);
			Optional.ofNullable(clientBean.getCity()).ifPresent(address::setCity);
			Optional.ofNullable(clientBean.getState()).ifPresent(address::setState);
			Optional.ofNullable(clientBean.getPincode()).ifPresent(address::setPincode);
			address.setUpdateTime(LocalDateTime.now());
			address.setUpdatedBy("ADMIN");
			storeFlexClientAddrsRepository.save(address);
		}
		return clientBean;
	}
	
	@Override
	public StoreFlexClientContactBean updateClientContacts(StoreFlexClientContactBean clientBean) throws StoreFlexServiceException {
		log.info("Starting method getStoreFlexClient", this);
		Optional<ClientContacts> contactOpt = storeFlexClientContactsRepository.findById(clientBean.getContactId());
		ClientContacts contact =null;
		if (contactOpt.isPresent()) {
			contact = contactOpt.get();
			Optional.ofNullable(clientBean.getContactName()).ifPresent(contact::setContactName);
			Optional.ofNullable(clientBean.getEmailId()).ifPresent(contact::setEmailId);
			Optional.ofNullable(clientBean.getLandLine()).ifPresent(contact::setLandLine);
			Optional.ofNullable(clientBean.getLandLineExt()).ifPresent(contact::setLandLineExt);
			Optional.ofNullable(clientBean.getMobileNo()).ifPresent(contact::setMobileNo);
			contact.setUpdateTime(LocalDateTime.now());
			contact.setUpdatedBy("ADMIN");
			storeFlexClientContactsRepository.save(contact);
		}
	
		return clientBean;
	}
	
	@Override
	public StoreFlexClientBean deActivateClient(StoreFlexClientBean clientBean) throws StoreFlexServiceException {
		 log.info("Starting method getStoreFlexClient", this);
		ClientProfile client =null;
		Optional<ClientProfile> clientProfileOpt = storeFlexClientRepository.findById(clientBean.getClientId());
		if(clientProfileOpt.isPresent()) {
			client = clientProfileOpt.get();
			client.setUpdatedate(LocalDateTime.now());
			client.setUpdatedBy("ADMIN");
			client.setStatus(false);
			client = storeFlexClientRepository.save(client);
		}
		return clientBean;
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

	@Override
	public Object updateClientContacts(String clientId, MultipartFile file) throws StoreFlexServiceException, IOException {
		 log.info("Starting method updateClientContacts", this);
		 ClientProfile clientProfile =null;
		 Optional<ClientProfile> clientProfileOpt = storeFlexClientRepository.findById(clientId);
		 if(clientProfileOpt.isPresent()) {
			 clientProfile= clientProfileOpt.get();
			 clientProfile.setPhotoName(file.getOriginalFilename());
			 clientProfile.setPhoto(ImageUtility.compressImage(file.getBytes()));
			 storeFlexClientRepository.save(clientProfile);
		 }
		return clientProfile;
	}

	@Override
	public Map<String, Boolean> deleteClientById(String clientId) throws StoreFlexServiceException {
		 log.info("Starting method deleteClientById", this);
		 Map<String, Boolean> response = new HashMap<>();
		 ClientProfile clientProfile =null;
		 Optional<ClientProfile> clientProfileOpt = storeFlexClientRepository.findById(clientId);
		 if(clientProfileOpt.isPresent()) {
			 clientProfile= clientProfileOpt.get();
			 clientProfile.setStatus(false);
			 clientProfile.setUpdatedate(LocalDateTime.now());
			 clientProfile.setUpdatedBy("ADMIN");
			 storeFlexClientRepository.save(clientProfile);
			 response.put("deleted", Boolean.TRUE);
		 }else {
			 response.put("deleted", Boolean.FALSE); 
		 }
		return response;
	}

}
