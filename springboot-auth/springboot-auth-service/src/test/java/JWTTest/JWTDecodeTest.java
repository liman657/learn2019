package JWTTest;

import com.learn.springauthserver.utils.JWTUtils;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * autor:liman
 * createtime:2020/9/15
 * comment:
 */
@Slf4j
public class JWTDecodeTest {

    @Test
    public void testJwtDecode(){
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiIxIiwic3ViIjoibGltYW4iLCJpc3MiOiJjb2RlIGZvciBmYW5nZmFuZyIsImlhdCI6MTYwMDE2NzM2OCwiZXhwIjoxNjAwMTY3NDI4fQ.8uk_sQWAu37z9V-1u8OCNSioKLcktA_kOXzA0EGLb30";

        Claims claims = JWTUtils.parseJWT(token);
        log.info("{}",claims);
    }

}
