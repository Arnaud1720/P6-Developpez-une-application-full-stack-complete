package com.openclassrooms.mddapi;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@Disabled("Désactivé tant que la BDD Docker n'est pas utilisée en test")
@SpringBootTest
class MddApiApplicationTests {

	@Test
	void contextLoads() {
	}

}
