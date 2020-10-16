# capstone-project-comp3900-w15a-lol_i_dunno
capstone-project-comp3900-w15a-lol_i_dunno created by GitHub Classroom

# Backend setup v1.0

Change logs:
- Nothing yet.
---

## Dependencies

- Java (OpenJDK Runtime Environment) 11 or newer.
- Maven

## How to Build
Run following command in the ```backend``` directory:

```
$ mvn package
```
To clean build:
```
$ mvn clean package
```

## How to Run backend server
Run following command in the ```backend``` directory:

```
$ mvn exec:java
```

## Example Testing

### Using ```cURL``` (windows may not work properly)

Open a new/separate terminal window.

#### GET-ing from a path

Example 1:
```
 curl localhost:8080/rest/msg
```

#### POST-ing JSON data:

Example 2:
``` 
{
    "email":"user@gmail.com",
    "password":"12345"
}
```
to path ```http://localhost:8080/rest/auth/login```:

```
curl -H "Content-Type: application/json" -d '{"email":"user@gmail.com","password":"12345"}' http://localhost:8080/rest/auth/login
```

Example 3:
```
{
    "email":"user@gmail.com",
    "password":"12345",
    "firstName":"John",
    "lastName":"Doe"
}
```
to path ```http://localhost:8080/rest/auth/register```

```
curl -H "Content-Type: application/json" -d '{"email":"user@gmail.com","password":"12345","firstName":"John","lastName":"Doe"}' http://localhost:8080/rest/auth/register
```

### Using Postman

Download Postman API Platform from https://www.postman.com/downloads/

1. Run the backend server.
2. Open the Postman application.

To POST:
1. Select POST from the bar menu to the left of the URL.
2. Enter the path URL e.g. http://localhost:8080/rest/auth/register
3. Go to 'Body' tab.
4. Change 'none' to raw.
5. Change 'Text' to 'JSON'.
6. Enter data in JSON format. e.g. {"email":"user@gmail.com", "password":"12345"}
7. Click 'Send'.
8. In 'Response', same expected outputs as using curl command should appear. e.g. "Login unsuccessful"
9. In the terminal running the server should have their respective outputs. See Example expected outputs sections for more info.

### Example expected outputs

Example 1:
On your terminal running the server should print movie categories.
On your terminal that executed ```curl``` command should print 
``` 
This is good
```

Example 2:

On your terminal running the server should print:
```
email=user@gmail.com, password=12345
```
On your terminal that executed ```curl``` command should print.
``` 
Login unsuccessful
```

Example 3:

On your terminal running the server should print:
```
first=John, last=Doe, email=user@gmail.com, password=12345
```
On your terminal that executed ```curl``` command should print.
``` 
Register unsuccessful
```