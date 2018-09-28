package aviadlevy.jenkins.plugin.giphy.translate;

import aviadlevy.jenkins.plugin.giphy.GiphyStep;
import org.kohsuke.stapler.DataBoundSetter;

/**
 * @author aviadlevy
 */
public abstract class GiphyTranslateStep extends GiphyStep {

    private String keyword;

    /**
     * Default constructor
     */
    public GiphyTranslateStep() {
    }


    public String getKeyword() {
        return keyword;
    }

    @DataBoundSetter
    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }
}
