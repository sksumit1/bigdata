/*
 pig -f unique_url_session.pig -param "client_param=117.239.195.66" -param "time_start_param=2015-07-22T09:01:00.000000Z" -param "time_end_param=2015-07-22T09:02:00.000000Z"
*/
register  /root/pig/mypig-0.0.1-SNAPSHOT.jar
records= load '/opt/paytm/input.log' USING PigStorage(' ') AS (timestamp,elb,client_port,backend_port,request_processing_time,backend_processing_time,response_processing_time,elb_status_code,backend_status_code,received_bytes,sent_bytes,method,request:chararray,user_agent,ssl_cipher,ssl_protocol);
filtered = foreach records GENERATE timestamp,REGEX_EXTRACT(client_port, '(.*):(.*)', 1) as client, request;
timefiltered = filter filtered by timestamp>='$time_start_param' and timestamp<='$time_end_param';
clientfiltered = filter timefiltered by client matches '$client_param';
loginlogout_session = group clientfiltered by client;
myoutput = foreach loginlogout_session generate group,mypig.SessionPageHits(clientfiltered);
store myoutput into '/tmp/myoutputdir' using PigStorage(':');