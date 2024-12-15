package com.example.jenkins;

import com.example.jenkins.base.IntegrationTest;
import org.junit.experimental.categories.Category;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Category(IntegrationTest.class)
class JenkinsApplicationTests {

	@Test
	void contextLoads() {
		System.out.println("Maybe not pretty but some INTEGRATION logs");
	}

}
