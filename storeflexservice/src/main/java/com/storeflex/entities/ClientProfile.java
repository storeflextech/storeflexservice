package com.storeflex.entities;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "client_profile")
public class ClientProfile  implements Serializable{
	
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "client_id", nullable = false)
	private String clientId;
	@Column(name = "compny_name")
	private String compyName;
	@Column(name = "compny_descp")
	private String compyDesc;
	@Column(name = "compny_photo")
	private byte[] photo;
	@Column(name = "compny_photo_name")
	private String photoName;
	@Column(name = "compny_url")
	private String url;
	@Column(name = "compny_gstno")
	private String gstNo;
	@Column(name = "created_by")
	private String createBy;
	@Column(name = "created_date")
	private LocalDateTime createDate;
	@Column(name = "update_by")
	private String updatedBy;
	@Column(name = "update_date")
	@JsonFormat(pattern="yyyy-MM-dd")
	private LocalDateTime updatedate;
	@Column(name = "status")
	private boolean status;

	@OneToMany(mappedBy = "clientProfile", cascade = CascadeType.ALL)
	private Set<ClientAddress> addresses = new HashSet<ClientAddress>();
	
	@OneToMany(mappedBy = "clientProfile", cascade = CascadeType.ALL)
	private Set<ClientContacts> contact = new HashSet<ClientContacts>();
	
	@OneToMany(mappedBy = "clientProfile", cascade = CascadeType.ALL)
	private Set<ClientUsers> users = new HashSet<ClientUsers>();
}
