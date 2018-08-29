package aviadlevy.jenkins.plugin.giphy.search.impl;

import aviadlevy.jenkins.plugin.giphy.search.GiphySearchStep;
import hudson.Extension;
import hudson.model.TaskListener;
import org.jenkinsci.plugins.workflow.steps.StepContext;
import org.jenkinsci.plugins.workflow.steps.StepDescriptor;
import org.jenkinsci.plugins.workflow.steps.StepExecution;
import org.kohsuke.stapler.DataBoundConstructor;

import java.util.Collections;
import java.util.Set;

import java.io.Serializable;

/**
 * @author aviadlevy
 */
public class GiphySearchRandomByKeywordStep extends GiphySearchStep implements Serializable {

    @DataBoundConstructor
    public GiphySearchRandomByKeywordStep() {
        super();
    }

    @Override
    public StepExecution start(StepContext stepContext) throws Exception {
        return new GiphySearchRandomByKeywordStepExecution(this, stepContext);
    }

    @Extension
    public static class DescriptorImpl extends StepDescriptor {

        @Override
        public Set<? extends Class<?>> getRequiredContext() {
            return Collections.singleton(TaskListener.class);
        }

        @Override
        public String getFunctionName() {
            return "giphyGetRandomByKeyword";
        }
    }
}
