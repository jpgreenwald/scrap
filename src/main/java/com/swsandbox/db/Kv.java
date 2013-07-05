package com.swsandbox.db;

import java.util.HashMap;
import java.util.Map;

/**
 * User: jgreenwald
 * Date: 7/5/13
 * Time: 12:13 PM
 */
public class Kv
{
    private Integer id;
    private Map<String, String> properties;

    public void addProperty(String key, String val)
    {
        if (getProperties() == null)
        {
            setProperties(new HashMap<String, String>());
        }
        getProperties().put(key, val);
    }

    public Map<String, String> getProperties()
    {
        return properties;
    }

    public void setProperties(Map<String, String> properties)
    {
        this.properties = properties;
    }

    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    @Override
    public String toString()
    {
        return "Kv{" +
                "id=" + id +
                ", properties=" + properties +
                '}';
    }
}
