pipeline {
  environment {
    registry = "smitlimbani/neurolabb"
    registryCredential = 'db-new-cred'
    dockerImage = ''
    dockerImageLatest = ''
  }
  agent any
  stages {
    stage('Cloning Git Backend') {
      steps {
        git 'https://github.com/smitlimbani/neuroLab.git'
      }
    }
    stage('Build Executable Jar'){
        steps {
             sh 'mvn clean test package'
        }
    }

    stage('Building image') {
       steps{
         script {
           dockerImage = docker.build registry + ":$BUILD_NUMBER"
           dockerImageLatest = docker.build registry + ":latest"
         }
       }
    }
    stage('Deploy Image') {
      steps{
        script {
          docker.withRegistry( '', registryCredential ) {
            dockerImage.push()
            dockerImageLatest.push()
          }
        }
      }
    }
    stage('Remove Unused docker image') {
      steps{
        sh "docker rmi $registry:$BUILD_NUMBER"
      }
    }
    stage('Execute Rundeck job') {
        steps {
          script {
            step([$class: "RundeckNotifier",
                  includeRundeckLogs: true,
                  jobId: "43932d11-d343-49e9-b9bf-beb1c19c1ab2",
                  rundeckInstance: "Rundeck",
                  shouldFailTheBuild: true,
                  shouldWaitForRundeckJob: true,
                  tailLog: true])
          }
        }
    }
  }
}
