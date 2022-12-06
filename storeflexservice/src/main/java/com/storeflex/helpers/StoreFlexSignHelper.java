package com.storeflex.helpers;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpRequest.BodyPublishers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.stereotype.Component;

import com.storeflex.beans.StoreFlexClientBean;
import com.storeflex.config.AppConfiguration;
import com.storeflex.exceptions.StoreFlexServiceException;
import com.storeflex.services.StoreFlexClientService;
import com.storeflex.services.StoreFlexClientSignService;

@Component
public class StoreFlexSignHelper {
    private static final Logger log = LoggerFactory.getLogger(StoreFlexHelper.class);

    @Autowired
	AppConfiguration config;

    public void sendForSigningToClient(StoreFlexClientService clientService, StoreFlexClientSignService clientSignService, String clientId) throws StoreFlexServiceException, JSONException, UnsupportedEncodingException, IOException, InterruptedException
    {
        StoreFlexClientBean client = (StoreFlexClientBean) clientService.getStoreFlexClient(clientId);
            
        if (client != null)
        {
            JSONObject returnVal = this.sendForSigning(client.getContact().iterator().next().getContactName(), client.getContact().iterator().next().getEmailId());

            String signRequestId = returnVal.getJSONObject("requests").getString("request_id");
            String signRequeststatus = returnVal.getJSONObject("requests").getString("request_status");

            clientSignService.createClientSignInfo(clientId, signRequestId, signRequeststatus);
        }     
    }

    public void updateSignStatus(StoreFlexClientSignService clientSignService, String request) throws JSONException, StoreFlexServiceException
    {
        JSONObject jObject = new JSONObject(request);
        String signRequestId = jObject.getJSONObject("requests").getString("request_id");
        String signRequeststatus = jObject.getJSONObject("requests").getString("request_status");
        log.info("Response from Signing", jObject.toString());
        clientSignService.updateClientSignInfo(signRequestId, signRequeststatus);
    }

    private JSONObject sendForSigning(String name, String emailId) throws JSONException, UnsupportedEncodingException, IOException, InterruptedException
    {
        String accessToken = this.generateAccessToken();

        HttpClient client = HttpClient.newHttpClient();
        
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create("https://sign.zoho.in/api/v1/templates/34025000000033255/createdocument"))
            .header("Content-Type", "application/x-www-form-urlencoded")
            .header("Authorization","Zoho-oauthtoken "+ accessToken)
            .POST(BodyPublishers.ofString("is_quicksend=true&testing=true&data=" + URLEncoder.encode(fillClientTemplate(name, emailId), "UTF-8")))
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
        actionJson.put("action_id","34025000000033276");
        //actionJson.put("private_note","Please sign this draft agreement");
        actionJson.put("verify_recipient", false);
        actionJson.put("role","Vendor");

        JSONArray actions = new JSONArray();    
        actions.put(actionJson);

        
        JSONObject fieldTextDataJson = new JSONObject();
        fieldTextDataJson.put("Vendor", "Foo Company");
        fieldTextDataJson.put("City", "Guwahati");
        fieldTextDataJson.put("State", "Assam");
        fieldTextDataJson.put("Date", "20");
        fieldTextDataJson.put("Month", "December");
        fieldTextDataJson.put("Year", "2022");

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

    private String generateAccessToken() throws IOException, InterruptedException, JSONException
    {
        String refreshToken = "refresh_token=" + config.getZohoRefreshToken();
        String clientId = "&client_id=" + config.getZohoClientId();
        String clientSecret = "&client_secret=" + config.getZohoClientSecret();
        String redirectUri = "&redirect_uri=https%3A%2F%2Fsign.zoho.com&grant_type=refresh_token";

        HttpClient client = HttpClient.newHttpClient();
        
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create("https://accounts.zoho.in/oauth/v2/token?" + refreshToken + clientId + clientSecret + redirectUri))
            .POST(HttpRequest.BodyPublishers.noBody())
            .build();

        HttpResponse<String> tes = client.send(request, HttpResponse.BodyHandlers.ofString());
        log.info("Filed Template:", tes.body());
        JSONObject jObject = new JSONObject(tes.body());

        return jObject.getString("access_token");
    }
    
}
