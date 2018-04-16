#!/bin/bash

# REDHAT_ACCOUNT_ID="a@redhat.com"
# REDHAT_ACCOUNT_PWD="12345678"
OPENSHIFT_URL="https://192.168.64.4:8443"
OPENSHIFT_TOKEN=$(oc whoami -t)
PROJECT_CICD="cicd"
PROJECT_DEV="dev"
PROJECT_STAGE="stage"
PROJECT_PROD="prod"


## login OpenShift
oc login --token=$OPENSHIFT_TOKEN

## create projects
oc new-project $PROJECT_CICD  --display-name="CI/CD TOOLS - $PROJECT_CICD project"
oc new-project $PROJECT_DEV   --display-name="CI/CD DEMO - $PROJECT_DEV project"
oc new-project $PROJECT_STAGE --display-name="CI/CD DEMO - $PROJECT_STAGE project"
oc new-project $PROJECT_PROD  --display-name="CI/CD DEMO - $PROJECT_PROD project"

## set privileges
oc policy add-role-to-user edit system:serviceaccount:$PROJECT_CICD:jenkins -n $PROJECT_DEV
oc policy add-role-to-user edit system:serviceaccount:$PROJECT_CICD:jenkins -n $PROJECT_STAGE
oc policy add-role-to-user edit system:serviceaccount:$PROJECT_CICD:jenkins -n $PROJECT_PROD

## import Image Stream & Template
oc create -f ../image-streams/cicd-demo-image-streams.json -n $PROJECT_CICD
oc create -f ../templates/openjdk-redis-template.yaml -n $PROJECT_CICD
oc create -f ../templates/openjdk-template.yaml -n $PROJECT_CICD
oc create -f ../templates/redis-template.yaml -n $PROJECT_CICD
oc create -f ../templates/pipeline-template.yaml -n $PROJECT_CICD
oc create -f ../templates/nexus3-template.yaml -n $PROJECT_CICD
oc create -f ../templates/sonarqube-template.yaml -n $PROJECT_CICD
oc create -f ../templates/pipeline-template.yml -n $PROJECT_CICD

#### provision whole CI/CD demo on UI



# docker login registry.connect.redhat.com --username $REDHAT_ACCOUNT_ID --password $REDHAT_ACCOUNT_PWD
# oc create secret generic redhat-connect --from-file=.dockerconfigjson=$HOME/.docker/config.json --type=kubernetes.io/dockerconfigjson
# oc secrets link default redhat-connect --for=pull

