University Domains and Names API in Java
========================================



### An API and JSON list contains domains, names and countries of most of the universities of the world.


Provides a search endpoint you can search for an autocomplete for university name or/and filter by country.

This is Java implementation of https://github.com/Hipo/university-domains-list-api

## API Search Endpoint

### Request
    /search?name=Middle


### Response
    [
    {
    web_page: "http://www.aum.edu.kw/",
    country: "Kuwait",
    domain: "aum.edu.kw",
    name: "American University of Middle East"
    },    
    {
    web_page: "http://www.mxcc.commnet.edu",
    country: "United States",
    domain: "mxcc.commnet.edu",
    name: "Middlesex Community College"
    },   
    {
    web_page: "http://www.middlesex.mass.edu",
    country: "United States",
    domain: "amiddlesex.mass.edu",
    name: "Middlesex Community College"
    },   
    {
    web_page: "http://www.middlesexcc.edu",
    country: "United States",
    domain: "middlesexcc.edu",
    name: "Middlesex County College"
    },   
    {
    web_page: "http://www.meu.edu.jo/",
    country: "Jordan",
    domain: "meu.edu.jo",
    name: "Middle East University"
    },
    {
    web_page: "http://www.odtu.edu.tr/",
    country: "Turkey",
    domain: "odtu.edu.tr",
    name: "Middle East Technical University"
    },
    {
    web_page: "http://www.mtsu.edu/",
    country: "USA",
    domain: "mtsu.edu",
    name: "Middle Tennessee State University"
    },
    {
    web_page: "http://www.mga.edu/",
    country: "USA",
    domain: "mga.edu",
    name: "Middle Georgia State College"
    },
    {
    web_page: "http://www.mdx.ac.uk/",
    country: "United Kingdom",
    domain: "mdx.ac.uk",
    name: "Middlesex University"
    },
    {
    web_page: "http://www.middlebury.edu/",
    country: "USA",
    domain: "middlebury.edu",
    name: "Middlebury College"
    }
    ]


### Request
    /search?name=Middle&country=Turkey


### Response
    [
    {
    web_page: "http://www.odtu.edu.tr/",
    country: "Turkey",
    domain: "odtu.edu.tr",
    name: "Middle East Technical University"
    }
    ]
    
## Run the Project

- Clone Project 
- Load the project in your IDE (i.e. IntelliJ)
- Run
