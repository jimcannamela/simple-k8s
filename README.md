# Simple K8s

Simple Spring project to demonstrate how to use ConfigMap and Secret values from a Kubernetes cluster.

## Docker

Quick Docker command references.

*Note: If you want to build your own images for testing, you'll need to replace all Docker Hub ID values of `davidgrieser` with your own username. Mostly in the k8s Deployment YAMLs, but also for the docker commands to build/run/push your own image.*

### Build Image

```shell
docker build -t davidgrieser/simple-k8s .
```

### Run Image

To test the image out locally and ensure it works correctly before pushing to a Docker Registry.

*Note: Control-C will exit and stop this image.*

```shell
docker run --rm -it -p 9000:8080 davidgrieser/simple-k8s
```

### Push Image

```shell
docker push davidgrieser/simple-k8s
```

## Kubernetes

All Kubernetes YAML is available in the [k8s](./k8s) directory.

### Service

Maps Service port 80 to Deployment port 8080.

```shell
kubectl apply -f k8s/simple-k8s-service.yaml
```

### Deployment

Set up the deployment for this API

```shell
kubectl apply -f k8s/simple-k8s-deploy.yaml
```

Check the status of a pod. Use Control-C to stop the log follow.

```shell
kubectl logs -f $(kubectl get pods | grep "simple-k8s" | awk '{print $1}')
```

To connect to this deployment in a browser. Navigate to [localhost:54321](http://localhost:54321) after running the following command.

```shell
kubectl port-forward service/simple-k8s 54321:80 
```

### Namespace Reader

In order to access ConfigMap and Secrets, you'll need to have role that has read permissions inside the cluster.

For development clusters the below instructions will work, but this is **NOT** something you should apply to a production cluster. To learn more read about [Using RBAC Authorization](https://kubernetes.io/docs/reference/access-authn-authz/rbac/).

A simple [namespace-reader.yaml](./k8s/namespace-reader.yaml) is provided that you can apply to a cluster with the following command.

```shell
kubectl apply -f k8s/namespace-reader.yaml
```

### ConfigMap

Used as an example of how values from the Config Map can override default values.

```shell
kubectl apply -f k8s/simple-k8s-configmap.yaml
```

Apply Deployment from Step 2

```shell
kubectl apply -f k8s/simple-k8s-deploy-step2.yaml
```

If you want to make additional ConfigMap changes, feel free to do so. Just be aware, that the Deployment doesn't monitor the ConfigMap. If you want the latest values to be used from the ConfigMap you'll have to restart the deployment yourself.

You can restart a deployment with the following command.

```shell
kubectl rollout restart -f k8s/simple-k8s-deploy.yaml
```

### Secret

Used as an example of how values from the Secret can override default values.

<div style="color: red">You should NOT put secrets in Git Repositories. This is done strickly because it is a demo.</div>

```shell
kubectl apply -f k8s/simple-k8s-secrets.yaml
```

Apply Deployment from Step 3

```shell
kubectl apply -f k8s/simple-k8s-deploy-step3.yaml
```
