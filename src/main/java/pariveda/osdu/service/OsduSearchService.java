package pariveda.osdu;

import org.json.*;
import java.io.IOException;
import okhttp3.*;

public class OsduSearchService extends OsduBaseService {
	public OsduSearchService(OsduBaseClient osdu_client){
		super(osdu_client);
	}

    // Endpoints
	public JSONObject Search(OsduQueryModel query) throws IOException {
        return this.client.Post("/api/search/v2/query", query.toJSON());
	}

	public JSONObject SearchWithPaging(OsduQueryModel query, String cursor) throws IOException {
        if (isValidCursor(cursor)) {
            JSONObject searchBody = query.toJSON();
            searchBody.put("cursor", cursor);
            return this.client.Post("/api/search/v2/query_with_cursor", searchBody);
        }
        else {
            throw new IllegalArgumentException("Provided Cursor is not numeric");
        }
	}

    // Helpers
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
}
