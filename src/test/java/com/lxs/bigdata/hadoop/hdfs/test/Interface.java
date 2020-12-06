package com.lxs.bigdata.hadoop.hdfs.test;


import sun.misc.BASE64Decoder;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.sql.Timestamp;
import java.util.Date;

/**
 * @author Mr.Li
 * @date 2020/7/1 - 9:42
 */
public class Interface {


    /**
     * base64转图片
     * @param base64str base64码
     * @param savePath 图片路径
     * @return
     */
    public static boolean generateImage(String base64str, String savePath) {
        //对字节数组字符串进行Base64解码并生成图片
        if (base64str == null) {
            return false;
        }
        BASE64Decoder decoder = new BASE64Decoder();
        try {
            //Base64解码
            byte[] b = decoder.decodeBuffer(base64str);
            for (int i = 0; i < b.length; ++i) {
                //调整异常数据
                if (b[i] < 0) {
                    b[i] += 256;
                }
            }
            //生成jpeg图片
            OutputStream out = new FileOutputStream(savePath);
            out.write(b);
            out.flush();
            out.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private void saveAuthUserAva(String inoutrecordId,String userAva){
        //第一种方法：使用new Timestamp(long)
        String path = "D\\:\\upload\\doorAva";
        File file = new File(path);
        if(!file.exists()){
            file.mkdirs();
        }
        Timestamp curTime = new Timestamp(new Date().getTime());
        String imgName = "test.jpg";
        String savePath=path+"\\"+imgName;
        Boolean res = generateImage(userAva,savePath);
    }

    /**
     * 保存人员出入口记录
     */
/*    @Before(POST.class)
    public void savePersonInOut(){
        String warehouseId = getPara("warehouse_id");//仓库id
        String doorCard = getPara("door_card");//出入库人卡号
//        String userName = getPara("user_name");//出入库人名称
        String unlockType = getPara("unlock_type");//开锁方式
        int action= getParaToInt("action");//出入库状态 1入库，2出库,3开锁失败
        String userAva= getPara("user_ava");//base64头像图片
        String remark = getPara("remark");//备份
        String uuidIn="15648643548478456484847";
        saveAuthUserAva(uuidIn,userAva);


    }*/


}
