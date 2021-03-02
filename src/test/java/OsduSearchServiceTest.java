import java.io.IOException;

import org.json.JSONException;
import org.testng.annotations.*;
import pariveda.osdu.*;

public class OsduSearchServiceTest {

    private OsduSearchService searchService;
    private String dataPartition;
    private String token;
    private String successUrl;
 
    @BeforeClass
    public void setUp() throws Exception {
        dataPartition = "opendes";
        // put in the token and base url to test
        token = "eyJraWQiOiJzZGtscjNMYVcwWm5nbHRYUGV0cjhmeUtVWWZKNStLZ2RSMyttV1FaTExvPSIsImFsZyI6IlJTMjU2In0.eyJzdWIiOiI4MmNlZjgzOS1iNmRmLTRmM2YtYjc0MS0yMjhiYjFhZGM4M2EiLCJldmVudF9pZCI6IjRkNzg0MDU1LTdlZTUtNDNkNS04ZmIyLWNlMDU4NGMwYTZhNSIsInRva2VuX3VzZSI6ImFjY2VzcyIsInNjb3BlIjoib3BlbmlkIGVtYWlsIiwiYXV0aF90aW1lIjoxNjE0NjA5MDIyLCJpc3MiOiJodHRwczpcL1wvY29nbml0by1pZHAuY2EtY2VudHJhbC0xLmFtYXpvbmF3cy5jb21cL2NhLWNlbnRyYWwtMV9WZWxNVEFEcEwiLCJleHAiOjE2MTQ3Mjk3NTcsImlhdCI6MTYxNDcyNjE1NywidmVyc2lvbiI6MiwianRpIjoiMWIyMDVhNWYtYWE4NC00YWIwLWEyZDMtNWQ1ODYxNDIwZDEyIiwiY2xpZW50X2lkIjoiN3QwZHRzbXIzdmJpM3BlZTk5djQyN29qdGgiLCJ1c2VybmFtZSI6ImFkbWluQHRlc3RpbmcuY29tIn0.en81K3DncAP4xzdhj2lXGR1dHtUCG-zhupPNVq54b-dsUnoXJ2-rswPz_4mOgeOBpIDXWddkPZ00m8ykPrOBc5X0V6dU0ZW8nj91qMfr8PwRlp8Hd2WQga6BMwkBKFXt78u_z42ZnVvsOEcTrG1yu3Ew3_cKDeNptN4fheDybkbKhiNiKXML9aSFeuOZFHW6-tB5AmcCkzYOt_sQMzZhAABPukrFnUvahq6tzysEYsfEdLVbbrD-OLfx0VUv_KLfb-HfRTKNTwUKBOyLpvabSVdK91BL3EUEnzc7nfeH5TOKKG93ECUbw2j9UuA4RVngrDR4-C31QsODXR2R_v7bGg";
        successUrl = "https://mzn6swtkf0.execute-api.ca-central-1.amazonaws.com";
        searchService = new OsduSearchService(
            new OsduSimpleClient(successUrl, dataPartition, token)
        );
    }
    
    @Test(groups = { "validation" }, enabled=false ) // Disabled for now; need to refactor to leverage env variables for valid tokens; currently errors out due to null response body
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

    @Test(groups = {"validation"},
    expectedExceptions = JSONException.class)
    public void expectedCursorLoadSuccess() {
        try {
            OsduQueryModel query = new OsduQueryModel("*:*:*:*");
            query.limit = 1;
            searchService.SearchWithPaging(query, "");
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

    @Test(groups = {"validation"},
    expectedExceptions = IllegalArgumentException.class)
    public void expectedCursorLoadError() {
        try {
            OsduQueryModel query = new OsduQueryModel("*:*:*:*");
            query.limit = 1;
            searchService.SearchWithPaging(query, "TWO");
        } catch (IOException e) {
            System.out.println("expectedSearchError: caught unexpected IOException:\n" + e.getMessage());
        }
    }
}
