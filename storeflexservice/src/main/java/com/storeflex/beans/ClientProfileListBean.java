package com.storeflex.beans;

import java.io.Serializable;
import java.util.List;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@NoArgsConstructor
@EqualsAndHashCode
public class ClientProfileListBean  implements Serializable{

	private static final long serialVersionUID = 1L;
	long totalRecords;
	List<StoreFlexClientBean> clientList;
}
