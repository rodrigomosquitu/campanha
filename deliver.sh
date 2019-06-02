bplist00�_WebMainResource�	
_WebResourceFrameName_WebResourceData_WebResourceMIMEType_WebResourceTextEncodingName^WebResourceURLPO�<html><head></head><body><pre style="word-wrap: break-word; white-space: pre-wrap;">#!/usr/bin/env bash

echo 'The following Maven command installs your Maven-built Java application'
echo 'into the local Maven repository, which will ultimately be stored in'
echo 'Jenkins''s local Maven repository (and the "maven-repository" Docker data'
echo 'volume).'
set -x
mvn jar:jar install:install help:evaluate -Dexpression=project.name
set +x

echo 'The following complex command extracts the value of the &lt;name/&gt; element'
echo 'within &lt;project/&gt; of your Java/Maven project''s "pom.xml" file.'
set -x
NAME=`mvn help:evaluate -Dexpression=project.name | grep "^[^\[]"`
set +x

echo 'The following complex command behaves similarly to the previous one but'
echo 'extracts the value of the &lt;version/&gt; element within &lt;project/&gt; instead.'
set -x
VERSION=`mvn help:evaluate -Dexpression=project.version | grep "^[^\[]"`
set +x

echo 'The following command runs and outputs the execution of your Java'
echo 'application (which Jenkins built using Maven) to the Jenkins UI.'
set -x
java -jar target/${NAME}-${VERSION}.jar
</pre></body></html>Ztext/plainUUTF-8_fhttps://raw.githubusercontent.com/jenkins-docs/simple-java-maven-app/master/jenkins/scripts/deliver.sh    ( ? Q g � � �$*                           