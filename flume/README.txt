Starting flume on cdh5
sudo service flume-ng-agent <start | stop | restart>

or 

/usr/bin/flume-ng agent -c /etc/flume-ng/conf -f /etc/flume-ng/conf/flume.conf -n agent



FLUME_LOG_DIR=/var/log/flume-ng
FLUME_CONF_DIR=/etc/flume-ng/conf
FLUME_RUN_DIR=/var/run/flume-ng
FLUME_HOME=/usr/lib/flume-ng


For running mysql-cassandra program



