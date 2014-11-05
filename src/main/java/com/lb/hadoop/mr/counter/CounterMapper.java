package com.lb.hadoop.mr.counter;

import java.io.IOException;
import java.util.regex.Pattern;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

/**
 * Created by lb on 2014/11/3.
 */
public class CounterMapper extends Mapper<Object, Text, Text, IntWritable> {

    private final static IntWritable v = new IntWritable(1);
    private Text k = new Text();

    Pattern pattern = Pattern.compile("\\|");

    @Override
    public void map(Object key, Text value, Context context)
            throws IOException, InterruptedException {
        for(String s : pattern.split(value.toString())){
            k.set(s);
            if(s.equals("lb")){
                // 自定义counter
                context.getCounter("word num:", "lb num").increment(1);
            }else{
                context.getCounter("word num:", "other num").increment(1);
            }
            context.write(k, v);
        }
    }
}


