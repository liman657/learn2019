package com.learn.jvm.chapter07;

/**
 * autor:liman
 * createtime:2019/12/3
 * comment:永久区是存放类元数据的区域（1.8之后叫元空间）如果一个系统定义了太多的类，那么永久区是有可能溢出的
 * 解决方式：
 * 1、增加MaxPermSize
 * 2、减少系统需要的类的数量
 * 3、使用ClassLoader合理的装载各个类，并定期进行回收
 *
 * TODO：同时GC效率过低也会产生OOM，GC是内存回收的关键，如果GC效率低下，那么系统的性能就会收到影响
 * TODO：如果系统的堆空间太小，那么GC所占的时间就会很多，并且回收所释放的内存就会很少。根据GC占用的系统时间以及GC释放内存的大小
 * TODO：虚拟机会评估GC的效率，一旦虚拟机认为GC的效率过低，就直接抛出OOM。但是，虚拟机不会对这个判定太随意
 * TODO：一般情况，会检查以下几个情况：花在GC上的时间是否超过了98%；老年代释放的内存是否小于2%；eden区释放的内存是否小于2%；连续5次GC都出现了上述情况中的同一种
 * TODO：只有同时满足上述所有情况，就会出现OOM——GC overhead limit exceeded
 */
public class PermOOM {

    public static void main(String[] args) {
        try{
            for(int i = 0;i<10000;i++){
            }
        }catch (Exception e){

        }
    }

}
