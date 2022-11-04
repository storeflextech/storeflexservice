package com.storeflex.dao;

public interface StoreFlexClientSignDao {
    void updateClientSignInfo(String requestId, String requestStatus);

    void createClientSignInfo(String clientId, String requestId, String requestStatus);    
}
