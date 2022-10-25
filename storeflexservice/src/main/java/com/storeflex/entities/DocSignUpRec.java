package com.storeflex.entities;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "doc_signup_records")
public class DocSignUpRec implements Serializable{
		
		private static final long serialVersionUID = 1L;
		@Id
		@Column(name = "record_id", nullable = false)
		@GeneratedValue(strategy = GenerationType.AUTO)
		private UUID recordId;
		@Column(name = "client_id")
		private String clientId;
		@Column(name = "doc_request_id")
		private String docRequestId;
		@Column(name = "status")
		private String status;
		@Column(name = "created_by")
		private String createBy;
		@Column(name = "created_date")
		private LocalDateTime createDate;
}
