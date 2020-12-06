package com.lxs.bigdata.mr.wc;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;
import java.util.Iterator;

/**
 *  Reducer 和 Mapper 中其实使用到的设计模式： 模板
 */
public class WordCountReducer extends Reducer<Text, IntWritable, Text, IntWritable> {


    /**
     * map 输出到 reduce端， 是按照相同的key分发到一个reduce上去执行
     *
     * reduce1: (hello, 1) (hello, 1) (hello, 1)  ===> (hello, <1,1,1>)
     * reduce2: (world, 1) (world, 1)            ===> (world, <1,1>)
     * @param key       world
     * @param values   <1，1，1>
     * @param context  上下文 将结果写进去
     */
    @Override
    protected void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
        int count = 0;

        //<1,1,1>
        Iterator<IntWritable> iterator = values.iterator();
        while(iterator.hasNext()){
            IntWritable value = iterator.next();
            System.out.println(value);
            count += value.get();
        }
        context.write(key, new IntWritable(count));
    }
}
