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