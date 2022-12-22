package com.storeflex.dao.impl;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.storeflex.beans.ClientWareHouseAddrBean;
import com.storeflex.beans.ClientWareHousePhtBean;
import com.storeflex.beans.ClientWareHousesBean;
import com.storeflex.beans.ErrorCodeBean;
import com.storeflex.beans.StoreFlexClientAddBean;
import com.storeflex.beans.WarehouseCategoriesBean;
import com.storeflex.beans.WarehouseListBean;
import com.storeflex.beans.WarehousePriceBean;
import com.storeflex.beans.WarehouseRequestBean;
import com.storeflex.beans.WarehouseViewBean;
import com.storeflex.beans.WarehouseViewBeanList;
import com.storeflex.constants.ErrorCodes;
import com.storeflex.dao.StoreFlexWarehouseDao;
import com.storeflex.entities.ClientAddress;
import com.storeflex.entities.ClientProfile;
import com.storeflex.entities.UniqueId;
import com.storeflex.entities.WareHousePhoto;
import com.storeflex.entities.Warehouse;
import com.storeflex.entities.WarehouseAddress;
import com.storeflex.entities.WarehouseFacilities;
import com.storeflex.entities.WarehouseHours;
import com.storeflex.entities.WarehouseIndustries;
import com.storeflex.entities.WarehousePrice;
import com.storeflex.entities.WarehouseStorages;
import com.storeflex.exceptions.StoreFlexServiceException;
import com.storeflex.helpers.StoreFlexHelper;
import com.storeflex.helpers.StoreFlexWarehouseHelper;
import com.storeflex.repositories.CityRepository;
import com.storeflex.repositories.StateRepository;
import com.storeflex.repositories.StoreFlexClientRepository;
import com.storeflex.repositories.UniquePrefixRepository;
import com.storeflex.repositories.WarehouseAddressRepository;
import com.storeflex.repositories.WarehouseFaciRepository;
import com.storeflex.repositories.WarehouseHoursRepository;
import com.storeflex.repositories.WarehouseIndusRepository;
import com.storeflex.repositories.WarehousePhotosRepository;
import com.storeflex.repositories.WarehousePriceRepository;
import com.storeflex.repositories.WarehouseRepository;
import com.storeflex.repositories.WarehouseStorgRepository;
import com.storeflex.view.entities.WarehouseView;
import com.storeflex.view.repositories.WarehouseViewRepository;
import com.storeflex.utilities.ImageUtility;
import com.storeflex.utilities.SearchSpecification;

@Component
public class StoreFlexWarehouseDaoImpl implements StoreFlexWarehouseDao {

	private static final Logger log = LoggerFactory.getLogger(StoreFlexWarehouseDaoImpl.class);

	@Autowired
	WarehouseRepository warehouseRepository;
	@Autowired
	WarehouseViewRepository warehouseViewRepository;
	@Autowired
	WarehouseAddressRepository warehouseAddresRepository;
	@Autowired
	CityRepository cityRepository;
	@Autowired
	StateRepository stateRepository;
	@Autowired
	StoreFlexClientRepository clientRepository;
	@Autowired
	WarehousePhotosRepository warehousePhotosRepository;
	@Autowired
	StoreFlexWarehouseHelper helper;
	@Autowired
	StoreFlexHelper storeflexhelper;
	@Autowired
	UniquePrefixRepository uniquePrefixRespository;
	@Autowired
	SearchSpecification searchSpecification;
	@Autowired
	WarehouseIndusRepository warehouseIndusRepository;
	@Autowired
	WarehouseFaciRepository warehouseFaciRepository;
	@Autowired
	WarehouseStorgRepository warehouseStorgRepository;
	@Autowired
	WarehousePriceRepository warehousePriceRepository;
	@Autowired
	WarehouseHoursRepository warehouseHoursRepository;

