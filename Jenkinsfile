#!groovy

def list = ['app1/Jenkinsfile']

// This shows a simple example of how to archive the build output artifacts.
node {
    git url: 'https://github.com/abhishekray07/test-python-app.git/'
    def rootDir = pwd()

    for (String item : list) {
        def jenkinsfile = load "${rootDir}/${item}"
        jenkinsfile.run()
    }
}
