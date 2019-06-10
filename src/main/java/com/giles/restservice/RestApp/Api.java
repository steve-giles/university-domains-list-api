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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import org.springframework.cglib.core.CollectionUtils;
import org.springframework.cglib.core.Predicate;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Scanner;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

/**
 * This class implements a RESTful web service to obtain university data
 *
 * @author Steve Giles
 */
@RestController
@CrossOrigin
@RequestMapping("/")
public class Api {
    /**
     * Performs a university search on https://raw.githubusercontent.com/Hipo/university-domains-list
     *
     * @param country The country to search
     * @param name The name to search
     * @return The contents of the search
     */
    @GetMapping("/search")
    public String index(@RequestParam(value = "country", required=false) String country,
                        @RequestParam(value = "name", required=false) String name) {
        String response = "";

        try {
            URL url = new URL("https://raw.githubusercontent.com/Hipo/university-domains-list/master/" +
                    "world_universities_and_domains.json");
            try {
                response = getResponseFromHttpUrl(url);

                // filter by name
                if (name != null) {
                    response = getUniversitiesByName(response, name);
                }

                // filter by country
                if (country != null) {
                    response = getUniversitiesByCountry(response, country);
                }

            } catch(IOException ie) {
                ie.printStackTrace();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return response;
    }

    /**
     * Create a university
     *
     * @param university The university to create
     * @return The created university
     */
    @PostMapping("/university")
    public University post(@RequestBody University university){
        // not implemented
        return null;
    }

    /**
     * Update a university
     *
     * @param university The university to update
     * @return The updated university
     */
    @PutMapping("/university")
    public University put(@RequestBody University university){
        // not implemented
        return null;
    }

    /**
     * This method extracts nodes from a JSON object which contain a name
     *
     * @param filterText The text as JSON containing all universities
     * @param filter The name of the university to find (contains)
     * @return The filtered text
     * @throws IOException Related to network and stream reading
     */
    private static String getUniversitiesByName(String filterText, String filter) throws IOException {

        ObjectMapper mapper = new ObjectMapper();
        CollectionType mapCollectionType = mapper.getTypeFactory()
                .constructCollectionType(List.class, University.class);
        List<University> universities = (List<University>) mapper.readValue(filterText, mapCollectionType);

        CollectionUtils.filter(universities, new Predicate() {
            @Override
            public boolean evaluate(Object o) {
                return ((University) o).getName().contains(filter);
            }
        });

        return mapper.writeValueAsString(universities);
    }

    /**
     * This method extracts nodes from a JSON object which contain a country
     *
     * @param filterText The text as JSON containing all universities
     * @param filter The country location of the university to find (contains)
     * @return The filtered text
     * @throws IOException Related to network and stream reading
     */
    private static String getUniversitiesByCountry(String filterText, String filter) throws IOException {

        ObjectMapper mapper = new ObjectMapper();
        CollectionType mapCollectionType = mapper.getTypeFactory()
                .constructCollectionType(List.class, University.class);
        List<University> universities = (List<University>) mapper.readValue(filterText, mapCollectionType);

        CollectionUtils.filter(universities, new Predicate() {
            @Override
            public boolean evaluate(Object o) {
                return ((University) o).getCountry().contains(filter);
            }
        });

        return mapper.writeValueAsString(universities);
    }

    /**
     * This method returns the entire result from the HTTP response.
     * See: https://www.programcreek.com/java-api-examples/java.net.HttpURLConnection
     *
     * @param url The URL to fetch the HTTP response from.
     * @return The contents of the HTTP response.
     * @throws IOException Related to network and stream reading
     */
    private static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        urlConnection.setRequestProperty("Content-Type", "application/json");

        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }
}
