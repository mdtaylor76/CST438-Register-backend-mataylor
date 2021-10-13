package com.cst438.domain;

import org.springframework.data.repository.CrudRepository;

public interface AdministratorRepository extends CrudRepository <Administrator, Integer>{

	public Administrator findByEmail(String email);
	
	@SuppressWarnings("unchecked")
	Administrator save(Enrollment e);
	
}
