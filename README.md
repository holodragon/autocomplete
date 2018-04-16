# Autocomplete Example (SpringBoot, OpenShift)

The root of repository is a standard Maven project, which can be compiled, run, and deploy as a normal SpringBoot application.

The openshift directory inclucde all deployment resource to deploy the application to OpenShift using S2I (Source-to-Image) builder images.

## Compile, Run, Test the application locally

execute following command locally
#### run application locally
```
mvn spring-boot:run
```

#### verify the application using browser
```
http://localhost:8080/
```
![alt text](https://github.com/holodragon/autocomplete/images/img1-1.png "running locally")

#### load data into local Map
```
http://localhost:8080/load/
```
![alt text](https://github.com/holodragon/autocomplete/images/img1-2.png "load data into local map")

#### check the autocomplete function

![alt text](https://github.com/holodragon/autocomplete/images/img1-3.png "autocomplete works")

## Deploy the application to OpenShift
#### login and create a project to OpenShift cluster.
```
oc login https://192.168.64.4:8443    #your openshift console url
oc new-project demoprj  --display-name="Demo Project"    #you may change the name as you want
```

#### import S2I template to the project.
```
oc create -f openshift/image-streams/cicd-demo-image-streams.json    #import required image stream in case missing
oc create -f openshift/templates/openjdk-redis-template.yaml
```
#### deploy the application on Openshift console.

1. Select the template named **"Red Hat OpenJDK 8 S2I, Redis (Ephemeral)"**

![alt text](https://github.com/holodragon/autocomplete/images/img2-1.png "select template")

2. Check the environment variables, modify the **Maven mirror path** and **application name** accordingly

![alt text](https://github.com/holodragon/autocomplete/images/img2-2.png "review envs")

3. depending on network bandwidth, the building time may be different

![alt text](https://github.com/holodragon/autocomplete/images/img2-3.png "building app")

4. after the build completed, the image will be auto-deployed and we can verify the app through auto-created route URL.

![alt text](https://github.com/holodragon/autocomplete/images/img2-4.png "app deployed")

5. similar running the app locally, data can be loaded into data store through uri **/load/**.

![alt text](https://github.com/holodragon/autocomplete/images/img2-5.png "load data into redis")

6. However, this time instead of import data to java map, the data will be loaded into redis database.

![alt text](https://github.com/holodragon/autocomplete/images/img2-6.png "verify data in redis")

![alt text](https://github.com/holodragon/autocomplete/images/img2-7.png "verify app function")



## Deploy CI/CD tools for the application on OpenShift

#### deploy Redis using template on both $PROJECT_DEV $PROJECT_STAGE Project, with variables: Host, Port, ID, Password

#### deploy pipeline using template named cicd in $PROJECT_CICD project

#### deploy pipeline using template named cicd in $PROJECT_CICD project



#### prepare for deploying on OpenShift

```
./openshift/script/provision-demo.sh
```


## Clean up

you can clean up all the demo resource created on your openshift cluster by following steps.
```
oc login https://192.168.64.4:8443  (your openshift console url)
oc delete project $PROJECT_CICD
oc delete project $PROJECT_DEV
oc delete project $PROJECT_STAGE
oc delete project $PROJECT_PROD
```