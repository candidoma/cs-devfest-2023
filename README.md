# Configure cluster

1. Create Kubernetes cluster or use existing one, see [cluster setup].
   Requirements:

   * Kubernetes version 1.8.1 or newer running on GKE or GCE

   * Monitoring scope `monitoring` set up on cluster nodes. **It is enabled by
     default, so you should not have to do anything**. See also [OAuth 2.0 API
     Scopes] to learn more about authentication scopes.

     You can use following commands to verify that the scopes are set correctly:
     - For GKE cluster `<my_cluster>`, use following command:
       ```
       gcloud container clusters describe <my_cluster>
       ```
       For each node pool check the section `oauthScopes` - there should be
       `https://www.googleapis.com/auth/monitoring` scope listed there.
     - For a GCE instance `<my_instance>` use following command:
       ```
       gcloud compute instances describe <my_instance>
       ```
       `https://www.googleapis.com/auth/monitoring` should be listed in the
       `scopes` section.


     To configure set scopes manually, you can use:
     - `--scopes` flag if you are using `gcloud container clusters create`
       command, see [gcloud
       documentation](https://cloud.google.com/sdk/gcloud/reference/container/clusters/create).
     - Environment variable `NODE_SCOPES` if you are using [kube-up.sh script].
       It is enabled by default.
     - To set scopes in existing clusters you can use `gcloud beta compute
       instances set-scopes` command, see [gcloud
       documentation](https://cloud.google.com/sdk/gcloud/reference/beta/compute/instances/set-scopes).
    * On GKE, you need cluster-admin permissions on your cluster. You can grant
      your user account these permissions with following command:
      ```
      kubectl create clusterrolebinding cluster-admin-binding --clusterrole cluster-admin --user $(gcloud config get-value account)
      ```

# Install Tools

## Stackdriver Adapter

kubectl apply -f tools/adapter_new_resource_model.yaml

If you use Workload Identity in your cluster, additional steps are necessary. In
the commands below, use your Project ID as **<project-id>** and Google Service Account as
**<google-service-account>**.

* Make sure your **<google-service-account>** has `monitoring.viewer` IAM role.

* Create IAM Policy Binding:

  ```
  gcloud iam service-accounts add-iam-policy-binding --role \
    roles/iam.workloadIdentityUser --member \
    "serviceAccount:<project-id>.svc.id.goog[custom-metrics/custom-metrics-stackdriver-adapter]" \
    <google-service-account>@<project-id>.iam.gserviceaccount.com
  ```

* Annotate the Custom Metrics - Stackdriver Adapter service account:

  ```
  kubectl annotate serviceaccount --namespace custom-metrics \
    custom-metrics-stackdriver-adapter \
    iam.gke.io/gcp-service-account=<google-service-account>@<project-id>.iam.gserviceaccount.com
  ```

# Demo1

This demo use a simple rest server that consumes pub sub message and use an HPA based on the CPU metric.

Deploy the yaml file in the folder demo1/yaml   

Create a ServiceAccount with the PubSub Consumer role and enable the WorkloadIdentity with the Kubernetes SA demo1/simple-rest-server-sa

To produce multiple message on the topic, edit the script under the folder demo1/scripts/pushMsg.sh and run it with a specific delay

# Demo 2

This demo use a simple rest server that consumes pub sub message and use an HPA based on the PubSub unacked messages metric.

Deploy the yaml file in the folder demo2/yaml   

Create a ServiceAccount with the PubSub Consumer role and enable the WorkloadIdentity with the Kubernetes SA demo2/simple-rest-server-sa

To produce multiple message on the topic, edit the script under the folder demo2/scripts/pushMsg.sh and run it with a specific delay
