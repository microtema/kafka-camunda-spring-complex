#!/bin/sh

echo create Camunda cluster locally...
kind create cluster --name camunda-platform-local

echo switch to the new cluster: camunda-platform-local
kubectl cluster-info --context kind-camunda-platform-local

echo deploy camunda via helm
helm repo add camunda https://helm.camunda.io
helm repo update

echo install camunda Platform 8

helm install dev camunda/camunda-platform \
    -f camunda-platform-core-kind-values.yaml

echo gateway fro clients
kubectl port-forward svc/dev-zeebe-gateway 26500:26500 -n default

# echo Operate
# kubectl port-forward svc/dev-operate  8061:80

# echo Operate
# kubectl port-forward svc/dev-tasklist 8062:80

