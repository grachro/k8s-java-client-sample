Kubernetesの~/.kube/configのcontextからnamespaecとサービスの一覧を出力する簡単なプログラムです。

## build

```
mvn clean install

```


## run
```
cd target
java -jar k8s-java-client-sample-1.0-SNAPSHOT.jar
```

## 実行結果例
```
##start
context [tab] namespace [tab] service-name
context [tab] namespace [tab] service-name
context [tab] namespace [tab] service-name
...
##finish
```


## 参考
Kubernets Client Libraries
https://kubernetes.io/docs/reference/using-api/client-libraries/

Offical Java Client
https://github.com/kubernetes-client/java/