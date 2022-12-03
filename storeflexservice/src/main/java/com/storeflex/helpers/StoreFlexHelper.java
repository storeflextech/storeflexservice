package com.storeflex.helpers;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.storeflex.beans.CustEnquiryBean;
import com.storeflex.beans.StoreFlexAddressBean;
import com.storeflex.beans.StoreFlexBean;
import com.storeflex.beans.StoreFlexContactBean;
import com.storeflex.config.AppConfiguration;
import com.storeflex.entities.StoreFlex;
import com.storeflex.entities.StoreFlexAddress;
import com.storeflex.entities.StoreFlexContact;
import com.storeflex.entities.UniqueId;


@Component
public class StoreFlexHelper {
	private static final Logger log = LoggerFactory.getLogger(StoreFlexHelper.class);
	
	@Autowired
	AppConfiguration config;
	
	@Autowired(required=true)
	JavaMailSender javaMailSender;
	
	@Autowired
	AppConfiguration appConfig;
	
	public StoreFlex createStoreFlex(UniqueId uniqueId,StoreFlexBean storeFlexBean,StoreFlex storeFlex) {
		 log.info("Start method createStoreFlex", this);
		storeFlex.setStoreFlexId(uniqueId.getPrex()+uniqueId.getNextReserveId());
		storeFlex.setAboutUs(storeFlexBean.getAboutUs());
		storeFlex.setCompyDesc(storeFlexBean.getCompyDesc());
		storeFlex.setCreateBy("ADMIN");
		storeFlex.setCreateDate(LocalDateTime.now());
		storeFlex.setStatus(true);
		storeFlex.setLeaders("ADMIN");
		storeFlex.setPromotion("TEST_PROMOTION");
		return storeFlex;
	}

	public Set<StoreFlexAddress> populateAddress(StoreFlexBean storeFlexBean,StoreFlex storeFlex,Set<StoreFlexAddress> addressSet) {
		 log.info("Starting method populateAddress", this);
		if(!CollectionUtils.isEmpty(storeFlexBean.getStoreFlexAddress())) {
			
			for(StoreFlexAddressBean addressBean : storeFlexBean.getStoreFlexAddress()) {
				StoreFlexAddress address = new StoreFlexAddress();
				address.setAddressType(addressBean.getAddressType());
				address.setHouseNo(addressBean.getHouseNo());
				address.setPlotNo(addressBean.getPlotNo());
				address.setStreetDetails(addressBean.getStreetDetails());
				address.setCityCode(addressBean.getCityCode());
				address.setStateCode(addressBean.getStateCode());
				address.setCountryCode(addressBean.getCountryCode());
				address.setPincode(addressBean.getPincode());
				address.setCreateBy(addressBean.getCreateBy());
				address.setCreateDate(LocalDateTime.now());
				address.setStoreflex(storeFlex);
				addressSet.add(address);
			}	
		}
		 log.info("End method populateAddress", this);
		return addressSet;
	}

	public Set<StoreFlexContact> populatedContacts(StoreFlexBean storeFlexBean,StoreFlex storeFlex, Set<StoreFlexContact> contactSet) {
		 log.info("Starting method populatedContacts", this);
		if(!CollectionUtils.isEmpty(storeFlexBean.getStoreFlexContact())) {
			for(StoreFlexContactBean contactBean : storeFlexBean.getStoreFlexContact()) {
				StoreFlexContact contact  =  new StoreFlexContact();
				contact.setComplianceMail(contactBean.getComplianceMail());
				contact.setContactName(contactBean.getContactName());
				contact.setCreateBy("ADMIN");
				contact.setCreateDate(LocalDateTime.now());
				contact.setCropMail(contactBean.getCropMail());
				contact.setFinanceMail(contactBean.getFinanceMail());
				contact.setLandLine(contactBean.getLandLine());
				contact.setLandLineExt(contactBean.getLandLineExt());
				contact.setMobileNo(contactBean.getMobileNo());
				contact.setSaleContactNo(contactBean.getSaleContactNo());
				contact.setStoreflex(storeFlex);
				contactSet.add(contact);
			}
		}
		 log.info("End method populatedContacts", this);
		return contactSet;
	}

	public UniqueId getStoreFlexPrefixDetails(List<UniqueId> prefixList) {
		 log.info("Start method getPrefixDetails", this);
		for(UniqueId id :prefixList) {
			if(id.getPrex().equalsIgnoreCase("SF-")) {
				log.info("End method getPrefixDetails", this);
				return id;
			}
		}
		return null;
	}
	
	public UniqueId getStoreFlexClintPrefixDetails(List<UniqueId> prefixList) {
		 log.info("Start method getStoreFlexClintPrefixDetails", this);
		for(UniqueId id :prefixList) {
			if(id.getPrex().equalsIgnoreCase("CL-")) {
				log.info("End method getStoreFlexClintPrefixDetails", this);
				return id;
			}
		}
		return null;
	}

	public UniqueId getWareHouseUniqueId(List<UniqueId> prefixList) {
		 log.info("Start method getWareHouseUniqueId", this);
			for(UniqueId id :prefixList) {
				if(id.getPrex().equalsIgnoreCase("WH-")) {
					log.info("End method getWareHouseUniqueId", this);
					return id;
				}
			}
			return null;
	}

	public void enquiryMail(CustEnquiryBean bean) throws MessagingException, IOException {
		log.info("Start method enquiryMail", this);
		 sendEmail(bean);
	}
	
	void sendEmail(CustEnquiryBean bean) throws AddressException, MessagingException, IOException {
		log.info("Start method sendEmail", this);
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
		   msg.setSubject("STOREFLEX EQUIRY-" +bean.getFirstName());
		   msg.setContent("Thanks for connecting with us <b>"+bean.getFirstName()+". We can help on your enquiry", "text/html");
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
