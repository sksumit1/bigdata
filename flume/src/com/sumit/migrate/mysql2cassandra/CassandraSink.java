package com.sumit.migrate.mysql2cassandra;

import org.apache.flume.Channel;
import org.apache.flume.Context;
import org.apache.flume.Event;
import org.apache.flume.EventDeliveryException;
import org.apache.flume.Transaction;
import org.apache.flume.conf.Configurable;
import org.apache.flume.event.EventHelper;
import org.apache.flume.sink.AbstractSink;
/**
 * 
 * @author Sumit Kumar
 *
 */
public class CassandraSink extends AbstractSink implements Configurable {

	private CassandraConnection sink = null;
	
	@Override
	public void configure(Context context) {
		sink = new CassandraConnection(context);
	}
	
	@Override
	public Status process() throws EventDeliveryException {
		Status result = Status.READY;
	    Channel channel = getChannel();
	    Transaction transaction = channel.getTransaction();
	    Event event = null;
	    try {
	      transaction.begin();
	      event = channel.take();

	      if (event != null) {
	        String userName = EventHelper.dumpEvent(event);
	        sink.saveData(userName);
	      } else {
	        // No event found, request back-off semantics from the sink runner
	        result = Status.BACKOFF;
	      }
	      transaction.commit();
	    } catch (Exception ex) {
	      transaction.rollback();
	      throw new EventDeliveryException("Failed to log event: " + event, ex);
	    } finally {
	      transaction.close();
	    }

	    return result;
	}
	
	 @Override
	  public void stop() {
		 super.stop();
		 this.sink.cleanUp();
	 }

}
