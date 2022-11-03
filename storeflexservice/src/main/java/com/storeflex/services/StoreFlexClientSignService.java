package com.storeflex.services;

import com.storeflex.exceptions.StoreFlexServiceException;

public interface StoreFlexClientSignService {
    void updateClientSignInfo(String requestId, String requestStatus) throws StoreFlexServiceException;

    void createClientSignInfo(String clientId, String requestId, String requestStatus) throws StoreFlexServiceException; 
    
}
