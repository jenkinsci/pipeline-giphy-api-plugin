package aviadlevy.jenkins.plugin.giphy.search;

import com.cloudbees.plugins.credentials.CredentialsProvider;
import com.cloudbees.plugins.credentials.CredentialsScope;
import com.cloudbees.plugins.credentials.CredentialsStore;
import com.cloudbees.plugins.credentials.SystemCredentialsProvider;
import com.cloudbees.plugins.credentials.domains.Domain;
import hudson.model.Job;
import hudson.model.Result;
import hudson.model.queue.QueueTaskFuture;
import hudson.util.Secret;
import jenkins.model.Jenkins;
import jenkins.model.ParameterizedJobMixIn;
import org.jenkinsci.plugins.plaincredentials.impl.StringCredentialsImpl;
import org.jenkinsci.plugins.workflow.cps.CpsFlowDefinition;
import org.jenkinsci.plugins.workflow.job.WorkflowJob;
import org.jenkinsci.plugins.workflow.job.WorkflowRun;
import org.junit.*;
import org.jvnet.hudson.test.JenkinsRule;
import org.mockserver.client.server.MockServerClient;
import org.mockserver.integration.ClientAndServer;
import org.mockserver.model.Header;

import java.io.IOException;
import java.util.List;

import static aviadlevy.jenkins.plugin.giphy.search.helpers.GiphySearchTestStepExecution.MOCK_SERVER_PORT;
import static org.mockserver.integration.ClientAndServer.startClientAndServer;
import static org.mockserver.matchers.Times.exactly;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;

/**
 * @author aviadlevy
 */
public class GiphyStepExecutionTest {

    private static final String MOCK_API_KEY = "testing-secret";
    private static final String KEYWORD = "test";
    private static final String RATING = "g";
    private static final String SUCCESS_RESPONSE = "success";
    private static final String MOCK_FORBIDDEN_API_KEY = "forbidden";
    private static ClientAndServer mockServer;
    @Rule
    public JenkinsRule j = new JenkinsRule();

    @BeforeClass
    public static void initServer() {
        mockServer = startClientAndServer(MOCK_SERVER_PORT);
    }

    @AfterClass
    public static void stopServer() {
        mockServer.stop();
    }

    @Before
    public void setup() throws IOException {
        for (CredentialsStore credentialsStore : CredentialsProvider.lookupStores(Jenkins.getInstance())) {
            if (credentialsStore instanceof SystemCredentialsProvider.StoreImpl) {
                List<Domain> domains = credentialsStore.getDomains();
                credentialsStore.addCredentials(domains.get(0),
                                                new StringCredentialsImpl(CredentialsScope.SYSTEM,
                                                                          "testing",
                                                                          "GitLab API Token",
                                                                          Secret.fromString(
                                                                                  MOCK_API_KEY)));
                credentialsStore.addCredentials(domains.get(0),
                                                new StringCredentialsImpl(CredentialsScope.SYSTEM,
                                                                          "forbidden",
                                                                          "GitLab API Token",
                                                                          Secret.fromString(
                                                                                  MOCK_FORBIDDEN_API_KEY)));
            }
        }
    }


    @Test
    public void successWorkflow() throws Exception {
        createResponse(MOCK_API_KEY, SUCCESS_RESPONSE, 200);
        WorkflowJob project = j.createProject(WorkflowJob.class);
        project.setDefinition(new CpsFlowDefinition(
                "def res = giphySearchTest(credentialsId: 'testing', keyword: '" + KEYWORD + "', " +
                        "rating: '" + RATING + "')\n" +
                        "echo res",
                true));
        WorkflowRun build = j.buildAndAssertSuccess(project);
        j.assertLogContains(SUCCESS_RESPONSE, build);
    }

    @Test
    public void forbiddenWorkflow() throws Exception {
        createResponse(MOCK_FORBIDDEN_API_KEY, "", 403);
        WorkflowJob project = j.createProject(WorkflowJob.class);
        project.setDefinition(new CpsFlowDefinition(
                "def res = giphySearchTest(credentialsId: 'forbidden', keyword: '" + KEYWORD + "', " +
                        "rating: '" + RATING + "')\n" +
                        "echo res",
                true));
        WorkflowRun build = buildAndAssertFailiure(j, project);
        j.assertLogContains("Your API key to giphy is invalid", build);
    }

