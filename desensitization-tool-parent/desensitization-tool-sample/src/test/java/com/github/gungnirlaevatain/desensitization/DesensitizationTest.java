package test.com.github.gungnirlaevatain.desensitization;

import com.github.gungnirlaevatain.desensitization.DesensitizationTool;
import com.github.gungnirlaevatain.desensitization.SampleApplication;
import com.github.gungnirlaevatain.desensitization.TestEntity;
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
        Assert.assertEquals("123****8911", phone);
        String idCard = DesensitizationTool.desensitize("idcard", "123456789123456789");
        Assert.assertEquals("**************6789", idCard);
        String idCard2 = DesensitizationTool.desensitize("idcard2", "123456789123456789");
        Assert.assertEquals("**************6789", idCard2);
        TestEntity innerEntity = new TestEntity("123456789123456798", "123", "12345678922", null);
        TestEntity testEntity = new TestEntity("123456789123456789", "123", "12345678911", innerEntity);
        DesensitizationTool.desensitize(testEntity);
        Assert.assertEquals("**************6789", testEntity.getTo());
        Assert.assertEquals("**************6798", testEntity.getEntity().getTo());
        Assert.assertEquals("123****8911", testEntity.getPhone());
        Assert.assertEquals("123****8922", testEntity.getEntity().getPhone());
        phone = DesensitizationTool.desensitize("commonPattern", "12345678911");
        Assert.assertEquals("123****8911", phone);
        idCard = DesensitizationTool.desensitize("commonPattern2", "123456789123456789");
        Assert.assertEquals("**************6789", idCard);
        idCard2 = DesensitizationTool.desensitize("commonPattern3", "132456");
        Assert.assertEquals("13**56", idCard2);
        idCard2 = DesensitizationTool.desensitize("commonPattern3", "1324567");
        Assert.assertEquals("13***67", idCard2);
    }


} 
