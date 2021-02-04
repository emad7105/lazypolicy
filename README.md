# lazypolicy


## Protobuff
To generate the classes and be able to read and write `BooleanExpression.proto`, you need to run the protocol buffer compiler protoc on your `.proto`. A full tutorial is available [here](https://developers.google.com/protocol-buffers/docs/javatutorial). In brief:  

1. Download the compiler from [here](https://developers.google.com/protocol-buffers/docs/downloads).  
    On MacOS: `brew install protobuf`  
    From source code:  
    ```
    ./configure   
    make
    sudo make install
    protoc --version
   ```
2. A plugin has been added to the `pom.xml` file with a maven dependency in the `rego-java` module.
3. To generate, just run `mvn clean install` 


## Open Policy Agent
To use OPA in a docker container (more info [here](https://www.openpolicyagent.org/docs/latest/deployments/)), run:  
```
cd lazypolicy
docker pull openpolicyagent/opa:0.20.5

# start OPA
docker run -p 8181:8181 openpolicyagent/opa:0.20.5 run --server --log-level debug

# start OPA with a volume mount (policy)
docker run -v $PWD:/policies -p 8181:8181 openpolicyagent/opa:0.20.5 run --server --log-level debug --bundle /policies

# test the container
curl -i localhost:8181/

# evaluate/compile policies with partially with multiple unknowns
opa eval --input policies/hierarchical-resources/input.json --data policies/hierarchical-resources/tagged-policy.rego --unknowns data.accountStates --unknowns data.broker --format pretty --partial data.vfinance.allow 

```


## Apache Solr
This project supports converters for Apache Solr, namely converting OPA residual policies to Solr filter queries. For test purposes, you need to have an instance of Solr running. To do so, you need to run Solr in a Docker container:  
```
docker run -d -p 8983:8983 paulcwarren/solr
```