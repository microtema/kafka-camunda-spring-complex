# Confluent Cloud (Apache Kafka) and Camunda Platform 8 (Zeebe) 

## Sample Glue Code Written in Java Spring Boot

Simple example code connecting Confluent Cloud (Kafka) and Camunda Platform 8 SaaS (Zeebe) by leveraging the Java Spring Boot integrations of both frameworks.

The example automates a very simple process, that

- writes a record to Kafka
- listens to the exact same record to correlate it back to the process instance

![](architecture.png)

## How to run

* Create Kafka cluster in Confluent Cloud: https://confluent.cloud/
* Create client credentials and add them to ``application.properties``

* Create Camunda cluster in Camunda 8 Saas: https://console.cloud.camunda.io/
* Create client credentials and add them to ``application.properties``

* Start up Java application, which will
  * Deploy the process (visible via Camunda Operate)
  * Create the topics (visible via Confluent Console)
* Kick off a new process instance, e.g.
  * via Camunda Desktop Modeler

## Glossary
* SASL: Simple Authentication and Security Layer
* JAAS: Java Authentication and Authorization Service
* SSL: Secure Socket Layer
