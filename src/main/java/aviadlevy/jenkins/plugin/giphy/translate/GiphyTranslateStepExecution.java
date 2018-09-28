package aviadlevy.jenkins.plugin.giphy.translate;

import aviadlevy.jenkins.plugin.giphy.GiphyStepExecution;
import org.apache.commons.lang.StringUtils;
import org.apache.http.client.utils.URIBuilder;
import org.jenkinsci.plugins.workflow.steps.StepContext;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * @author aviadlevy
 */
public abstract class GiphyTranslateStepExecution<T> extends GiphyStepExecution<T, GiphyTranslateStep> {
    /**
     * Constructor for StepExecution
     *
     * @param giphyStep the step running
     * @param context   step context
     */
    public GiphyTranslateStepExecution(GiphyTranslateStep giphyStep, StepContext context) {
        super(giphyStep, context);
    }

    @Override
    protected void validateSpecificStepValues() {
        if (StringUtils.isEmpty(step.getKeyword())) {
            throw new IllegalArgumentException("you must provide keyword");
        }
    }

    @Override
    protected URI getGiphyUri(String apiKey) throws URISyntaxException {
        URIBuilder uriBuilder = new URIBuilder();
        uriBuilder.setScheme(getScheme());
        uriBuilder.setHost(getGiphyHost());
        uriBuilder.setPath("/v1/gifs/random");
        uriBuilder.setParameter("api_key", apiKey);
        uriBuilder.setParameter("q", step.getKeyword());
        return uriBuilder.build();
    }
}
