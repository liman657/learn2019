package com.learn.java8strategy.foundation;

import com.learn.java8strategy.foundation.common.Customer;
import com.learn.java8strategy.foundation.common.Order;
import com.learn.java8strategy.foundation.common.Person;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.*;

import static junit.framework.TestCase.assertTrue;

/**
 * autor:liman
 * createtime:2020/12/20
 * comment:
 * 对应java攻略:JAVA常见问题的简单解法一书中的第三章
 */
@Slf4j
public class StreamOperateLearn {

    public static void main(String[] args) {
        createStreamDemo();
        reduceDemo();
        reduceOrderCheckDemo();
        reducePeekDemo();
        string2StreamDemo();
        reduceGetCountDemo();
        reduceCollectDemo();
        reduceFindFirstElementDemo();
        streamMatchDemo();
        streamMapAndFlatMap();
    }

    /**
     * 创建流的几种方式
     * Stream.of 底层也是调用的Arrays.stream方法
     * Stream.iterate
     * Stream.generate
     * Iterable接口
     * Arrays.stream方法
     */
    public static void createStreamDemo() {
        //Stream.of创建流
        String names = Stream.of("Gomez", "Morticia", "Wednesday", "Pugsley").collect(Collectors.joining(","));
        log.info("stream.of创建的流为:{}", names);

        //Arrays.stream方法创建流，需要提前准备好数组数据
        String[] munsters = {"Herman", "Lily", "Eddie", "Marilyn", "Grandpa"};
        names = Arrays.stream(munsters).collect(Collectors.joining(","));

        log.info("Arrays.stream创建的流为:{}", names);

        //Strem.iterate创建一个无限顺序的有序流
        List<BigDecimal> nums = Stream.iterate(BigDecimal.ONE, n -> n.add(BigDecimal.ONE)).limit(10).collect(Collectors.toList());
        log.info("Stream.iterate创建的流为:{}", nums);

        List<LocalDate> days = Stream.iterate(LocalDate.now(), ld -> ld.plusDays(1L)).limit(10).collect(Collectors.toList());
        log.info("Stream.iterate创建的日期流为:{}", days);

        List<Double> generateNums = Stream.generate(Math::random).limit(10).collect(Collectors.toList());
        log.info("Stream.generate创建的数字流为:{}", generateNums);

        //通过集合创建stream，实现了Collection接口的集合才能通过集合本身调用stream方法，数组不行
        List<String> bradyBunch = Arrays.asList("Greg", "Marcia", "Peter", "Jan", "Bobby", "Cindy");
        List<String> collectStream = bradyBunch.stream().collect(Collectors.toList());
        log.info("集合创建的Stream结果为：{}", collectStream);

        //range和rangeClosed创建流
        List<Integer> intsRangeStream = IntStream.range(10, 15).boxed().collect(Collectors.toList());
        log.info("int range产生的stream:{}", intsRangeStream);

        List<Long> longRangeStream = LongStream.rangeClosed(10, 15).boxed().collect(Collectors.toList());
        log.info("long rangeClosed 参数的stream:{}", longRangeStream);
    }

    /**
     * 装箱流实例——对流中的数据做一个简单的类型转换，转成成另一个数据类型的集合
     * createStreamDemo中针对字符串的Collectors.toList，能较好的将字符流转换成字符集合，但是针对基本的数据类型则不行
     */
    public static void boxedStreamDemo() {
        /**
         * 这里编译会报错 因为IntStream复写了Stream接口中的collect方法，其中不再接受List<Object>类型的集合
         *
         */
        //IntStream.of(3,1,5,2,5,77,8).collect(Collectors.toList())

        /**
         * 第一种方法，通过boxed方法完成数据的包装，形成一个集合，IntStream中的boxed最终会返回一个Stream<Integer>的stream
         */
        IntStream.of(3, 1, 5, 2, 5, 77, 8).boxed().collect(Collectors.toList());

        /**
         * 第二种方法：<U> Stream<U> mapToObj(IntFunction<? extends U> mapper);
         * 看mapToObj方法的签名就知道了
         */
        IntStream.of(3, 1, 5, 2, 5, 77, 8).mapToObj(Integer::valueOf).collect(Collectors.toList());

        /**
         * 第三种方法，就是直接采用IntStream中的collect的参数来
         * 第一个参数是Supplier，第二个参数是accumulator累加器，第三个参数是：combiner组合器
         */
        IntStream.of(3, 1, 5, 2, 5, 77, 8).collect(ArrayList::new, ArrayList::add, ArrayList::addAll);

        IntStream.of(3, 1, 5, 2, 5, 77, 8).toArray();//直接返回数组。
    }

