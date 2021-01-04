package pariveda.osdu;

import org.json.*;
import java.io.IOException;

import okhttp3.*;

public class OsduSimpleClient extends OsduBaseClient{
	//Bring your own token client, gets called in the app.java
	String token;
	
	OsduSimpleClient(String my_url, String my_datapartition, String my_contentType, String my_token){
		super(my_url, my_datapartition, my_contentType);
		this.token = my_token;
	}

	public JSONObject Search(String query) throws IOException{
		System.out.println("OSDU Search.. Query: "+ query);
		RequestBody body = RequestBody.create(this.mediaType, query);
	    Request request = new Request.Builder()
	      .url(this.url+"/api/search/v2/query")
	      .method("POST", body)
	      .addHeader("Content-Type", this.contentType)
	      .addHeader("data-partition-id", this.dataPartition)
	      .addHeader("Authorization", this.token)
	      .build();
	    
	    Response response = this.client.newCall(request).execute();
	    JSONObject resp = new JSONObject(response.body().string());
	    //System.out.println("Resp in OSDU Simple Client: " +resp);
	    return resp;
	}
	public JSONObject Delivery(String query) throws IOException{
		System.out.println("OSDU Delivery.. Query: "+ query);
		RequestBody body = RequestBody.create(this.mediaType, query);
	    Request request = new Request.Builder()
	      .url(this.url+"/api/delivery/v2/GetFileSignedUrl")
	      .method("POST", body)
	      .addHeader("Content-Type", this.contentType)
	      .addHeader("data-partition-id", this.dataPartition)
	      .addHeader("Authorization", this.token)
	      .build();
	    
	    Response response = this.client.newCall(request).execute();
	    JSONObject resp = new JSONObject(response.body().string());
	    //System.out.println("Resp in OSDU Simple Client: " +resp);
	    return resp;
	}
	

}
