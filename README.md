# osdupy

A simple python client for the [OSDU](https://community.opengroup.org/osdu) data platform.

### SimpleOsduClient

BYOT: Bring your own token. Great for backend service or business logic that supplements a
front-end application.

This client assumes you are obtaining a token yourself (e.g. via your application's
login form or otheer mechanism. With this SimpleOsduClient, you simply provide that token.
With this simplicity, you are also then respnsible for reefreeshing the token as needed and
re-instantiating the client with the new token.


## Currently supported methods

- [search](osdu/search.py)
  - query
- [delivery](osdu/delivery.py)
  - get_signed_urls

## Usage

```java
String url = "base_url";
    	String dataPartition = "dataparition";
    	String contentType =  "application/json";
    	String token = "";
    	var client = new OsduSimpleClient(url, dataPartition, contentType, token);
```

### Using the client

Below are just a few usage examples.

#### Search for records by query

```java
JSONObject resp = client.Search(query); 
System.out.println(resp.toString(4));	    
	    
```
#### Get signed URL

```java
JSONObject signedURL = client.Delivery("srn list");
System.out.println(signedURL.toString(4));		    
	    
```


