import java.io.IOException;

import org.json.*;
import org.testng.annotations.*;
import pariveda.osdu.*;

public class OsduSearchServiceTest {

    private OsduSearchService searchService;
    private String dataPartition;
    private String awsRegion;
    private String cognitoId;
    private String username;
    private String password;
    private String successUrl;
 
    @BeforeClass
    public void setUp() throws Exception {
        dataPartition = "opendes";
        // put in the token and base url to test
        awsRegion = "ca-central-1";
        cognitoId = "7or95445c0stsdq32q1788mffq";
        username = "admin@testing.com";
        password = "7JOOsIit";
        successUrl = "https://mzn6swtkf0.execute-api.ca-central-1.amazonaws.com";
        OsduAWSClient client = new OsduAWSClient(successUrl, dataPartition, awsRegion, cognitoId, username, password);
        client.awsProfile = "par-osdu";
        searchService = new OsduSearchService(client);
    }
    
    @Test(groups = { "validation" })
    public void expectedSearchSuccess() {
        try {
            OsduQueryModel query = new OsduQueryModel("*:*:*:*");
            query.limit = 1;
            searchService.Search(query);
            System.out.println("expectedSearchSuccess: Search query is valid.");
        } catch (Exception e) {
            org.testng.Assert.fail("unexpected exception", e);
        }
    }

    @Test(groups = {"validation"})
    public void expectedCursorLoadSuccess() {
        try {
            OsduQueryModel query = new OsduQueryModel("*:*:*:*");
            query.limit = 1;
            JSONObject results = searchService.SearchWithPaging(query);
            searchService.SearchWithPaging(query, results.getString("cursor"));
            System.out.println("expectedSearchSuccess: Search query is valid.");
        } catch (IOException e) {
            System.out.println("expectedSearchError: caught unexpected IOException:\n" + e.getMessage());
        }
    }

    @Test(groups = { "validation" }, 
    expectedExceptions = IllegalArgumentException.class) 
    public void expectedSearchPayloadError() {
        try {
            OsduQueryModel query = new OsduQueryModel("*:*");
            searchService.Search(query);
            searchService.SearchWithPaging(query, "2");
        }
        catch (IOException e) {
            System.out.println("expectedSearchError: caught unexpected IOException:\n" + e.getMessage());
        }
    }
}
