package aviadlevy.jenkins.plugin.giphy.search;

import org.jenkinsci.plugins.workflow.steps.Step;
import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.DataBoundSetter;

/**
 * @author aviadlevy
 */
public abstract class GiphySearchStep extends Step {

    private String credentialsId;
    private String keyword;
    private String rating;

    public GiphySearchStep() {
    }

    public String getCredentialsId() {
        return credentialsId;
    }

    @DataBoundSetter
    public void setCredentialsId(String credentialsId) {
        this.credentialsId = credentialsId;
    }

    public String getKeyword() {
        return keyword;
    }

    @DataBoundSetter
    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getRating() {
        return rating;
    }

    @DataBoundSetter
    public void setRating(String rating) {
        this.rating = rating;
    }


}
