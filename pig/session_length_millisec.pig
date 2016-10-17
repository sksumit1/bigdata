/*
 pig -f session_length_millisec.pig  -param "input=/opt/paytm/input.log" -param "output=/tmp/outputdir"
*/
register  /root/pig/mypig-0.0.1-SNAPSHOT.jar
records= load '$input' USING PigStorage(' ') AS (timestamp:datetime,elb,client_port,backend_port,request_processing_time,backend_processing_time,response_processing_time,elb_status_code,backend_status_code,received_bytes,sent_bytes,method,request:chararray,user_agent,ssl_cipher,ssl_protocol);
filtered = foreach records GENERATE timestamp,REGEX_EXTRACT(client_port, '(.*):(.*)', 1) as client;
loginlogout_session = group filtered by client;
myoutput = foreach loginlogout_session generate mypig.SessionLength(filtered);
group_all = group myoutput all;
avg_session = foreach group_all generate AVG(myoutput);
store avg_session into '$output' using PigStorage(':');