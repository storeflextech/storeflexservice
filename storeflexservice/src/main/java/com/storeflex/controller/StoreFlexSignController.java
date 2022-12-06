package com.storeflex.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.*;

import com.storeflex.exceptions.StoreFlexServiceException;
import com.storeflex.helpers.StoreFlexSignHelper;
import com.storeflex.response.StoreFlexResponse;
import com.storeflex.response.StoreFlexResponse.Status;
import com.storeflex.services.StoreFlexClientService;
import com.storeflex.services.StoreFlexClientSignService;

import io.swagger.annotations.ApiOperation;

@RestController
public class StoreFlexSignController {
    private static final Logger log = LoggerFactory.getLogger(StoreFlexProfileController.class);

    @Autowired
	StoreFlexClientSignService clientSignService;

    @Autowired
	StoreFlexClientService clientService;

    @Autowired
	StoreFlexSignHelper helper;

    @PostMapping(value="/sign" , produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value="sign" , notes ="sign storeflex client" , nickname="sign")
	public StoreFlexResponse<Object> signDocument(@RequestParam String clientId){
        StoreFlexResponse<Object> response = new StoreFlexResponse<Object>();

        try
        {
            helper.sendForSigningToClient(clientService, clientSignService, clientId);

            response.setStatus(Status.SUCCESS);
            response.setStatusCode(Status.SUCCESS.getCode());
            response.setMessage("Sent for Client Signing successfully");
        }
        catch(StoreFlexServiceException e) 
        {
            response.setStatus(Status.BUSENESS_ERROR);
            response.setStatusCode(Status.BUSENESS_ERROR.getCode()); 
            response.setMessage("System Error...."+e.getMessage()); 
        }
		catch(JSONException e)
        {
            response.setStatus(Status.BUSENESS_ERROR);
			response.setStatusCode(Status.BUSENESS_ERROR.getCode()); 
			response.setMessage("System Error...."+e.getMessage());
        }
        catch(UnsupportedEncodingException e)
        {
            response.setStatus(Status.BUSENESS_ERROR);
			response.setStatusCode(Status.BUSENESS_ERROR.getCode()); 
			response.setMessage("System Error...."+e.getMessage());
        }
        catch(IOException e)
        {
            response.setStatus(Status.BUSENESS_ERROR);
			response.setStatusCode(Status.BUSENESS_ERROR.getCode()); 
			response.setMessage("System Error...."+e.getMessage());
        }
        catch(InterruptedException e)
        {
            response.setStatus(Status.BUSENESS_ERROR);
			response.setStatusCode(Status.BUSENESS_ERROR.getCode()); 
			response.setMessage("System Error...."+e.getMessage());
        }
        
        return response;
	}

    @PostMapping(value="/signstatus" )
	@ApiOperation(value="signstatus" , notes ="sign storeflex client" , nickname="signstatus")
    public ResponseEntity<Object> signStatus(@RequestBody String request)
    {        
        try
        {
            helper.updateSignStatus(clientSignService, request);            
        }
		catch(JSONException e)
        {
            log.info("Error reading JSON Object from request");
        }
        catch(StoreFlexServiceException e) 
        {
            log.info("Service Exception:", e.getMessage());
        }
        
        return ResponseEntity.ok().build();
	}
}
