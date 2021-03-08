package pariveda.osdu;

import org.json.*;
import java.util.*;
import java.io.IOException;

public class OsduDeliveryService {
	public OsduV2DeliveryService V2;

	public OsduDeliveryService(OsduBaseClient osdu_client){
		this.V2 = new OsduV2DeliveryService(osdu_client);
	}
}
