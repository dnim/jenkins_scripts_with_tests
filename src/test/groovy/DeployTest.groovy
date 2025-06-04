// src/test/groovy/DeployTest.groovy
import com.lesfurets.jenkins.unit.BasePipelineTest
import org.junit.Before
import org.junit.Test

class DeployTest extends BasePipelineTest {
    def script

    @Before
    void setUp() {
        super.setUp()
        // Load the deploy.groovy script from vars
        script = loadScript('vars/deploy.groovy')
        // Mock hello, greet, and sh
        helper.registerAllowedMethod('hello', [String]) { msg ->
            // Use println instead of binding.echo to avoid MissingMethodException
            println "hello: ${msg}"
        }
        helper.registerAllowedMethod('greet', [String]) { name ->
            return "Hello, ${name}!"
        }
        helper.registerAllowedMethod('sh', [Map]) { Map args ->
            return "Simulated shell: ${args.script}"
        }
        helper.registerAllowedMethod('echo', [String]) { msg -> }
        binding.setVariable('env', [:])
    }

    @Test
    void testDeployDefault() {
        binding.env = [:] // Ensure env is always present
        def result = script.call([:])
        assert result == 'Simulated shell: echo Deploying to dev'
        assert helper.callStack.findAll { it.methodName == 'echo' }
    }

    @Test
    void testDeployWithEnv() {
        binding.env = [ENV_NAME: 'prod'] // Set env as property, not variable
        def result = script.call([:])
        assert result == 'Simulated shell: echo Deploying to prod'
    }

    @Test
    void testSkipDeploy() {
        def result = script.call([deploy: false])
        assert result == 'Skipped'
    }
}
