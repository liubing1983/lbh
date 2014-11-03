package com.lb.mr.counter;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;


/**
 * Created by lb on 2014/11/3.
 */
public class CounterDriver implements  Tool{

    @Override
    public Configuration getConf() {
        return null;
    }

    @Override
    public void setConf(Configuration arg0) {

    }

    @Override
    public int run(String[] arg0) throws Exception {

        return 0;
    }

    public static void main(String[] args) throws Exception{
        int i = ToolRunner.run(new CounterDriver() , args);
        System.exit(i);
    }
}

