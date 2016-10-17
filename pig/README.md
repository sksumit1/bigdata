Provides solution to https://github.com/PaytmLabs/WeblogChallenge

Processing & Analytical goals:

Q) Sessionize the web log by IP. Sessionize = aggregrate all page hits by visitor/IP during a fixed time window. https://en.wikipedia.org/wiki/Session_(web_analytics)

Ans)

url_count_session.pig

Given a IP and time range, this gives the frequency of different urls visited by the ip address/user

url_count_all_sessions.pig

Given a time range, this gives the frequency of different urls visited by all the ip address/user

Q) Determine the average session time

session_length_millisec.pig

Calculates the average session length inside this log file

Q) Determine unique URL visits per session. To clarify, count a hit to a unique URL only once per session.

unique_url_session.pig

Given a IP and time range, this gives the unique list of urls visited by the ip address/user

unique_url_all_sessions.pig

Given the time range, this gives the unique list of urls visited by all the ip address/user

Q) Find the most engaged users, ie the IPs with the longest session times

max_session_length_millisec.pig

This gives the IP address with the longest session time & the session value (in millisec)

Assumption:

The entire log file contains a single session window for all.

This can be parameterized to calculate max session length & average session length inside that time window
