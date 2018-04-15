# Autocomplete Example (SpringBoot, OpenShift)

The root of repository is a standard Maven project, which can be compiled, run, and deploy as a normal SpringBoot application.

The openshift directory inclucde all deployment resource to deploy the application to OpenShift using S2I (Source-to-Image) builder images.

## compile, run, test the application locally

execute following command locally
### run the application
```
mvn spring-boot:run
```

### prepare for deploying on OpenShift

```
./openshift/script/provision-demo.sh
```