	@Override
	public Warehouse createWarehouse(ClientWareHousesBean request) throws StoreFlexServiceException {
		log.info("Starting method createWarehouse", this);

		List<UniqueId> prefixList = uniquePrefixRespository.findAll();
		UniqueId uniqueId = storeflexhelper.getWareHouseUniqueId(prefixList);

		Warehouse warehouse = new Warehouse();
		if (null != request.getClientId()) {
			Optional<ClientProfile> clientProfileOpt = clientRepository.findById(request.getClientId());
			if (clientProfileOpt.isPresent()) {
				WarehouseAddress address = new WarehouseAddress();
				WarehouseHours hours = new WarehouseHours();
				WarehousePrice price = new WarehousePrice();
				Set<WarehouseAddress> addressSet = new HashSet<WarehouseAddress>();
				// new warehouse creation
				if (null == request.getWarehouseId()) {
					warehouse.setWarehouseId(uniqueId.getPrex() + uniqueId.getNextReserveId());
					warehouse.setClientId(request.getClientId());
					warehouse.setCreateBy("ADMIN");
					warehouse.setCreateDate(LocalDateTime.now());
					warehouse.setWarehouseName(request.getWarehouseName());
					warehouse.setDescp(request.getDescp());
					warehouse.setStatus(true);
					warehouse.setWarehouseTaxId(request.getWarehouseTaxId());
					warehouse.setProfilePhotoName(request.getProfilePhotoName());
					warehouse.setProfilePhoto(request.getProfilePhoto());
					addressSet = helper.populateAddress(addressSet, address, request, warehouse);
					hours = helper.populateHours(hours, request, warehouse);
					price = helper.populatePrice(price, request, warehouse);
					if (!CollectionUtils.isEmpty(addressSet)) {
						warehouse.setAddress(addressSet);
					}
					warehouse.setFacilitiesId(request.getFacilitiesId());
					warehouse.setStoragesId(request.getStoragesId());
					warehouse.setIndustryId(request.getIndustryId());
					warehouse.setDockhighdoors(request.getDockhighdoors());
					warehouse.setAtgradedoors(request.getAtgradedoors());
					warehouse.setCeillingheight(request.getCeillingheight());
					warehouse.setForkliftcapacity(request.getForkliftcapacity());
					warehouse = warehouseRepository.save(warehouse);
					// increase the count of Client ReserveId
					uniqueId.setNextReserveId(uniqueId.getNextReserveId() + 1);
					uniquePrefixRespository.save(uniqueId);
					// saving hours
					if (null != hours.getDay() || hours.isOpenall()) {
						warehouseHoursRepository.save(hours);
					}

					if (null != price.getAvailspace() && null != price.getRatesqtft()) {
						warehousePriceRepository.save(price);
					}

				} else {
					// warehouse already exist and update

					Optional<Warehouse> warehouseOpt = warehouseRepository.findById(request.getWarehouseId());
					if (warehouseOpt.isPresent()) {
						warehouse = warehouseOpt.get();
						Optional.ofNullable(request.getWarehouseName()).ifPresent(warehouse::setWarehouseName);
						Optional.ofNullable(request.getDescp()).ifPresent(warehouse::setDescp);
						Optional.ofNullable(request.getWarehouseTaxId()).ifPresent(warehouse::setWarehouseTaxId);
						Optional.ofNullable(request.getProfilePhotoName()).ifPresent(warehouse::setProfilePhotoName);
						Optional.ofNullable(request.getProfilePhoto()).ifPresent(warehouse::setProfilePhoto);
						Optional.ofNullable(request.getFacilitiesId()).ifPresent(warehouse::setFacilitiesId);
						Optional.ofNullable(request.getStoragesId()).ifPresent(warehouse::setStoragesId);
						Optional.ofNullable(request.getIndustryId()).ifPresent(warehouse::setIndustryId);
						
						Optional.ofNullable(request.getDockhighdoors()).ifPresent(warehouse::setDockhighdoors);
						Optional.ofNullable(request.getAtgradedoors()).ifPresent(warehouse::setAtgradedoors);
						Optional.ofNullable(request.getCeillingheight()).ifPresent(warehouse::setCeillingheight);
						Optional.ofNullable(request.getForkliftcapacity()).ifPresent(warehouse::setForkliftcapacity);
						warehouse.setUpdateDate(LocalDateTime.now());
						warehouse.setUpdatedBy("ADMIN");
						warehouse = warehouseRepository.save(warehouse);
					}
					// address update
					if (!CollectionUtils.isEmpty(request.getAddress())) {
						for (ClientWareHouseAddrBean bean : request.getAddress()) {
							Optional<WarehouseAddress> addressOpt = warehouseAddresRepository
									.findById(bean.getAddressId());
							if (addressOpt.isPresent()) {
								address = addressOpt.get();
								Optional.ofNullable(bean.getAddressType()).ifPresent(address::setAddressType);
								Optional.ofNullable(bean.getPlotNo()).ifPresent(address::setPlotNo);
								Optional.ofNullable(bean.getHouseNo()).ifPresent(address::setHouseNo);
								Optional.ofNullable(bean.getStreetDetails()).ifPresent(address::setStreetDetails);
								Optional.ofNullable(bean.getCity()).ifPresent(address::setCityId);
								Optional.ofNullable(bean.getState()).ifPresent(address::setState);
								Optional.ofNullable(bean.getPincode()).ifPresent(address::setPincode);
								address.setUpdateDate(LocalDateTime.now());
								address.setUpdatedBy("ADMIN");
								warehouseAddresRepository.save(address);
							}
						}
					}
					// update price request
					if (null != request.getWarehouseprice().getPriceId()) {
						Optional<WarehousePrice> warehousePriceOpts = warehousePriceRepository
								.findById(request.getWarehouseprice().getPriceId());
						if (warehousePriceOpts.isPresent()) {
							WarehousePrice priceUpdate = warehousePriceOpts.get();
							Optional.ofNullable(request.getWarehouseprice().getAvailspace())
									.ifPresent(priceUpdate::setAvailspace);
							Optional.ofNullable(request.getWarehouseprice().getMinordersqt())
									.ifPresent(priceUpdate::setMinordersqt);
							Optional.ofNullable(request.getWarehouseprice().getRatesqtft())
									.ifPresent(priceUpdate::setRatesqtft);
							priceUpdate.setUpdateDate(LocalDateTime.now());
							priceUpdate.setUpdatedBy("ADMIN");
							warehousePriceRepository.save(priceUpdate);
						}
					}
					// Update hours
					if (null != request.getHours().getId()) {
						Optional<WarehouseHours> warehouseHoursOpts = warehouseHoursRepository
								.findById(request.getHours().getId());
						if (warehouseHoursOpts.isPresent()) {
							WarehouseHours hoursupdate = warehouseHoursOpts.get();
							if (request.getHours().isOpenall()) {
								hoursupdate.setStarttime(null);
								hoursupdate.setEndtime(null);
								hoursupdate.setOpenall(true);
							} else {
								hoursupdate.setStarttime(request.getHours().getStarttime());
								hoursupdate.setEndtime(request.getHours().getEndtime());
								hoursupdate.setOpenall(false);
								hoursupdate.setDay(request.getHours().getOpenday());
							}
							hoursupdate.setUpdateDate(LocalDateTime.now());
							hoursupdate.setUpdatedBy("ADMIN");
							warehouseHoursRepository.save(hoursupdate);
						}
					}
				}
			} else {
				log.error("client id is not is request", this);
				return null;
			}
		} else {
			log.error("client id is not is request", this);
			return null;
		}
		return warehouse;
	}

