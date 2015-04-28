Starting flume on cdh5
sudo service flume-ng-agent <start | stop | restart>

or 

/usr/bin/flume-ng agent -c /etc/flume-ng/conf -f /etc/flume-ng/conf/flume.conf -n agent
