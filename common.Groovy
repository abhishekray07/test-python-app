#!groovy

// We define commonly used steps in this file which can be re-used

// bucket where we store all builds on S3
def S3_BUILD_BUCKET = "qdeploy"


def sparseGitCheckout(String gitRepo, String checkoutPath) {
    stage('Setup Git Repo') {
        // do sparse checkout of ans repo
        checkout([$class: 'GitSCM', branches: [[name: '*/master']],
                  doGenerateSubmoduleConfigurations: false,
                  extensions: [[$class: 'SparseCheckoutPaths',sparseCheckoutPaths: [[path: "${checkoutPath}"]]]], submoduleCfg: [],
                  userRemoteConfigs: [[url: "${gitRepo}"]]])
    }
}


def pushToS3(String filePath, String destFilename) {

    stage('Deploying to S3') {
        // push to s3
        withAWS(region: 'us-east-1') {
            s3Upload bucket: "${S3_BUILD_BUCKET}", file: "${filePath}", path: "applications/${env.JOB_NAME}/${env.BUILD_NUMBER}/${destPath}"
        }
    }
}


def buildMavenProject(String applicationPath) {
    stage('Build') {
        sh "mvn -f ${applicationPath}/pom.xml clean install"
    }
}

def deployToMarathon(String filePath, String uri) {
    def marathonUrl = 'http://dwhmaster7494979740769402770:8080'

    stage('Deploying to Marathon') {
        marathon credentialsId: '', docker: '', filename: "${filePath}", id: '', uris: [[uri: "${uri}"]], url: "${marathonUrl}"
    }
}

// return the URI for marathon
def getMarathonURI(String applicationPath) {
    return "s3a://${S3_BUILD_BUCKET}/applications/${env.JOB_NAME}/${env.BUILD_NUMBER}/${applicationPath}"
}

return this