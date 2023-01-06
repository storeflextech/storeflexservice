package com.storeflex.utilities;

import javax.persistence.criteria.Predicate;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import com.storeflex.beans.WarehouseRequestBean;
import com.storeflex.entities.ClientProfile;
import com.storeflex.entities.WarehouseAddress;
import com.storeflex.view.entities.WarehouseView;

import lombok.NoArgsConstructor;

@Component
@NoArgsConstructor
public class SearchSpecification {

	public Specification<WarehouseView> getWarehouseDetails(WarehouseRequestBean request){
		return (root,query,builder)->{
			List<Predicate> predicates = new ArrayList<>();
			if(!StringUtils.isEmpty(request.getCity())) {
				predicates.add(builder.like(root.get("city"), "%" + request.getCity() + "%"));
			}
			if(!StringUtils.isEmpty(request.getState())) {
				predicates.add(builder.like(root.get("state"), "%" + request.getState() + "%"));
			}
			if(!StringUtils.isEmpty(request.getPincode())) {
				predicates.add(builder.like(root.get("pincode"), "%" + request.getPincode() + "%"));
			}
			 query.orderBy(builder.desc(root.get("createdTime")));
			return builder.and(predicates.toArray(new Predicate[0]));
		};
	}
	
	public Specification<WarehouseView> getWarehouseDetails(String status) {
		return (root, query, builder) -> {
			List<Predicate> predicates = new ArrayList<>();
			if (!StringUtils.isEmpty(status)) {
				predicates.add(builder.equal(root.get("status"), status));
			}
			if (predicates.size() == 0) {
				return null;
			}
			return builder.and(predicates.toArray(new Predicate[0]));
		};
	}
	
	public Specification<ClientProfile> getClientDetails(String status,String clientId,String gstNo) {
		return (root, query, builder) -> {
			List<Predicate> predicates = new ArrayList<>();
			if (!StringUtils.isEmpty(status)) {
				predicates.add(builder.equal(root.get("status"), status));
			}
			if (!StringUtils.isEmpty(clientId)) {
				predicates.add(builder.like(root.get("clientId"), clientId+"%"));
			}
			if (!StringUtils.isEmpty(gstNo)) {
				predicates.add(builder.like(root.get("gstNo"), gstNo+"%"));
			}
			if (predicates.size() == 0) {
				return null;
			}
			return builder.and(predicates.toArray(new Predicate[0]));
		};
	}

}
