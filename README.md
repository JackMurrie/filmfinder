# capstone-project-comp3900-w15a-lol_i_dunno
capstone-project-comp3900-w15a-lol_i_dunno created by GitHub Classroom

<<<<<<< HEAD
=======


>>>>>>> 6ecaa61b359bb2581dcdbd1b042e503460f200f2
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

# Frontend

This project was bootstrapped with [Create React App](https://github.com/facebook/create-react-app).

## Installation and Setup Instructions

Installation:
You will need `node` and `npm` installed globally on your machine.  

 Visit https://www.npmjs.com/get-npm and click “Download Node.js and npm”. 

Run:<br />
`npm install`<br />
`npm install @material-ui/core`<br />
`npm install @material-ui/icons`<br />
`npm install @material-ui/lab`<br />
`npm install react-router-dom` <br />

To Start Server:

`npm start`  

To Visit App:

`localhost:3000/`  

Build app for production: 

`npm run build`

It correctly bundles React in production mode and optimizes the build for the best performance.

The build is minified and the filenames include the hashes.<br />

See the section about [deployment](https://facebook.github.io/create-react-app/docs/deployment) for more information.


## Dependencies 

/node_modules


This section has moved here: https://facebook.github.io/create-react-app/docs/troubleshooting#npm-run-build-fails-to-minify
