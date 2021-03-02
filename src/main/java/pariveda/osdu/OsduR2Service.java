package pariveda.osdu;

public class OsduR2Service {
	public OsduDeliveryService Delivery;
	public OsduSearchService Search;
	
	public OsduR2Service(OsduBaseClient osdu_client){
		this.Delivery = new OsduDeliveryService(osdu_client);
		this.Search = new OsduSearchService(osdu_client);
	}
}