    /**
     * reduce 归约的实例
     * 归约——通过流操作生成单一值
     * map-reduce中的reduce就是这个思想
     * Java的函数式范式经常采用 映射-筛选-归约（map-filter-reduce）的过程处理数据
     */
    public static void reduceDemo() {

        //1. 内置归约操作
        //sum，count,max,min,average,min都是归约方法，如果流中没有元素，结果为空或者没定义，这里头某些方法会返回Optional
        String[] strings = "this is an array of strings".split(" ");
        long count = Arrays.stream(strings).map(String::length).count();
        log.info("there are {} strings ", count);

        int totalLength = Arrays.stream(strings).mapToInt(String::length).sum();
        log.info("the total length is : {}", totalLength);

        OptionalDouble ave = Arrays.stream(strings).mapToInt(String::length).average();
        log.info("the average length is :{}", ave);

        //IntStream中的max和min不需要比较器，采用int的自然顺序完成比较，而Stream中的该方法需要比较器
        OptionalInt max = Arrays.stream(strings).mapToInt(String::length).max();
        OptionalInt min = Arrays.stream(strings).mapToInt(String::length).min();
        log.info("this max length is :{},the min length is :{}", max, min);


        //2. 基本归约实现
        //OptionalInt reduce(IntBinaryOperator op);
        //int reduce(int identity, IntBinaryOperator op);
        //x 和 y 的初始值是范围内的前两个值。二元运算符返回的值在下一次迭代时变为 x（累加器）的值，而 y 依次传入流的每个值。
        int sum = IntStream.rangeClosed(1, 10)
                .reduce((x, y) -> x + y).orElse(0);
        log.info("simple reduce result : {}", sum);

        //如果我们希望先处理每个数字，然后再求和呢？例如，在求和之前将所有的数字增加一倍。
        //错误的写法
        int doubleSumWrong = IntStream.rangeClosed(1, 10)
                .reduce((x, y) -> x + 2 * y).orElse(0);
        log.info("double sum wrong:{}", doubleSumWrong);
        //正确的写法
        int doubleSumRight = IntStream.rangeClosed(1, 10)
                .reduce(0, (x, y) -> x + 2 * y);
        log.info("double sum right:{}", doubleSumRight);

        /**
         * 3. Java标准库中的二元运算符
         * 用二元运算符来简化reduce
         * 标准库引入的一些新方法使归约操作变得特别简单.
         */
        //二元运算符求和
        Integer sumOperatorResult = Stream.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
                .reduce(0, Integer::sum);
        log.info("binry opertor sum result:{}", sumOperatorResult);

        //二元运算符求最大值
        Integer maxOperatorResult = Stream.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
                .reduce(Integer.MIN_VALUE, Integer::max);
        log.info("binary operator max result:{}", maxOperatorResult);

        //关于String的拼接
        /**
         * 这里的String::concat方法，本身只接受一个参数，按理说是不能适配与reduce的第二个参数的
         * 但是String类型调用方法引用的时候，第一个参数将作为concat的目标，而第二个参数是concat的参数。
         * 由于返回结果是String类型，因此还是可以适配reduce的第二个参数
         */
        String concatStringResult = Stream.of("this", "is", "a", "list")
                .reduce("", String::concat);
        log.info("binary opertor string concat result:{}", concatStringResult);

        /**
         * concat效率比较慢，
         */
        String collectStrResult = Stream.of("this", "is", "a", "list")
                .collect(StringBuilder::new,
                        (sb, str) -> sb.append(str),
                        (sb1, sb2) -> sb1.append(sb2)).toString();
        log.info("reduce string result:{}", collectStrResult);

        /**4. reduce方法的最一般形式
         *  <U> U reduce(U identity, BiFunction<U,? super T,U> accumulator, BinaryOperator<U> combiner)
         *
         */
        List<String> names = Arrays.asList("Tom", "Grace", "Ada", "Karen");
        //
        List<Person> peopleList = names.stream().map(name -> new Person(name)).collect(Collectors.toList());

        peopleList.stream().reduce(new HashMap<String, Person>(),
                (map, person) -> {
                    map.put(person.getUserId(), person);
                    return map;
                }, (map1, map2) -> {
                    map1.putAll(map2);
                    return map1;
                });

    }

    /**
     * reduce 操作实现校验排序实例
     * 利用reduce操作检查排序是否正确
     */
    public static void reduceOrderCheckDemo() {
        List<String> strings = Arrays.asList("this", "is", "a", "list", "of", "strings");

        List<String> sorted = strings.stream()
                .sorted(Comparator.comparingInt(String::length))
                .collect(Collectors.toList());
        log.info("排序后的字符顺序为:{}", sorted);

        //检测字符排序是否正确
        sorted.stream().reduce((prev, cur) -> {
            assertTrue(prev.length() <= cur.length());
            return cur;
        });
    }

