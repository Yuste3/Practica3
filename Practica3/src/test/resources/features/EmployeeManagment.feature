Feature: Employees features

  Scenario: Inserting employee
    Given a employeeDTO to insert in DDBB with code 88, name "Daniel", role "Pruebas" and practice "prueba"
    When I insert a employee
    Then returns NO_CONTENT

  Scenario: Getting employees
    Given employee exists with code 88
    When I get the employee with code 88
    Then returns OK
    And return must contain employee's name "Daniel"
    And return must contain employee's role "Pruebas"
    And return must contain employee's practice "prueba"