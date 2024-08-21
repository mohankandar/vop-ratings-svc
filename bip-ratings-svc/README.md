## What is this repository for?
This project is the service application module for the RatingsSvc project to expose a REST API, validate and process requests, and call external partners.

## About the service application
The service application consists of:
- The configuration elements to connect to Vault and Consul, boot strap Spring Boot, and configure the platform capabilities for the application.
- Exposure of an API with request validation and exception management.
- Service capabilities to process requests and acquire data from external partners.

The responsibilities and capabilities of the service application are :exclamation:add information here:exclamation:.

## How to add the dependency
The service application project is one of the sub-project `<modules>` in a reactor project.  Add the service project to the reactor POM.
```xml
<module>bip-ratings-svc</module>
```

## Config Values
The bootstrap.yml file contains modifiable config values for the application.
- `spring.cloud.consul.config.watch.enabled` can be set to true or false. When set to false it will no longer watch the consul config values and then refresh the application and corresponding value in the application to the new updated value in consul. When set to true, it will refresh and restart the application upon any K/V changes made in consul. When updated, it will display the following logs to identify the updated fields. 
```
    INFO o.s.boot.SpringApplication               : The following profiles are active: <active profiles>
    INFO o.s.boot.SpringApplication               : Started application in 0.237 seconds (JVM running for 181.985)
    INFO o.s.c.e.event.RefreshEventListener       : Refresh keys changed: [<application name>.<name of key>] 
``` 

## Diagrams

#### Class Diagram
:exclamation:add class diagrams to /docs/images, and add html img tags to refer to them:exclamation:

#### Sequence Diagrams

:exclamation:add sequence diagrams to /docs/images, and add html img tags to refer to them:exclamation:

# Performance Tests
Performance tests are executed to gauge if the application would be able to handle a reasonable request load.

The project uses Apache JMeter.

It is recommended that JMeter GUI mode be used for developing tests, and command mode (command-line execution) for test execution.

## Project Structure

`pom.xml` - The Maven configuration for building and deploying the project.

`src/test/jmeter` - Performance testing configurations (jmx files) go in this directory.

## Execution

Testing executes requests against the rest end points available in RatingsSvc Service.

Every request must contain a valid JWT header, so every test calls the `/token` end point to generate a JWT token for the user.

## Performance Test Configuration

The test suite can be configured to:
- execute each test a different number of times.
- execute each test with different number of threads.

Below is an example of typical configuration values. To override any of the properties, pass them on the command line as `-D` arguments, e.g. `-Ddomain=(env)`.

|Property|Description|Default Value|Perf Env Test Values|
|-|-|-|-|
|domain| RatingsSvc service Base Url|localhost| |
|port|RatingsSvc Service Port|8080|443 |
|protocol|RatingsSvc Service Protocol|http|https |
|Health.threadGroup.threads|Number of threads for Health Status|5|150|
|Health.threadGroup.rampUp|Thead ramp up|2|150|
|Health.threadGroup.loopCount|Number of executions|10|-1|
|Health.threadGroup.duration|Scheduler Duration in seconds|200|230|
|Health.threadGroup.startUpDelay|Delay to Start|5|30|
|SampleInfo.threadGroup.threads|Number of threads for PID based Sample Info|5|150|
|SampleInfo.threadGroup.rampUp|Thead ramp up|2|150|
|SampleInfo.threadGroup.loopCount|Number of executions|10|-1|
|SampleInfo.threadGroup.duration|Scheduler Duration in seconds|200|230|
|SampleInfo.threadGroup.startUpDelay|Delay to Start|2|30|
|SampleInfoNoRecordFound.threadGroup.threads|Number of threads PID based Sample Info with No Record Found PID|5|150|
|SampleInfoNoRecordFound.threadGroup.rampUp|Thead ramp up|2|150|
|SampleInfoNoRecordFound.threadGroup.loopCount|Number of executions |10|-1|
|SampleInfoNoRecordFound.threadGroup.duration|Scheduler Duration in seconds|200|230|
|SampleInfoNoRecordFound.threadGroup.startUpDelay|Delay to Start|2|30|
|SampleInfoInvalidPid.threadGroup.threads|Number of threads PID based Sample Info with Invalid PID|5|150|
|SampleInfoInvalidPid.threadGroup.rampUp|Thead ramp up|2|150|
|SampleInfoInvalidPid.threadGroup.loopCount|Number of executions |10|-1|
|SampleInfoInvalidPid.threadGroup.duration|Scheduler Duration in seconds|200|230|
|SampleInfoInvalidPid.threadGroup.startUpDelay|Delay to Start|2|30|
|SampleInfoNullPid.threadGroup.threads|Number of threads PID based Sample Info with null PID|5|150|
|SampleInfoNullPid.threadGroup.rampUp|Thead ramp up|2|150|
|SampleInfoNullPid.threadGroup.loopCount|Number of executions |10|-1|
|SampleInfoNullPid.threadGroup.duration|Scheduler Duration in seconds|200|230|
|SampleInfoNullPid.threadGroup.startUpDelay|Delay to Start|2|30|
|BearerTokenCreate.threadGroup.threads|Number of threads for Bearer Token Create/Generate|5|150|
|BearerTokenCreate.threadGroup.rampUp|Thead ramp up|1|50|
|BearerTokenCreate.threadGroup.loopCount|Number of executions |1|1|

## Running the tests

To execute performance tests locally, navigate to the `bip-ratings-svc-perftest` directory, and run
```bash
mvn clean verify -Pperftest
```
If you need to override any of the properties add the to the command using the appropriate `-Dpropety=value` argument(s).

#### Sample Command
An example for executing the test in performance test environment:

