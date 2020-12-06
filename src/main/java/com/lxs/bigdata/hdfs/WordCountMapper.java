package com.lxs.bigdata.hdfs;

/**
 * @author Mr.Li
 * @date 2020/6/22 - 22:16
 */
public interface WordCountMapper {

    public void map(String line, mapContext mapContext);

}
