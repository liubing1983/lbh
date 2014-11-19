package com.lb.hadoop.mr.sort;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.Partitioner;

/**
 * Created by lb on 2014/11/18.
 */
public class WritablePair  implements WritableComparable<WritablePair>{

    private Text first;
    private Text second;

    public WritablePair(){
        set(new Text(),new Text());
    }

    public WritablePair(Text first,String second){
        set(first,new Text(second));
    }

    public WritablePair(Text first,Text second){
        set(first,second);
    }

    public void set(Text first ,Text second){
        this.first = first;
        this.second = second;
    }

    public Text getFirst(){
        return first;
    }

    public Text getSecond(){
        return second;
    }

    @Override
    public void readFields(DataInput in) throws IOException {
        first.readFields(in);
        second.readFields(in);
    }

    @Override
    public void write(DataOutput out) throws IOException {
        first.write(out);
        second.write(out);
    }

    public int hashcode(){
        return first.hashCode()*163+second.hashCode();
    }

    public boolean equals(Object o){
        if(o instanceof WritablePair){
            WritablePair tp = (WritablePair)o;
            return first.equals(tp.first) && second.equals(tp.second);
        }
        return false;
    }

    public String toString(){
        return first +"\t"+second;
    }

    @Override
    public int compareTo(WritablePair tp) {
        int cmp = first.compareTo(tp.first);
        if(cmp !=0){
            return cmp;
        }
        return second.compareTo(tp.second);
    }

    public static class FirstComparator extends WritableComparator{

        private static final Text.Comparator TEXT_COMPARATOR = new Text.Comparator();

        protected FirstComparator(Class<? extends WritableComparable> keyClass) {
            super(keyClass);
        }
        public FirstComparator(){
            super(WritablePair.class);
        }

        public int compare(byte[] b1,int s1,int l1,byte[] b2,int s2,int l2){

            int firstL1 = 0;
            int firstL2 = 0;
            try {
                firstL1 = WritableUtils.decodeVIntSize(b1[s1]) + readVInt(b1,s1);
                firstL2 = WritableUtils.decodeVIntSize(b2[s2]) + readVInt(b2,s2);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return TEXT_COMPARATOR.compare(b1 , s1, firstL1 , b2 , s2 , firstL2);
        }

        public int compare(WritableComparable a, WritableComparable b){
            if(a instanceof WritablePair && b instanceof WritablePair){
                return ((WritablePair)a).first.compareTo(((WritablePair)b).first);
            }
            return super.compare(a, b);
        }

    }

    /**
     * 分区函数类。根据first确定Partition。
     */
    public static class KeyPartitioner extends Partitioner<WritablePair, NullWritable> {

        @Override
        public int getPartition(WritablePair key, NullWritable value, int numPartitions) {
            return (key.getFirst().hashCode() & Integer.MAX_VALUE) % numPartitions;
        }

    }

}
