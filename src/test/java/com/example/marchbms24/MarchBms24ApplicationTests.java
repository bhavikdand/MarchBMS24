package com.example.marchbms24;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MarchBms24ApplicationTests {

    @Test
    void testAdd_OnePlusOne() throws Exception{
        // A -> Arrange
        int a = 1;
        int b = 1;
        // A -> Act
        int ans = a + b;
        // A -> Assert
//        if (ans != 2){
//            throw new Exception("1 + 1 should be equal to 2");
//        }  else {
//
//        }
//        assert ans == 3;
        assertEquals(2, ans, "1+1 should be 3");

    }

}
