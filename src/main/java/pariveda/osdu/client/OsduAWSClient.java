package pariveda.osdu;

import org.json.*;
import java.io.IOException;
import okhttp3.*;
import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProvider;
import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProviderClientBuilder;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.services.cognitoidp.model.InitiateAuthRequest;
import com.amazonaws.services.cognitoidp.model.AuthenticationResultType;
import com.amazonaws.services.cognitoidp.model.AuthFlowType;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;

public class OsduAWSClient extends OsduBaseClient{
    // AWS Info
    String awsRegion;
    public String awsProfile;

    // Auth Info
    String clientId;
    String username;
    String password;
    String refreshToken;
	
	public OsduAWSClient(String my_url, String my_datapartition, String aws_region, String client_id, String username, String password) throws IOException {
		super(my_url, my_datapartition);
        this.awsRegion = aws_region;
        this.awsProfile = "";
        this.clientId = client_id;
        this.username = username;
        this.password = password;
	}

	protected String GenerateAccessToken() {
        AWSCognitoIdentityProviderClientBuilder cognitoClientBuilder = AWSCognitoIdentityProviderClientBuilder
            .standard()
            .withRegion(this.awsRegion);
        if (this.awsProfile != "") {
            cognitoClientBuilder = cognitoClientBuilder.withCredentials(
                new ProfileCredentialsProvider(this.awsProfile)
            );
        }
        AWSCognitoIdentityProvider cognitoClient = cognitoClientBuilder.build();

        InitiateAuthRequest authRequest = (new InitiateAuthRequest())
            .withAuthFlow(AuthFlowType.USER_PASSWORD_AUTH)
            .withClientId(this.clientId)
            .addAuthParametersEntry("USERNAME", this.username)
            .addAuthParametersEntry("PASSWORD", this.password);

        AuthenticationResultType authResult = cognitoClient.initiateAuth(authRequest)
            .getAuthenticationResult();
        this.refreshToken = authResult.getRefreshToken();
        return authResult.getAccessToken();
	}
}
