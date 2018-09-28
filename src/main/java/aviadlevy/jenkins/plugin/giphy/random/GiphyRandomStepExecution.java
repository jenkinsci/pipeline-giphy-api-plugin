package aviadlevy.jenkins.plugin.giphy.random;

import aviadlevy.jenkins.plugin.giphy.GiphyStepExecution;
import org.apache.commons.lang.StringUtils;
import org.apache.http.client.utils.URIBuilder;
import org.jenkinsci.plugins.workflow.steps.StepContext;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * @author aviadlevy
 */
public abstract class GiphyRandomStepExecution<T> extends GiphyStepExecution<T, GiphyRandomStep> {
    /**
     * Constructor for StepExecution
     *
     * @param giphyStep the step running
     * @param context   step context
     */
    public GiphyRandomStepExecution(GiphyRandomStep giphyStep, StepContext context) {
        super(giphyStep, context);
    }

    @Override
    protected void validateSpecificStepValues() {
        if (StringUtils.isEmpty(step.getTag())) {
            throw new IllegalArgumentException("you must provide tag");
        }
    }

    @Override
    protected URI getGiphyUri(String apiKey) throws URISyntaxException {
        URIBuilder uriBuilder = new URIBuilder();
        uriBuilder.setScheme(getScheme());
        uriBuilder.setHost(getGiphyHost());
        uriBuilder.setPath("/v1/gifs/random");
        uriBuilder.setParameter("api_key", apiKey);
        uriBuilder.setParameter("tag", step.getTag());
        uriBuilder.setParameter("rating", step.getRating());
        return uriBuilder.build();
    }
}
