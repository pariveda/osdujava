package pariveda.osdu;

import org.json.*;
import java.util.*;
import java.io.IOException;

public class OsduV2DeliveryService extends OsduBaseService {
	public OsduV2DeliveryService(OsduBaseClient osdu_client){
		super(osdu_client);
	}

	public JSONObject GetSignedUrlsFromSrns(List<String> srns) throws IOException {
        JSONObject deliveryBody = new JSONObject();
        deliveryBody.put("srns", srns);

        return this.client.Post("/api/delivery/v2/GetFileSignedUrl", deliveryBody);
	}
}
