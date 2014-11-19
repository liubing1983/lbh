package com.lb.hadoop.mr.sort;

import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;
import java.util.regex.Pattern;

/**
 * Created by lb on 2014/11/18.
 */
public class SortMapper extends Mapper<Object, Text, WritablePair, NullWritable> {

    private WritablePair k = new WritablePair();
    private Text kf = new Text();
    private Text ks = new Text();
    Pattern p = Pattern.compile(",");

    public void map(Object key, Text value, Context context)throws IOException,InterruptedException {
        String s[]  = p.split(value.toString(), -1);
        kf.set(s[0]);
        ks.set(s[1]);
        k.set(kf, ks);

        context.write(k, NullWritable.get());
    }
}
