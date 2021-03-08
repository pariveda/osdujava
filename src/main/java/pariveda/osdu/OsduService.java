package pariveda.osdu;

public class OsduService {
	public OsduDeliveryService Delivery;
	public OsduSearchService Search;
	
	public OsduService(OsduBaseClient osdu_client){
		this.Delivery = new OsduDeliveryService(osdu_client);
		this.Search = new OsduSearchService(osdu_client);
	}
}
