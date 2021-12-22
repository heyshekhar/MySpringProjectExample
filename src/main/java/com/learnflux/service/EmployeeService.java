package com.learnflux.service;

import org.springframework.data.repository.CrudRepository;

import com.learnflux.dto.EmployeeDto;

public interface EmployeeService extends CrudRepository<EmployeeDto, Integer>{

}
