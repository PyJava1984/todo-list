# todo-list

Prerequisite:
1. Java 15
2. Mysql 5.7
3. Maven

Step-1: Create database and import data using "todolist.sql" script
Step-2: Enter into "todo-listing"
Step-3: Run Maven command "mvn spring-boot:run"
Step-4: Open Postman
Step-5: Check API, details of API below -

url: http://localhost:8091/api/auth/signup [this api use for signup in system]
method: POST
json: 
{
    "username":"check1",
    "email":"check1@yomail.com",
    "password":"check123"
}

url: http://localhost:8091/api/auth/signin [this use for login, credential provided]
method: POST
json: 
{
    "username":"check1",
    "password":"check123"
}

url: http://localhost:8091/api/test/user [Get all user list]
method: GET
json: {}
Authorization: Bearer token

url: http://localhost:8091/api/todolist/lists [Create ToDo List]
method: POST
json: 
{
    "name":"checklist5",
    "priority":"High"
}
Authorization: Bearer token

url: http://localhost:8091/api/todolist/lists/{Todo List Id}/items [Create Task in ToDo List]
method: POST
json: 
{
    "completed":false,
    "name":"checkitem1",
    "priority":"High"
}
Authorization: Bearer token

url: http://localhost:8091/api/todolist/lists [Get all ToDo List (Order by priority) ]
method: GET
json: 
{
    "completed":false,
    "name":"checkitem1",
    "priority":"High"
}
Authorization: Bearer token

url: http://localhost:8091/api/todolist/lists/{Todo List Id} [Get Specific ToDo List (task order by priority) ]
method: GET
json: 
{
    "completed":false,
    "name":"checkitem1",
    "priority":"High"
}
Authorization: Bearer token

url: http://localhost:8091/api/todolist/lists/{Todo List Id}/false [Get completed ToDo List (task order by priority) ]
method: GET
json: 
{
    "completed":false,
    "name":"checkitem1",
    "priority":"High"
}
Authorization: Bearer token

url: http://localhost:8091/api/todolist/lists/{Todo List Id} [Update Todo List]
method: PUT
json: 
{
    "name":"checklist5",
    "priority":"High"
}
Authorization: Bearer token

url: http://localhost:8091/api/todolist/lists/{Todo List Id}/items/{Task Id} [Update task of Todo List]
method: PUT
json: 
{
    "completed":false,
    "name":"checkitem1",
    "priority":"High"
}
Authorization: Bearer token


url: http://localhost:8091/api/todolist/lists/{Todo List Id} [Delete Todo List]
method: DELETE
json:{}
Authorization: Bearer token

url: http://localhost:8091/api/todolist/lists/{Todo List Id}/items/{Task Id} [Delete task of Todo List]
method: DELETE
json:{}
Authorization: Bearer token
