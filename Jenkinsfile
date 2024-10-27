pipeline { 
agent any
	tools{
		maven 'maven'
	} 
	
    stages { 
        
        stage ('Build') { 
            
            steps
            {
                git 'https://github.com/jglick/simple-maven-project-with-tests.git'
                sh "mvn -Dmaven.test.failure.ignore=true clean package"
            }
            post
            {
				success{
					junit '**/target/surefire-reports/TEST-*.xml'
					archiveArtifacts 'target/*.jar'
				}	
			}
        }
        
       stage('Deploy to QA') {
            steps {
               	echo('deploy to QA') 
                }
       }
            
       stage('Regression Automation Tests'){
			steps{
				catchError(buildResult:'SUCCESS',stageResult: 'FAILURE'){
					git 'https://github.com/ayeshayusufgit/May2024POMSeries.git'
					sh "mvn clean test -Dsurefire.suiteXmlFiles=src/test/resources/testrunners/testng_regression.xml"
				}
			}
	   }        
                
        stage('Publish Allure Reports') {
           steps {
                script {
                    allure([
                        includeProperties: false,
                        jdk: '',
                        properties: [],
                        reportBuildPolicy: 'ALWAYS',
                        results: [[path: '/allure-results']]
                    ])
                }
            }
        }
        
        
       stage('Publish Extent Report'){
            steps{
                     publishHTML([allowMissing: false,
                                  alwaysLinkToLastBuild: false, 
                                  keepAll: false, 
                                  reportDir: 'build', 
                                  reportFiles: 'TestExecutionReport.html', 
                                  reportName: 'HTML Regression Extent Report', 
                                  reportTitles: ''])
            }
        }
        
       stage('Deploy to Stage') {
            steps {
               	echo('Deploy to Stage') 
          }
       }
       
       stage('Sanity Automation Tests'){
			steps{
				catchError(buildResult:'SUCCESS',stageResult: 'FAILURE'){
					git 'https://github.com/ayeshayusufgit/May2024POMSeries.git'
					sh "mvn clean test -Dsurefire.suiteXmlFiles=src/test/resources/testrunners/testng_sanity.xml"
				}
			}
	   }
	   
	   stage('Publish Sanity Extent Report'){
            steps{
                     publishHTML([allowMissing: false,
                                  alwaysLinkToLastBuild: false, 
                                  keepAll: false, 
                                  reportDir: 'build', 
                                  reportFiles: 'TestExecutionReport.html', 
                                  reportName: 'HTML Sanity Extent Report', 
                                  reportTitles: ''])
            }
        }
        stage('Deploy to PROD') {
            steps {
               	echo('Deploy to PROD') 
          }
       }
    }
 }