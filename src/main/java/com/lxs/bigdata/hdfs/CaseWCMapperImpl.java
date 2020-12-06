package com.lxs.bigdata.hdfs;

/**
 * @author Mr.Li
 * @date 2020/6/23 - 13:50
 */
public class CaseWCMapperImpl implements WordCountMapper {

    @Override
    public void map(String line, mapContext mapContext) {
        String[] worlds = line.toLowerCase().split("\t");

        for(String world : worlds){
            if(mapContext.containsKey(world)){
                mapContext.write(world, Integer.parseInt(mapContext.get(world).toString()) + 1);
            }else{
                mapContext.write(world, 1);
            }
        }
    }
}
