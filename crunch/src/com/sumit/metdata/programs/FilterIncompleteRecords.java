package com.sumit.metdata.programs;

import org.apache.crunch.FilterFn;
import org.apache.crunch.PCollection;
import org.apache.crunch.Pipeline;
import org.apache.crunch.PipelineResult;
import org.apache.crunch.impl.mr.MRPipeline;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.filecache.DistributedCache;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

public class FilterIncompleteRecords extends Configured implements Tool {
	
	public static void main(String[] args) throws Exception {
		if (args.length != 2) {
		      System.err.println("Usage: FilterIncompleteRecords <input path> <output path>");
		      System.exit(-1);
		    }
		    int res = ToolRunner.run(new Configuration(), new FilterIncompleteRecords(), args);
		    System.exit(res);   
	}

	@Override
	public int run(String[] args) throws Exception {
		Configuration config = getConf();
		DistributedCache.addFileToClassPath(new Path("crunch-core.jar"), config);
		Pipeline pipeline = new MRPipeline(FilterIncompleteRecords.class, config);
		PCollection<String> collections = pipeline.readTextFile(args[0]);
		collections.filter(filterFunction());
		pipeline.writeTextFile(collections, args[1]);
		PipelineResult result = pipeline.done();
	    return result.succeeded() ? 0 : 1;
	}
	
	static FilterFn<String> filterFunction() {
		return new FilterFn<String>() {
			@Override
			public boolean accept(String input) {
				MetaRecordParser parser = new MetaRecordParser();
				parser.parser(input);
				System.err.println("Data "+input+" "+parser.isComplete());
				return parser.isComplete();
			}
		};
	}

}
