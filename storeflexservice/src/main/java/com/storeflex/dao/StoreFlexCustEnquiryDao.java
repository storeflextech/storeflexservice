package com.storeflex.dao;

import com.storeflex.beans.CustEnquiryBean;
import com.storeflex.exceptions.StoreFlexServiceException;

public interface StoreFlexCustEnquiryDao {

	Object enquiry(CustEnquiryBean bean)throws StoreFlexServiceException;

}
