FROM payara/server-full:4.181
MAINTAINER Guillermo Miralles Campillo mirallescampilloguillermo@gmail.com
ENV PASSWORD_FILE /opt/payara41/password.txt
ENV PAYARA_DOMAIN domain1
ENV LIB_DIR /opt/payara41/glassfish/domains/domain1/lib
ENV PREPARE_RESOURCES_FILE /opt/payara41/prepare_resources.sh
ENV JDBC_POOL PostgresPool
ENV JDBC_JNDI jdbc/carapp
ENV JMS_CONFACTORY jms/CarAppConFactory
ENV JMS_CAR jms/CarAppCar
ENV JMS_CAR_DEST CarAppCarDest
ENV JMS_COUNTRY jms/CarAppCountry
ENV JMS_COUNTRY_DEST CarAppCountryDest
ENV JMS_BRAND jms/CarAppBrand
ENV JMS_BRAND_DEST CarAppBrandDest
COPY --chown=payara:payara DockerData/password.txt ${PASSWORD_FILE}
COPY --chown=payara:payara DockerData/postgresql-42.2.5.jar ${LIB_DIR}/postgresql-42.2.5.jar
COPY --chown=payara:payara DockerData/prepare_resources.sh ${PREPARE_RESOURCES_FILE}
RUN ${PREPARE_RESOURCES_FILE}
RUN rm ${PREPARE_RESOURCES_FILE}
EXPOSE 4848 8080 8181 9009 7676