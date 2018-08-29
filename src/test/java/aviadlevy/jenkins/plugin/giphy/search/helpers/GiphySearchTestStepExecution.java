package aviadlevy.jenkins.plugin.giphy.search.helpers;

import aviadlevy.jenkins.plugin.giphy.search.GiphySearchStep;
import aviadlevy.jenkins.plugin.giphy.search.GiphySearchStepExecution;
import org.jenkinsci.plugins.workflow.steps.StepContext;
import org.json.simple.JSONObject;

/**
 * @author aviadlevy
 */
public class GiphySearchTestStepExecution extends GiphySearchStepExecution<String> {

    public static final int MOCK_SERVER_PORT = 1080;

    public GiphySearchTestStepExecution(GiphySearchStep giphyStep,
                                        StepContext context) {
        super(giphyStep, context);
    }

    @Override
    protected String getScheme() {
        return "http";
    }

    @Override
    protected String getGiphyHost() {
        return "localhost:" + MOCK_SERVER_PORT;
    }

    @Override
    protected String handleGiphySearchResponse(JSONObject json) {
        return (String) json.get("message");
    }
}
