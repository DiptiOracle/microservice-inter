package com.example.demo.controller;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Account;
import com.example.demo.entity.Employee;
import com.example.demo.repo.EmployeeRepository;

@RestController
public class EmployeeController {

	@Autowired
	private EmployeeRepository employeeRepository;
	
	@GetMapping("/emp/name/{empName}")
	public Employee findEmployeeByName(@PathVariable String empName){
		return employeeRepository.findByFirstName(empName);
	}
	@GetMapping("/emp/all")
	public List<Employee> findAllEmployee(){
		return employeeRepository.findAll();
	}
	@PostMapping("/emp/save")
	public String saveEmployee(@RequestBody Employee emp) {
		employeeRepository.save(emp);
		return emp.getFirstName() + " Save Successfully";
	}
	@DeleteMapping("/emp/delete/{empId}")
	public String deleteEmployee(@PathVariable Long empId) {
		//Employee emp = employeeRepository.getOne(empId);
		
		Optional<Employee> emp2 = employeeRepository.findById(empId);
		employeeRepository.delete(emp2.get());
		return emp2.get().getFirstName() + " deleted successfully";
	}
	@GetMapping("/emp/totalbalance/{empName}")
	public String getTotalBalanceOfTheEmployee(@PathVariable String empName) {
		Employee emp = findEmployeeByName(empName);
		BigDecimal totalbalance = new BigDecimal(0.0);
		for(Account account : emp.getAccounts()) {
			totalbalance = totalbalance.add(account.getBalance());
		}
		return "Total Balance of "+emp.getFirstName()+"="+totalbalance;
	}
}
