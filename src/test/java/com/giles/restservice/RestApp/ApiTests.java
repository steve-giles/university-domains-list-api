/*
 * Copyright (c) 2019 Steve Giles
 *
 * Permission is hereby granted, free of charge, to any person obtaining
 * a copy of this software and associated documentation files (the
 * "Software"), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish,
 * distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to
 * the following conditions:
 *
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 * OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
 * WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 */
package com.giles.restservice.RestApp;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.any;

/**
 * Unit tests for {@link Api}
 *
 * @author Steve Giles
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ApiTests {
    private static final ObjectMapper mapper = new ObjectMapper();

    @MockBean
    private Api mockApi;

    @LocalServerPort
    private int randomServerPort;

    @Test
    public void contextLoads() {
    }

    @Test
    public void testGetUniversitiesFindsNoMatches() {
        RestTemplate restTemplate = new RestTemplate();
        when(mockApi.index(any(), any())).thenReturn(null);

        final String baseUrl = "http://localhost:" + randomServerPort + "/search";
        try {
            URI uri = new URI(baseUrl);

            ResponseEntity<String> result = restTemplate.getForEntity(uri, String.class);

            // verify request succeed
            Assert.assertEquals(200, result.getStatusCodeValue());
            Assert.assertEquals(null, result.getBody());
        } catch (URISyntaxException e) {
            Assert.fail(e.getMessage());
        }
    }

    @Test
    public void testGetUniversitiesByName() {
        final String baseUrl = "http://localhost:" + randomServerPort + "/search?name=Middle";

        when(mockApi.index(any(), any())).thenReturn(TestConstants.NAME);

        performMockSearch(baseUrl, 200, 10, TestConstants.NAME);
    }

    @Test
    public void testAllUSUniversitiesByNameAndCountry() {
        final String baseUrl = "http://localhost:" + randomServerPort + "/search?name=Middle&country=Turkey";

        when(mockApi.index(any(), any())).thenReturn(TestConstants.NAME_AND_COUNTRY);

        performMockSearch(baseUrl, 200, 1, TestConstants.NAME_AND_COUNTRY);
    }

    /**
     * Performs a mock RESTful call to the backend to retrieve universities
     *
     * @param url The request uri
     * @param expectedStatusCode Expected status code
     * @param expectedSize Expected size
     * @param expectedMessageBody Expected message body
     */
    private void performMockSearch(String url, long expectedStatusCode, long expectedSize,
                               String expectedMessageBody) {
        RestTemplate restTemplate = new RestTemplate();

        try {
            URI uri = new URI(url);

            ResponseEntity<String> result = restTemplate.getForEntity(uri, String.class);

            // verify request succeed
            Assert.assertEquals(expectedStatusCode, result.getStatusCodeValue());

            try {
                JsonNode root = mapper.readTree(result.getBody());
                Assert.assertEquals(    expectedSize, root.size());
                Assert.assertEquals(expectedMessageBody, result.getBody());
            } catch(IOException ie) {
                Assert.fail(ie.getMessage());
            }
        } catch (URISyntaxException e) {
            Assert.fail(e.getMessage());
        }
    }
}
