package com.demo.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.demo.entities.Account;
import com.demo.entities.Role;

public interface RoleRepository extends CrudRepository<Role, Integer>{
	
	@Query("from Role where name = 'ROLE_MEMBER' or name = 'ROLE_ADMIN' order by id desc")
	public List<Role> findAllNew();
	
}
