package com.learnflux.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.learnflux.dto.EmployeeDto;


@Repository
public class EmployeeIMPL {

	@Autowired
	EmployeeService employeeService;
	
	public String saveDataOnDB(EmployeeDto empDto) {
		employeeService.save(empDto);
		
		return "success";
	}
}
