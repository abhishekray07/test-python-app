pipeline {
    agent any
    stages {
         stage('Build & Deploy') {
             steps {
                sh '''#!/bin/bash
                DOCKER_LOGIN=`aws ecr get-login --no-include-email --region us-east-1`
                ${DOCKER_LOGIN}
				echo "Docker Login"
                '''
             }
         }
    }
}
