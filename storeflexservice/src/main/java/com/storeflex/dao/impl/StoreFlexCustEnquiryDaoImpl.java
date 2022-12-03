package com.storeflex.dao.impl;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.storeflex.beans.CustEnquiryBean;
import com.storeflex.dao.StoreFlexCustEnquiryDao;
import com.storeflex.entities.CustEnquiry;
import com.storeflex.exceptions.StoreFlexServiceException;
import com.storeflex.repositories.CustEnquiryRepository;

@Component
public class StoreFlexCustEnquiryDaoImpl implements StoreFlexCustEnquiryDao{

	private static final Logger log = LoggerFactory.getLogger(StoreFlexCustEnquiryDaoImpl.class);

	@Autowired
	CustEnquiryRepository custEnquiryRepository;
	@Override
	public Object enquiry(CustEnquiryBean bean) throws StoreFlexServiceException {
		log.info("Starting method enquiry", this);
		CustEnquiry custEnquiry = new CustEnquiry();
		custEnquiry.setFirstName(bean.getFirstName());
		custEnquiry.setMiddleName(bean.getMiddleName());
		custEnquiry.setLastName(bean.getLastName());
		custEnquiry.setMobileNo(bean.getMobileNo());
		custEnquiry.setEmail(bean.getEmail());
		custEnquiry.setDescp(bean.getDescp());
		custEnquiry.setCreateBy("ADMIN");
		custEnquiry.setCreateDate(LocalDateTime.now());
		if(null!=custEnquiry.getEmail()) {
			custEnquiryRepository.save(custEnquiry);
			return custEnquiry;
		}else {
			return null;
		}
	 
	}

}
