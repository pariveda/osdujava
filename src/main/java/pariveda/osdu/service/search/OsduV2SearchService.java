package pariveda.osdu;

import org.json.*;
import java.io.IOException;
import okhttp3.*;

public class OsduV2SearchService extends OsduBaseService {
	public OsduV2SearchService(OsduBaseClient osdu_client){
		super(osdu_client);
	}

    // Endpoints
	public JSONObject Search(OsduQueryModel query) throws IOException {
        return this.client.Post("/api/search/v2/query", query.toJSON());
	}

	public JSONObject SearchWithPaging(OsduQueryModel query) throws IOException {
		return this.SearchWithPaging(query, null);
	}
	public JSONObject SearchWithPaging(OsduQueryModel query, String cursor) throws IOException {
		if (cursor == null) {
			return this.client.Post("/api/search/v2/query_with_cursor", query.toJSON());
		}
		else {
            JSONObject searchBody = query.toJSON();
            searchBody.put("cursor", cursor);
            return this.client.Post("/api/search/v2/query_with_cursor", searchBody);
		}
	}
}
