package aviadlevy.jenkins.plugin.giphy.random.impl;

import aviadlevy.jenkins.plugin.giphy.random.GiphyRandomStep;
import hudson.Extension;
import hudson.model.TaskListener;
import org.jenkinsci.plugins.workflow.steps.StepContext;
import org.jenkinsci.plugins.workflow.steps.StepDescriptor;
import org.jenkinsci.plugins.workflow.steps.StepExecution;
import org.kohsuke.stapler.DataBoundConstructor;

import java.io.Serializable;
import java.util.Collections;
import java.util.Set;

/**
 * @author aviadlevy
 */
public class GiphyRandomStepImpl extends GiphyRandomStep implements Serializable {

    @DataBoundConstructor
    public GiphyRandomStepImpl() {
        super();
    }

    @Override
    public StepExecution start(StepContext context) throws Exception {
        return new GiphyRandomStepExecutionImpl(this, context);
    }

    @Extension
    public static class DescriptorImpl extends StepDescriptor {

        @Override
        public Set<? extends Class<?>> getRequiredContext() {
            return Collections.singleton(TaskListener.class);
        }

        @Override
        public String getFunctionName() {
            return "giphyRandom";
        }
    }
}
