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

Now, in a real production environement, definition all you property values inside integration is less that ideal.

To do that we create:

* add configmap with the properties:
```sh
kubectl create configmap my-properties --from-file=./configured.properties
```

* add a secret with the secured properties:
```sh
kubectl create secret generic my-secret-properties --from-literal="message=My message secret that fires only 5 times"
```

  * Run the following command:
```sh
kubectl apply -f flow-binding-configured.yaml
```


