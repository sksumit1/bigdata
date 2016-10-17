package mypig;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.pig.EvalFunc;
import org.apache.pig.backend.executionengine.ExecException;
import org.apache.pig.data.BagFactory;
import org.apache.pig.data.DataBag;
import org.apache.pig.data.Tuple;
import org.apache.pig.data.TupleFactory;
//Aggregate all page hits by visitor/IP during a fixed time window
public class SessionPageHitsCounts extends EvalFunc<DataBag> {
    
	public DataBag exec(Tuple input) throws ExecException {
		DataBag sessionDetails = (DataBag) input.get(0);
		Map<String,Integer> items = new HashMap<String,Integer>();
		DataBag returnBag = BagFactory.getInstance().newDefaultBag();
		for (Tuple tuple : sessionDetails) {
			String url = (String) tuple.get(2);
			Integer count = items.get(url);
			if(count == null) {
				count = 0;
			}
			items.put(url, ++count);
		}
		Iterator<String> it = items.keySet().iterator();
		while(it.hasNext()) {
			Tuple tp = TupleFactory.getInstance().newTuple();
			String url = it.next();
			tp.append(url);
			tp.append(items.get(url));
			returnBag.add(tp);
		}
		return returnBag;
	}
}