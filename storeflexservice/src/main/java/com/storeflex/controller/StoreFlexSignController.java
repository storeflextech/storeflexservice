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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.boot.configurationprocessor.json.*;

import com.storeflex.response.StoreFlexResponse;
import com.storeflex.response.StoreFlexResponse.Status;

import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
public class StoreFlexSignController {
    private static final Logger log = LoggerFactory.getLogger(StoreFlexProfileController.class);

    @PostMapping(value="/sign" , produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value="sign" , notes ="sign storeflex client" , nickname="sign")
	public StoreFlexResponse<Object> signDocument(){
        StoreFlexResponse<Object> response = new StoreFlexResponse<Object>();

        try{
            this.sendForSigning();

            response.setStatus(Status.SUCCESS);
            response.setStatusCode(Status.SUCCESS.getCode());
            response.setMessage("Sent for Client Signing successfully");
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
    public ResponseEntity<Object> signStatus(String request){
        //StoreFlexResponse<Object> response = new StoreFlexResponse<Object>();

        try{
            JSONObject jObject = new JSONObject(request);
            log.info("Response from Signing", jObject.toString());
            //this.sendForSigning();

            
        }
		catch(JSONException e)
        {
            /*response.setStatus(Status.BUSENESS_ERROR);
			response.setStatusCode(Status.BUSENESS_ERROR.getCode()); 
			response.setMessage("System Error...."+e.getMessage());*/
        }
        /*catch(UnsupportedEncodingException e)
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
        }*/
        
        return ResponseEntity.ok().build();
	}

    private void sendForSigning() throws JSONException, UnsupportedEncodingException, IOException, InterruptedException
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
            .header("Authorization","Zoho-oauthtoken "+ "1000.97c36dbab11dbff4bf1e29c29995fd90.c7593419157959516c22aef099d08813")
            .POST(BodyPublishers.ofString("is_quicksend=true&data=" + URLEncoder.encode(fillClientTemplate(), "UTF-8")))
            .build();

        HttpResponse<String> tes = client.send(request, HttpResponse.BodyHandlers.ofString());
        log.info("Response from Signing", tes.body());        
    }

    private String fillClientTemplate() throws JSONException
    {
        log.info("Starting method filClienTemplate");

        String dataJsonAsString = null;

        JSONObject actionJson = new JSONObject();
        actionJson.put("action_type","SIGN");
        //TODO: Fill up before running.
        actionJson.put("recipient_email","");
        actionJson.put("recipient_name","");
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
