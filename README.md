# secure-backend-with-api-gateway
Spring Boot based REST API gateway secured with OAuth2 

## authserver required configuration

Open *application.properties* and
- change *acme* to a custom CLIENT_ID
- change *acmesecret* to a strong secret key with lots of entropy

```
cd Auth\ Server/src/main/resources/
open -a Xcode application.properties

security.oauth2.client.clientId: acme
security.oauth2.client.clientSecret: acmesecret
...
```

This registers a client *acme* with a secret and some authorized grant types including "authorization_code"

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
