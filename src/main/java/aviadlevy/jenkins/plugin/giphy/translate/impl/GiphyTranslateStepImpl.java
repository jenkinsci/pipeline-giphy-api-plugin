package aviadlevy.jenkins.plugin.giphy.translate.impl;

import aviadlevy.jenkins.plugin.giphy.translate.GiphyTranslateStep;
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
public class GiphyTranslateStepImpl extends GiphyTranslateStep implements Serializable {

    @DataBoundConstructor
    public GiphyTranslateStepImpl() {
        super();
    }

    @Override
    public StepExecution start(StepContext context) throws Exception {
        return new GiphyTranslateStepExecutionImpl(this, context);
    }

    @Extension
    public static class DescriptorImpl extends StepDescriptor {

        @Override
        public Set<? extends Class<?>> getRequiredContext() {
            return Collections.singleton(TaskListener.class);
        }

        @Override
        public String getFunctionName() {
            return "giphyTranslate";
        }
    }
}
