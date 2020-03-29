podTemplate(yaml: """
apiVersion: v1
kind: Pod
metadata:
  labels:
    jenkins/kube-default: true
    app: jenkins
    component: agent
spec:
  containers:
    - name: jnlp
      image: jenkinsci/jnlp-slave:latest
      resources:
        limits:
          cpu: 1
          memory: 2Gi
        requests:
          cpu: 1
          memory: 2Gi
      imagePullPolicy: Always
      env:
      - name: POD_IP
        valueFrom:
          fieldRef:
            fieldPath: status.podIP
      - name: DOCKER_HOST
        value: tcp://localhost:2375
    - name: docker
      image: docker:latest
      command:
      - cat
      tty: true
      volumeMounts:
      - mountPath: /var/run/docker.sock
        name: docker-sock
    - name: maven
      image: docker.maustyle.de/java-with-maven:8-jdk
      tty: true
      command:
      - cat
      env:
      - name: DOCKER_HOST
        value: tcp://localhost:2375
  volumes:
    - name: docker-sock
      hostPath:
         path: /var/run/docker.sock
"""
){ 
	node(POD_LABEL) {
	
			def serverVersion=''
            stage('Checkout'){
                checkout scm
            }
			
			container('maven'){
				stage('Get Versions'){
						serverVersion = sh(returnStdout: true, script: 'cd backend && mvn help:evaluate -Dexpression=project.version -q -DforceStdout').trim()
				}

        stage('Build'){
          sh 'docker ps && cd backend && mvn clean install'
        }
			}

      
			
			container('docker'){
				stage('Build Docker Image') {
                	    sh "cd backend && docker build --tag tommyelroy/coronatracker-server:$serverVersion --build-arg VERSION=$serverVersion ."	
				}
				if(env.BRANCH_NAME=='master'){
					stage('Push Docker Image'){
						withCredentials([string(credentialsId: 'dockerhub-tommyelroy-passwort', variable: 'password')]) {
										sh "docker login --username='tommyelroy' --password='$password' && docker push tommyelroy/coronatracker-server:$serverVersion"
						}		
					}
				}
			}
    }
}