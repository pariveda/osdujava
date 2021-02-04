import org.testng.annotations.*;
import pariveda.osdu.OsduBaseClient;
 
public class OsduBaseClientTest {
    private OsduBaseClient baseClient;
    private String dataPartition;
    private String successUrl;
    private String successContentType;

    private String errorUrl;
    private String errorContentType;
 
    @BeforeClass
    public void setUp() {
      dataPartition = "opendes";
      
      successUrl = "https://server.test.com";
      successContentType = "application/json";

      errorUrl = "";
      errorContentType = "";
    }
    
    @Test(groups = { "validation" })
    public void expectedValidationSuccess() {
      baseClient = new OsduBaseClient(successUrl, dataPartition, successContentType);
      System.out.println("ExpectedValidationSuccess: baseClient created with no issues.");
    }
    
    @Test(groups = { "validation" }, expectedExceptions = IllegalArgumentException.class,
    expectedExceptionsMessageRegExp="Provided URL .*")
    public void expectedUrlError() {
      baseClient = new OsduBaseClient(errorUrl, dataPartition, successContentType);
      // nothing will print due to the thrown exception -- System.out.println("ExpectedUrlError: Invalid URL caught in validation.");
    }

    @Test(groups = { "validation" }, expectedExceptions = IllegalArgumentException.class,
    expectedExceptionsMessageRegExp="Provided Content Type .*")
    public void expectedContentTypeError() {
        baseClient = new OsduBaseClient(successUrl, dataPartition, errorContentType);
        // nothing will print due to the thrown exception -- System.out.println("ExpectedContentTypeError: Invalid Content Type caught in validation.");
    } 

    // Separate suite for integration test
    // refer to osdujs tests
}