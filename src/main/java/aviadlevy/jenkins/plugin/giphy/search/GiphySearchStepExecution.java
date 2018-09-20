package aviadlevy.jenkins.plugin.giphy.search;

import aviadlevy.jenkins.plugin.giphy.GiphyStepExecution;
import org.apache.commons.lang.StringUtils;
import org.apache.http.client.utils.URIBuilder;
import org.jenkinsci.plugins.workflow.steps.StepContext;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * @author aviadlevy
 */
public abstract class GiphySearchStepExecution<T> extends GiphyStepExecution<T, GiphySearchStep> {
    private static final String DEFAULT_RATING = "g";

    /**
     * Constructor for StepExecution
     *
     * @param giphyStep the step running
     * @param context step context
     */
    public GiphySearchStepExecution(GiphySearchStep giphyStep, StepContext context) {
        super(giphyStep, context);
    }

    /**
     * validate the required values exists, and set default value to optional in case they don't exists
     */
    protected void validateSpecificStepValues() {
        if (StringUtils.isEmpty(step.getKeyword())) {
            throw new IllegalArgumentException("you must provide keyword");
        }
        if (StringUtils.isEmpty(step.getRating())) {
            step.setRating(DEFAULT_RATING);
        }
    }

    /**
     * build the giphy uri
     *
     * @param apiKey the apikey to giphy
     * @return the uri to execute
     * @throws URISyntaxException in case the uri is not valid
     */
    protected URI getGiphyUri(String apiKey) throws URISyntaxException {
        URIBuilder uriBuilder = new URIBuilder();
        uriBuilder.setScheme(getScheme());
        uriBuilder.setHost(getGiphyHost());
        uriBuilder.setPath("/v1/gifs/search");
        uriBuilder.setParameter("api_key", apiKey);
        uriBuilder.setParameter("q", step.getKeyword());
        uriBuilder.setParameter("rating", step.getRating());
        return uriBuilder.build();
    }
}
