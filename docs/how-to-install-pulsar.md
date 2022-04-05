### 1 add pulsar helm repo & update

```
helm repo add apache https://pulsar.apache.org/charts
helm repo update
```

### 2 download pulsar chart

```git clone https://github.com/apache/pulsar-helm-chart```

### 3 change workdir to pulsar helm chart repo

```cd pulsar-helm-chart/```

### 3 run prepare scripts

```./prepare_helm_release.sh -n pulsar -k pulsar-mini -c```

### 4 use helm to install

```
helm install  \
 --values examples/values-minikube.yaml \
 --set initialize=true --namespace pulsar  pulsar-mini apache/pulsar
 ```

### 5 verify pod status

```
(Running or Completed are must)
kubectl get pods -n pulsar
NAME                                      READY   STATUS      RESTARTS   AGE
pulsar-mini-bookie-0                      1/1     Running     0          13m
pulsar-mini-bookie-init-q52cx             0/1     Completed   0          13m
pulsar-mini-broker-0                      1/1     Running     0          13m
pulsar-mini-grafana-645b89b979-r8zm9      1/1     Running     0          13m
pulsar-mini-prometheus-7b84bff494-2jjgp   1/1     Running     0          13m
pulsar-mini-proxy-0                       1/1     Running     0          13m
pulsar-mini-pulsar-init-ffbj5             0/1     Completed   0          13m
pulsar-mini-toolset-0                     1/1     Running     0          13m
pulsar-mini-zookeeper-0                   1/1     Running     0          13m
```

### 6 verify service status

```
kubectl get services -n pulsar
NAME                     TYPE           CLUSTER-IP      EXTEYRNAL-IP   PORT(S)                               AGE
pulsar-mini-bookie       ClusterIP      None            <none>        3181/TCP,8000/TCP                     21m
pulsar-mini-broker       ClusterIP      None            <none>        8080/TCP,6650/TCP                     21m
pulsar-mini-grafana      LoadBalancer   10.109.63.178   <pending>     3000:30878/TCP                        21m
pulsar-mini-prometheus   ClusterIP      None            <none>        9090/TCP                              21m
pulsar-mini-proxy        LoadBalancer   10.97.221.242   <pending>     80:30134/TCP,6650:32579/TCP           21m
pulsar-mini-toolset      ClusterIP      None            <none>        <none>                                21m
pulsar-mini-zookeeper    ClusterIP      None            <none>        8000/TCP,2888/TCP,3888/TCP,2181/TCP   21m
```