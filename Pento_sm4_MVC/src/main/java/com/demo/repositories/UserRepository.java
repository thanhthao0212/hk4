package com.demo.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.demo.entities.Account;
import com.demo.entities.User;

public interface UserRepository extends CrudRepository<User, Integer>{
	
	@Query("from User order by id desc")
	public List<User> findAllNew();
	
}
