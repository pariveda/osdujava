package pariveda.osdu;

import org.json.*;
import java.io.IOException;
import okhttp3.*;

public class OsduSimpleClient extends OsduBaseClient{
	//Bring your own token client, gets called in the app.java
	String token;
	
	public OsduSimpleClient(String my_url, String my_datapartition, String my_token) throws IOException {
		super(my_url, my_datapartition);
		this.token = my_token;
	}

	protected String GenerateAccessToken() {
		return this.token;
	}
}
