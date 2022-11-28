package com.storeflex.dao;

import com.storeflex.beans.TestAuthBean;
import com.storeflex.exceptions.StoreFlexServiceException;

public interface StoreFlexAuthDao {

	Object sllogin(TestAuthBean bean) throws StoreFlexServiceException;

}
