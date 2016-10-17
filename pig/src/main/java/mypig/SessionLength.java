package mypig;

import org.apache.pig.EvalFunc;
import org.apache.pig.backend.executionengine.ExecException;
import org.apache.pig.data.DataBag;
import org.apache.pig.data.Tuple;
import org.joda.time.DateTime;
//Determine the average session time
public class SessionLength extends EvalFunc<Long> {
    
	public Long exec(Tuple input) throws ExecException {
		DataBag sessionDetails = (DataBag) input.get(0);
		DateTime min = (DateTime) sessionDetails.iterator().next().get(0);
		DateTime max = min;
		for (Tuple tuple : sessionDetails) {
			DateTime temp = (DateTime) tuple.get(0);
			if(min.isAfter(temp)) {
				min = temp;
			} else if(max.isBefore(temp)) {
				max = temp;
			}
		}
		return max.getMillis()-min.getMillis();
	}
}