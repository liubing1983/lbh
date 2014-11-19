package com.lb.hadoop.mr.sort;


import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

/**
 * Created by lb on 2014/11/18.
 */
public class SortDriver   extends Configured implements Tool {
    public int run(String[] args) throws Exception{
        // 判断入参个数
        if (args.length != 2) {
            System.out.printf( "参数个数不对. Usage: %s [generic options] <input dir> <output dir>\n", getClass().getSimpleName());
            ToolRunner.printGenericCommandUsage(System.out);
            return -1;
        }

        // 删除output目录
        FileSystem fs = FileSystem.get(getConf());
        fs.delete(new Path(args[1]), true);

        Job job = new Job(getConf());
        job.setJarByClass(SortDriver.class);
        job.setJobName("lb sort");

        // 设置文件输入路径
        FileInputFormat.setInputPaths(job, new Path(args[0]));
        // 设置结果文件输出路径
        FileOutputFormat.setOutputPath(job, new Path(args[1]));

        // 设置map类
        job.setMapperClass(SortMapper.class);
        // 手动设置reduce个数
        job.setNumReduceTasks(2);
        // 设置reduce类
        job.setReducerClass(SortReducer.class);

        // job.setSortComparatorClass(LongWritableDecreasingComparator.class);

        // map输出key/value类型
        job.setMapOutputKeyClass(WritablePair.class);
        job.setMapOutputValueClass(NullWritable.class);

       // job.setOutputKeyClass(NullWritable.class);
       // job.setOutputValueClass(WritablePair.class);

        // 分区函数
        //job.setPartitionerClass(WritablePair.KeyPartitioner.class);
        // 分组函数
        //job.setGroupingComparatorClass(WritablePair.FirstComparator.class);

        return job.waitForCompletion(true) ? 0 : 1;
    }

    public static void main(String[] args) throws Exception{
        System.out.println(ToolRunner.run(new SortDriver(), args));
    }
}
