import java.io.IOException;

import org.json.*;
import org.testng.annotations.*;
import pariveda.osdu.*;
import io.github.cdimascio.dotenv.Dotenv;

public class OsduSearchServiceTest {

    private OsduSearchService searchService;
    private String dataPartition;
    private String awsRegion;
    private String awsProfile;
    private String cognitoId;
    private String username;
    private String password;
    private String osduUrl;
 
    @BeforeClass
    public void setUp() throws Exception {
        // Get Environment Variables
        Dotenv dotenv = Dotenv.configure().ignoreIfMissing().load();
        dataPartition = dotenv.get("DATA_PARTITION");
        awsRegion = dotenv.get("AWS_REGION");
        cognitoId = dotenv.get("COGNITO_ID");
        username = dotenv.get("OSDU_USERNAME");
        password = dotenv.get("OSDU_PASSWORD");
        osduUrl = dotenv.get("OSDU_URL");
        awsProfile = dotenv.get("AWS_PROFILE");
        System.out.println(awsProfile);

        // Create client and service
        OsduAWSClient client = new OsduAWSClient(osduUrl, dataPartition, awsRegion, cognitoId, username, password);
        if (awsProfile != null) {
            client.awsProfile = awsProfile;
        }
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
