package org.demo.todo.todoapp;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TodoappApplicationTests {

	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	public void getTodoItemsOnList() {
		String result = restTemplate.getForObject("http://localhost:" + port + "/todo", String.class);
		System.out.println(result);
		assert result.equals("[{\"itemName\":\"name\",\"categories\":[\"category\"]}]");
	}

	@Test
	public void getLists() {
		String result = restTemplate.getForObject("http://localhost:" + port + "/lists", String.class);
		System.out.println(result);
		assert result.equals("{\"123\":\"exampleList1\",\"124\":\"exampleList2\"}");
	}

	@Test
	public void createList() {
		String result = restTemplate.postForObject("http://localhost:" + port + "/list", "myNewList" ,String.class);
		System.out.println(result);
		assert result.equals("123");
	}

	@Test
	public void deleteList() {
		restTemplate.delete("http://localhost:" + port + "/list/listId", "myNewList");
	}

}
