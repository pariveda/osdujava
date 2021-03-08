package pariveda.osdu;

import org.json.*;
import java.io.IOException;
import okhttp3.*;

public class OsduSearchService {
	public OsduV2SearchService V2;

	public OsduSearchService(OsduBaseClient osdu_client){
		this.V2 = new OsduV2SearchService(osdu_client);
	}
}
