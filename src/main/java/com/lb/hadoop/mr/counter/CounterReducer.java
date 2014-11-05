package com.lb.hadoop.mr.counter;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

/**
 * Created by lb on 2014/11/3.
 */
public class CounterReducer  extends Reducer<Text, IntWritable, IntWritable, Text>{

}