	@Override
	public Object getWarehouseById(String warehouseId) throws StoreFlexServiceException {
		log.info("Starting method createWarehouse", this);
		Optional<Warehouse> warehouseOpt = warehouseRepository.findById(warehouseId);
		if (warehouseOpt.isPresent()) {
			Warehouse warehouse = warehouseOpt.get();
			ClientWareHousesBean warehousebean = new ClientWareHousesBean();
			ClientWareHouseAddrBean warehouseAddreBean = new ClientWareHouseAddrBean();
			warehousebean.setClientId(warehouse.getClientId());
			warehousebean.setWarehouseId(warehouse.getWarehouseId());
			warehousebean.setCreateBy(warehouse.getCreateBy());
			warehousebean.setCreateDate(warehouse.getCreateDate());
			warehousebean.setDescp(warehouse.getDescp());
			warehousebean.setProfilePhotoName(warehouse.getProfilePhotoName());
			warehousebean.setProfilePhoto(warehouse.getProfilePhoto());
			warehousebean.setStatus(warehouse.isStatus());
			warehousebean.setWarehouseTaxId(warehouse.getWarehouseTaxId());
			warehousebean.setFacilitiesId(warehouse.getFacilitiesId());
			warehousebean.setIndustryId(warehouse.getIndustryId());
			warehousebean.setStoragesId(warehouse.getStoragesId());
			warehousebean.setCreateBy(warehouse.getCreateBy());
			warehousebean.setCreateDate(warehouse.getCreateDate());
			warehousebean.setUpdatedBy(warehouse.getUpdatedBy());
			warehousebean.setUpdateDate(warehouse.getUpdateDate());
			Set<ClientWareHouseAddrBean> addressSet = new HashSet<ClientWareHouseAddrBean>();
			for (WarehouseAddress address : warehouse.getAddress()) {
				warehouseAddreBean.setAddressId(address.getAddressId());
				warehouseAddreBean.setAddressType(address.getAddressType());
				warehouseAddreBean.setHouseNo(address.getHouseNo());
				warehouseAddreBean.setPlotNo(address.getPlotNo());
				warehouseAddreBean.setCity(address.getCityId());
				warehouseAddreBean.setState(address.getState());
				warehouseAddreBean.setCountry(address.getCountryId());
				warehouseAddreBean.setPincode(address.getPincode());
				warehouseAddreBean.setCreateBy(address.getCreateBy());
				warehouseAddreBean.setCreateDate(address.getCreateDate());
				warehouseAddreBean.setUpdatedBy(warehouse.getUpdatedBy());
				warehouseAddreBean.setUpdateDate(warehouse.getUpdateDate());
				addressSet.add(warehouseAddreBean);
			}
			Optional<WarehousePrice> priceOpt = warehousePriceRepository.getPriceForWarehouse(warehouseId);
			if (priceOpt.isPresent()) {
				WarehousePrice price = priceOpt.get();
				WarehousePriceBean pricebean = new WarehousePriceBean();
				pricebean.setPriceId(price.getPriceId());
				pricebean.setCreateBy(price.getCreateBy());
				pricebean.setCreateDate(price.getCreateDate());
				pricebean.setUpdatedBy(price.getUpdatedBy());
				pricebean.setUpdateDate(price.getUpdateDate());
				pricebean.setAvailspace(price.getAvailspace());
				pricebean.setMinordersqt(price.getMinordersqt());
				pricebean.setRatesqtft(price.getRatesqtft());
				warehousebean.setWarehouseprice(pricebean);
			} else {
				log.error(warehouseId + " pricing is not been set", this);
			}
			warehousebean.setAddress(addressSet);
			return warehousebean;
		}
		return null;
	}

