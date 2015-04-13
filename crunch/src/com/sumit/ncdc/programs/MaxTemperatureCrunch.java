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

public class MaxTemperatureCrunch {
  
  public static void main(String[] args) throws Exception {
    if (args.length != 2) {
      System.err.println("Usage: MaxTemperatureCrunch <input path> <output path>");
      System.exit(-1);
    }

    Pipeline pipeline = new MRPipeline(MaxTemperatureCrunch.class);
    PCollection<String> records = pipeline.readTextFile(args[0]);

    PTable<String, Integer> yearTemperatures = records
        .parallelDo(toYearTempPairsFn(), Writables.tableOf(Writables.strings(), Writables.ints()));
    PGroupedTable<String,Integer> groupedTable = yearTemperatures.groupByKey();
    PTable<String, Integer> maxTemps = groupedTable
      .combineValues(Aggregators.MAX_INTS());
    
    maxTemps.write(To.textFile(args[1]));
    PipelineResult result = pipeline.done();
    System.exit(result.succeeded() ? 0 : 1);
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

}
