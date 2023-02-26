# AWS SQS with LocalStack

* This repo contains sample code for SQS publish and listener with localstack.

* There is an SQS listener which consumes messages from queue.

* This code is written in Java 11 and Spring Boot framework.

# TODO before running the code
* Make sure <b>docker</b> & <b>docker-compose</b> is up & running on your system.
* Run <b>docker-compose.yml</b> file in the repo which will start a localstack container. Use below command for this purpose:
  ```
  docker-compose up -d
  ```
* Create a sample queue in localstack using below command:
  ```
  aws sqs create-queue --queue-name <queue-name> --attributes FifoQueue=true --endpoint-url=<your-localstack-container-url>
  ```
  * queue-name: specify any name for the queue
  * FifoQueue: to specify whether queue should be fifo or not
  * endpoint-url: usually it is http://localhost:4566, can be different depending upon your localstack container config
* Update application.yml file and add aws credentials, any dummy credentials will work with localstack but make sure they match AWS configuration defined for localstack.