package com.tmquang.score.security.services;

import com.tmquang.score.models.Employee;
import com.tmquang.score.payload.request.UserRequest;
import com.tmquang.score.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements UserDetailsService {
  @Autowired
  EmployeeRepository employeeRepository;

  public Employee saveEmployee(Employee employee){
    return employeeRepository.save(employee);
  }

  public List<Employee> getAll(){
    return  employeeRepository.findAll();
  }

  public Employee getById(Integer id){
    return employeeRepository.getById(id);
  }

  @Override
  @Transactional
  public UserDetails loadUserByUsername(String employeeCode) throws UsernameNotFoundException {
    Employee employee = employeeRepository.findByCode(employeeCode)
        .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + employeeCode));

    return EmployeeImpl.build(employee);
  }

  public Employee update(Integer id, Employee data){
    Optional<Employee> findEmployeeOptional = employeeRepository.findById(id);

    if(findEmployeeOptional.isPresent()){
      Employee findEmployee = findEmployeeOptional.get();
      findEmployee.setEmail(data.getEmail());
      findEmployee.setPhone(data.getPhone());
      findEmployee.setAddress(data.getAddress());
      findEmployee.setDob(data.getDob());
      findEmployee.setRole(data.getRole());
      findEmployee.setDepartment(data.getDepartment());
      return employeeRepository.save(findEmployee);
    }
    return null;
  }

  public Employee update(Integer id, UserRequest data){
    Optional<Employee> findEmployeeOptional = employeeRepository.findById(id);

    if(findEmployeeOptional.isPresent()){
      Employee findEmployee = findEmployeeOptional.get();
      findEmployee.setEmail(data.getEmail());
      findEmployee.setPhone(data.getPhone());
      findEmployee.setAddress(data.getAddress());
      findEmployee.setDob(data.getDob());

      return employeeRepository.save(findEmployee);
    }
    return null;
  }

  public Employee changePassword(Integer id, String newPassword){
    Optional<Employee> findEmployeeOptional = employeeRepository.findById(id);

    if(findEmployeeOptional.isPresent()){
      Employee findEmployee = findEmployeeOptional.get();
      findEmployee.setPassword(newPassword);
      return employeeRepository.save(findEmployee);
    }
    return null;
  }

  public void delete(Integer id){
    Employee findEmployee = employeeRepository.findById(id).orElse(null);

    if (findEmployee == null){
      throw new RuntimeException("Can not find employee with id: " + id);
    }

    employeeRepository.delete(findEmployee);
  }
}
