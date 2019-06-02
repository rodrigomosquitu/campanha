pipeline {
    agent { docker { image 'maven:3.3.3' } }

    stages {
        stage('build') {
            steps {
                sh 'mvn -B -DskipTests clean package'
            }
        }
        stage('Test') {
            steps {
                sh 'mvn test'
            }
        }
        stage('Deploy') {
            steps {
                echo 'Deploying....'
            }
        }
        stage('Deliver') { 
            steps {
                sh './deliver.sh' 
            }
        }
    }
}
