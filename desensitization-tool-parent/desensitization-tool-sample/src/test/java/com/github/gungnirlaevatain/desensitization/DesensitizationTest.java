package test.com.github.gungnirlaevatain.desensitization;

import com.github.gungnirlaevatain.desensitization.DesensitizationTool;
import com.github.gungnirlaevatain.desensitization.SampleApplication;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = SampleApplication.class)
public class DesensitizationTest {

    @Before
    public void before() throws Exception {
    }

    @After
    public void after() throws Exception {
    }

    /**
     * Method: main(String[] args)
     */
    @Test
    public void testMain() throws Exception {
        String phone = DesensitizationTool.desensitize("mobilephone", "12345678911");
        String idCard = DesensitizationTool.desensitize("idcard", "123456789123456789");
        Assert.assertEquals("123****8911", phone);
        Assert.assertEquals("**************6789", idCard);
    }


} 
