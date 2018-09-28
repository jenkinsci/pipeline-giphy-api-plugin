package aviadlevy.jenkins.plugin.giphy.translate.impl;

import aviadlevy.jenkins.plugin.giphy.translate.GiphyTranslateStep;
import aviadlevy.jenkins.plugin.giphy.translate.GiphyTranslateStepExecution;
import org.jenkinsci.plugins.workflow.steps.StepContext;
import org.json.simple.JSONObject;

/**
 * @author aviadlevy
 */
public class GiphyTranslateStepExecutionImpl extends GiphyTranslateStepExecution<String> {
    /**
     * Constructor for StepExecution
     *
     * @param giphyStep the step running
     * @param context   step context
     */
    public GiphyTranslateStepExecutionImpl(GiphyTranslateStep giphyStep,
                                           StepContext context) {
        super(giphyStep, context);
    }

    @Override
    protected String handleGiphyResponse(GiphyTranslateStep step, JSONObject json) {
        return (String) ((JSONObject) ((JSONObject) ((JSONObject) json.get("data"))
                .get("images"))
                .get(step.getImageSize()))
                .get("url");
    }
}
