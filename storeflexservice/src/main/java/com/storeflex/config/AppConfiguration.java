package com.storeflex.config;

import java.io.Serializable;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.secretsmanager.AWSSecretsManager;
import com.amazonaws.services.secretsmanager.AWSSecretsManagerClientBuilder;
import com.amazonaws.services.secretsmanager.model.GetSecretValueRequest;
import com.amazonaws.services.secretsmanager.model.GetSecretValueResult;
import com.google.gson.Gson;
import com.storeflex.beans.AwsSecrets;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
@Setter
@Getter
@ToString
@NoArgsConstructor
@Configuration
@Component
public class AppConfiguration implements Serializable{
	private static final long serialVersionUID = -2550185165626007488L;

	@Value("${testuser.username_temp}")
	private String test_user;
	@Value("${testuser.password_temp}")
	private String test_password;
	@Value("${testuser.username_SL}")
	private String sl_user;
	@Value("${testuser.password_SL}")
	private String sl_password;
	@Value("${testuser.username_CL}")
	private String cl_user;
	@Value("${testuser.password_CL}")
	private String cl_password;
	@Value("${testuser.username_CU}")
	private String cust_user;
	@Value("${testuser.password_CU}")
	private String cust_password;
	@Value("${jwt.secret}")
	private String secret;
	
	@Value("${spring.mail.host}")
	private String mailHost;
	@Value("${spring.mail.username}")
	private String mailHostUser;
	@Value("${spring.mail.password}")
	private String mailHostPsd;
	@Value("${spring.mail.properties.mail.smtp.auth}")
	private boolean auth;
	@Value("${spring.mail.properties.mail.smtp.connectiontimeout}")
	private long connectiontimeout;
	@Value("${spring.mail.properties.mail.smtp.timeout}")
	private long timeout;
	@Value("${spring.mail.properties.mail.smtp.writetimeout}")
	private long writetimeout;
	@Value("${spring.mail.properties.mail.starttls.enable}")
	private boolean enable;
	@Value("${spring.mail.username}")
	private String mailUser;
	@Value("${spring.mail.password}")
	private String mailUserPsw;

	@Value("${signing.zoho.clientID}")
	private String zohoClientId;
	@Value("${signing.zoho.clientSecret}")
	private String zohoClientSecret;
	@Value("${signing.zoho.refreshToken}")
	private String zohoRefreshToken;
	
	@Value("${cloud.aws.credentails.access-key}")
	private String accessKey;
	@Value("${cloud.aws.credentails.secret-key}")
	private String secretKey;
	
	@Value("${storeflexaws.dbsecret:name}")
	private String dbsecret;
	@Value("${storeflexaws.dbsecret:region}")
	private String region;
	
	@Value("${postgres.driver}")
	private String driver;
	@Value("${postgres.db}")
	private String jdbcName;
	@Value("${postgres.dbname}")
	private String dbName;
	

	//create a parsing object 
	private Gson gson = new Gson();
	
	//primary DB loading
	@Bean
	public DataSource dataSource() {
		AwsSecrets secrets = getSecret();
		return DataSourceBuilder.create()
				.driverClassName(driver).url(jdbcName+":" + secrets.getEngine() + "://"
						+ secrets.getHost() + ":" + secrets.getPort() + "/"+dbName)
				.username(secrets.getUsername()).password(secrets.getPassword()).build();
	}
	
	//DB scrcet manager
	private AwsSecrets getSecret() {

		String secretName = "dev/storeflexDB";
		String awsRegion = "ap-south-1";
		
		AWSSecretsManager client = AWSSecretsManagerClientBuilder.standard()
				.withRegion(awsRegion)
				.withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials(accessKey,secretKey)))
				.build();

		String secret =null;

		GetSecretValueRequest getSecretValueRequest = new GetSecretValueRequest().withSecretId(secretName);
		GetSecretValueResult getSecretValueResult = null;

		try {
			getSecretValueResult = client.getSecretValue(getSecretValueRequest);
		} catch (Exception e) {
			throw e;
		}
		if (getSecretValueResult.getSecretString() != null) {
			secret = getSecretValueResult.getSecretString();
			return gson.fromJson(secret, AwsSecrets.class);
		}
		return null;

	}
	
}
