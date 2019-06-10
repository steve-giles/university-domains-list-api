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

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * University class
 *
 * @author Steve Giles
 */
public class University {

    private String name;
    private String alphaTwoCode;
    private String stateProvince;
    private String country;
    private String[] webpages;
    private String[] domains;

    public University(@JsonProperty("name") String name,
                      @JsonProperty("alpha_two_code") String alphaTwoCode,
                      @JsonProperty("state-province") String stateProvince,
                      @JsonProperty("country") String country,
                      @JsonProperty("web_pages") String[] webpages,
                      @JsonProperty("domains") String[] domains) {
        this.name = name;
        this.alphaTwoCode = alphaTwoCode;
        this.stateProvince = stateProvince;
        this.country = country;
        this.webpages = webpages;
        this.domains = domains;
    }

    public University() {}

    public String getName() {
        return name;
    }

    public String getAlphaTwoCode() {
        return alphaTwoCode;
    }

    public String getStateProvince() {
        return stateProvince;
    }

    public String getCountry() {
        return country;
    }

    public String[] getWebpages() {
        return webpages;
    }

    public String[] getDomains() {
        return domains;
    }
}
