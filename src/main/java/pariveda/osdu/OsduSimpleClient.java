package pariveda.osdu;

import org.json.*;
import java.io.IOException;
import okhttp3.*;

public class OsduSimpleClient extends OsduBaseClient{
	//Bring your own token client, gets called in the app.java
	String token;
	
	public OsduSimpleClient(String my_url, String my_datapartition, String my_contentType, String my_token){
		super(my_url, my_datapartition, my_contentType);
		this.token = my_token;
	}

	public JSONObject Search(String query) throws IOException{
		System.out.println("OSDU Search.. Query: "+ query);
		if(isValidSearchQuery(query)) {
			RequestBody body = RequestBody.create(query, this.mediaType);
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
		else {
			throw new IllegalArgumentException("Provided Search Query does not contain valid kind pattern");
		}
	}

	public JSONObject SearchWithPaging(String query, String cursor) throws IOException{
		if(isValidSearchQuery(query)) {
			if(isValidCursor(cursor)) {
				JSONObject string_query = AssignCursor(query, cursor);

				RequestBody body = RequestBody.create(string_query.toString(), this.mediaType);
				Request request = new Request.Builder()
					.url(this.url+"/api/search/v2/query_with_cursor")
					.method("POST", body)
					.addHeader("Content-Type", this.contentType)
					.addHeader("data-partition-id", this.dataPartition)
					.addHeader("Authorization", this.token)
					.build();

				Response response = this.client.newCall(request).execute();
				JSONObject resp = new JSONObject(response.body().string());
				return resp;
			} 
			else {
				throw new IllegalArgumentException("Provided Cursor is not numeric");
			}
		}
		else {
			throw new IllegalArgumentException("Provided Search Query does not contain valid kind pattern"); 
		}
	}

	protected boolean isValidCursor(String cursor) throws JSONException {
		if (cursor == null) {
			return false;
		}
		try {
			int int_cursor = Integer.parseInt(cursor);
		} catch (NumberFormatException e) {
			return false;
		}
		return true;
	}

	protected boolean isValidSearchQuery(String query) throws JSONException {
		JSONObject queryJson = new JSONObject(query);
		if( queryJson.getString("kind").matches(".+:.+:.+:.+") ) {
			return true;
		}
		return false;
	}

	// separate helper function thate retunrs the JSON object 
	public JSONObject AssignCursor(String query, String cursor) throws IOException{
		JSONObject string_query = new JSONObject(query);

		if (string_query.get("limit") == null) {
			string_query.put("limit", "1000");
		}
		string_query.put("cursor", cursor);

		return string_query;
	}

	public JSONObject Delivery(String query) throws IOException{
		System.out.println("OSDU Delivery.. Query: "+ query);
		RequestBody body = RequestBody.create(query, this.mediaType);
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
