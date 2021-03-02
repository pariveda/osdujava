package pariveda.osdu;

import org.json.*;
import java.util.*;
import java.io.IOException;

public class OsduQueryModel {
    public String kind;
    public String query;
    public List<String> returnedFields;
    public Integer limit;

    public OsduQueryModel(String kind) throws IOException {
        if (kind.matches(".+:.+:.+:.+")) {
            this.kind = kind;
        }
        else {
			throw new IllegalArgumentException("Provided Search Query does not contain valid kind pattern");
        }
        this.limit = 1000;
        this.query = "";
        this.returnedFields = new ArrayList<String>();
    }

    public JSONObject toJSON() {
        JSONObject obj = new JSONObject();

        obj.put("kind", this.kind);
        obj.put("limit", this.limit);
        if (this.query != "") {
            obj.put("query", this.query);
        }
        if (this.returnedFields.size() > 0) {
            obj.put("returnedFields", this.returnedFields);
        }

        return obj;
    }

    public String toString() {
        return this.toJSON().toString();
    }
}