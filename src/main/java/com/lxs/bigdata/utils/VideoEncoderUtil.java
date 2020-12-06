//package com.lxs.bigdata.utils;
//
//import ws.schild.jave.*;
//
//import java.io.File;
//
///**
// * @author Mr.Li
// * @date 2020/7/10 - 14:42
// */
//public class VideoEncoderUtil {
//
//    public static void main(String[] args) {
//        //源文件地址：
//        String source = "D:\\workspace_bigdata_01\\hadoop-train-v2\\input\\172.16.38.15_80_1_1594286025241.mp4";
//        File file = new File(source);
//        //转码后生成地址
//        String target ="D:\\workspace_bigdata_01\\hadoop-train-v2\\input\\111.mp4";
//        try {
//            transform(file,target);
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//    }
//
//    /**
//     *
//     * @param source 源文件
//     * @param destPath 目标文件
//     * @return
//     */
//    public static File transform(File source , String destPath) {
//        File target = new File(destPath);
//
//        EncodingAttributes attrs = getEncodingAttributes("aac","libx264","mp4");
//
//        transferEncoder(source, target, attrs);
//
//        return target;
//    }
//
//    /**
//     *
//     * @param source 源文件
//     * @param destPath 目标文件
//     * @param audioCodec 音频格式
//     * @param videoCodec 视频编码格式
//     * @param format 格式
//     * @return
//     */
//    public static File transform(File source , String destPath,String audioCodec,String videoCodec,String format) {
//        File target = new File(destPath);
//
//        EncodingAttributes attrs = getEncodingAttributes(audioCodec,videoCodec,format);
//
//        transferEncoder(source, target, attrs);
//
//        return target;
//    }
//
//    /**
//     *
//     * @param source 源文件
//     * @param target 目标文件
//     * @param attrs 相关配置信息
//     */
//    private static void transferEncoder(File source, File target, EncodingAttributes attrs) {
//        Encoder encoder = new Encoder();
//        try {
//            encoder.encode(new MultimediaObject(source), target, attrs);
//        } catch (Exception e) {
//            System.out.println("视频转码失败:" + e);
//        }
//    }
//
//
//    /**
//     * 转码相关配置信息
//     * @param audioCodec 音频格式
//     * @param videoCodec  视频编码格式
//     * @param format 格式
//     * @return
//     */
//    private static EncodingAttributes getEncodingAttributes(String audioCodec,String videoCodec,String format) {
//        AudioAttributes audio = new AudioAttributes();
//        //audio.setCodec("libmp3lame"); // mp3
//        audio.setCodec(audioCodec);
//        audio.setBitRate(new Integer(36000));
//        audio.setChannels(new Integer(2)); //1 mono 单声道 2 stereo 立体声
//        audio.setSamplingRate(new Integer(44100));
//
//        VideoAttributes video = new VideoAttributes();
//        video.setCodec(videoCodec);
//        video.setBitRate(new Integer(160000));
//        video.setFrameRate(new Integer(15));
//        video.setSize(new VideoSize(400, 300));
//
//        EncodingAttributes attrs = new EncodingAttributes();
//        attrs.setFormat(format);
//        attrs.setAudioAttributes(audio);
//        attrs.setVideoAttributes(video);
//        return attrs;
//    }
//
//
//}