	@Override
	public void upload(MultipartFile file, String clientId, String warehouseId)
			throws StoreFlexServiceException, IOException {
		log.info("Starting method upload", this);
		Optional<ClientProfile> clientProfileOpt = clientRepository.findById(clientId);
		if (clientProfileOpt.isPresent()) {
			Optional<Warehouse> warehouseOpt = warehouseRepository.findById(warehouseId);
			if (warehouseOpt.isPresent()) {
				Warehouse warehouse = warehouseOpt.get();
				WareHousePhoto phts = new WareHousePhoto();
				phts.setCreateBy("Admin");
				phts.setCreateDate(LocalDateTime.now());
				phts.setPhts(file.getBytes());
				phts.setPhtDescp(file.getOriginalFilename());
				phts.setPhtName(file.getOriginalFilename());
				phts.setWarehouse(warehouse);
				warehousePhotosRepository.save(phts);
			}

		}
	}

	@Override
	public WarehouseListBean getWarehouseList(String clientId, Pageable paging) throws StoreFlexServiceException {
		log.info("Starting method getWarehouseList", this);
		WarehouseListBean beanlist = new WarehouseListBean();
		List<ClientWareHousesBean> warehouseBeanList = new ArrayList<ClientWareHousesBean>();
		Page<Warehouse> warehousePageList = warehouseRepository.findByClientId(clientId, paging);
		// List<Warehouse> warehousePageList =
		// warehouseRepository.getWarehouseListByClient(clientId);
		for (Warehouse warehouse : warehousePageList) {
			ClientWareHousesBean warehouseBean = new ClientWareHousesBean();
			warehouseBean.setClientId(warehouse.getClientId());
			warehouseBean.setWarehouseId(warehouse.getWarehouseId());
			warehouseBean.setWarehouseName(warehouse.getWarehouseName());
			warehouseBean.setCreateBy(warehouse.getCreateBy());
			warehouseBean.setCreateDate(warehouse.getCreateDate());
			warehouseBean.setDescp(warehouse.getDescp());
			warehouseBean.setStatus(warehouse.isStatus());
			warehouseBean.setUpdatedBy(warehouse.getUpdatedBy());
			warehouseBean.setUpdateDate(warehouse.getUpdateDate());
			warehouseBean.setWarehouseTaxId(warehouse.getWarehouseTaxId());

			ClientWareHouseAddrBean addressBean = new ClientWareHouseAddrBean();
			Set<ClientWareHouseAddrBean> addressBeanList = new HashSet<ClientWareHouseAddrBean>();
			for (WarehouseAddress address : warehouse.getAddress()) {
				addressBean.setAddressId(address.getAddressId());
				addressBean.setAddressType(address.getAddressType());
				addressBean.setPlotNo(address.getPlotNo());
				addressBean.setHouseNo(address.getHouseNo());
				addressBean.setStreetDetails(address.getStreetDetails());
				addressBean.setCity(address.getCityId());
				addressBean.setCountry(address.getCountryId());
				addressBean.setCreateBy(address.getCreateBy());
				addressBean.setCreateDate(address.getCreateDate());
				addressBean.setPincode(address.getPincode());
				addressBean.setUpdatedBy(address.getUpdatedBy());
				addressBean.setUpdateDate(address.getUpdateDate());
				addressBeanList.add(addressBean);
			}
			warehouseBean.setAddress(addressBeanList);
			/*
			 * ClientWareHousePhtBean phtBean = new ClientWareHousePhtBean();
			 * Set<ClientWareHousePhtBean> phtBeanList = new
			 * HashSet<ClientWareHousePhtBean>(); for(WareHousePhoto pht:
			 * warehouse.getPhotos()) { phtBean.setCreateBy(pht.getCreateBy());
			 * phtBean.setCreateDate(pht.getCreateDate()); phtBean.setPhotoId(
			 * pht.getPhotoId()); phtBean.setPhtDescp(pht.getPhtDescp());
			 * phtBean.setPhtName(pht.getPhtName()); phtBean.setPhts(pht.getPhts());
			 * phtBean.setUpdatedBy(pht.getUpdatedBy());
			 * phtBean.setUpdateDate(pht.getUpdateDate()); phtBeanList.add(phtBean); }
			 */

			warehouseBeanList.add(warehouseBean);
		}
		beanlist.setTotalRecords(warehousePageList.getTotalElements());
		beanlist.setWarehouseList(warehouseBeanList);
		return beanlist;
	}

