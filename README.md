# eightballapi
This project was created to test hosting an API from a [Raspberry Pi](https://www.raspberrypi.org/). The Raspberry Pi runs Raspbian Stretch Lite. The project was created using Spring Boot initializr. It requires a Postgres DB. The API has been Dockerized and was run on the Raspberry Pi in the same custom bridge network as the database container.
## Dependencies
* Java
* Maven
* Postgres
* Docker
## Docker Installation on Raspberry Pi
Install [Docker](https://www.docker.com/).
Setup custom bridge network.
```
$ curl -sSL https://get.docker.com | sh
$ sudo usermod -aG docker pi
$ docker network create eightballnetwork
```
## Database Setup on Raspberry Pi
Pull a ARM-architecture Postgres Docker image: 
[tobi312/rpi-postgresql:9.6](https://hub.docker.com/r/tobi312/rpi-postgresql/)
```
$ docker pull tobi312/rpi-postgresql:9.6
$ mkdir -p ~/docker/volumes/postgres
$ docker run --name eightballdb -d -p 5432:5432 \
$            -v ~/docker/volumes/postgres:/var/lib/postgresql/data \
$            -e POSTGRES_DB=<DBNAME> \
$            -e POSTGRES_USER=<DBUSER> \
$            -e POSTGRES_PASSWORD=<DBPASSWORD> \
$            -network eightballnetwork \
$            tobi312/rpi-postgresql:9.6
```
## Build the API Docker Image
1. Build a jar file of the API with [Maven](https://maven.apache.org/)
2. Build a docker image
3. Save the docker image to a tar file

Note: In the absence of a Docker registry, the image is saved to a tar file to be transferred via SCP.
```
$ mvn package
$ docker build -t eightballapi:0.0.1 \
$              --build-arg EIGHTBALL_DB_URL=<DBURL> \
$              --build-arg EIGHTBALL_DB_USER=<DBUSER> \
$              --build-arg EIGHTBALL_DB_PASSWORD=<DBPASSWORD> \
$              --build-arg EIGHTBALL_API_KEY=<APIKEY> .
$ docker save -o eightballapi_0.0.1.tar eightballapi:0.0.1
```
## Deploy the API Docker Image
1. Transfer the tar file containing the saved docker image to the home directory of the Raspberry Pi
2. Import the image from the tar file
3. Run a container from the image
```
$ scp eightballapi_0.0.1.tar <RemoteUser>@<RemoteAddress>:~/
$ docker load -i ~/eightballapi_0.0.1.tar
$ docker run -it -p 7000:7000 --network eightballnetwork eightballapi:0.0.1
```
