# edu-rabbit
Capstone project for Messaging Systems course


## Launch
1. run RabbitMQ cluster
    ```
    docker network rm rabbit_cluster
    docker network create rabbit_cluster
    docker run -d --hostname rabbit1 --network rabbit_cluster --name rabbit1 -e RABBITMQ_ERLANG_COOKIE='rabbitcluster' -p 30000:5672 -p 30001:15672 rabbitmq:management
    docker run -d --hostname rabbit2 --network rabbit_cluster --name rabbit2 -e RABBITMQ_ERLANG_COOKIE='rabbitcluster' -p 30002:5672 -p 30003:15672 rabbitmq:management
    docker run -d --hostname rabbit3 --network rabbit_cluster --name rabbit3 -e RABBITMQ_ERLANG_COOKIE='rabbitcluster' -p 30004:5672 -p 30005:15672 rabbitmq:management
    ```
2. wait until all instances started, check there are not problems
3. connect nodes in the cluster
    ```
    docker exec -ti rabbit2 sh -c "rabbitmqctl stop_app" && \
    docker exec -ti rabbit2 sh -c "rabbitmqctl join_cluster rabbit@rabbit1" && \
    docker exec -ti rabbit2 sh -c "rabbitmqctl start_app" && \
    
    docker exec -ti rabbit3 sh -c "rabbitmqctl stop_app" && \
    docker exec -ti rabbit3 sh -c "rabbitmqctl join_cluster rabbit@rabbit1" && \
    docker exec -ti rabbit3 sh -c "rabbitmqctl start_app"
    ```
4. check by `http://localhost:30001/#/`(guest/guest) that cluster is running
5. create queue
    ```
    docker exec -ti rabbit1 sh -c "rabbitmqadmin declare queue name=mq.messaging durable=true" && \
    docker exec -ti rabbit1 sh -c "rabbitmqadmin declare exchange name=ex.messaging type=direct" && \
    docker exec -ti rabbit1 sh -c "rabbitmqadmin declare binding source=ex.messaging destination=mq.messaging"
    ```

