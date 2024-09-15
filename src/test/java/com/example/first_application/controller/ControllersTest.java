package com.example.first_application.controller;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ControllersTest {

	@LocalServerPort
	private int port;

	@Test
	public void testHelloEndpoint() throws Exception {
		String urlString = "http://localhost:" + port + "/hello";
		HttpURLConnection connection = null;
		BufferedReader reader = null;

		try {
			// Create URL and open connection
			URI uri = new URI(urlString);
			URL url = uri.toURL();
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			connection.setRequestProperty("User-Agent", "Mozilla/5.0 Firefox/26.0");

			// Check the response code
			int responseCode = connection.getResponseCode();
			assertEquals(200, responseCode);

			// Read and build response string
			reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			StringBuilder response = new StringBuilder();
			String line;
			while ((line = reader.readLine()) != null) {
				response.append(line);
			}

			// Parse and validate JSON response manually
			String jsonResponse = response.toString();
			String expectedGreeting = "Hello, World";
			String actualGreeting = extractGreetingFromJson(jsonResponse);
			assertEquals(expectedGreeting, actualGreeting);

		} finally {
			if (reader != null) {
				reader.close();
			}
			if (connection != null) {
				connection.disconnect();
			}
		}
	}

	@Test
	public void testHelloEndpointWithName() throws Exception {
		String name = "TestUser";
		String urlString = "http://localhost:" + port + "/hello?name=" + name;
		HttpURLConnection connection = null;
		BufferedReader reader = null;

		try {
			// Create URL and open connection
			URI uri = new URI(urlString);
			URL url = uri.toURL();
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			connection.setRequestProperty("User-Agent", "Mozilla/5.0 Firefox/26.0");

			// Check the response code
			int responseCode = connection.getResponseCode();
			assertEquals(200, responseCode);

			// Read and build response string
			reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			StringBuilder response = new StringBuilder();
			String line;
			while ((line = reader.readLine()) != null) {
				response.append(line);
			}

			// Parse and validate JSON response manually
			String jsonResponse = response.toString();
			String expectedGreeting = "Hello, " + name;
			String actualGreeting = extractGreetingFromJson(jsonResponse);
			assertEquals(expectedGreeting, actualGreeting);

		} finally {
			if (reader != null) {
				reader.close();
			}
			if (connection != null) {
				connection.disconnect();
			}
		}
	}

	private String extractGreetingFromJson(String json) {
		int start = json.indexOf("greeting") + 11; // "greeting": ".length = 11
		int end = json.indexOf("\"", start);
		return json.substring(start, end);
	}

	@Test
	public void testInfoEndpoint() throws IOException, URISyntaxException {
		String urlString = "http://localhost:" + port + "/info";

		// Create URL object
		URI uri = new URI(urlString);
		URL url = uri.toURL();
		// Open connection
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod("GET");
		connection.setRequestProperty("Content-Type", "application/json");

		// Check response code
		int responseCode = connection.getResponseCode();
		assertEquals(200, responseCode);

		// Read and build response string
		StringBuilder response = new StringBuilder();
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
			String line;
			while ((line = reader.readLine()) != null) {
				response.append(line);
			}
		}

		// Close connection
		connection.disconnect();

		// Parse JSON response manually (assuming simple structure)
		String jsonResponse = response.toString();
		assertTrue(jsonResponse.contains("\"time\""));
		assertTrue(jsonResponse.contains("\"client_address\""));
		assertTrue(jsonResponse.contains("\"host_name\""));
		assertTrue(jsonResponse.contains("\"headers\""));
	}
}
