package com.swsandbox.util;

import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;

/**
 * User: jgreenwald
 * Date: 6/30/13
 * Time: 10:30 AM
 */
public class JsonUtil
{

    private static final ObjectMapper mapper;

    static
    {
        mapper = new ObjectMapper();
    }

    public static <T> String stringify(T o)
    {
        try
        {
            return mapper.writeValueAsString(o);
        }
        catch (IOException e)
        {
            return "";
        }
    }
}
