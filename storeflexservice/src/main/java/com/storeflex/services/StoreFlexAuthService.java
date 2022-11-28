package com.storeflex.services;

import com.storeflex.beans.TestAuthBean;
import com.storeflex.exceptions.StoreFlexServiceException;

public interface StoreFlexAuthService {

	Object sllogin(TestAuthBean bean) throws StoreFlexServiceException;

}
