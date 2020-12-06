package com.lxs.bigdata.mr.wc;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

/**
 * 使用MR统计HDFS上文件对应的词频
 */
public class WordCountApp {

    public static void main(String[] args) throws Exception {

        System.setProperty("HADOOP_USER_NAME", "root");

        // 1） 读取HDFS上的文件
        Configuration configuration = new Configuration();
/*
        //设置副本系数 默认会去配置文件中加载为3
        configuration.set("dfs.replication", "1");
        //是否客户端应该使用DN的HostName，在连接DN时，默认是使用IP。
        configuration.set("dfs.client.use.datanode.hostname", "true");
*/

        configuration.set("fs.defaultFS", "hdfs://xuesen000:8020");

        //创建一个Job
        Job job = Job.getInstance(configuration);

        // 设置job对应的 主类
        job.setJarByClass(WordCountApp.class);

        // 设置job对应的参数： 设置自定义的Mapper 和 Reducer处理类
        job.setMapperClass(WordCountMapper.class);
        job.setReducerClass(WordCountReducer.class);

        // 设置job对应的参数： Map输出key value 的类型
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(IntWritable.class);

        // 设置job对应的参数： Reducer输出 key value 的类型
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);

        // 设置job对应的参数：Mapper输出 key value 的类型： 作业输入和输出的路径
        FileInputFormat.setInputPaths(job, new Path("/wordcount/input"));
        FileOutputFormat.setOutputPath(job, new Path("/wordcount/output"));


        // 提交job
        boolean result = job.waitForCompletion(true);

        System.exit(result ? 0 : -1);

    }
}
