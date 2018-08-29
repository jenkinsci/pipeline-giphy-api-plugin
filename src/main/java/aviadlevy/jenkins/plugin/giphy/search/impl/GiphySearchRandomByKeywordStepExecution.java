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

    public GiphySearchRandomByKeywordStepExecution(GiphySearchStep giphyStep, StepContext context) {
        super(giphyStep, context);
    }

    @Override
    protected String handleGiphySearchResponse(JSONObject json) {
        JSONArray data = (JSONArray) json.get("data");
        int index = ThreadLocalRandom.current()
                                     .nextInt(data.size() + 1);
        String imageUrl =
                (String) ((JSONObject) ((JSONObject) ((JSONObject) data.get(index)).get("images")).get(
                        "downsized_medium")).get("url");
        return imageUrl;
    }
}
