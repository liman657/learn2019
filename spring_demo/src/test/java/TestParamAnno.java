import com.learn.springmvc.Annotation.SelfRequestParam;
import com.learn.springmvc.TestParam;
import org.junit.Test;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

/**
 * autor:liman
 * comment:
 */
public class TestParamAnno {

    @Test
    public void testAnnoParam(){
        TestParam testParam = new TestParam();

        Method[] methods = testParam.getClass().getDeclaredMethods();
        for(Method method:methods){
            method.setAccessible(true);

            Parameter[] parameters = method.getParameters();
            for(Parameter parameter:parameters){
                System.out.println(parameter);
                Annotation[] annotations = parameter.getAnnotations();
                for(Annotation annotation:annotations){
                    System.out.println(annotation);
                    SelfRequestParam selfRequestParam = (SelfRequestParam)annotation;
                    System.out.println(selfRequestParam.value());
                }
            }
        }
    }

}