    /**
     * reduce peek的操作
     * 在处理流时可以通过peek查看流中的各个元素。
     */
    public static void reducePeekDemo() {
        int peekStart = IntStream.rangeClosed(0, 200)
                //如果想打印stream数据流每一步的数据，可以利用在真正map之前加一个map操作，用于输出.
                .map(n -> {
                    System.out.print(n + " ");
                    return n;
                })
                .map(n -> n * 2)
                .filter(n -> n % 3 == 0)
                .sum();
        System.out.println();
        log.info("peek 的简单原理，结果为:{}", peekStart);

        int peekResult = IntStream.rangeClosed(0, 100)
                .peek(n -> System.out.printf("original: %d%n", n))
                .map(n -> n * 2)
                .peek(n -> System.out.printf("doubled : %d%n", n))
                .filter(n -> n % 3 == 0)
                .peek(n -> System.out.printf("filtered: %d%n", n))
                .sum();
        log.info("peek的最终结果:{}", peekResult);
    }

    /**
     * 字符串转换为流实例
     * 通过惯用的流处理技术（而不是对 String 中的各个字符进行循环）实现字符串与
     * 流之间的转换。
     * <p>
     * String 不属于集合框架（collections framework），因此无法
     * 实现 Iterable，不存在一种能将 String 转换为 Stream 的 stream 工厂方法。
     * <p>
     * 尽管 Arrays.stream 提供了
     * 用于处理 int[]、 long[]、 double[] 甚至 T[] 的方法，却并未定义用于处理 char[] 的方法。
     * <p>
     * String 类实现 CharSequence 接口，它
     * 引入了两种能生成 IntStream 的方法（chars 和 codePoints），它们都是接口中的默认方法，
     * 因此存在可用的实现。
     * <p>
     * default IntStream chars()
     * default IntStream codePoints()
     * <p>
     * chars 和 codePoints 方法的不同之处在于， chars 方法用于处理 UTF-16 编码字符，而
     * codePoints 方法用于处理完整的 Unicode 代码点（code point）集。
     */
    public static void string2StreamDemo() {
        String str = "hdfssfdh";
        boolean oldresult = isPalindrome(str);
        boolean newresult = isPalindromeNewVersion(str);
        log.info("判断回文字符串结果:老：{}，新：{}", oldresult, newresult);
    }

    /**
     * Java7中判断回文字符串的方法
     *
     * @param s
     * @return
     */
    private static boolean isPalindrome(String s) {
        StringBuilder sb = new StringBuilder();
        for (char c : s.toCharArray()) {
            if (Character.isLetterOrDigit(c)) {
                sb.append(c);
            }
        }
        String forward = sb.toString().toLowerCase();
        String backward = sb.reverse().toString().toLowerCase();
        return forward.equals(backward);
    }

    /**
     * java8中判断回文字符串
     *
     * @param s
     * @return
     */
    private static boolean isPalindromeNewVersion(String s) {
        //codePoints返回一个IntStream（每个字符的ascll码的数值流）
        String forward = s.toLowerCase().codePoints()
                .filter(Character::isLetterOrDigit)

                //有意思的是collect方法，这三个参数详细参见书籍
                .collect(StringBuilder::new,
                        StringBuilder::appendCodePoint,
                        StringBuilder::append)
                .toString();

        String backward = new StringBuilder(forward).reverse().toString();
        return forward.equals(backward);

    }

    /**
     * reduce 获取元素数量
     * 获取流中元素的数量
     * 使用 java.util.stream.Stream 接口定义的 count 方法，或 java.util.stream.Collectors
     * 类定义的 counting 方法
     */
    public static void reduceGetCountDemo() {
        long streamCount = Stream.of(3, 1, 4, 1, 5, 9, 2, 6, 5).count();
        log.info("stream count result : {}", streamCount);

        /**
         * partitioningBy 方法的第一个参数是 Predicate，其作用是将字符串分为满足谓词和不
         * 满足谓词的两类。如果 partitioningBy 方法只有这一个参数，则结果为 Map<Boolean,
         * List<String>>，其中键为 true 和 false，值为偶数长度和奇数长度字符串的列表。
         * 本例采用 partitioningBy 方法的双参数重载形式，它传入 Predicate 和 Collector。 Collector
         * 被称为下游收集器，用于对返回的每个字符串列表进行后期处理。这就是 Collectors.
         * counting 方法的用例。
         */
        List<String> strings = Arrays.asList("this", "is", "a", "list", "of", "strings");
        Map<Boolean, Long> numberLengthMap = strings.stream().collect(Collectors.partitioningBy(s -> s.length() % 2 == 0, Collectors.counting()));
        log.info("number length map result:{}", numberLengthMap);
    }

