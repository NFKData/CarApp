/opt/payara41/bin/asadmin start-domain --debug=true ${PAYARA_DOMAIN}
/opt/payara41/bin/asadmin --user admin --passwordfile ${PASSWORD_FILE} create-jdbc-connection-pool --restype=javax.sql.ConnectionPoolDataSource --datasourceclassname=org.postgresql.ds.PGConnectionPoolDataSource --property password=admin:databaseName=carapp:serverName=postgres:user=postgres:portNumber=5432 ${JDBC_POOL}
/opt/payara41/bin/asadmin --user admin --passwordfile ${PASSWORD_FILE} create-jdbc-resource --connectionpoolid ${JDBC_POOL} ${JDBC_JNDI}
/opt/payara41/bin/asadmin --user admin --passwordfile ${PASSWORD_FILE} create-jms-resource --restype=javax.jms.TopicConnectionFactory ${JMS_CONFACTORY}
/opt/payara41/bin/asadmin --user admin --passwordfile ${PASSWORD_FILE} create-jmsdest --desttype=topic ${JMS_CAR_DEST}
/opt/payara41/bin/asadmin --user admin --passwordfile ${PASSWORD_FILE} create-jms-resource --restype=javax.jms.Topic --property name=${JMS_CAR_DEST} ${JMS_CAR}
/opt/payara41/bin/asadmin --user admin --passwordfile ${PASSWORD_FILE} create-jmsdest --desttype=topic ${JMS_COUNTRY_DEST} 
/opt/payara41/bin/asadmin --user admin --passwordfile ${PASSWORD_FILE} create-jms-resource --restype=javax.jms.Topic --property name=${JMS_COUNTRY_DEST} ${JMS_COUNTRY}
/opt/payara41/bin/asadmin --user admin --passwordfile ${PASSWORD_FILE} create-jmsdest --desttype=topic ${JMS_BRAND_DEST}
/opt/payara41/bin/asadmin --user admin --passwordfile ${PASSWORD_FILE} create-jms-resource --restype=javax.jms.Topic --property name=${JMS_BRAND_DEST} ${JMS_BRAND}
/opt/payara41/bin/asadmin --user admin --passwordfile ${PASSWORD_FILE} stop-domain