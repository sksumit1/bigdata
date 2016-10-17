package mypig;

import java.util.HashSet;
import java.util.Set;

import org.apache.pig.EvalFunc;
import org.apache.pig.backend.executionengine.ExecException;
import org.apache.pig.data.BagFactory;
import org.apache.pig.data.DataBag;
import org.apache.pig.data.Tuple;
import org.apache.pig.data.TupleFactory;
//aggregrate all page hits by visitor/IP during a fixed time window
public class SessionPageHits extends EvalFunc<DataBag> {
    
	public DataBag exec(Tuple input) throws ExecException {
		DataBag sessionDetails = (DataBag) input.get(0);
		Set<String> items = new HashSet<String>();
		DataBag returnBag = BagFactory.getInstance().newDefaultBag();
		for (Tuple tuple : sessionDetails) {
			items.add((String) tuple.get(2));
		}
		for (String item : items) {
			Tuple tp = TupleFactory.getInstance().newTuple();
			tp.append(item);
			returnBag.add(tp);
		}
		return returnBag;
	}
}