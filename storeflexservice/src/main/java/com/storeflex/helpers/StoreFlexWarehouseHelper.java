package com.storeflex.helpers;

import java.time.LocalDateTime;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.storeflex.beans.ClientWareHouseAddrBean;
import com.storeflex.beans.ClientWareHousePhtBean;
import com.storeflex.beans.ClientWareHousesBean;
import com.storeflex.beans.WarehousePriceBean;
import com.storeflex.beans.WarehousehoursBean;
import com.storeflex.entities.WareHousePhoto;
import com.storeflex.entities.Warehouse;
import com.storeflex.entities.WarehouseAddress;
import com.storeflex.entities.WarehouseHours;
import com.storeflex.entities.WarehousePrice;

@Component
public class StoreFlexWarehouseHelper {
	private static final Logger log = LoggerFactory.getLogger(StoreFlexWarehouseHelper.class);

	public Set<WarehouseAddress> populateAddress(Set<WarehouseAddress> addressSet, WarehouseAddress address,ClientWareHousesBean request,Warehouse warehouse) {
		 log.info("Starting method populateAddress", this);
		 for(ClientWareHouseAddrBean addressBean :  request.getAddress()) {
			 address.setAddressType(addressBean.getAddressType());
			 address.setHouseNo(addressBean.getHouseNo());
			 address.setPlotNo(addressBean.getPlotNo());
			 address.setStreetDetails(addressBean.getStreetDetails());
			 address.setCityId(addressBean.getCity());
			 address.setState(addressBean.getState());
			 address.setCountryId(addressBean.getCountry());
			 address.setPincode(addressBean.getPincode());
			 if(null==request.getWarehouseId()) {
				 address.setCreateBy("ADMIN");
				 address.setCreateDate(LocalDateTime.now()); 
			 }
			 address.setWarehouse(warehouse);
			 addressSet.add(address);
		 }
		 log.info("Ending method populateAddress", this);
		 return addressSet;
	}

	public Set<WareHousePhoto> populatePhots(Set<WareHousePhoto> photosSet, WareHousePhoto photos,
			ClientWareHousesBean request) {
		 log.info("Starting method populatePhots", this);
		 for(ClientWareHousePhtBean photBean:request.getPhotos()) {
			 photos.setCreateBy("ADMIN");
			 photos.setCreateDate(LocalDateTime.now());
			 photos.setPhtName(photBean.getPhtName());
			 photos.setPhtDescp(photBean.getPhtDescp());
			 photos.setPhts(photBean.getPhts());
			 photosSet.add(photos);
		 }
		 log.info("Ending method populatePhots", this);
		return null;
	}

	

	public WarehouseHours populateHours(WarehouseHours hours, ClientWareHousesBean request, Warehouse warehouse) {
		 log.info("Starting method populateHours", this);
		 if(null!=request.getHours()) {
			 WarehousehoursBean bean = request.getHours();
			 hours.setWarehouseId(warehouse.getWarehouseId());
			 hours.setCreateBy("ADMIN");
			 hours.setCreateDate(LocalDateTime.now());
			 if(bean.isOpenall()) {
				 hours.setOpenall(true);
				 hours.setStarttime(null);
				 hours.setEndtime(null);
			 }else {
				 hours.setDay(bean.getOpenday());
				 hours.setOpenall(false);
				 hours.setStarttime(bean.getStarttime());
				 hours.setEndtime(bean.getEndtime()); 
			 }
		 }
		return hours;
	}

	public WarehousePrice populatePrice(WarehousePrice price, ClientWareHousesBean request, Warehouse warehouse) {
		 log.info("Starting method populatePrice", this);
		 if(null!=request.getWarehouseprice()) {
			 WarehousePriceBean bean =  request.getWarehouseprice();
			 price.setAvailspace(bean.getAvailspace());
			 price.setCreateBy("ADMIN");
			 price.setCreateDate(LocalDateTime.now());
			 price.setMinordersqt(bean.getMinordersqt());
			 price.setRatesqtft(bean.getRatesqtft());
			 price.setWarehouseId(warehouse.getWarehouseId());
			 
		 }
		return price;
	}

}
