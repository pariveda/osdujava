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
        // put in the token and base url to test
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
        String cursor = "2";
        try {
            simpleClient.Search(query);
            simpleClient.SearchWithPaging(query, cursor);
        }
        catch (IOException e) {
            System.out.println("expectedSearchError: caught unexpected IOException:\n" + e.getMessage());
        }
    }

    @Test(groups = { "validation" }, 
    expectedExceptions = IllegalArgumentException.class) 
    public void expectedSearchPayloadError() {
        String query = "{ kind: \"*:*\" }";
        String cursor = "2";
        try {
            simpleClient.Search(query);
            simpleClient.SearchWithPaging(query, cursor);
        }
        catch (IOException e) {
            System.out.println("expectedSearchError: caught unexpected IOException:\n" + e.getMessage());
        }
    }

    @Test(groups = {"validation"},
    expectedExceptions = JSONException.class)
    public void expectedCursorLoadSuccess() {
        String query = "{ kind: \"*:*:*:*\" }";
        String cursor = "2";
        try {
            simpleClient.SearchWithPaging(query, cursor);
        } catch (IOException e) {
            System.out.println("expectedSearchError: caught unexpected IOException:\n" + e.getMessage());
        }
    }

    @Test(groups = {"validation"},
    expectedExceptions = IllegalArgumentException.class)
    public void expectedCursorLoadError() {
        String query = "{ kind: \"*:*:*:*\" }";
        String cursor = "TWO";
        try {
            simpleClient.SearchWithPaging(query, cursor);
        } catch (IOException e) {
            System.out.println("expectedSearchError: caught unexpected IOException:\n" + e.getMessage());
        }
    }

}
