package com.storeflex.services.impl;

import java.io.IOException;

import javax.mail.MessagingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.storeflex.beans.CustEnquiryBean;
import com.storeflex.dao.StoreFlexCustEnquiryDao;
import com.storeflex.exceptions.StoreFlexServiceException;
import com.storeflex.helpers.StoreFlexHelper;
import com.storeflex.services.StoreFlexCustEnquiryService;

@Component
public class StoreFlexCustEnquiryServiceImp implements StoreFlexCustEnquiryService{
	private static final Logger log = LoggerFactory.getLogger(StoreFlexCustEnquiryServiceImp.class);
	@Autowired
	StoreFlexCustEnquiryDao dao;
	@Autowired
	StoreFlexHelper helper;
	
	@Override
	public Object enquiry(CustEnquiryBean bean) throws StoreFlexServiceException {
		log.info("Starting method storeFlexClient", this);
		return dao.enquiry(bean);
	}

	@Override
	public void enquiryMail(CustEnquiryBean bean) throws StoreFlexServiceException, MessagingException, IOException {
		log.info("Starting method enquiryMail", this);
		helper.enquiryMail(bean);
		
	}

}
