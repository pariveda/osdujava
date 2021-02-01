package pariveda.osdu;

import java.net.URL;

import okhttp3.*;

public class OsduBaseClient {
	String url;
	String dataPartition;
	String contentType;
	OkHttpClient client;
	MediaType mediaType;
	
	public OsduBaseClient(String url, String dataPartition, String contentType){
		boolean isValidUrl = validURL(url);
		boolean isValidContentType = validContentType(contentType);
		if (isValidUrl && isValidContentType) {
			this.url = url;
			this.dataPartition = dataPartition;
			this.contentType = contentType;		
			this.client = new OkHttpClient().newBuilder().build();
			this.mediaType = MediaType.parse(this.contentType);
		} else {
			String exceptionMessage = "";
			if (!isValidUrl) exceptionMessage+= "Provided URL \"" + url + "\" is not valid\n";
			if (!isValidContentType) exceptionMessage+= "Provided Content Type \"" + contentType + "\" is not valid\n";
			throw new IllegalArgumentException(exceptionMessage);
		}
	}

	private boolean validURL(String url) {
		try {
			new URL(url).toURI();
			return true;
		}
		catch (Exception e) {
			return false;
		}
	}

	private boolean validContentType(String contentType) {
		if (MediaType.parse(contentType)!=null) return true;
		else return false;
	}
	
}
