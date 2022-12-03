package com.storeflex.services;

import java.io.IOException;

import javax.mail.MessagingException;

import com.storeflex.beans.CustEnquiryBean;
import com.storeflex.exceptions.StoreFlexServiceException;

public interface StoreFlexCustEnquiryService {

	Object enquiry(CustEnquiryBean bean) throws StoreFlexServiceException;

	void enquiryMail(CustEnquiryBean bean)throws StoreFlexServiceException, MessagingException, IOException;

}
