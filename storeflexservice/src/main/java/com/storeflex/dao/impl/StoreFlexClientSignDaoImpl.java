package com.storeflex.dao.impl;

import java.util.List;
import java.util.Optional;
import java.time.LocalDateTime;
//import java.util.List;
//import java.util.function.Predicate;
//import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.storeflex.dao.StoreFlexClientSignDao;

import com.storeflex.entities.DocSignUpRec;
import com.storeflex.entities.ClientProfile;
import com.storeflex.repositories.DocSignUpRecRepository;
import com.storeflex.repositories.StoreFlexClientRepository;

public class StoreFlexClientSignDaoImpl implements StoreFlexClientSignDao {

    private static final Logger log = LoggerFactory.getLogger(StoreFlexClientSignDaoImpl.class);

    @Autowired
	DocSignUpRecRepository docSignUpRecRepository;
    @Autowired
	StoreFlexClientRepository clientRepository;

    @Override
    public void updateClientSignInfo(String requestId, String requestStatus)
    {
        List<DocSignUpRec> clientSignInfo = docSignUpRecRepository.findAll();
        if (clientSignInfo != null)
        {
            DocSignUpRec clientSignInfos = clientSignInfo.stream().filter(p -> p.getDocRequestId() == requestId).collect(null);
            if (clientSignInfos != null)
            {
                clientSignInfos.setStatus(requestStatus);

                docSignUpRecRepository.save(clientSignInfos);
            }
            else
            {
                log.error("Client Signing Request id is not is not valid", this ); 
            }
        }

    }

    @Override
    public void createClientSignInfo(String clientId, String requestId, String requestStatus)
    {
        Optional<ClientProfile> clientProfileOpt =  clientRepository.findById(clientId);
        if(clientProfileOpt.isPresent()) {
            DocSignUpRec docSignUpRec = new DocSignUpRec();
            docSignUpRec.setClientId(clientId);
            docSignUpRec.setStatus(requestStatus);
            docSignUpRec.setDocRequestId(requestId);
            docSignUpRec.setCreateDate(LocalDateTime.now());
            docSignUpRec.setCreateBy("ADMIN");

            docSignUpRecRepository.save(docSignUpRec);
        }
        else
        {
            log.error("client id is not is not valid", this ); 
        }
    }
    
}