    @Test
    public void tooManyRequestsWorkflow() throws Exception {
        createResponse(MOCK_API_KEY, "too many requests", 429);
        WorkflowJob project = j.createProject(WorkflowJob.class);
        project.setDefinition(new CpsFlowDefinition(
                "def res = giphySearchTest(credentialsId: 'testing', keyword: '" + KEYWORD + "', " +
                        "rating: '" + RATING + "')\n" +
                        "echo res",
                true));
        WorkflowRun build = buildAndAssertFailiure(j, project);
        j.assertLogContains("Too Many Requests", build);
    }

    @Test
    public void whenApiKeyIsEmpty_throwException() throws Exception {
        WorkflowJob project = j.createProject(WorkflowJob.class);
        project.setDefinition(new CpsFlowDefinition(
                "def res = giphySearchTest(keyword: '" + KEYWORD + "', " +
                        "rating: '" + RATING + "')\n" +
                        "echo res",
                true));
        WorkflowRun build = buildAndAssertFailiure(j, project);
        j.assertLogContains("you must provide credential id", build);
    }

    @Test
    public void whenKeywordIsEmpty_throwException() throws Exception {
        WorkflowJob project = j.createProject(WorkflowJob.class);
        project.setDefinition(new CpsFlowDefinition(
                "def res = giphySearchTest(credentialsId: 'testing', " +
                        "rating: '" + RATING + "')\n" +
                        "echo res",
                true));
        WorkflowRun build = buildAndAssertFailiure(j, project);
        j.assertLogContains("you must provide keyword", build);
    }

    @Test
    public void whenRatingIsEmpty_dontThrowException() throws Exception {
        createResponse(MOCK_API_KEY, SUCCESS_RESPONSE, 200);
        WorkflowJob project = j.createProject(WorkflowJob.class);
        project.setDefinition(new CpsFlowDefinition(
                "def res = giphySearchTest(credentialsId: 'testing', " +
                        "keyword: '" + KEYWORD + "')\n" +
                        "echo res",
                true));
        WorkflowRun build = j.buildAndAssertSuccess(project);
        j.assertLogContains(SUCCESS_RESPONSE, build);
    }

    @Test
    public void whenApiKeyIsNotInitializedOnJenkins_throwException() throws Exception {
        WorkflowJob project = j.createProject(WorkflowJob.class);
        project.setDefinition(new CpsFlowDefinition(
                "def res = giphySearchTest(credentialsId: 'not-initialize', keyword: '" + KEYWORD + "', " +
                        "rating: '" + RATING + "')\n" +
                        "echo res",
                true));
        WorkflowRun build = buildAndAssertFailiure(j, project);
        j.assertLogContains("you must provide initialized credential id", build);
    }

    private void createResponse(String mockForbiddenApiKey, String response, int statusCode) {
        new MockServerClient("localhost", MOCK_SERVER_PORT)
                .when(
                        request()
                                .withMethod("GET")
                                .withPath("/v1/gifs/search")
                                .withQueryStringParameter("api_key", mockForbiddenApiKey)
                                .withQueryStringParameter("q", KEYWORD)
                                .withQueryStringParameter("rating", RATING),
                        exactly(1))
                .respond(
                        response()
                                .withStatusCode(statusCode)
                                .withHeaders(
                                        new Header("Content-Type", "application/json;"))
                                .withBody("{ \"message\": \"" + response + "\" }")
                );
    }

    private WorkflowRun buildAndAssertFailiure(JenkinsRule j, WorkflowJob project) throws Exception {
        QueueTaskFuture f = new ParameterizedJobMixIn() {
            @Override protected Job asJob() {
                return project;
            }
        }.scheduleBuild2(0);
        return (WorkflowRun) j.assertBuildStatus(Result.FAILURE, f);
    }
}