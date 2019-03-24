package com.learn.JSON;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.learn.JSON.domain.Speaker;
import org.junit.Test;

import javax.swing.*;
import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.*;

/**
 * autor:liman
 * comment: JSON基础类型测试
 */
public class BasicJsonTypesTest {

    private static final String TEST_SPEAKER = "age=39"+"fullName=\"liman\""+"tags=[\"JavaScript\",\"AngularJS\",\"Yeoman\"]"+"registered=true";

    @Test
    public void serializeBasicTypes(){
        try{
            ObjectMapper mapper = new ObjectMapper();
            Writer writer = new StringWriter();
            int age = 18;
            String fullName = new String("liman");
            List<String> tags = new ArrayList<String>();
            Arrays.asList("JavaScript","AngularJs","Yeoman");

            boolean registered = true;
            String speaker = null;

            writer.write("age=");
            //writeValue方法将Java数据类型转换为JSON，这里是将转换结果输出到了writer对象中
            mapper.writeValue(writer,age);
            writer.write("\nfullName=");
            mapper.writeValue(writer,fullName);
            writer.write("\ntags=");
            mapper.writeValue(writer,tags);
            writer.write("\nregistered=");
            mapper.configure(SerializationFeature.INDENT_OUTPUT,true);
            mapper.writeValue(writer,registered);
            speaker = writer.toString();
            System.out.println(speaker);

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void deSerializeBasicTypes(){
        try{
            String ageJson = "{\"age:\"39}";

            ObjectMapper mapper = new ObjectMapper();
            //readValue将Java数据类型转换为JSON
            Map<String,Object> ageMap = mapper.readValue(ageJson,new TypeReference<HashMap<String,Integer>>(){});
            Integer age = (Integer) ageMap.get("age");
            System.out.println("age="+age+"\n");

        }catch (Exception e){

        }
    }

    /**
     * 将一个对象序列化成json数据
     */
    @Test
    public void serizlizeObject(){
        try{
            ObjectMapper mapper = new ObjectMapper();
            Writer writer = new StringWriter();
            String[] tags = {"JavaScript","AngularJS","Yeoman"};
            Speaker speaker = new Speaker(1,18,"liman",tags,true);
            String speakerStr = null;

            mapper.configure(SerializationFeature.INDENT_OUTPUT,true);
            //将对象输出到json字符串
            speakerStr = mapper.writeValueAsString(speaker);
            System.out.println(speakerStr);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 将json数据序列化成对象
     */
    @Test
    public void deSerializeObject(){
        try{
            ObjectMapper mapper = new ObjectMapper();
            String[] tags = {"JavaScript","AngularJS","Yeoman"};
            Speaker speaker = new Speaker(1,18,"liman",tags,true);
            mapper.configure(SerializationFeature.INDENT_OUTPUT,true);
            String speakerStr = mapper.writeValueAsString(speaker);

            //将json数据输入到json文件中
            FileOutputStream fileOutputStream = new FileOutputStream("speaker.json");
            fileOutputStream.write(speakerStr.getBytes());
            File speakerFile = new File("speaker.json");

            //从json文件中读取数据
            Speaker speakerFromFile = mapper.readValue(speakerFile,Speaker.class);
            System.out.println(speakerFromFile);
            System.out.println(speakerFromFile.getFullName());

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 将json解析成多个对象
     */
    @Test
    public void deSerializeMultipleObjects(){
        try{
            ObjectMapper mapper = new ObjectMapper();
//            String[] tags = {"JavaScript","AngularJS","Yeoman"};
//            List<Speaker> speakerList = new ArrayList<>();
//            for(int i=1;i<=10;i++){
//                Speaker speaker = new Speaker(i,18,"liman"+i,tags,true);
//                speakerList.add(speaker);
//            }
////            Speaker speaker = new Speaker(1,18,"liman",tags,true);
//            mapper.configure(SerializationFeature.INDENT_OUTPUT,true);
//            String speakerStr = mapper.writeValueAsString(speakerList);
//            //将json数据输入到json文件中
//            FileOutputStream fileOutputStream = new FileOutputStream("speakerList.json");
//            fileOutputStream.write(speakerStr.getBytes());
            File speakerFile = new File("speakerList.json");

            JsonNode arrNode = mapper.readTree(speakerFile).get("speakers");
            List<Speaker> speakers = new ArrayList<Speaker>();
            if(arrNode.isArray()){
                for(JsonNode objNode:arrNode){
                    System.out.println(objNode);
                    //将节点中的数据反序列化成Speaker对象
                    speakers.add(mapper.convertValue(objNode,Speaker.class));
                }
            }

            for(Speaker sp :speakers){
                System.out.println(sp.toString());
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}