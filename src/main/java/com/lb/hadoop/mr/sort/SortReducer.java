package com.lb.hadoop.mr.sort;

import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

/**
 * Created by lb on 2014/11/18.
 */
public class SortReducer extends Reducer<WritablePair, NullWritable, NullWritable, Text> {
    Text v = new Text();
    public void reduce(WritablePair key, Iterable<NullWritable> values, Context context) throws IOException,
            InterruptedException {

        for(NullWritable t : values){
            v = new Text(key.toString());
            context.write(NullWritable.get(), v);
        }

    }
}
