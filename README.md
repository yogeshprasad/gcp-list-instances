# gcp-list-instances
This project help in listing the GCP instances

## Prepare Environment 
- Export GOOGLE_APPLICATION_CREDENTIALS and provide GCP credentials JSON file path like below
```export GOOGLE_APPLICATION_CREDENTIALS=/Users/ykurmi/system/work-notes/gcp-key.json```
- Export GCP_PROJECT_ID and provide GCP project ID like below ```export GCP_PROJECT_ID=gcp-dev-project```
- Clone this project and build using mvn like below ```mvn clean install```

## Run Project
Start the server like below
```java -jar target/example.smallest-0.0.1-SNAPSHOT.war```

once this start you can hit an url 
```http://localhost:8080/```

Check the logs for the list of instances it will get listed like below
```aidl
=================================================================================
Instance Name: gke-skajagar-k8scluster-default-pool-eb78586
Instance Labels: {goog-gke-node=}
=================================================================================
Instance Name: k8po-kind-stable-env-vm
Instance Labels: null
=================================================================================
Instance Name: skajagar-delete
Instance Labels: {skajagar=test-labels}
=================================================================================
```