# Vehicle Administration
This project is built for fun and should show an application for vehicle 
administration.

## How-To run
_Requires Docker and Docker Compose installed on your machine._

Simply run the following command:
```bash
docker compose up -d
```
This should build the frontend as well as the backend part and run it inside the
Docker containers together with a PostgreSQL database.

You should be able to access the Vehicle Administration App via http://localhost:9090.
As admin user I already added `MrT` with the obvious password of `aTeam` related to the TV show.

Any e-mail sent will be automatically fetched by the MailHog. You can access those e-mails via
the MailHogs web-frontend running at http://localhost:8025.

When you change something inside the code it requires to rebuild the related Docker image of the related service 
for the change to become available to another Docker Compose run.

## How-To run frontend and backend manually
**Backend**: Run main inside [TestBackendApplication](backend/src/test/kotlin/info/deckermail/vehicle_administration/backend/TestBackendApplication.kt) -
This will automatically spin up a required Docker Container with a PostgreSQL for you.

**Web Frontend**: 
1. Change line for `baseUrl` inside [useApi](web-frontend/src/http/useApi.ts)
2. Run `npm start`

## Backend
The backend has been set up with the Spring Initializer and contains a Spring
Boot Kotlin application.

## Web Frontend
The web frontend is a React app, setup with create-react-app.

## Shortcuts
I did some shortcuts intentionally since I think that they are not really necessary for a demo and might be just 
time-consuming for me. Such shortcuts are:
* _Authentication_ - I've just used basic auth which is just easy to use inside a demo app. Usually I'd go with OAuth2.
* _CORS_ - I've disabled CORS so that I can easily switch ports of the frontend part. Usually I'd add a specific CORS
configuration which contains the real frontend URL
* _Frontend-Config_ - I've hardcoded the backend API URL just for convenience. I skipped setting up specific environments 
to use.
* _CSS_ - I'm not a good web designer, therefore I skipped the most parts of beautiful design and concentrated on the 
functional parts.
* _Using docker-compose_ - I wanted to provide a demo which can be run with one command. I could have chosen k8s deployments,
but I thought it would be easier to just do a `docker compose up -d`.
* _Default roles and users_ - I added an application event listener which executes after the application has started. This
listener will initialize some things including the default roles and users. Usually, I'd do this with Liquibase or 
something similar and not with an application listener. For me, it was easier to use JPA for the data schema and the 
listener for my initial test data.
* _Skipping the tests at build_ - When using Testcontainers it can be difficult to execute the tests within a Docker 
multi-stage-build. To avoid malfunction I just skipped them and executed them manually within IntelliJ to ensure my 
logic works as expected.
* _React project setup_ - I just used the `create-react-app` to set up the React frontend. Usually I'd put some more 
love into the project setup, but I think this isn't as important as the function I built.
* _Plaintext user password in DB_ - Usually, this is a very severe security issue and should never happen. Passwords 
should be hashed with salt so that they can't be reproduced or read from any system. For simplicity reasons I skipped 
hashing of passwords.  
