docker-compose up -d
cd CarAppBackend
mvn clean install
docker cp DockerData/password.txt CarAppBackend:/opt/payara41/password.txt
docker exec CarAppBackend /opt/payara41/bin/asadmin --user admin --passwordfile /opt/payara41/password.txt deploy /opt/payara41/app/car-api.war
docker exec CarAppBackend rm /opt/payara41/password.txt