```bash
mvn clean verify -Pperftest -Dprotocol=<> -Ddomain=<> -Dport=<> -DBearerTokenCreate.threadGroup.threads=150 -DBearerTokenCreate.threadGroup.rampUp=50 -DBearerTokenCreate.threadGroup.loopCount=1 -DPersonHealth.threadGroup.threads=150 -DPersonHealth.threadGroup.rampUp=150 -DPersonHealth.threadGroup.loopCount=-1 -DPersonHealth.threadGroup.duration=230 -DPersonHealth.threadGroup.startUpDelay=30 -DPersonInfo.threadGroup.threads=150 -DPersonInfo.threadGroup.rampUp=150 -DPersonInfo.threadGroup.loopCount=-1 -DPersonInfo.threadGroup.duration=230 -DPersonInfo.threadGroup.startUpDelay=30 -DPersonInfoNoRecordFound.threadGroup.threads=150 -DPersonInfoNoRecordFound.threadGroup.rampUp=150 -DPersonInfoNoRecordFound.threadGroup.loopCount=-1 -DPersonInfoNoRecordFound.threadGroup.duration=230 -DPersonInfoNoRecordFound.threadGroup.startUpDelay=30 -DPersonInfoInvalidPid.threadGroup.threads=150 -DPersonInfoInvalidPid.threadGroup.rampUp=150 -DPersonInfoInvalidPid.threadGroup.loopCount=-1 -DPersonInfoInvalidPid.threadGroup.duration=230 -DPersonInfoInvalidPid.threadGroup.startUpDelay=30 -DPersonInfoNullPid.threadGroup.threads=150 -DPersonInfoNullPid.threadGroup.rampUp=150 -DPersonInfoNullPid.threadGroup.loopCount=-1 -DPersonInfoNullPid.threadGroup.duration=230 -DPersonInfoNullPid.threadGroup.startUpDelay=30
```

## How to set up JMeter and Create Test Plan (JMX)
For an example from the VOP Reference app, see [VOP Reference - Performance Testing Guide](https://github.ec.va.gov/EPMO/bip-reference-person/tree/master/bip-reference-perftest)

# Functional Tests
Functional tests are created to make sure the end points in ratingssvc are working as expected.

Project is built on Java and Maven, and uses the Sring RestTemplate jars for core API validations.

## Project Structure

`pom.xml` - The Maven configuration for building and deploying the project.

`src/inttest/resources/gov/va/ratingssvc/feature` - This is where you will create the cucumber feature files that contain the Feature
and Scenarios for the RatingsSvc service you are testing. As a good practice, it is suggested to group all the similar business functionality in one feature file.

`src/inttest/java/gov/va/bip/ratingssvc/service/steps` - The implementation steps related to the feature and scenarios mentioned in the cucumber file for the API needs to be created in this location.  In GenericSteps.java class Generic steps like validating the status code, setting header for the service are implemented here to avoid duplication of steps implementation.

`src/inttest/java/gov/va/bip/ratingssvc/service/runner` - Cucumber runner class that contains all feature file entries that needs to be executed at runtime. The annotations provided in the cucumber runner class will assist in bridging the features to step definitions.

`src/inttest/resources/request/dev` – This folder contains DEV request files if the body of your API call is static and needs to be sent as a XML/JSON file.

`src/inttest/resources/request/va` – This folder contains VA request files if the body of your API call is static and needs to be sent as a XML/JSON file.

`src/inttest/resources/response` – This folder contains response files that you may need to compare output, you can store the Response files in this folder. 

`src/test/resources/users/dev` - All the property files for DEV users should go under this folder.

`src/test/resources/users/va` - All the property files for VA users should go under this folder.

`src/inttest/resources/logback-test.xml` - Logback Console Appender pattern and loggers defined for this project.
Various packages and their corresponding log levels are specified here. By Default, the log level is WARN and it can be overridden by runtime environment variable. For e.g., -DLOG_LEVEL_VOP_FRAMEWORK_TEST=INFO

`src/inttest/resources/config/ratingssvc-inttest-dev.properties` – DEV configuration properties such as URL are specified here.

`src/inttest/resources/config/ratingssvc-inttest-stage.properties` – STAGE configuration properties such as URL are specified here.

**Note: All the configurations are defined external to the code and is per profile/environment. The naming convention of the file is vetservices-inttest-&lt;env>.properties**

## Execution

To execute the functional tests in local bip-ratings-svc the service needs to be up and running.

# How to Build and Test ratingssvc service
[Quick Start Guide](/docs/quick-start-guide.md)

**Command Line:** Execute the RatingsSvc service functional tests using the environment specific command below. 
```bash
Default Local: mvn verify -Pinttest -Dcucumber.options="--tags @DEV" 
```

```bash
DEV: mvn verify -Pinttest -Dtest.env=dev -Ddockerfile.skip=true -Dcucumber.options="--tags @DEV"
```


## More Details For Functional Test
For examples from the VOP Reference service, see [Integration Testing Guide](https://github.ec.va.gov/EPMO/bip-reference-person/tree/master/bip-reference-inttest)


## Enabling Kafka
The Archetype service has been configured to set utilize Confluent Kafka Services if desired. Currently, if you wish to use kafka services, you will need to communicate this desire out to the VOP Platform Team. We will assist in helping to set up your environment to fit your project. 

In the code, all configs related to the kafka services are the in application .yml in the resources directory. You will need to make sure that you have the correct paths for your cert files for your key and trust store and SSL is the default security standards. 

If you require further assistance in developing your application, feel free to reach out to the VOP Platform team for assistance on Slack at @platform-framework or through email at @E_API_Framework. 