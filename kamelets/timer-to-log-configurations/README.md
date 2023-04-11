# Timer to Log custom config example

There are many way to configure an intergration. These example show how to make them

## Embedded in your flow declaration

`flow-binding-basic.yaml`

This is the basic one. You can have your configuration directly inside your integration definition.

You can define your properties inside the source/sink/etc description :

  * Run the following command:
```sh
kubectl apply -f flow-binding-embedded-1.yaml
```

* The following result is expected
```txt
TODO change 1.12
timer-to-log-basic-7d7dbfc489-tbxf8 integration 2023-04-11 15:27:10,301 INFO  [log-sink] (Camel (camel-1) thread #1 - timer://tick) Exchange[ExchangePattern: InOnly, BodyType: String, Body: I am a very slow hardcoded message]
```

You can also define your properties inside the integration spec:

  * Run the following command:
```sh
kubectl apply -f flow-binding-embedded-2.yaml
```

* The following result is expected
```txt
TODO change 1.12
timer-to-log-embedded-2-5779d4c759-vzns2 integration 2023-04-11 15:38:51,880 INFO  [log-sink] (Camel (camel-1) thread #1 - timer://tick) Exchange[ExchangePattern: InOnly, BodyType: String, Body: I am a faster hardcoded message=]
```

## Configmaps/Secrets

Now, in a real production environement, defining all your property values inside your integration is less that ideal. So we use configmaps and properties.

To do that we create:

* add configmap with the properties:
```sh
kubectl create configmap my-properties --from-file=./configured.properties
```

* add a secret with the secured properties:
```sh
kubectl create secret generic my-secret-properties --from-literal="camel.kamelet.timer-source.message=My secret message"
```

  * Run the following command:
```sh
kubectl apply -f flow-binding-configured.yaml
```

* The following result is expected
```txt
TODO change 1.12
timer-to-log-embedded-2-5779d4c759-vzns2 integration 2023-04-11 15:38:51,880 INFO  [log-sink] (Camel (camel-1) thread #1 - timer://tick) Exchange[ExchangePattern: InOnly, BodyType: String, Body: I am a faster hardcoded message=]
```

## Custom properties

You can also choose to define your own set of properties to inject in your integrations.

To do that we create:

* add configmap with the properties:
```sh
kubectl create configmap my-custom-properties --from-file=./custom.properties
```

  * Run the following command:
```sh
kubectl apply -f flow-binding-custom-properties.yaml
```

## Custom kamelet configuration

You can also choose to define your own set of properties as a complete custom configuration.

To do that we create:

* add configmap with the properties:
```sh
kubectl create secret generic timer-source \
     --from-literal='camel.kamelet.timer-source.message=The message that should not be seen'
kubectl label secret timer-source camel.apache.org/kamelet=mariadb-source

kubectl create secret generic timer-source.mytimerconfig \
     --from-literal='camel.kamelet.timer-source.mytimerconfig.message=The message that should be seen'
kubectl label secret  timer-source.mytimerconfig camel.apache.org/kamelet=timer-source camel.apache.org/kamelet.configuration=mytimerconfig
```

  * Run the following command:
```sh
kubectl apply -f flow-binding-custom-kamelet-config.yaml
```

>> ERROR, shows timer-source instead of timer-source.mytimerconfig