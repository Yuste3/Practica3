package com.example.practica3.DTOs;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class EmployeeDTO {
    private int code;
    private String name;
    private String role;
    private String practice;
}



//TODO
/*
* Empleados
*  - Id --> PK NN
*  - Name --> NN
*  - Role --> NN
*  - Practice --> Foreign key (Puede ser nulo)
*
* Practicas
*  - Code (Id) --> PK
*
* Proyecto
*  - Code (Id) --> PK
*
* Asignaciones
*  - Id_Supervisor --> Foreign key (empleados)
*  - Id_Empleado --> Foreign key (empleados)
*  - Code practice --> Foreign key (practice)
*  - New Code proyecto --> Foreign key (Proyecto)
*  - Old Code proyecto --> Foreign key (Proyecto)
*  - Comentario
*  - Porcentaje
*  - Fecha inicio
*  - Fecha fin
*
*
*
*
* (Travel allocation is offsite) se quita esa columna demomento
* */