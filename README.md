# Autocomplete Example (SpringBoot, OpenShift)

The root of repository is a standard Maven project, which can be compiled, run, and deploy as a normal SpringBoot application.

The openshift directory include all deployment resource to deploy the application to OpenShift using S2I (Source-to-Image) builder images.

## Compile, Run, Test the application locally

execute following command locally
#### run application locally
the default profile leverage local HashMap as implementation of index store.
```
mvn spring-boot:run
```

#### verify the application using browser
```
http://localhost:8080/
```
![alt text](https://github.com/holodragon/autocomplete/raw/master/images/img1-1.png "running locally")

#### load data into local Map
```
http://localhost:8080/load/
```
![alt text](https://github.com/holodragon/autocomplete/raw/master/images/img1-2.png "load data into local map")

#### check the autocomplete function

![alt text](https://github.com/holodragon/autocomplete/raw/master/images/img1-3.png "autocomplete works")

### Problems
1. developer has to manage IP, Port when running application locally. if there are more than one app running at the same time, they need to be manage possible IP & Port conflict.
2. not "production-like" environment, which may include like OS dependencies, databases, external system dependencies, etc.


## Run the application locally with Docker
Here, we are going to use Redis as an external index store. The implementation of Redis cache store is in **cloud** profile.

The **cloud** profile, extract redis host, port, and password to system vairables. So we need to declare the vairables before we run the app.

```
## get and run Redis container
docker run -p 6379:6379 -d redis

## check Redis service
docker ps
docker exec -i -t {container id} /bin/bash
/>redis-cli
/>auth {password}
/>keys *

## declare required variables
export REDIS_HOST=127.0.0.1
export REDIS_PORT=6379
export REDIS_PASSWORD=

## run the app with cloud profile
mvn spring-boot:run -Dspring.profiles.active=cloud
```

#### verify the app which load index to redis.

#### clean up
you can clean up the environment by following commands:
```
docker kill {container id}
docker rm {container id}
```

### Solutions
1. developer has to manage IP, Port when running application locally. if there are more than one app running at the same time, they need to be manage possible IP & Port conflict.
* docker provide relatively easy way to manage port mapping for running multiple application on the same host at the same time.

2. not "production-like" environment, which may include like OS dependencies, databases, external system dependencies, etc.
* docker provide an easy way to run application/database whith installation or complicated configuration.


### Problems
1. if developer want to deliver the whole running environment to other people or teams(ex: Ops team), there are more than just docker images.
2. for more complicate network requirement, more manual tasks need to be done. ex: running a cluster environment for an application.
3. the image may not comply company standard or possess potential security risk which is usually not aware by developers. for this example, such as JDK, Redis, OS, etc.


## Deploy and run on OpenShift
#### login and create a project to OpenShift cluster.
```
oc login https://192.168.64.4:8443    ## your openshift console url
oc new-project demoprj  --display-name="Demo Project"    ## you may change the name as required
```

#### import S2I template to the project.
```
oc create -f openshift/image-streams/cicd-demo-image-streams.json    #import required image stream in case missing
oc create -f openshift/templates/openjdk-redis-template.yaml
```
#### deploy the application on Openshift console.

1. Select the template named **"Red Hat OpenJDK 8 S2I, Redis (Ephemeral)"**

![alt text](https://github.com/holodragon/autocomplete/raw/master/images/img2-1.png "select template")

2. Check the environment variables, modify the **Maven mirror path** and **application name** accordingly

![alt text](https://github.com/holodragon/autocomplete/raw/master/images/img2-2.png "review envs")

3. depending on network bandwidth, the building time may be different

![alt text](https://github.com/holodragon/autocomplete/raw/master/images/img2-3.png "building app")

4. after the build completed, the image will be auto-deployed and we can verify the app through auto-created route URL.

![alt text](https://github.com/holodragon/autocomplete/raw/master/images/img2-4.png "app deployed")

5. similar running the app locally, data can be loaded into data store through uri **/load/**.

![alt text](https://github.com/holodragon/autocomplete/raw/master/images/img2-5.png "load data into redis")

6. However, this time instead of import data to java map, the data will be loaded into redis database.

![alt text](https://github.com/holodragon/autocomplete/raw/master/images/img2-6.png "verify data in redis")

![alt text](https://github.com/holodragon/autocomplete/raw/master/images/img2-7.png "verify app function")

### Clean up

you can clean up resources created on your openshift cluster by following commends.
```
oc login https://192.168.64.4:8443  (your openshift console url)
oc delete project demoprj
```

### Solutions
1. if developer want to deliver the whole running environment to other people or teams(ex: Ops team), there are more than just docker images.
* the template pre-built on OpenShift helps to concrete the deliverable of the app with its dependencies.
2. for more complicate network requirement, more manual tasks need to be done. ex: running a cluster environment for an application.
* OpenShift can expose application easily and provide accessibility to developer, even for complicate network requirements like clustering configuration.
3. the image may not comply company standard or possess potential security risk which is usually not aware by developers. for this example, such as JDK, Redis, OS, etc.
* The service catalog on OpenShift provide a standard portal for dev and QA to acquire certified, secured, efficient environment with auto-build & deploy capability.

## Deploy CI/CD tools for the application on OpenShift

```
./openshift/script/provision-demo.sh
```

### Clean up

you can clean up all the demo resource created on your openshift cluster by following commends.
```
oc login https://192.168.64.4:8443  (your openshift console url)
oc delete project $PROJECT_CICD
oc delete project $PROJECT_DEV
oc delete project $PROJECT_STAGE
oc delete project $PROJECT_PROD
```