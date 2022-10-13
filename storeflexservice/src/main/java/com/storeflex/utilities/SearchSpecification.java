package com.storeflex.utilities;

import javax.persistence.criteria.Predicate;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import com.storeflex.beans.WarehouseRequestBean;
import com.storeflex.entities.WarehouseAddress;

import lombok.NoArgsConstructor;

@Component
@NoArgsConstructor
public class SearchSpecification {

	public Specification<WarehouseAddress> getWarehouseDetails(WarehouseRequestBean request){
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
			return builder.and(predicates.toArray(new Predicate[0]));
		};
	}
}
