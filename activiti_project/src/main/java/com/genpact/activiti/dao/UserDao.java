package com.genpact.activiti.dao;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import com.genpact.activiti.entity.User;

public interface UserDao extends CrudRepository<User, Serializable> ,JpaSpecificationExecutor<User>{
	public User findByNameAndPassword(String name,String password);
}
