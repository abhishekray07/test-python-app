#!groovy

def projects = ['app1/Jenkinsfile']

// This shows a simple example of how to archive the build output artifacts.
node {

    // checkout git repo
    git url: 'https://github.com/abhishekray07/test-python-app.git/'

    // run the steps for each of the sub-projects
    def rootDir = pwd()

    for (int i=0; i < projects.size(); i++) {
        def projectFile = load "${rootDir}/${projects[i]}"
        projectFile.runFile()
    }

}
