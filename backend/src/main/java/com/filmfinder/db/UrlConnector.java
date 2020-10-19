package com.filmfinder.db;

import java.net.URL;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;

// read generic data from url
public class UrlConnector {
    

    public Map getJsonEntries(String url) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();

        URL _url = new URL(url);
        
        Map<?, ?> map = objectMapper.readValue(_url, Map.class);

        return map;
    }
}
