package aviadlevy.jenkins.plugin.giphy.search;

import com.cloudbees.plugins.credentials.CredentialsMatcher;
import com.cloudbees.plugins.credentials.CredentialsMatchers;
import hudson.security.ACL;
import jenkins.model.Jenkins;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
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
// TODO: add javadoc to all code
public abstract class GiphySearchStepExecution<T> extends SynchronousNonBlockingStepExecution<T> {
    private static final String DEFAULT_RATING = "g";
    private final GiphySearchStep step;
    private final transient JSONParser jsonParser;

    public GiphySearchStepExecution(GiphySearchStep giphyStep, StepContext context) {
        super(context);
        this.step = giphyStep;
        this.jsonParser = new JSONParser();
    }

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
        return handleGiphySearchResponse(json);
    }

    private void validateValues() {
        if (StringUtils.isEmpty(step.getKeyword())) {
            throw new IllegalArgumentException("you must provide keyword");
        }
        if (StringUtils.isEmpty(step.getCredentialsId())) {
            throw new IllegalArgumentException("you must provide credential id");
        }
        if (StringUtils.isEmpty(step.getRating())) {
            step.setRating(DEFAULT_RATING);
        }
    }

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

    private URI getGiphyUri(String apiKey) throws URISyntaxException {
        URIBuilder uriBuilder = new URIBuilder();
        uriBuilder.setScheme(getScheme());
        uriBuilder.setHost(getGiphyHost());
        uriBuilder.setPath("/v1/gifs/search");
        uriBuilder.setParameter("api_key", apiKey);
        uriBuilder.setParameter("q", step.getKeyword());
        uriBuilder.setParameter("rating", step.getRating());
        return uriBuilder.build();
    }

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
     * method that responsible to what should we do with the response from giphy
     *
     * @param json the response from giphy with JSON format
     * @return the return value of the execution
     */
    protected abstract T handleGiphySearchResponse(JSONObject json);


    private StringCredentials lookupCredentials(String credentialId) {
        List<StringCredentials> credentials =
                com.cloudbees.plugins.credentials.CredentialsProvider.lookupCredentials(
                        StringCredentials.class,
                        Jenkins.get(),
                        ACL.SYSTEM,
                        Collections.emptyList());
        CredentialsMatcher matcher = CredentialsMatchers.withId(credentialId);
        return CredentialsMatchers.firstOrNull(credentials, matcher);
    }
}
