import java.io.IOException;

import org.json.JSONException;
import org.testng.annotations.*;
import pariveda.osdu.OsduSimpleClient;

public class OsduSimpleClientTest {

    private OsduSimpleClient simpleClient;
    private String dataPartition;
    private String token;
    private String successUrl;
    private String successContentType;
 
    @BeforeClass
    public void setUp() {
        dataPartition = "opendes";
        token = "abcde";
        successUrl = "https://server.test.com";
        successContentType = "application/json";
        simpleClient = new OsduSimpleClient(successUrl, dataPartition, successContentType, token);
    }
    
    @Test(groups = { "validation" }, enabled=false ) // Disabled for now; need to refactor to leverage env variables for valid tokens; currently errors out due to null response body
    public void expectedSearchSuccess() {
        String query = "{ kind: \"a:b:c:d\", limit: 1 }";
        try {
            simpleClient.Search(query);
            System.out.println("expectedSearchSuccess: Search query is valid.");
        } catch (Exception e) {
            org.testng.Assert.fail("unexpected exception", e);
        }
    }
    
    @Test(groups = { "validation" }, 
    expectedExceptions = JSONException.class) 
    public void expectedSearchJSONError() {
        String query = "test123";
        try {
            simpleClient.Search(query);
        }
        catch (IOException e) {
            System.out.println("expectedSearchError: caught unexpected IOException:\n" + e.getMessage());
        }
    }

    @Test(groups = { "validation" }, 
    expectedExceptions = IllegalArgumentException.class) 
    public void expectedSearchPayloadError() {
        String query = "{ kind: \"*:*\" }";
        try {
            simpleClient.Search(query);
        }
        catch (IOException e) {
            System.out.println("expectedSearchError: caught unexpected IOException:\n" + e.getMessage());
        }
    }

}
