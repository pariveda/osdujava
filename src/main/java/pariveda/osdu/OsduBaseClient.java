package pariveda.osdu;

import okhttp3.*;

public class OsduBaseClient {
	String url;
	String dataPartition;
	String contentType;
	OkHttpClient client;
	MediaType mediaType;
	
	OsduBaseClient(){
		System.out.println("url, dataparition, contenttype are required");
	}
	
	OsduBaseClient(String my_url, String my_datapartition, String my_contentType){
		this.url = my_url;
		this.dataPartition = my_datapartition;
		this.contentType = my_contentType;		
		this.client = new OkHttpClient().newBuilder().build();
		this.mediaType = MediaType.parse(this.contentType);
	}
	
}
