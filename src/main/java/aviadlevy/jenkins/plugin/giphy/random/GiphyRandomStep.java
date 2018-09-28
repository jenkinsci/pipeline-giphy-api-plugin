package aviadlevy.jenkins.plugin.giphy.random;

import aviadlevy.jenkins.plugin.giphy.GiphyStep;
import org.kohsuke.stapler.DataBoundSetter;

/**
 * @author aviadlevy
 */
public abstract class GiphyRandomStep extends GiphyStep {

    private String tag;

    /**
     * Default constructor
     */
    public GiphyRandomStep() {
    }


    public String getTag() {
        return tag;
    }

    @DataBoundSetter
    public void setTag(String tag) {
        this.tag = tag;
    }
}
