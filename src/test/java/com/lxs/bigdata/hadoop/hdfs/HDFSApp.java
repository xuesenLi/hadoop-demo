package com.lxs.bigdata.hadoop.hdfs;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.*;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.util.Progressable;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.net.URI;

/**
 *  未测试的:
 *  拷贝本地文件到HDFS :  copyFromLocalFile
 *  拷贝本地大文件到HDFS（带进度） :
 *
 *  拷贝HDFS到本地 :  copyToLocalFile
 *
 *  查看目标文件夹下所有的文件:  listStatus
 *  查看目标文件夹下所有的文件(递归):  listFiles(path, true)
 *
 *  查看文件块信息 ： getFileStatus
 *
 *  删除文件 ： delete
 * @author Mr.Li
 * @date 2020/6/19 - 15:24
 */
public class HDFSApp {

    Configuration configuration = null;

    FileSystem fileSystem = null;

    public static final String HDFS_PATH = "hdfs://xuesen000:8020";


    @Before
    public void setUp() throws Exception{
        System.out.println("------------------setUp------------------");
        configuration = new Configuration();
        //设置副本系数 默认回去配置文件中加载为3
        configuration.set("dfs.replication", "1");
        //是否客户端应该使用DN的HostName，在连接DN时，默认是使用IP。
        configuration.set("dfs.client.use.datanode.hostname", "true");

        fileSystem = FileSystem.get(new URI(HDFS_PATH), configuration, "root");
    }

    @Test
    public void delete() throws IOException {
        System.out.println(fileSystem.delete(new Path("/hdfsapi/README.txt"), true));

    }
    /**
     * 查看文件块信息
     * @throws IOException
     */
    @Test
    public void getFileStatus() throws IOException{
        FileStatus fileStatus = fileSystem.getFileStatus(new Path("/hdfsapi/java.tar.gz"));
        BlockLocation[] blocks = fileSystem.getFileBlockLocations(fileStatus, 0, fileStatus.getLen());
        for(BlockLocation block: blocks){
            for(String name : block.getNames()){
                System.out.println(name + ": " + block.getOffset() + " : " + block.getLength() + ": " + block.getHosts());
            }
        }


    }

    /**
     * 查询所有文件
     * @throws IOException
     */
    @Test
    public void listStatus() throws IOException {
        FileStatus[] fileStatuses = fileSystem.listStatus(new Path("/hdfsapi"));
        for(FileStatus f : fileStatuses){
            String isDir = f.isDirectory() ? "文件夹" : "文件";
            String permission = f.getPermission().toString();
            short replication = f.getReplication();
            long len = f.getLen();
            String path = f.getPath().toString();
            System.out.println(isDir + "\t" + permission + "\t" + replication+ "\t" +
                    len + "\t" + path);
        }
    }

    /**
     * 递归 查询所有文件
     * @throws IOException
     */
    @Test
    public void listFiles() throws IOException {
        RemoteIterator<LocatedFileStatus> locatedFileStatusRemoteIterator = fileSystem.listFiles(new Path("/hdfsapi"), true);
        while(locatedFileStatusRemoteIterator.hasNext()){
            LocatedFileStatus f = locatedFileStatusRemoteIterator.next();
            String isDir = f.isDirectory() ? "文件夹" : "文件";
            String permission = f.getPermission().toString();
            short replication = f.getReplication();
            long len = f.getLen();
            String path = f.getPath().toString();
            System.out.println(isDir + "\t" + permission + "\t" + replication+ "\t" +
                    len + "\t" + path);
        }
    }


    /**
     * 拷贝本地大文件到HDFS（带进度） :
     * @throws IOException
     */
    @Test
    public void copyFromLocalBigFile() throws IOException{
        InputStream in = new BufferedInputStream(new FileInputStream(new File("E:\\Markdown文件\\centOS\\jdk-8u101-linux-x64.tar.gz")));
        FSDataOutputStream out = fileSystem.create(new Path("/hdfsapi/java.tar.gz"),
                new Progressable() {
                    @Override
                    public void progress() {
                        System.out.print(".");
                    }
                });
        IOUtils.copyBytes(in, out, 4096);

    }

    @Test
    public void run() throws Exception{
        fileSystem.mkdirs(new Path("/hdfsapi"));
    }

    /**
     * 读取文件
     * @throws IOException
     */
    @Test
    public void test() throws IOException {
        FSDataInputStream in = fileSystem.open(new Path("/hdfsapi/hello.txt"));
        IOUtils.copyBytes(in, System.out, 4096, false);
    }

    /**
     * 写文件
     * @throws IOException
     */
    @Test
    public void test1() throws IOException {
        FSDataOutputStream out = fileSystem.create(new Path("/hdfsapi/hello.txt"));
        out.writeUTF("hello"+ "\t" +"lxs"  + "\t" + "lxs");
        out.flush();
        out.close();
    }

    @Test
    public void rename() throws Exception{
        Path oldPath = new Path("/hdfsapi/success.txt");
        Path newPath = new Path("/hdfsapi/falt.txt");
        System.out.println(fileSystem.rename(oldPath, newPath));

    }

    @Test
    public void testReplication(){
        System.out.println(configuration.get("dfs.replication"));
    }

    @After
    public void tearDown(){
        configuration = null;
        fileSystem = null;
        System.out.println("-------------------tearDown--------------");
    }


    @Test
    public void javaVersion(){
        String property = System.getProperty("java.version");
        System.out.println(property);
        System.out.println(System.getProperty("java.version").substring(0, 3));
        System.out.println(System.getProperty("java.version").substring(0, 3).compareTo("1.7") >= 0);


    }
}
