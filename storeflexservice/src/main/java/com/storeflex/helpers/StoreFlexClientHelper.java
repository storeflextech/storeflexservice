package com.storeflex.helpers;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.storeflex.beans.StoreFlexClientBean;
import com.storeflex.beans.CustEnquiryBean;
import com.storeflex.beans.StoreFlexClientAddBean;
import com.storeflex.beans.StoreFlexClientContactBean;
import com.storeflex.beans.StoreFlexUserBean;
import com.storeflex.config.AppConfiguration;
import com.storeflex.entities.ClientAddress;
import com.storeflex.entities.ClientContacts;
import com.storeflex.entities.ClientProfile;

@Component
public class StoreFlexClientHelper {
	private static final Logger log = LoggerFactory.getLogger(StoreFlexClientHelper.class);

	@Autowired
	AppConfiguration appConfig;
	
	public Set<ClientAddress> populateClientAddress(StoreFlexClientBean request, ClientProfile clientProfile,
			Set<ClientAddress> clientAddressSet) {
		log.info("Starting method populateClientAddress", this);
		for (StoreFlexClientAddBean clientAddressReq : request.getAddresses()) {
			if (clientAddressReq.getAddressId() == null) {
				ClientAddress address = new ClientAddress();
				address.setAddressType(clientAddressReq.getAddressType());
				address.setStreetDetails(clientAddressReq.getStreetDetails());
				address.setCity(clientAddressReq.getPlotNo());
				address.setState(clientAddressReq.getCity());
				address.setCountry(clientAddressReq.getCountry());
				address.setPincode(clientAddressReq.getPincode());
				address.setCreateBy("ADMIN");
				address.setCreateDate(LocalDateTime.now());
				address.setClientProfile(clientProfile);
				clientAddressSet.add(address);
			}
		}
		log.info("Ending method populateClientAddress", this);
		return clientAddressSet;
	}

	public Set<ClientContacts> populateClientContact(StoreFlexClientBean request, ClientProfile clientProfile,
			Set<ClientContacts> clientContactSet) {
		log.info("Starting method populateClientContact", this);
		for (StoreFlexClientContactBean clientContactReq : request.getContact()) {
			if (clientContactReq.getContactId() == null) {
				ClientContacts contacts = new ClientContacts();
				contacts.setContactName(clientContactReq.getContactName());
				contacts.setCreateBy("ADMIN");
				contacts.setCreateDate(LocalDateTime.now());
				contacts.setEmailId(clientContactReq.getEmailId());
				contacts.setLandLineExt(clientContactReq.getLandLineExt());
				contacts.setLandLine(clientContactReq.getLandLine());
				contacts.setMobileNo(clientContactReq.getMobileNo());
				contacts.setClientProfile(clientProfile);
				clientContactSet.add(contacts);
			} 
		}
		log.info("Ending method populateClientContact", this);
		return clientContactSet;
	}

