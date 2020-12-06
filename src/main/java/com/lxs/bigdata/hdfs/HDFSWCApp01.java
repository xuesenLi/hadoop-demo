package com.lxs.bigdata.hdfs;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.Map;
import java.util.Properties;

/**
 * 使用HDFS API完成word count 统计
 * 需求： 统计HDFS上的文件的wc, 然后将统计结果输出到HDFS
 *
 * 功能拆解：
 * 1） 读取HDFS上的文件
 * 2） 业务处理（词频统计）：对文件中的每一行数据都要进行业务处理（按照分隔符分割）
 * 3） 将处理结果缓存起来
 * 4） 将结果输出到HDFS
 *
 * @author Mr.Li
 * @date 2020/6/21 - 20:19
 */
public class HDFSWCApp01 {

    public static void main(String[] args) throws Exception {
        // 1） 读取HDFS上的文件
        Configuration configuration =  new Configuration();
        //设置副本系数 默认回去配置文件中加载为3
        configuration.set("dfs.replication", "1");
        //是否客户端应该使用DN的HostName，在连接DN时，默认是使用IP。
        configuration.set("dfs.client.use.datanode.hostname", "true");

        Properties properties = ParamsUtils.getProperties();


        Path input = new Path(properties.getProperty(Constants.INPUT_PATH));
        FileSystem fs = FileSystem.get(new URI(properties.getProperty(Constants.HDFS_URI)), configuration, "root");
        RemoteIterator<LocatedFileStatus> iterator = fs.listFiles(input, false);

        //TODO... 通过反射创建对象
        Class<?> clazz = Class.forName(properties.getProperty(Constants.MAPPER_CLASS));
        WordCountMapper mapper = (WordCountMapper) clazz.newInstance();
        //WordCountMapper mapper = new WordCountMapperImpl();

        mapContext mapContext = new mapContext();

        while(iterator.hasNext()){
            LocatedFileStatus fileStatus = iterator.next();
            FSDataInputStream in = fs.open(fileStatus.getPath());
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            String line = "";

            while ((line = reader.readLine()) != null){
                // 2） 业务处理（词频统计）
                mapper.map(line, mapContext);
            }

            reader.close();
            in.close();
        }

        // 3） 将处理结果缓存起来
        Map<Object, Object> map = mapContext.getCacheMap();

        // 4） 将结果输出到HDFS
        Path output = new Path(properties.getProperty(Constants.OUTPUT_PATH));

        FSDataOutputStream out = fs.create(new Path(output, properties.getProperty(Constants.OUTPUT_FILE)));
        //
        map.forEach((k, V) -> {
            try {
                out.write((k.toString() + " \t" + V.toString() + "\n").getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        out.close();
        fs.close();
        System.out.println("------------统计词频-----------");

    }


}
