# secure-backend-with-api-gateway
Spring Boot based REST API gateway secured with OAuth2 

## authserver

The OAuth2 server is used to issue tokens that are used within the distrubuted microservies if any.
The microservices are located in the resources folder.

You get outta the box a custom implementation of Spring Security ```UserDetailsService``` 
located ```src/main/java/com/romaine/backend/AuthUserDetailsService.java```

This custom implementation uses a MongoDB respository as the user database to authenticate againist. 

Because of this a template ```User.java``` class is provided.

**Note** This starter-template has a fully configured mongo configuration done programmatically.
All you need to do is change the *dbName* and *dbURI* in the application.properties to your own MongoDB server
```
authserver.dbName=dbName
authserver.dbURI=mongodb://dbURI
```
**Yes, this means you can create as many custom models and there repository interfaces as you like.**

## authserver required configuration

Open *application.properties* and
- change *client_id* to a custom CLIENT_ID
- change *clientidsecret* to a strong secret key with lots of entropy

```
cd Auth\ Server/src/main/resources/
open -a Xcode application.properties

security.oauth2.client.clientId: client_id
security.oauth2.client.clientSecret: clientidsecret
...
```

This registers a client *client_id* with a secret and some authorized grant types including "authorization_code"
Authorized grant types include "client_credentials" and "password" which is the recommended grant types for mobile clients. 

**Note**
To take advantage of all the features of our authserver we want to be able to create tokens for users. To get a token on behalf of a user of our backend we need to be able to authenticate the user. 
This is why the "password" grant type is included in the:
```
security.oauth2.client.authorized-grant-types:
```
The "client_credentials" grant type should be used to test the connection to the token endpoint.

- Optional define a *server.port=9999* if you have full control of the deployment environment.
  - If you do this you can remove the *Procfile* from Auth Server root. It is used when deplying to heroku.

- Then set a predictable password for testing also set the context path so that it doesn’t use the default ("/") 

```
server.port=9999
security.user.password=password
server.contextPath=/auth
```

To start the server locally to make sure it is working correctly:

```
mvn spring-boot:run
```

The ```src/main/java/com/romaine/backend/AuthserverApplication.java``` implements ```CommandLineRunner``` and creates a test user account for you. Check the logs for the details it should look like this:

```
 INFO 10256 --- [           main] s.b.c.e.t.TomcatEmbeddedServletContainer : Tomcat started on port(s): 9999 (https)
Created User:
User [id=56aeb1e276fcf14fed19d985, username=user1, password=password, firstName=John, lastName=Doe, email=John.Doe@email.com, properties={role=user, account-enabled=true, account-not-locked=true, account-created-on=2016-01-31T20:16:18.260, authorites=[ROLE_USER], account-not-expired=true, credentials-not-expired=true}]
```

####How to get an Access Token
You can grab a token as the "client_id" client with this curl
```
$ curl client_id:clientidsecret@localhost:9999/auth/oauth/token -d grant_type=client_credentials
{"access_token":"4b6ff0dd-cebe-4adf-96e7-5af934bbb793","token_type":"bearer","expires_in":43199,"scope":"read write"}
```
To grab an access token for a "user" with the "client_id" client try this curl. 
**Note** Remember that an easy to remember password was set in application.properties ``` security.user.password=password ```
```
$ curl client_id:clientidsecret@localhost:9999/auth/oauth/token -d grant_type=password -d username=user -d password=password
{"access_token":"957208f5-09aa-423c-89bc-0861bec780fa","token_type":"bearer","refresh_token":"8587b43f-1a89-4a05-a9e2-e8226c88fbe5","expires_in":43199,"scope":"read write"}
```
This verifies that the "password" grant, where you exchange a username and password for an access token works.

####Spring Boot OAuth2 suggestions for "social" login
**Note** This template doesn't support "social" login by default but it can be easily added by appending 3rd party configuration(s) to the application.properties file.
Password grant is appropriate for a native or mobile application, and where you have a local user database to store and validate the credentials. For a web app, or any app with "social" login, you need the "authorization code" grant, and that means you need a browser to handle redirects and render the user interfaces from the external providers.

####How to get an Access Token via HTTPS
The authserver supports HTTPS when the ```spring.profiles.active=https``` is set. This tells spring boot's embedded web server to use HTTPS (SSL/TLS).
HTTPS requires a signed certificate and a certificate password which we provide using property values located in ```application-https.properties```.
You can generate a self-signed certificate using Java's keytool.
The generated keystore file should be placed in
```
*/src/main/resources
```
If properly configured the below curl should look similar to
```
curl -k https://acme:acmesecret@localhost:9999/auth/oauth/token -d grant_type=password -d username=user1 -d password=password
{"access_token":"5e972db0-8654-4498-be79-3d8c98489fa5","token_type":"bearer","refresh_token":"71425be9-aeb2-428a-b848-78a85c4d2830","expires_in":43199,"scope":"write"}
```
