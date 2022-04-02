# capstone-project-comp3900-w15a-lol_i_dunno
capstone-project-comp3900-w15a-lol_i_dunno created by GitHub Classroom

## Dependencies

- Java (OpenJDK Runtime Environment) 11 or newer.
- Maven
- Node.js > 13
- npm

# Backend

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

## Start Dev Containers

Run following command in the `backend` directory:

```
docker build -t filmfinder-backend .
```

```
docker run \
    -it \
    --rm \
    --network filmfinder --network-alias backend \
    -v "$(pwd):/app" \
    -p 8080:8080 \
    filmfinder-backend
```

Run following command in the `root` directory:

```
docker build -t filmfinder .
```

```
docker run \
    -it \
    --rm \
    --network filmfinder \
    -w /app \
    -v "$(pwd):/app" \
    -p 3000:3000 \
    -e CHOKIDAR_USEPOLLING=true \
    filmfinder
```

Visit 'localhost:3000`

### Alternatively 

Run both containers with docker compose from the `root` directory:

```
docker-compose up -d
```

To see the frontend and backend services logs run 
```
docker-compose logs -f
```

To tear down the services run 
```
docker-compose down
```