    /**
     * reduce 获取数值流中元素的数量、总和、最小值、最大值以及平均值
     */
    public static void reduceCollectDemo() {

        /**
         * 基本类型流 IntStream、 DoubleStream 与 LongStream 为 Stream 接口引入了用于处理基本数
         * 据类型的方法， summaryStatistics 就是其中一种方法
         */
        DoubleSummaryStatistics statsNumbers = DoubleStream.generate(Math::random)
                .limit(1_000)
                .summaryStatistics();
        log.info("统计数据结果为:{}", statsNumbers);
    }

    /**
     * reduce 查找流的第一个元素
     * 查找满足流中特定条件的第一个元素
     * findFirst 或 findAny 方法
     */
    public static void reduceFindFirstElementDemo() {
        Optional<Integer> firstElement = Stream.of(3, 1, 4, 1, 5, 9, 2, 6, 5)
                .filter(n -> n % 2 == 0)
                .findFirst();

        Optional<Integer> anyElement = Stream.of(3, 1, 4, 1, 5, 9, 2, 6, 5)
                .filter(n -> n % 2 == 0)
                .findAny();

        log.info("查找第一个元素结果为：{}，查找任意一个元素结果为:{}", firstElement, anyElement);
    }

    /**
     *
     */
    public static void streamMatchDemo() {
        boolean allMatchresult = IntStream.of(2, 3, 5, 7, 11, 13, 17, 19).allMatch(i -> isPrime(i));
        boolean anyMathcResult = IntStream.of(4, 6, 8, 9, 10, 12, 14, 15, 16, 18, 20).anyMatch(i->isPrime(i));
        log.info("allMatch result : {},anyMatch result : {}",allMatchresult,anyMathcResult);
    }

    /**
     * noneMath判断是否是质数
     * @param num
     * @return
     */
    private static boolean isPrime(int num) {
        int limit = (int) (Math.sqrt(num) + 1);
        return num == 2 || num > 1 && IntStream.range(2, limit).noneMatch(divisor -> num % divisor == 0);
    }

    /**
     * <R> Stream<R> map(Function<? super T,? extends R> mapper)
     */
    public static void streamMapAndFlatMap(){
        Customer sheridan = new Customer("Sheridan");
        Customer ivanova = new Customer("Ivanova");
        Customer garibaldi = new Customer("Garibaldi");

        sheridan.addOrder(new Order(1))
                .addOrder(new Order(2))
                .addOrder(new Order(3));
        ivanova.addOrder(new Order(4))
                .addOrder(new Order(5));

        List<Customer> customers = Arrays.asList(sheridan, ivanova, garibaldi);

        //当输入参数和输出类型之间存在一一对应的关系时，将执行 map 操作
        //一个客户名称对应一个客户
        customers.stream().map(Customer::getName).forEach(System.out::println);

        //最终的返回类型Stream<List<Order>>
        customers.stream().map(Customer::getOrders).forEach(System.out::println);
        //最终的返回类型为Stream<Stream<Order>>
        //[Order(id=1), Order(id=2), Order(id=3)] [Order(id=4), Order(id=5)] []
        customers.stream().map(customer->customer.getOrders().stream()).forEach(System.out::println);

        /**
         * 对于每个泛型参数 T，函数生成的是 Stream<R> 而不仅仅是 R。
         * <R> Stream<R> flatMap(Function<? super T,? extends Stream<? extends R>> mapper)
         */
        //最终的返回类型为Stream<Order>
        // Order{id=1}, Order{id=2}, Order{id=3}, Order{id=4}, Order{id=5}
        customers.stream().flatMap(customer->customer.getOrders().stream()).forEach(System.out::println);

    }

    /**
     * 流的拼接 慎用
     * static <T> Stream<T> concat(Stream<? extends T> a, Stream<? extends T> b)
     * 两个输入流所包含的元素类型必须相同才能操作拼接
     */
    public static void streamContactDemo(){
        Stream<String> first = Stream.of("a", "b", "c").parallel();
        Stream<String> second = Stream.of("X", "Y", "Z");
        List<String> strings = Stream.concat(first, second).collect(Collectors.toList());
        List<String> stringList = Arrays.asList("a", "b", "c", "X", "Y", "Z");
    }

    /**
     * 惰性流实例
     * 流是惰性的，在达到终止条件前不会处理元素，达到终止条件后才通过流水线逐一处理每个元素。
     */
    public static void streamLazyDemo(){
        OptionalInt firstEvenDoubleDivBy3 = IntStream.range(100, 200)
                .map(n -> n * 2)
                .filter(n -> n % 3 == 0)
                .findFirst();
        System.out.println(firstEvenDoubleDivBy3);
    }

}
