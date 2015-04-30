package com.sumit.migrate.mysql2cassandra;

import java.util.List;

import org.apache.flume.Context;
import org.apache.flume.Event;
import org.apache.flume.EventDrivenSource;
import org.apache.flume.channel.ChannelProcessor;
import org.apache.flume.conf.Configurable;
import org.apache.flume.event.EventBuilder;
import org.apache.flume.source.AbstractSource;
/**
 * 
 * @author Sumit Kumar
 *
 */
public class MySqlSource extends AbstractSource implements EventDrivenSource, Configurable {

	private MySqlConnection source = null;
	@Override
	public void configure(Context context) {
		source = new MySqlConnection(context);
	}
	
	public void start() {
		final ChannelProcessor channel = getChannelProcessor();
		List<String> userNames = source.getData();
		for(String user: userNames) {	
			Event event = EventBuilder.withBody( user.getBytes());
			channel.processEvent(event);
		}
	}
	
	public void stop() {		
		super.stop();
		this.source.cleanUp();
	}

}
