package pariveda.osdu;

public abstract class OsduBaseService {
    protected OsduBaseClient client;

    public OsduBaseService(OsduBaseClient osdu_client) {
        this.client = client;
    }
}