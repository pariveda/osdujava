package pariveda.osdu;

import org.json.*;
import java.net.URL;
import okhttp3.*;
import java.util.*;
import java.io.IOException;

public abstract class OsduBaseClient {
	String url;
	String dataPartition;
	String contentType;
	OkHttpClient client;
	MediaType mediaType;
	String accessToken;
	
	public OsduBaseClient(String url, String dataPartition) throws IOException {
		boolean isValidUrl = validURL(url);
		if (isValidUrl) {
			this.url = url;
			this.dataPartition = dataPartition;
			this.contentType = "application/json";		
			this.client = new OkHttpClient().newBuilder().build();
			this.mediaType = MediaType.parse(this.contentType);
			this.accessToken = "";
		} else {
			String exceptionMessage = "";
			if (!isValidUrl) exceptionMessage+= "Provided URL \"" + url + "\" is not valid\n";
			throw new IllegalArgumentException(exceptionMessage);
		}
	}

	protected abstract String GenerateAccessToken();

	// Request Sending
	public JSONObject Post(String endpoint, JSONObject body) throws IOException {
		return this.Post(endpoint, body, null);
	}
	public JSONObject Post(String endpoint, JSONObject body, Map<String, String> query_parameters) throws IOException {
		// Get Access Token
		if (this.accessToken == "") {
			this.accessToken = this.GenerateAccessToken();
		}

		// Create Request Body
		RequestBody requestBody = RequestBody.create(body.toString(), this.mediaType);

		// Generate URL
		String url = this.url + endpoint;
		if (query_parameters != null) {
			String queryParameterString = "?";
			for (Map.Entry<String, String> entry : query_parameters.entrySet()) {
				String key = entry.getKey();
				String val = entry.getValue();
				queryParameterString += key + "=" + val + "&";
			}
			queryParameterString = queryParameterString.substring(0, queryParameterString.length() - 1);
			url += queryParameterString;
		}

		// Build Request
		Request request = new Request.Builder()
		.url(url)
		.method("POST", requestBody)
		.addHeader("Content-Type", this.contentType)
		.addHeader("data-partition-id", this.dataPartition)
		.addHeader("Authorization", "Bearer " + this.accessToken)
		.build();
		
		// Send Request
		Response response = this.client.newCall(request).execute();
		JSONObject resp = new JSONObject(response.body().string());
		return resp;
	}

	// Helpers
	private boolean validURL(String url) {
		try {
			new URL(url).toURI();
			return true;
		}
		catch (Exception e) {
			return false;
		}
	}
}
