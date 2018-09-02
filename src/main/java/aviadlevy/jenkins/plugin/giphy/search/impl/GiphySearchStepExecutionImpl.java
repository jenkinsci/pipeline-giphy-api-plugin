package aviadlevy.jenkins.plugin.giphy.search.impl;

import aviadlevy.jenkins.plugin.giphy.search.GiphySearchStep;
import aviadlevy.jenkins.plugin.giphy.search.GiphySearchStepExecution;
import org.jenkinsci.plugins.workflow.steps.StepContext;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author aviadlevy
 */
public class GiphySearchStepExecutionImpl extends GiphySearchStepExecution<List<String>> {
    /**
     * Constructor for StepExecution
     *
     * @param giphyStep the step running
     * @param context   step context
     */
    public GiphySearchStepExecutionImpl(GiphySearchStep giphyStep,
                                        StepContext context) {
        super(giphyStep, context);
    }

    @Override
    protected List<String> handleGiphySearchResponse(GiphySearchStep step, JSONObject json) {
        JSONArray data = (JSONArray) json.get("data");
        return (List<String>) data.stream()
                .map(d -> ((JSONObject) ((JSONObject) ((JSONObject) d)
                        .get("images"))
                        .get(step.getImageSize()))
                        .get("url"))
                .collect(Collectors.toList());
    }
}
