package aviadlevy.jenkins.plugin.giphy.search.impl;

import aviadlevy.jenkins.plugin.giphy.search.GiphySearchStep;
import aviadlevy.jenkins.plugin.giphy.search.GiphySearchStepExecution;
import org.jenkinsci.plugins.workflow.steps.StepContext;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.concurrent.ThreadLocalRandom;

/**
 * @author aviadlevy
 */
public class GiphySearchRandomByKeywordStepExecution extends GiphySearchStepExecution<String> {

    /**
     * Constructor for StepExecution
     *
     * @param giphyStep the step running
     * @param context step context
     */
    public GiphySearchRandomByKeywordStepExecution(GiphySearchStep giphyStep, StepContext context) {
        super(giphyStep, context);
    }

    /**
     * method that responsible to what should we do with the response from giphy
     *
     * @param step the step with all the values from the user
     * @param json the response from giphy with JSON format
     * @return the return value of the execution
     */
    @Override
    protected String handleGiphySearchResponse(GiphySearchStep step,
                                               JSONObject json) {
        JSONArray data = (JSONArray) json.get("data");
        int index = ThreadLocalRandom.current()
                                     .nextInt(data.size() + 1);
        String imageUrl =
                (String) ((JSONObject) ((JSONObject) ((JSONObject) data.get(index)).get("images")).get(
                        step.getImageSize())).get("url");
        return imageUrl;
    }
}
