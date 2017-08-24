#!groovy

// This shows a simple example of how to archive the build output artifacts.
node {
    git url: 'https://github.com/abhishekray07/test-python-app.git/'
    def rootDir = pwd()
    def example = load "${rootDir}/example.Groovy"
    example.exampleMethod()
}
