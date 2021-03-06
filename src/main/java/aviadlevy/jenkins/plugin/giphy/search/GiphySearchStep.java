package aviadlevy.jenkins.plugin.giphy.search;

import aviadlevy.jenkins.plugin.giphy.GiphyStep;
import org.kohsuke.stapler.DataBoundSetter;

/**
 * @author aviadlevy
 */
public abstract class GiphySearchStep extends GiphyStep {

    private String keyword;

    /**
     * Default constructor
     */
    public GiphySearchStep() {
    }


    public String getKeyword() {
        return keyword;
    }

    @DataBoundSetter
    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }
}
