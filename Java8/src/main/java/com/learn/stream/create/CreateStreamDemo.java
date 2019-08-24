package com.learn.stream.create;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.function.Supplier;
import java.util.stream.Stream;

/**
 * autor:liman
 * createtime:2019/8/22
 * comment:创建stream流的几种方式
 * 1、Collection.Stream()静态方法
 * 2、Arrays.stream()中的静态方法
 * 3、Stream.of()静态方法获得流
 * 4、Stream.iterate()和Stream.generate创建流
 */
public class CreateStreamDemo {

    public static void main(String[] args) {

//        Stream<String> streamFromCollection = createStreamFromCollection();
//        streamFromCollection.forEach(System.out::println);

//        Stream<String> streamFromValues = createStreamFromValues();
//        streamFromValues.forEach(System.out::println);

//        Stream<String> streamFromArrays = createStreamFromArrays();
//        streamFromArrays.forEach(System.out::println);

        createStreamFromFile();
    }

    /**
     * Collection接口及其子类都能利用stream()方法构建stream流。
     *
     * @return
     */
    public static Stream<String> createStreamFromCollection() {
        List<String> list = Arrays.asList("hello", "alex", "liman", "world", "stream");
        return list.stream();
    }

    /**
     * 直接利用Stream的ofStream方法构建Stream
     *
     * @return
     */
    public static Stream<String> createStreamFromValues() {
        return Stream.of("hello", "alex", "liman", "world", "stream");
    }

    /**
     * Arrays并没有继承至Collections但是也有自己的Stream方法
     *
     * @return
     */
    public static Stream<String> createStreamFromArrays() {
        String[] strings = {"hello", "alex", "liman", "world", "stream"};
        return Arrays.stream(strings);
    }

    public static Stream<Integer> createStreamFromIterator() {
        Stream<Integer> iterate = Stream.iterate(0, n -> n+2);
        return iterate;
    }

    public static Stream<Double> createStreamFromGenerate(){
        Stream<Double> stream = Stream.generate(Math::random);
        return stream;
    }

    public static Stream<String> createStreamFromFile() {
        Path path = Paths.get("F:\\gupaoProject2019\\Java8\\src\\main\\java\\com\\learn\\stream\\common\\DishContainer.java");
        try (Stream<String> streamFromFile = Files.lines(path)) {
            streamFromFile.forEach(System.out::println);
            return streamFromFile;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static Stream<Obj> createObjStreamFromGenerate() {
        return Stream.generate(new ObjSupplier()).limit(10);
    }

    static class ObjSupplier implements Supplier<Obj> {

        private int index = 0;

        private Random random = new Random(System.currentTimeMillis());

        @Override
        public Obj get() {
            index = random.nextInt(100);
            return new Obj(index, "Name->" + index);
        }
    }

    static class Obj {
        private int id;
        private String name;

        public Obj(int id, String name) {
            this.id = id;
            this.name = name;
        }

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        @Override
        public String toString() {
            return "Obj{" +
                    "name='" + name + '\'' +
                    ", id=" + id +
                    '}';
        }
    }

}
