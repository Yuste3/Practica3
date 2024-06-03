package com.example.practica3.Mappers;

import com.example.practica3.DTOs.EmployeeDTO;
import com.example.practica3.DTOs.UEmployeeDTO;
import com.example.practica3.model.Employee;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {

    @Mapping(source="code", target="employeeID")
    @Mapping(source="practice", target="practice.name")
    Employee employeeDTOToEmployee(EmployeeDTO employeeDTO);

    @Mapping(source="employeeID", target="code")
    @Mapping(source="practice.name", target="practice")
    EmployeeDTO employeeToDTO(Employee employee);

    @Mapping(source="practice", target="practice.name")
    Employee uEmployeeDTOToEmployee(UEmployeeDTO uEmployeeDTO);

}
