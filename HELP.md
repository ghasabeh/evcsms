# EVCSMS

>  The Electric Vehicle Charging Station Management System

EVCSMS is a little part of electronic vehicle charging management system that allows you to create charging companies and their respective stations.

## What is EVCSMS?
In a nutshell EVCSMS allows you store companies and their hierarchy. Company has an extra field named path that maintains all parents companies to the root . Also, you can store one or more stations for a company with their geolocations.

Here is an example. You may define such data structure in EVCSMS:

### Company

| Id | Name | Parent Company | Path |
|---:|:----:|:--------------:|:-----|
| 1 | `A` |`-` | 0 |
| 2 | `B` | `A`|0,1|
| 3 | `C` | `B`|0,1,2|
| 4 | `D` | `C`|0,1,2,3|
| 5 | `E` | `A`|0,1|

Later you can provide stations, and their geolocations for companies.

| Id | Name | Company | Latitude | Longitude
|---:|:----:|:--------------:|:-----|:-----|
| 1 | `Station-A1` | `A`|0.123456|0.000000|
| 2 | `Station-A2` | `A`|0.123456|0.123456|
| 3 | `Station-B1` | `B`|0.445678|0.111111|
| 4 | `Station-C1` | `C`|2.654321|-2.654321|
| 5 | `Station-C2` | `C`|3.654321|-3.654321|

## Features
EVCSMS v1 provides the minimum features needed to support its main duty, it includes:
* Create, Update, Delete and Find Company.
  

* Create, Update, Delete and Find Stations.
  

* An api to gets all stations within the radius of n kilometers from a point (latitude, longitude) ordered by distance (With Pagination)
  

* An api to gets all stations including all the children stations in the tree, for the given company_id (With Pagination)

To see all api specifications go to [Swagger page]((http://localhost:8090/swagger-ui/index.html?configUrl=/v3/api-docs/swagger-config#/)). Of course to see swagger you must run the application.
## Installation
EVCSMS makes use of Sail, Laravel's time-saving dockerizing tool.
You're just simple steps away from deploying the project and starting to work on it.

0. make sure you have `git`, `jdk-17`, `maven`, `docker` and `docker-compose`installed.
1. clone the project to your preferred directory.
   ```shell
    git clone https://a_ghasabeh@bitbucket.org/a_ghasabeh/evcsms.git
   ```
2. To setup database use commands below
    ```shell
    cd evcsms
   
    .src/main/resources/setup/InstallDatabase.sh
    ```
   or just run:
   
   ```shell
   docker-compose -f src\main\resources\setup\postgres.yml up -d
   ```

3. To build project:
   ```shell
   mvn -DskipTests=true clean install
   ```
4. To run the app with docker 
   ```shell
   docker build -t evcsms .
   docker run -p 8090:8090 --network=postgres_network -itd --name=evcsms evcsms
   ```

<img src="https://www.freeiconspng.com/thumbs/warning-icon-png/warning-icon-5.png" width="20" /> In <b>windows</b>, it has some problems with docker networks. you should obtain postgres host ip via command below and modify application.yml to use from it.
   
```shell 
docker inspect -f "{{.NetworkSettings.Networks.postgres_network.IPAddress }}"  postgres
   ```
   
or simply just run with:
   ```shell
   java -jar target/evcsms.jar
```
#Tests
To make sure everything is working, you may run EVCSMS's tests. Don't forget to create and config a test database before running tests!


## TODOs
There are many enhancements still applicable on EVCSMS, most important ones include:
- Write more unit tests and integration tests.
- I am new to docker. enhance docker file and docker-compose to run application alongside it's dependency just with one file  
- Write a simple, not fancy interface that will consume your API programmatically.
- Make more documentations.

---

Developed with Spring boot framework. This app made as requested by Devolon.

<br>
<img src="https://spring.io/images/spring-logo-9146a4d3298760c2e7e49595184e1975.svg" width="100" alt="laravel logo">
<br>
<img src="https://logodix.com/logo/1614477.png" width="100" alt="laravel logo">
<br>
<img src="https://media-exp1.licdn.com/dms/image/C560BAQE0KP4uvwVOGg/company-logo_200_200/0/1519874975273?e=2159024400&v=beta&t=CkfwEoN1f15LYPPpzpLnceXBQ-lOz4MxfTTlHeODoJg" width="100" alt="devolon logo">