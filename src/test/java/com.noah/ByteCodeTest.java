package com.noah;

import cn.org.rapid_framework.generator.GeneratorFacade;
import cn.org.rapid_framework.generator.GeneratorProperties;
import net.ptnetwork.entity.CglibBean;
import org.junit.Test;

import javax.sound.sampled.*;
import java.lang.reflect.Method;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

/**
 * Cglib测试类
 *
 * @author cuiran
 * @version 1.0
 */
public class ByteCodeTest {

    /**
     a* @throws ClassNotFoundException
     * @throws Exception
     */
    @Test
    public  void main() throws Exception {


        // 设置类成员属性  
        HashMap propertyMap = new HashMap();

        propertyMap.put("id", Integer.class);

        propertyMap.put("name", String.class);

        propertyMap.put("address", String.class);

        // 生成动态 Bean  
        CglibBean bean = new CglibBean("com.noah", "Test", propertyMap);

//        // 给 Bean 设置值
//        bean.setValue("id", new Integer(123));
//
//        bean.setValue("name", "454");
//
//        bean.setValue("address", "789");
//
//        // 从 Bean 中获取值，当然了获得值的类型是 Object
//
//        System.out.println("  >> id      = " + bean.getValue("id"));
//
//        System.out.println("  >> name    = " + bean.getValue("name"));
//
//        System.out.println("  >> address = " + bean.getValue("address"));

        // 获得bean的实体  
        Object object = bean.getObject();

        // 通过反射查看所有方法名  
        Class clazz = object.getClass();
        Method[] methods = clazz.getDeclaredMethods();
        for (int i = 0; i < methods.length; i++) {
            System.out.println(methods[i].getName());
        }
        Thread.sleep(TimeUnit.SECONDS.toMillis(5));
        play();

    }


    public static void play() {

        try {
            AudioFormat audioFormat =
//                    new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, 44100F,
//                    8, 1, 1, 44100F, false);
                    new AudioFormat(AudioFormat.Encoding.PCM_SIGNED,44100F, 16, 2, 4,
                                    44100F, true);
            DataLine.Info info = new DataLine.Info(TargetDataLine.class, audioFormat); TargetDataLine targetDataLine = (TargetDataLine) AudioSystem.getLine(info);
            targetDataLine.open(audioFormat);
            final SourceDataLine sourceDataLine;
            info = new DataLine.Info(SourceDataLine.class, audioFormat);
            sourceDataLine = (SourceDataLine) AudioSystem.getLine(info);
            sourceDataLine.open(audioFormat);
            targetDataLine.start();
            sourceDataLine.start();
            FloatControl fc=(FloatControl)sourceDataLine.getControl(FloatControl.Type.MASTER_GAIN);
            double value=2;
            float dB = (float)
                    (Math.log(value==0.0?0.0001:value)/Math.log(10.0)*20.0);
            fc.setValue(dB);
            int nByte = 0;
            final int bufSize=4*100;
            byte[] buffer = new byte[bufSize];
            while (nByte != -1) {
                //System.in.read();
                nByte = targetDataLine.read(buffer, 0, bufSize);
                sourceDataLine.write(buffer, 0, nByte);
                System.out.println(new String(buffer));
            }
            sourceDataLine.stop();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}