	public StoreFlexClientBean populateClientList(ClientProfile clientProfile, StoreFlexClientBean clientBean) {
		log.info("Starting method populateClientList", this);
		Set<StoreFlexClientAddBean> addressSet = new HashSet<StoreFlexClientAddBean>();
		Set<StoreFlexClientContactBean> contactSet = new HashSet<StoreFlexClientContactBean>();
		clientBean.setClientId(clientProfile.getClientId());
		clientBean.setCompyDesc(clientProfile.getCompyDesc());
		clientBean.setCompyName(clientProfile.getCompyName());
		clientBean.setCreateBy(clientProfile.getCreateBy());
		clientBean.setCreateDate(clientProfile.getCreateDate());
		clientBean.setUpdatedate(clientProfile.getUpdatedate());
		clientBean.setUpdatedBy(clientProfile.getUpdatedBy());
		clientBean.setUrl(clientProfile.getUrl());
		//clientBean.setPhotoName(clientProfile.getPhotoName());
		//clientBean.setPhoto(clientProfile.getPhoto());
		clientBean.setGstNo(clientProfile.getGstNo());
		clientBean.setStatus(clientProfile.getStatus());

		Set<ClientAddress> clientAddressSet = clientProfile.getAddresses();
		if (!CollectionUtils.isEmpty(clientAddressSet)) {
			for (ClientAddress clientAdd : clientAddressSet) {
				StoreFlexClientAddBean address = new StoreFlexClientAddBean();
				address.setAddressId(clientAdd.getAddressId());
				address.setAddressType(clientAdd.getAddressType());
				address.setPlotNo(clientAdd.getPlotNo());
				address.setStreetDetails(clientAdd.getStreetDetails());
				address.setCity(clientAdd.getCity());
				address.setState(clientAdd.getState());
				address.setPincode(clientAdd.getPincode());
				address.setCountry(clientAdd.getCountry());
				address.setCreateBy(clientAdd.getCreateBy());
				address.setCreateDate(clientAdd.getCreateDate());
				address.setUpdatedBy(clientAdd.getUpdatedBy());
				address.setUpdateDate(clientAdd.getUpdateTime());
				addressSet.add(address);
			}
		}
		clientBean.setAddresses(addressSet);

		Set<ClientContacts> clientContactSet = clientProfile.getContact();
		if (!CollectionUtils.isEmpty(clientContactSet)) {
			for (ClientContacts clientContacts : clientContactSet) {
				StoreFlexClientContactBean contacts = new StoreFlexClientContactBean();
				contacts.setContactId(clientContacts.getContactId());
				contacts.setContactName(clientContacts.getContactName());
				contacts.setCreateBy(clientContacts.getCreateBy());
				contacts.setCreateDate(clientContacts.getCreateDate());
				contacts.setEmailId(clientContacts.getEmailId());
				contacts.setLandLineExt(clientContacts.getLandLineExt());
				contacts.setLandLine(clientContacts.getLandLine());
				contacts.setMobileNo(clientContacts.getMobileNo());
				contactSet.add(contacts);
			}
		}
		clientBean.setContact(contactSet);
		log.info("Ending method populateClientList", this);
		return clientBean;
	}

	public void onboardUser(StoreFlexUserBean bean) throws MessagingException, IOException {
		log.info("Start method onboardUser", this);
		onboardUserEmail(bean);
	}
	
	void onboardUserEmail(StoreFlexUserBean bean) throws AddressException, MessagingException, IOException {
		log.info("Start method onboardUserEmail", this);
		Properties props = new Properties();
		   props.put("mail.smtp.auth", appConfig.isAuth());
		   props.put("mail.smtp.starttls.enable", appConfig.isEnable());
		   props.put("mail.smtp.host", appConfig.getMailHost());
		   props.put("mail.smtp.port", "587");
		   Session session = Session.getInstance(props, new javax.mail.Authenticator() {
			      protected PasswordAuthentication getPasswordAuthentication() {
			         return new PasswordAuthentication(appConfig.getMailUser(), appConfig.getMailUserPsw());
			      }
			   });
		   
		   Message msg = new MimeMessage(session);
		   msg.setFrom(new InternetAddress(appConfig.getMailUser(), false));

		   msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(bean.getEmail()));
		   msg.setSubject("Welcome to Storeflex-" +bean.getFirstName());
		   msg.setContent("Thanks your registration to Storeflex platform is done <b>"+bean.getFirstName()+".</br> Please login to  below url <li>http://52.66.213.74/ </li> . Username is your register email and password will be combination of your last name with register mobile no , example : If user name will <b>'Jon Martin'</b> and mobile no is '<b>123456694</b>' , then password will be <b> Martin123456694</b> ", "text/html");
		   msg.setSentDate(new Date());

		  /* MimeBodyPart messageBodyPart = new MimeBodyPart();
		   messageBodyPart.setContent("Thanks for connecting with us <b>"+bean.getFirstName()+". We can help on your enquiry", "text/html");

		   Multipart multipart = new MimeMultipart();
		   multipart.addBodyPart(messageBodyPart);
		   MimeBodyPart attachPart = new MimeBodyPart();

		   attachPart.attachFile("/var/tmp/image19.png");
		   multipart.addBodyPart(attachPart);
		   msg.setContent(multipart);*/
		   Transport.send(msg);  
		   
       
        log.info("End method sendEmail", this);

    }
}
