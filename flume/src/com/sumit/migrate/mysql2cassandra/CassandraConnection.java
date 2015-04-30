package com.sumit.migrate.mysql2cassandra;

import java.util.Iterator;
import java.util.List;

import org.apache.flume.Context;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Host;
import com.datastax.driver.core.Metadata;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.querybuilder.Batch;
import com.datastax.driver.core.querybuilder.Insert;
import com.datastax.driver.core.querybuilder.QueryBuilder;
/**
 * 
 * @author Sumit Kumar
 *
 */
public class CassandraConnection {
	
	private String host,user,password;
	private int port;
	private Cluster cluster = null;
	private Session session = null;;
	
	CassandraConnection(Context context)
	{
		initialize(context);
	}
	
	public void initialize(Context context) {
		host = context.getString("cassandraHost");
		port = context.getInteger("cassandraPort");
		user = context.getString("cassandraUser");
		password = context.getString("cassandraPassword");
		this.cluster = Cluster.builder().addContactPoint(host).withPort(port).withCredentials(user, password).build();
	      final Metadata metadata = cluster.getMetadata();
	      System.out.printf("Connected to cluster: %s\n", metadata.getClusterName());
	      for (final Host host : metadata.getAllHosts())
	      {
	         System.out.printf("Datacenter: %s; Host: %s; Rack: %s\n",
	            host.getDatacenter(), host.getAddress(), host.getRack());
	      }
	      session = cluster.connect();
	}
	
	public void saveData(List<String> data) {
		Batch batch = QueryBuilder.batch();
		Iterator<String> it = data.iterator();
		
		while(it.hasNext()) {
			Insert insert = QueryBuilder.insertInto("test","user").value("name", it.next());
			batch.add(insert);
		}
		session.execute(batch);
	}
	
	public void saveData(String data) {		
		Insert insert = QueryBuilder.insertInto("test","user").value("name", data);
		session.execute(insert);
	}
	
	public void cleanUp() {
		if(session != null) {
			try {
				session.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if(cluster != null) {
			try {
				cluster.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
