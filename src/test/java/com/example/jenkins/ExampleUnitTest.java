package com.example.jenkins;

import com.example.jenkins.base.UnitTest;
import org.junit.experimental.categories.Category;
import org.junit.jupiter.api.Test;

@Category(UnitTest.class)
public class ExampleUnitTest {

    @Test
    void exampleTest() {
        System.out.println("Maybe not pretty but some UNIT logs");
    }
}