	@Override
	public Set<ClientWareHousePhtBean> getWarehousePics(String warehouseId) throws StoreFlexServiceException {
		log.info("Starting method getWarehousePics", this);
		List<WareHousePhoto> warehousePhotoList = warehousePhotosRepository.findByWarehouseId(warehouseId);
		Set<ClientWareHousePhtBean> phtBeanList = new HashSet<ClientWareHousePhtBean>();
		for (WareHousePhoto pht : warehousePhotoList) {
			ClientWareHousePhtBean phtBean = new ClientWareHousePhtBean();
			phtBean.setCreateBy(pht.getCreateBy());
			phtBean.setCreateDate(pht.getCreateDate());
			phtBean.setPhotoId(pht.getPhotoId());
			phtBean.setPhtDescp(pht.getPhtDescp());
			phtBean.setPhtName(pht.getPhtName());
			phtBean.setPhts(pht.getPhts());
			phtBean.setUpdatedBy(pht.getUpdatedBy());
			phtBean.setUpdateDate(pht.getUpdateDate());
			phtBeanList.add(phtBean);

		}

		log.info("Starting method getWarehousePics", this);
		return phtBeanList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public WarehouseViewBeanList getWarehouseSearch(WarehouseRequestBean build, int page, int size)
			throws StoreFlexServiceException {
		log.info("Starting method getWarehouseSearch", this);
		ErrorCodeBean errorbean = new ErrorCodeBean();
		List<WarehouseView> list = null;
		Pageable pageable = PageRequest.of(page, size);
		Page<WarehouseView> pages = null;
		List<WarehouseViewBean> beanList = new ArrayList<WarehouseViewBean>();
		WarehouseViewBeanList warehouseViewList = new WarehouseViewBeanList();
		if (!StringUtils.isEmpty(build.getCity())) {
			String cityCode = cityRepository.getCityCode(build.getCity());
			if (!StringUtils.isEmpty(cityCode)) {
				build.setCity(cityCode);
			} else {
				log.error("City name" + build.getCity() + " is not in  bussniess", ErrorCodes.CITY_NOT_EXIST);
				errorbean.setErrorCode(ErrorCodes.CITY_NOT_EXIST);
				errorbean.setErrorMessage("City name " + build.getCity() + " is not in  bussniess");
				warehouseViewList.setErrorCode(errorbean);
				return warehouseViewList;
			}
		}

		if (!StringUtils.isEmpty(build.getState())) {
			String stateCode = stateRepository.getStateCode(build.getState());
			if (!StringUtils.isEmpty(stateCode)) {
				build.setState(stateCode);
			} else {
				log.error("State name " + build.getState() + " is not in bussniess", ErrorCodes.CITY_NOT_EXIST);
				errorbean.setErrorCode(ErrorCodes.CITY_NOT_EXIST);
				errorbean.setErrorMessage("State name " + build.getState() + " is not in  bussniess");
				warehouseViewList.setErrorCode(errorbean);
				return warehouseViewList;
			}

		}
		Specification<WarehouseView> viewObj = searchSpecification.getWarehouseDetails(build);
		if (null != viewObj) {
			pages = warehouseViewRepository.findAll(viewObj, pageable);
			if (pages != null && pages.getContent() != null) {
				list = pages.getContent();
				if (!CollectionUtils.isEmpty(list)) {
					for (WarehouseView view : list) {
						WarehouseViewBean viewbean = new WarehouseViewBean();
						viewbean.setWarehouseId(view.getWarehouseId());
						viewbean.setWarehouseName(view.getWarehouseName());
						viewbean.setProfilePicName(view.getProfilePicName());
						viewbean.setProfilePic(view.getProfilePic());
						viewbean.setClientId(view.getClientId());
						viewbean.setDescp(view.getDescp());
						viewbean.setHouseNo(view.getHouseNo());
						viewbean.setPlotNo(view.getPlotNo());
						viewbean.setStreetAddrs(view.getStreetAddrs());
						viewbean.setCity(view.getCity());
						viewbean.setState(view.getState());
						viewbean.setPincode(view.getPincode());
						viewbean.setStatus(view.isStatus());
						beanList.add(viewbean);
					}
				}
			}
			if (!CollectionUtils.isEmpty(beanList)) {
				warehouseViewList.setWarehouseViewBean(beanList);
				warehouseViewList.setTotalRecord(pages.getTotalElements());
			}
		}
		return warehouseViewList;
	}

	@Override
	public byte[] uploadWareHouseProfilePic(String warehouseId, MultipartFile file)
			throws StoreFlexServiceException, IOException {
		log.info("Starting method getWarehouseSearch", this);
		Warehouse warehouse = null;
		Optional<Warehouse> warehouseOpt = warehouseRepository.findById(warehouseId);
		if (warehouseOpt.isPresent()) {
			warehouse = warehouseOpt.get();
			warehouse.setProfilePhotoName(file.getOriginalFilename());
			warehouse.setProfilePhoto(ImageUtility.compressImage(file.getBytes()));
			warehouseRepository.save(warehouse);
		}
		return ImageUtility.decompressImage(warehouse.getProfilePhoto());
	}

	@Override
	public Map<String, Boolean> deleteWarehouseById(String warehouseId) throws StoreFlexServiceException {
		log.info("Starting method getWarehouseSearch", this);
		Map<String, Boolean> response = new HashMap<>();
		Warehouse warehouse = null;
		Optional<Warehouse> warehouseOpt = warehouseRepository.findById(warehouseId);
		if (warehouseOpt.isPresent()) {
			warehouse = warehouseOpt.get();
			warehouse.setStatus(false);
			warehouse.setUpdateDate(LocalDateTime.now());
			warehouse.setUpdatedBy("ADMIN");
			warehouseRepository.save(warehouse);
			response.put("deleted", Boolean.TRUE);
		} else {
			response.put("deleted", Boolean.FALSE);
		}
		return response;
	}

	@Override
	public WarehouseViewBeanList getAllWarehouses(int page, int size) throws StoreFlexServiceException {
		log.info("Starting method getAllWarehouses", this);
		ErrorCodeBean errorbean = new ErrorCodeBean();
		Pageable pageable = PageRequest.of(page, size);
		Page<WarehouseView> pages = null;
		List<WarehouseView> list = null;
		pages = warehouseViewRepository.findAll(pageable);
		List<WarehouseViewBean> beanList = new ArrayList<WarehouseViewBean>();
		WarehouseViewBeanList warehouseViewList = new WarehouseViewBeanList();
		if (pages != null && pages.getContent() != null) {
			list = pages.getContent();
			if (!CollectionUtils.isEmpty(list)) {
				for (WarehouseView view : list) {
					WarehouseViewBean viewbean = new WarehouseViewBean();
					viewbean.setWarehouseId(view.getWarehouseId());
					viewbean.setWarehouseName(view.getWarehouseName());
					viewbean.setProfilePicName(view.getProfilePicName());
					viewbean.setProfilePic(view.getProfilePic());
					viewbean.setClientId(view.getClientId());
					viewbean.setDescp(view.getDescp());
					viewbean.setHouseNo(view.getHouseNo());
					viewbean.setPlotNo(view.getPlotNo());
					viewbean.setStreetAddrs(view.getStreetAddrs());
					viewbean.setCity(view.getCity());
					viewbean.setState(view.getState());
					viewbean.setPincode(view.getPincode());
					viewbean.setStatus(view.isStatus());
					beanList.add(viewbean);
				}
			}
			if (!CollectionUtils.isEmpty(beanList)) {
				warehouseViewList.setWarehouseViewBean(beanList);
				warehouseViewList.setTotalRecord(pages.getTotalElements());
			}
		} else {
			log.error("No Warehouses found", ErrorCodes.WL_001);
			errorbean.setErrorCode(ErrorCodes.WL_001);
			errorbean.setErrorMessage("No Warehouses found");
			warehouseViewList.setErrorCode(errorbean);
			return warehouseViewList;
		}

		log.info("End method getAllWarehouses", this);
		return warehouseViewList;
	}

	@Override
	public WarehouseCategoriesBean getWareshouseCategories() throws StoreFlexServiceException {
		log.info("Starting method getWareshouseCategories", this);
		WarehouseCategoriesBean bean = new WarehouseCategoriesBean();
		Hashtable<String, String> map = new Hashtable<String, String>();
		Hashtable<String, String> map1 = new Hashtable<String, String>();
		Hashtable<String, String> map2 = new Hashtable<String, String>();
		List<WarehouseIndustries> indusList = warehouseIndusRepository.findAll();
		List<WarehouseStorages> storageList = warehouseStorgRepository.findAll();
		List<WarehouseFacilities> faciList = warehouseFaciRepository.findAll();
		bean.setIndustry("Industries served*");
		for (WarehouseIndustries indus : indusList) {
			map.put(indus.getCode(), indus.getNameVal());
		}
		bean.setIndustries(map);
		bean.setStorage("Storage Layouts*");
		for (WarehouseStorages storage : storageList) {
			map1.put(storage.getCode(), storage.getNameVal());
		}
		bean.setStorages(map1);

		bean.setFacility("Facility Qualifications*");
		for (WarehouseFacilities faci : faciList) {
			map2.put(faci.getCode(), faci.getNameVal());
		}
		bean.setFacilities(map2);
		log.info("End method getWareshouseCategories", this);
		return bean;
	}

	private void setCity(String string1) {
	}
}
