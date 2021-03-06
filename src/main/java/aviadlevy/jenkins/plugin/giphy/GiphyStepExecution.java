package aviadlevy.jenkins.plugin.giphy;

import com.cloudbees.plugins.credentials.CredentialsMatcher;
import com.cloudbees.plugins.credentials.CredentialsMatchers;
import hudson.security.ACL;
import jenkins.model.Jenkins;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.jenkinsci.plugins.plaincredentials.StringCredentials;
import org.jenkinsci.plugins.workflow.steps.StepContext;
import org.jenkinsci.plugins.workflow.steps.SynchronousNonBlockingStepExecution;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.List;

/**
 * @author aviadlevy
 */
public abstract class GiphyStepExecution<T, S extends GiphyStep> extends SynchronousNonBlockingStepExecution<T> {
    protected final S step;
    private final transient JSONParser jsonParser;

    protected static final String DEFAULT_IMAGE_SIZE = "downsized_medium";
    private static final String DEFAULT_RATING = "g";

    /**
     * Constructor for StepExecution
     *
     * @param giphyStep the step running
     * @param context step context
     */
    public GiphyStepExecution(S giphyStep, StepContext context) {
        super(context);
        this.step = giphyStep;
        this.jsonParser = new JSONParser();
    }

    /**
     * Meat of the execution.
     *
     * When this method returns, a step execution is over.
     */
    @Override
    protected T run() throws Exception {
        validateValues();
        String apiKey = validateAndGetApiKey();

        URI uri = getGiphyUri(apiKey);

        String jsonString;
        try (CloseableHttpClient client = HttpClientBuilder.create()
                .build()) {
            jsonString = getJsonResponse(uri, client);
        }
        if (StringUtils.isEmpty(jsonString)) {
            throw new Exception("giphy respond with empty body");
        }
        JSONObject json = (JSONObject) jsonParser.parse(jsonString);
        return handleGiphyResponse(step, json);
    }

    /**
     * validate the required values exists, and set default value to optional in case they don't exists
     */
    protected void validateValues() {
        if (StringUtils.isEmpty(step.getCredentialsId())) {
            throw new IllegalArgumentException("you must provide credential id");
        } if (StringUtils.isEmpty(step.getImageSize())) {
            step.setImageSize(DEFAULT_IMAGE_SIZE);
        }
        if (StringUtils.isEmpty(step.getRating())) {
            step.setRating(DEFAULT_RATING);
        }
        validateSpecificStepValues();
    }

    /**
     * validate the required values for the specific step implementation exists, and set default value to optional in
     * case they don't exists
     */
    protected abstract void validateSpecificStepValues();

    /**
     * build the giphy uri
     *
     * @param apiKey the apikey to giphy
     * @return the uri to execute
     * @throws URISyntaxException in case the uri is not valid
     */
    protected abstract URI getGiphyUri(String apiKey) throws URISyntaxException;

    /**
     * method that responsible to what should we do with the response from giphy
     *
     * @param step the step with all the values from the user
     * @param json the response from giphy with JSON format
     * @return the return value of the execution
     */
    protected abstract T handleGiphyResponse(S step,
                                             JSONObject json);

    /**
     * validate the the key is save to Jenkins credentials
     * @return the apikey if exists
     * @throws IllegalArgumentException when the key is not saved to credentials
     */
    private String validateAndGetApiKey() {
        String apiKey;
        try {
            apiKey = lookupCredentials(step.getCredentialsId()).getSecret()
                    .getPlainText();
        }
        catch (NullPointerException e) {
            throw new IllegalArgumentException("you must provide initialized credential id");
        }
        return apiKey;
    }

    /**
     * execute the requst to giphy, and parse the response to JSON
     *
     * @param uri the uri to request
     * @param client the http client we use to execute
     * @return the response from giphy
     * @throws IOException in case of an error during the execution
     */
    private String getJsonResponse(URI uri,
                                   CloseableHttpClient client) throws IOException {
        HttpGet request = new HttpGet(uri);
        HttpResponse response = client.execute(request);
        if (response.getStatusLine()
                .getStatusCode() != 200) {
            handleNotOKStatusCode(response.getStatusLine().getStatusCode());
        }
        return EntityUtils.toString(response.getEntity());
    }

    /**
     * We can now override it in the tests and test the api calls to giphy without actually calling giphy each time
     * TODO: make mockServer work with https and then remove this method
     *
     * @return the scheme we want to call
     */
    protected String getScheme() {
        return "https";
    }

    /**
     * We can now override it in the tests and test the api calls to giphy without actually calling giphy each time
     *
     * @return the host we want to call
     */
    protected String getGiphyHost() {
        return "api.giphy.com";
    }


    /**
     * Throw exception by the response status code
     *
     * @param statusCode the status code from giphy
     */
    private void handleNotOKStatusCode(int statusCode) {
        switch (statusCode) {
            case 400:
                throw new IllegalArgumentException("Bad Request: Your request to giphy was formatted incorrectly or " +
                                                           "missing required parameters.");
            case 403:
                throw new IllegalArgumentException("Your API key to giphy is invalid");
            case 429:
                throw new RuntimeException("Too Many Requests: Your API Key is making too many requests. " +
                                                   "consider requesting a Production Key to upgrade your API Key rate limits.");
            default:
                throw new RuntimeException("Giphy respond: " + statusCode);
        }
    }

    /**
     * get the credential from Secret text, or null if not exists
     *
     * @param credentialId the credential id from the user
     * @return the credential from Secret text, or null if not exists
     */
    private StringCredentials lookupCredentials(String credentialId) {
        List<StringCredentials> credentials =
                com.cloudbees.plugins.credentials.CredentialsProvider.lookupCredentials(
                        StringCredentials.class,
                        Jenkins.getInstance(),
                        ACL.SYSTEM,
                        Collections.emptyList());
        CredentialsMatcher matcher = CredentialsMatchers.withId(credentialId);
        return CredentialsMatchers.firstOrNull(credentials, matcher);
    }
}
