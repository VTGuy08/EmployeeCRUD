# EmployeeCRUD
CRUD Rest service using Spring Boot and MongoDB

Assumes that the user has downloaded and started a MongoDB:
mongod -dbpath C:\path\to\data

When running the application you can perform some searches by
typing the following into a browser (of course replacing the id with one that exists):

List db contents:
http://localhost:8080/employees

List search methods (findByLastName, findById):
http://localhost:8080/employees/search

Search db contents by id:
http://localhost:8080/employees/search/findById?id=5a7a789a8baddc3088dd2c03
