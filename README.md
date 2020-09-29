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
=======
This project was bootstrapped with [Create React App](https://github.com/facebook/create-react-app).

## Install node_modules first

## Available Scripts

In the project directory, you can run:

### `npm start`

Runs the app in the development mode.<br />
Open [http://localhost:3000](http://localhost:3000) to view it in the browser.

The page will reload if you make edits.<br />
You will also see any lint errors in the console.

### `npm test`

Launches the test runner in the interactive watch mode.<br />
See the section about [running tests](https://facebook.github.io/create-react-app/docs/running-tests) for more information.

### `npm run build`

Builds the app for production to the `build` folder.<br />
It correctly bundles React in production mode and optimizes the build for the best performance.

The build is minified and the filenames include the hashes.<br />
Your app is ready to be deployed!

See the section about [deployment](https://facebook.github.io/create-react-app/docs/deployment) for more information.

### `npm run eject`

**Note: this is a one-way operation. Once you `eject`, you can’t go back!**

If you aren’t satisfied with the build tool and configuration choices, you can `eject` at any time. This command will remove the single build dependency from your project.

Instead, it will copy all the configuration files and the transitive dependencies (webpack, Babel, ESLint, etc) right into your project so you have full control over them. All of the commands except `eject` will still work, but they will point to the copied scripts so you can tweak them. At this point you’re on your own.

You don’t have to ever use `eject`. The curated feature set is suitable for small and middle deployments, and you shouldn’t feel obligated to use this feature. However we understand that this tool wouldn’t be useful if you couldn’t customize it when you are ready for it.

## Learn More

You can learn more in the [Create React App documentation](https://facebook.github.io/create-react-app/docs/getting-started).

To learn React, check out the [React documentation](https://reactjs.org/).

### Code Splitting

This section has moved here: https://facebook.github.io/create-react-app/docs/code-splitting

### Analyzing the Bundle Size

This section has moved here: https://facebook.github.io/create-react-app/docs/analyzing-the-bundle-size

### Making a Progressive Web App

This section has moved here: https://facebook.github.io/create-react-app/docs/making-a-progressive-web-app

### Advanced Configuration

This section has moved here: https://facebook.github.io/create-react-app/docs/advanced-configuration

### Deployment

This section has moved here: https://facebook.github.io/create-react-app/docs/deployment

### `npm run build` fails to minify

This section has moved here: https://facebook.github.io/create-react-app/docs/troubleshooting#npm-run-build-fails-to-minify

