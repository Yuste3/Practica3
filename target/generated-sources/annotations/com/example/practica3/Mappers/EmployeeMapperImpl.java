package com.example.practica3.Mappers;

import com.example.practica3.DTOs.EmployeeDTOs.EmployeeDTO;
import com.example.practica3.DTOs.EmployeeDTOs.UEmployeeDTO;
import com.example.practica3.model.Employee;
import com.example.practica3.model.Practice;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-06-11T11:06:26+0200",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.2 (Oracle Corporation)"
)
@Component
public class EmployeeMapperImpl implements EmployeeMapper {

    @Override
    public Employee employeeDTOToEmployee(EmployeeDTO employeeDTO) {
        if ( employeeDTO == null ) {
            return null;
        }

        Employee.EmployeeBuilder employee = Employee.builder();

        employee.practice( employeeDTOToPractice( employeeDTO ) );
        employee.employeeID( (long) employeeDTO.getCode() );
        employee.name( employeeDTO.getName() );
        employee.role( employeeDTO.getRole() );

        return employee.build();
    }

    @Override
    public EmployeeDTO employeeToDTO(Employee employee) {
        if ( employee == null ) {
            return null;
        }

        EmployeeDTO employeeDTO = new EmployeeDTO();

        if ( employee.getEmployeeID() != null ) {
            employeeDTO.setCode( employee.getEmployeeID().intValue() );
        }
        employeeDTO.setPractice( employeePracticeName( employee ) );
        employeeDTO.setName( employee.getName() );
        employeeDTO.setRole( employee.getRole() );

        return employeeDTO;
    }

    @Override
    public Employee uEmployeeDTOToEmployee(UEmployeeDTO uEmployeeDTO) {
        if ( uEmployeeDTO == null ) {
            return null;
        }

        Employee.EmployeeBuilder employee = Employee.builder();

        employee.practice( uEmployeeDTOToPractice( uEmployeeDTO ) );
        employee.name( uEmployeeDTO.getName() );
        employee.role( uEmployeeDTO.getRole() );

        return employee.build();
    }

    protected Practice employeeDTOToPractice(EmployeeDTO employeeDTO) {
        if ( employeeDTO == null ) {
            return null;
        }

        Practice.PracticeBuilder practice = Practice.builder();

        practice.name( employeeDTO.getPractice() );

        return practice.build();
    }

    private String employeePracticeName(Employee employee) {
        if ( employee == null ) {
            return null;
        }
        Practice practice = employee.getPractice();
        if ( practice == null ) {
            return null;
        }
        String name = practice.getName();
        if ( name == null ) {
            return null;
        }
        return name;
    }

    protected Practice uEmployeeDTOToPractice(UEmployeeDTO uEmployeeDTO) {
        if ( uEmployeeDTO == null ) {
            return null;
        }

        Practice.PracticeBuilder practice = Practice.builder();

        practice.name( uEmployeeDTO.getPractice() );

        return practice.build();
    }
}
