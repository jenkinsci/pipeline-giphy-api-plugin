package aviadlevy.jenkins.plugin.giphy.random.impl;

import aviadlevy.jenkins.plugin.giphy.random.GiphyRandomStep;
import aviadlevy.jenkins.plugin.giphy.random.GiphyRandomStepExecution;
import org.jenkinsci.plugins.workflow.steps.StepContext;
import org.json.simple.JSONObject;

/**
 * @author aviadlevy
 */
public class GiphyRandomStepExecutionImpl extends GiphyRandomStepExecution<String> {
    /**
     * Constructor for StepExecution
     *
     * @param giphyStep the step running
     * @param context   step context
     */
    public GiphyRandomStepExecutionImpl(GiphyRandomStep giphyStep,
                                        StepContext context) {
        super(giphyStep, context);
    }

    @Override
    protected String handleGiphyResponse(GiphyRandomStep step, JSONObject json) {
        return (String) ((JSONObject) ((JSONObject) ((JSONObject) json.get("data"))
                .get("images"))
                .get(step.getImageSize()))
                .get("url");
    }
}
