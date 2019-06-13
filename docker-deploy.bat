@echo off
docker-compose up -d carappfrontend
cd CarAppBackend
call mvn clean install
docker cp DockerData/password.txt CarAppBackend:/opt/payara41/password.txt
docker exec CarAppBackend /opt/payara41/bin/asadmin --user admin --passwordfile /opt/payara41/password.txt undeploy car-api
docker exec CarAppBackend /opt/payara41/bin/asadmin --user admin --passwordfile /opt/payara41/password.txt deploy /opt/payara41/app/car-api.war
docker exec CarAppBackend rm /opt/payara41/password.txt
docker-compose up -d dbmigration
docker attach CarAppBackend
