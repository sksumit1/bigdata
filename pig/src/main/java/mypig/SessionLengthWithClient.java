package mypig;

import org.apache.pig.EvalFunc;
import org.apache.pig.backend.executionengine.ExecException;
import org.apache.pig.data.DataBag;
import org.apache.pig.data.Tuple;
import org.apache.pig.data.TupleFactory;
import org.joda.time.DateTime;
//Determine the session time per client
public class SessionLengthWithClient extends EvalFunc<Tuple> {
    
	public Tuple exec(Tuple input) throws ExecException {
		DataBag sessionDetails = (DataBag) input.get(0);
		Tuple tp = sessionDetails.iterator().next();
		String client = (String) tp.get(1);
		DateTime min = (DateTime) tp.get(0);
		DateTime max = min;
		for (Tuple tuple : sessionDetails) {
			DateTime temp = (DateTime) tuple.get(0);
			if(min.isAfter(temp)) {
				min = temp;
			} else if(max.isBefore(temp)) {
				max = temp;
			}
		}
		Tuple returnTuple = TupleFactory.getInstance().newTuple();
		returnTuple.append(max.getMillis()-min.getMillis());
		returnTuple.append(client);
		return returnTuple;
	}
}