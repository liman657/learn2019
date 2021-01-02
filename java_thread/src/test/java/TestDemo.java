//import org.junit.Test;

/**
 * autor:liman
 * createtime:2019/7/21
 * comment:
 */
public class TestDemo {

//    @Test
    public void testResizeStamp(){
        int n = 16;
        int stamp = Integer.numberOfLeadingZeros(n)|(1<<(16-1));
        System.out.println(stamp);
    }

}
