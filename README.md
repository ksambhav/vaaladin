# Project Base for Vaadin and Spring Boot

This project can be used as a starting point to create your own Vaadin application with Spring Boot.
It contains all the necessary configuration and some placeholder files to get you started.

The best way to create your own project based on this starter is [start.vaadin.com](https://start.vaadin.com/) - you can
get only the necessary parts and choose the package naming you want to use.

## Running the Application

There are two ways to run the application :  using `mvn spring-boot:run` or by running the `Application` class directly
from your IDE.

You can use any IDE of your preference,but we suggest Eclipse or Intellij IDEA.
Below are the configuration details to start the project using a `spring-boot:run` command. Both Eclipse and Intellij
IDEA are covered.

#### Eclipse

- Right click on a project folder and select `Run As` --> `Maven build..` . After that a configuration window is opened.
- In the window set the value of the **Goals** field to `spring-boot:run`
- You can optionally select `Skip tests` checkbox
- All the other settings can be left to default

Once configurations are set clicking `Run` will start the application

#### Intellij IDEA

- On the right side of the window, select Maven --> Plugins--> `spring-boot` --> `spring-boot:run` goal
- Optionally, you can disable tests by clicking on a `Skip Tests mode` blue button.

Clicking on the green run button will start the application.

After the application has started, you can view your it at http://localhost:8080/ in your browser.

If you want to run the application locally in the production mode, use `spring-boot:run -Pproduction` command instead.

### Running Integration Tests

Integration tests are implemented using [Vaadin TestBench](https://vaadin.com/testbench). The tests take a few minutes
to run and are therefore included in a separate Maven profile. We recommend running tests with a production build to
minimize the chance of development time toolchains affecting test stability. To run the tests using Google Chrome,
execute

`mvn verify -Pit,production`

and make sure you have a valid TestBench license installed.

## Structure

Vaadin web applications are full-stack and include both client-side and server-side code in the same project.

| Directory                                  | Description                                 |
|:-------------------------------------------|:--------------------------------------------|
| `src/main/frontend/`                       | Client-side source directory                |
| &nbsp;&nbsp;&nbsp;&nbsp;`index.html`       | HTML template                               |
| &nbsp;&nbsp;&nbsp;&nbsp;`index.ts`         | Frontend entrypoint                         |
| &nbsp;&nbsp;&nbsp;&nbsp;`main-layout.ts`   | Main layout Web Component (optional)        |
| &nbsp;&nbsp;&nbsp;&nbsp;`views/`           | UI views Web Components (TypeScript / HTML) |
| &nbsp;&nbsp;&nbsp;&nbsp;`styles/`          | Styles directory (CSS)                      |
| `src/main/java/<groupId>/`                 | Server-side source directory                |
| &nbsp;&nbsp;&nbsp;&nbsp;`Application.java` | Server entrypoint                           |
| &nbsp;&nbsp;&nbsp;&nbsp;`AppShell.java`    | application-shell configuration             |

## Useful links

- Read the documentation at [vaadin.com/docs](https://vaadin.com/docs).
- Follow the tutorials at [vaadin.com/tutorials](https://vaadin.com/tutorials).
- Watch training videos and get certified at [vaadin.com/learn/training](https://vaadin.com/learn/training).
- Create new projects at [start.vaadin.com](https://start.vaadin.com/).
- Search UI components and their usage examples at [vaadin.com/components](https://vaadin.com/components).
- View use case applications that demonstrate Vaadin capabilities
  at [vaadin.com/examples-and-demos](https://vaadin.com/examples-and-demos).
- Discover Vaadin's set of CSS utility classes that enable building any UI without custom CSS in
  the [docs](https://vaadin.com/docs/latest/ds/foundation/utility-classes).
- Find a collection of solutions to common use cases in [Vaadin Cookbook](https://cookbook.vaadin.com/).
- Find Add-ons at [vaadin.com/directory](https://vaadin.com/directory).
- Ask questions on [Stack Overflow](https://stackoverflow.com/questions/tagged/vaadin) or join
  our [Discord channel](https://discord.gg/MYFq5RTbBn).
- Report issues, create pull requests in [GitHub](https://github.com/vaadin/platform).

## Running locally using Docker

//TODO

## Deployment using Kubernetes

The application is packaged as Docker image which can be deployed as Docker compose or as a Kubernetes
deployment. Here we will demonstrate deployment using Kubernetes running as
a [Docker Desktop](https://www.docker.com/products/docker-desktop/) (just for fun ;-p). These are **NOT** a
recommendation
for a
production grade environment.

### Database

We will be using MySQL as our choice for the RDBMS. We will be using
the [MySQL Operator for Kubernetes](https://dev.mysql.com/doc/mysql-operator/en/) for deploying a simple single node
cluster

```commandline
kubectl create ns vaaladin
```

```commandline
helm repo add mysql-operator https://mysql.github.io/mysql-operator/
```

```commandline
helm repo update
```

```commandline
helm install mysql-operator mysql-operator/mysql-operator --namespace vaaladin
```

```commandline
helm upgrade --install vaaladin-mysql-db mysql-operator/mysql-innodbcluster -n vaaladin --values deployment/mysql-values.yaml
```

### NGINX Ingress

```commandline
kubectl apply -f https://raw.githubusercontent.com/kubernetes/ingress-nginx/main/deploy/static/provider/cloud/deploy.yaml
```

### Monitoring

We will be using https://artifacthub.io/packages/helm/prometheus-community/kube-prometheus-stack for monitoring, logging
and alerting requirements. This will help to profile a typical Vaadin application resources footprint under load.

```commandline
helm repo add grafana https://grafana.github.io/helm-charts
```

```commandline
helm repo add prometheus-community https://prometheus-community.github.io/helm-charts
```

```commandline
helm repo update
```

```commandline
helm install --values deployment/loki-values.yaml loki --namespace=vaaladin grafana/loki
```

```commandline
helm upgrade --install kube-prometheus-stack prometheus-community/kube-prometheus-stack -f deployment/prom-values.yaml -n vaaladin
```

Import [Grafana Dashboard](https://grafana.com/grafana/dashboards/12900-springboot-apm-dashboard/)

![img.png](img.png)

### Install Kafka using [Strimzi](https://strimzi.io/)

```commandline
kubectl create namespace kafka
```

```commandline
kubectl create -f 'https://strimzi.io/install/latest?namespace=kafka' -n kafka
```

```commandline
kubectl apply -f deployment\strimzi.yaml -n kafka
```