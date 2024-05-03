package com.tmquang.score.security.services;

import com.tmquang.score.models.Employee;
import com.tmquang.score.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EmployeeServiceImpl implements UserDetailsService {
  @Autowired
  EmployeeRepository employeeRepository;

  public Employee saveEmployee(Employee employee){
    return employeeRepository.save(employee);
  }

  @Override
  @Transactional
  public UserDetails loadUserByUsername(String employeeCode) throws UsernameNotFoundException {
    Employee employee = employeeRepository.findByCode(employeeCode)
        .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + employeeCode));

    return EmployeeImpl.build(employee);
  }

}
