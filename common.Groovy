#!groovy

// We define commonly used steps in this file which can be re-used

def sparseGitCheckout(String gitRepo, String checkoutPath) {
    stage('Setup Git Repo') {
        steps {
            // do sparse checkout of ans repo
            checkout([$class: 'GitSCM', branches: [[name: '*/master']],
                      doGenerateSubmoduleConfigurations: false,
                      extensions: [[$class: 'SparseCheckoutPaths',sparseCheckoutPaths: [[path: "${checkoutPath}"]]]], submoduleCfg: [],
                      userRemoteConfigs: [[url: "${gitRepo}"]]])
        }
    }
}


def pushToS3(String s3Bucket, String filePath, String destPath) {
    stage('Deploying to S3') {
        // push to s3
        withAWS(region: 'us-east-1') {
            s3Upload bucket: "${s3Bucket}", file: "${filePath}", path: "${destPath}"
        }
    }
}


def deployToMarathon(String filePath, String uri) {
    def marathonUrl = 'http://dwhmaster7494979740769402770:8080'

    stage('Deploying to Marathon') {
        marathon credentialsId: '', docker: '', filename: "${filePath}", id: '', uris: [[uri: "${uri}"]], url: "${marathonUrl}"
    }
}

return this