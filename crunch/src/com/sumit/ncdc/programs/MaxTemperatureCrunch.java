package com.sumit.ncdc.programs;
import org.apache.crunch.DoFn;
import org.apache.crunch.Emitter;
import org.apache.crunch.PCollection;
import org.apache.crunch.PGroupedTable;
import org.apache.crunch.PTable;
import org.apache.crunch.Pair;
import org.apache.crunch.Pipeline;
import org.apache.crunch.PipelineResult;
import org.apache.crunch.fn.Aggregators;
import org.apache.crunch.impl.mr.MRPipeline;
import org.apache.crunch.io.To;
import org.apache.crunch.types.writable.Writables;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.filecache.DistributedCache;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

public class MaxTemperatureCrunch extends Configured implements Tool {
  
  public static void main(String[] args) throws Exception {
    if (args.length != 2) {
      System.err.println("Usage: MaxTemperatureCrunch <input path> <output path>");
      System.exit(-1);
    }
    int res = ToolRunner.run(new Configuration(), new MaxTemperatureCrunch(), args);
    System.exit(res);   
  }
  static DoFn<String, Pair<String, Integer>> toYearTempPairsFn() {
    return new DoFn<String, Pair<String, Integer>>() {
      NcdcRecordParser parser = new NcdcRecordParser();
      @Override
      public void process(String input, Emitter<Pair<String, Integer>> emitter) {
        parser.parse(input);
        if (parser.isValidTemperature()) {
          emitter.emit(Pair.of(parser.getYear(), parser.getAirTemperature()));
        }
      }
    };
  }
@Override
	public int run(String[] args) throws Exception {
		Configuration config = getConf();
		//config.addResource("/usr/lib/crunch/crunch-core.jar");
		DistributedCache.addFileToClassPath(new Path("crunch-core.jar"), config);
	    Pipeline pipeline = new MRPipeline(MaxTemperatureCrunch.class,config);
	    PCollection<String> records = pipeline.readTextFile(args[0]);
	
	    PTable<String, Integer> yearTemperatures = records
	        .parallelDo(toYearTempPairsFn(), Writables.tableOf(Writables.strings(), Writables.ints()));
	    PGroupedTable<String,Integer> groupedTable = yearTemperatures.groupByKey();
	    PTable<String, Integer> maxTemps = groupedTable
	      .combineValues(Aggregators.MAX_INTS());
	    
	    maxTemps.write(To.textFile(args[1]));
	    PipelineResult result = pipeline.done();
	    return result.succeeded() ? 0 : 1;
	}

}
