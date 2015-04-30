Starting flume on cdh5
sudo service flume-ng-agent <start | stop | restart>

or 

/usr/bin/flume-ng agent -c /etc/flume-ng/conf -f /etc/flume-ng/conf/flume.conf -n agent



FLUME_LOG_DIR=/var/log/flume-ng
FLUME_CONF_DIR=/etc/flume-ng/conf
FLUME_RUN_DIR=/var/run/flume-ng
FLUME_HOME=/usr/lib/flume-ng


For running mysql-cassandra program

For adding custom jars
/usr/lib/flume-ng/plugins.d/mysqltocassandra/lib
[cloudera@quickstart lib]$ ls 
MySqlToCassandraForFlume.jar
[cloudera@quickstart lib]$ cd ../libext/
[cloudera@quickstart libext]$ ls
cassandra-all-2.0.9.jar          guava-16.0.1.jar  metrics-core-3.0.2.jar               netty-3.9.0.Final.jar
cassandra-driver-core-2.1.2.jar  lz4-1.2.0.jar     mysql-connector-java-5.1.13-bin.jar
[cloudera@quickstart libext]$ pwd
/usr/lib/flume-ng/plugins.d/mysqltocassandra/libext
[cloudera@quickstart libext]$ 


