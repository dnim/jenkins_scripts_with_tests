// vars/deploy.groovy
// Example of a more complex Jenkins shared library script

def call(Map config = [:]) {
    def envName = config.get('env', env.ENV_NAME ?: 'dev')
    def shouldDeploy = config.get('deploy', true)
    def result = ''

    if (shouldDeploy) {
        echo "Deploying to ${envName}..."
        result = sh(script: "echo Deploying to ${envName}", returnStdout: true).trim()
        notify("Deployment to ${envName} started.")
    } else {
        echo "Skipping deployment for ${envName}."
        result = 'Skipped'
    }
    return result
}

// Helper function usage
def notify(String message) {
    hello(message)
}
