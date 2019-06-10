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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * Unit tests for {@link Api}
 *
 * @author Steve Giles
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ApiTests {
    private static final ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private Api api;

    @LocalServerPort
    private int randomServerPort;

    @Test
    public void contextLoads() {
    }

    @Test
    public void testGetAllUniversitiesWithNoNameOrCountry() {
        RestTemplate restTemplate = new RestTemplate();

        final String baseUrl = "http://localhost:" + randomServerPort + "/search";
        try {
            URI uri = new URI(baseUrl);

            ResponseEntity<String> result = restTemplate.getForEntity(uri, String.class);

            //Verify request succeed
            Assert.assertEquals(200, result.getStatusCodeValue());

            try {
                // verify the correct number of results were returned
                JsonNode root = mapper.readTree(result.getBody());
                Assert.assertEquals(9684, root.size());
            } catch(IOException ie) {
                Assert.fail(ie.getMessage());
            }
        } catch (URISyntaxException e) {
            Assert.fail(e.getMessage());
        }
    }

    @Test
    public void testGetUniversitiesByName() {
        RestTemplate restTemplate = new RestTemplate();

        // find all universities containing the name "Middle"
        final String baseUrl = "http://localhost:" + randomServerPort + "/search?name=Middle";
        try {
            URI uri = new URI(baseUrl);

            ResponseEntity<String> result = restTemplate.getForEntity(uri, String.class);

            //Verify request success
            Assert.assertEquals(200, result.getStatusCodeValue());

            try {
                JsonNode root = mapper.readTree(result.getBody());
                Assert.assertEquals(10, root.size());
                Assert.assertEquals(TestConstants.NAME, result.getBody());
            } catch(IOException ie) {
                Assert.fail(ie.getMessage());
            }
        } catch (URISyntaxException e) {
            Assert.fail(e.getMessage());
        }
    }

    @Test
    public void testAllUSUniversitiesByNameAndCountry() {
        RestTemplate restTemplate = new RestTemplate();

        final String baseUrl = "http://localhost:" + randomServerPort + "/search?name=Middle&country=Turkey";
        try {
            URI uri = new URI(baseUrl);

            ResponseEntity<String> result = restTemplate.getForEntity(uri, String.class);

            //Verify request succeed
            Assert.assertEquals(200, result.getStatusCodeValue());

            try {
                JsonNode root = mapper.readTree(result.getBody());
                Assert.assertEquals(    1, root.size());
                Assert.assertEquals(TestConstants.NAME_AND_COUNTRY, result.getBody());
            } catch(IOException ie) {
                Assert.fail(ie.getMessage());
            }
        } catch (URISyntaxException e) {
            Assert.fail(e.getMessage());
        }
    }
}