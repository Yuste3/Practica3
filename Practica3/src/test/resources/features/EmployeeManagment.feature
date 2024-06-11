Feature: Employees features

  Scenario: Inserting employee
    Given a employeeDTO to insert in DDBB with code 88, name "Daniel", role "Pruebas" and practice ""
    When I insert a employee
    Then returns CONFLICT