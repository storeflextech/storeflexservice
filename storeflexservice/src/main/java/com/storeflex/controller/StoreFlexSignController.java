package com.storeflex.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.*;
import java.net.http.HttpRequest.BodyPublishers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.*;

import com.storeflex.beans.StoreFlexClientBean;
import com.storeflex.exceptions.StoreFlexServiceException;
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

    @PostMapping(value="/sign" , produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value="sign" , notes ="sign storeflex client" , nickname="sign")
	public StoreFlexResponse<Object> signDocument(@RequestParam String clientId){
        StoreFlexResponse<Object> response = new StoreFlexResponse<Object>();

        try{
            StoreFlexClientBean client = (StoreFlexClientBean) clientService.getStoreFlexClient(clientId);
            
            if (client != null)
            {
                JSONObject returnVal = this.sendForSigning(client.getContact().iterator().next().getContactName(), client.getContact().iterator().next().getEmailId());

                String signRequestId = returnVal.getJSONObject("requests").getString("request_id");
                String signRequeststatus = returnVal.getJSONObject("requests").getString("request_status");

                clientSignService.createClientSignInfo(clientId, signRequestId, signRequeststatus);
                response.setStatus(Status.SUCCESS);
                response.setStatusCode(Status.SUCCESS.getCode());
                response.setMessage("Sent for Client Signing successfully");
            }            
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
    public ResponseEntity<Object> signStatus(String request)
    {        
        try
        {
            JSONObject jObject = new JSONObject(request);
            String signRequestId = jObject.getJSONObject("requests").getString("request_id");
            String signRequeststatus = jObject.getJSONObject("requests").getString("request_status");
            log.info("Response from Signing", jObject.toString());
            clientSignService.updateClientSignInfo(signRequestId, signRequeststatus);  
            
            //clientSignService.createClientSignInfo("CL-101", signRequestId, signRequeststatus);
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

    private JSONObject sendForSigning(String name, String emailId) throws JSONException, UnsupportedEncodingException, IOException, InterruptedException
    {
        /*HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create("https://sign.zoho.in/api/v1/templates/34025000000019007")).header("Authorization","Zoho-oauthtoken "+ "1000.c61486d96394dc8aa4461d4f353a0bec.ee2ca369bc3b47272ccb81c0f38c12cf").GET().build();
        try{
            HttpResponse<String> tes = client.send(request, HttpResponse.BodyHandlers.ofString());
            log.info(tes.body());
        }
        catch(Exception e){
            log.info("Test", e);
        }
        
        client.sendAsync(request, null);
        client.sendAsync(request, BodyHandlers.ofString()).thenApply(HttpResponse::body).thenAccept(System.out::println)join();*/      
        
        
        
        HttpClient client = HttpClient.newHttpClient();
        
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create("https://sign.zoho.in/api/v1/templates/34025000000019007/createdocument"))
            .header("Content-Type", "application/x-www-form-urlencoded")
            .header("Authorization","Zoho-oauthtoken "+ "1000.ef1fc9751eee6d21dd0fd2892ffbb1a0.c309776ae8af5f2dace43e1dc5b5914e")
            .POST(BodyPublishers.ofString("is_quicksend=true&data=" + URLEncoder.encode(fillClientTemplate(name, emailId), "UTF-8")))
            .build();

        HttpResponse<String> tes = client.send(request, HttpResponse.BodyHandlers.ofString());
        String returnVal = tes.body();
        log.info("Response from Signing", returnVal);
        return new JSONObject(returnVal);        
    }

    private String fillClientTemplate(String name, String emailId) throws JSONException
    {
        log.info("Starting method filClienTemplate");

        String dataJsonAsString = null;

        JSONObject actionJson = new JSONObject();
        actionJson.put("action_type","SIGN");
        //TODO: Fill up before running.
        actionJson.put("recipient_email", emailId);
        actionJson.put("recipient_name", name);
        actionJson.put("action_id","34025000000019032");
        //actionJson.put("private_note","Please sign this draft agreement");
        actionJson.put("verify_recipient", false);
        actionJson.put("role","Client");

        JSONArray actions = new JSONArray();    
        actions.put(actionJson);

        
        JSONObject fieldTextDataJson = new JSONObject();
        fieldTextDataJson.put("Text - 1", "Test");

        JSONObject fieldDataJson = new JSONObject();
        fieldDataJson.put("field_text_data", fieldTextDataJson);

        JSONObject templatesJson = new JSONObject();
        templatesJson.put("actions", actions);
        templatesJson.put("field_data", fieldDataJson);

        JSONObject dataJson = new JSONObject();
        dataJson.put("templates", templatesJson);

        dataJsonAsString = dataJson.toString();

        log.info("Filed Template:", dataJsonAsString);
        
        return dataJsonAsString;
    }

}
