package aviadlevy.jenkins.plugin.giphy;

import org.jenkinsci.plugins.workflow.steps.Step;
import org.kohsuke.stapler.DataBoundSetter;

/**
 * @author aviadlevy
 */
public abstract class GiphyStep extends Step {

    private String imageSize;
    private String credentialsId;
    private String rating;

    /**
     * Default constructor
     */
    public GiphyStep() {
    }

    public String getCredentialsId() {
        return credentialsId;
    }

    @DataBoundSetter
    public void setCredentialsId(String credentialsId) {
        this.credentialsId = credentialsId;
    }


    public String getImageSize() {
        return imageSize;
    }

    @DataBoundSetter
    public void setImageSize(String imageSize) {
        this.imageSize = imageSize;
    }

    public String getRating() {
        return rating;
    }

    @DataBoundSetter
    public void setRating(String rating) {
        this.rating = rating;
    }
}
