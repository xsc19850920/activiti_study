package com.genpact.activiti.dao;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.genpact.activiti.entity.LeaveBill;

public interface LeaveBillDao extends CrudRepository<LeaveBill, Serializable> ,JpaSpecificationExecutor<LeaveBill>,PagingAndSortingRepository<LeaveBill, Serializable>{
	List<LeaveBill> findByUserIdAndDeleteFlag(Long id,int deleteFlag);
}
