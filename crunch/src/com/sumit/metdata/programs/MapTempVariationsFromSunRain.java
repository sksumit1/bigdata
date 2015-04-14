package com.sumit.metdata.programs;

import org.apache.crunch.MapFn;
import org.apache.crunch.PCollection;
import org.apache.crunch.Pipeline;
import org.apache.crunch.PipelineResult;
import org.apache.crunch.impl.mr.MRPipeline;
import org.apache.crunch.types.writable.Writables;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.filecache.DistributedCache;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

public class MapTempVariationsFromSunRain extends Configured implements Tool {
	
	public static void main(String[] args) throws Exception {
	    if (args.length != 2) {
	      System.err.println("Usage: MapTempVariationsFromSunRain <input path> <output path>");
	      System.exit(-1);
	    }
	    int res = ToolRunner.run(new Configuration(), new MapTempVariationsFromSunRain(), args);
	    System.exit(res);   
	  }

	@Override
	public int run(String[] args) throws Exception {
		Configuration conf = getConf();
		DistributedCache.addFileToClassPath(new Path("crunch-core.jar"), conf);
		Pipeline pipeline = new MRPipeline(MapTempVariationsFromSunRain.class, conf);
		PCollection<String>collection = pipeline.readTextFile(args[0]);
		PCollection<String> resultCollection = collection.parallelDo("TempVariationsFromSunRanin",getMapFn(), Writables.strings());
		pipeline.writeTextFile(resultCollection, args[1]);
		PipelineResult result = pipeline.done();
	    return result.succeeded() ? 0 : 1;
	}
	
	private static MapFn<String, String> getMapFn() {
		return new MapFn<String, String>() {
			MetaRecordParser parser = new MetaRecordParser();
			@Override
			public String map(String input) {				
				parser.parser(input);
				String record = (parser.getMaxTemp()-parser.getMinTemp())+" "+parser.getSunHours()+" "+parser.getRain();
				return record;
			}
		};
	}

	

}
