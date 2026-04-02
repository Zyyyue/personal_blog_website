package com.xixizai.personalblogwebsite;

import cn.hutool.core.util.RandomUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class RandomTest {

    @Test
    void test(){
        int j;
        for(int i=0;i<100;i++){
            j= RandomUtil.randomInt(0, 3);
            System.out.println(j);
        }
    }

}
