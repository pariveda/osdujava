# osdujava

A simple java client for the [OSDU](https://community.opengroup.org/osdu) data platform.

### SimpleOsduClient

BYOT: Bring your own token. Great for backend service or business logic that supplements a
front-end application.

This client assumes you are obtaining a token yourself (e.g. via your application's
login form or otheer mechanism. With this SimpleOsduClient, you simply provide that token.
With this simplicity, you are also then responsible for refreshing the token as needed and
re-instantiating the client with the new token.


## Currently supported methods

- [search](osdu/OsduSimpleClient.java)
  - query
- [delivery](osdu/OsduSimpleClient.java)
  - get_signed_urls

## Usage

```java
String url = "base_url";
String dataPartition = "dataparition";
String token = "token_string_value_to_access_osdu"; 
var client = new OsduSimpleClient(url, dataPartition, token);
var service = new OsduService(client);
```

or 

```java
String url = "base_url";
String dataPartition = "dataparition";
String awsRegion = "token_string_value_to_access_osdu"; 
String cognitoClientId = "cognito_client_id";
String username = "username";
String password = "password";
var client = new OsduAWSClient(url, dataPartition, awsRegion, cognitoClientId, username, password);
var service = new OsduService(client);
```

### Using the service

Below are just a few usage examples.

#### Search for records by query

```java
JSONObject resp = service.Search.V2.Search(query); 
System.out.println(resp.toString(4));
```
#### Get signed URL

```java
JSONObject signedURL = service.Delivery.V2.GetSignedUrlsFromSrns(["srn", "srn"]);
System.out.println(signedURL.toString(4));
```


