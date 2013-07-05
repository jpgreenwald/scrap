package com.swsandbox.db;

import com.datastax.driver.core.*;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;

/**
 * User: jgreenwald
 * Date: 7/5/13
 * Time: 9:55 AM
 */
public class CassandraService
{
    private Cluster cluster;

    private void connect(String node)
    {
        cluster = Cluster.builder().addContactPoint(node).build();
        Metadata metadata = cluster.getMetadata();
        System.out.printf("Connected to cluster: %s\n", metadata.getClusterName());
        for (Host host : metadata.getAllHosts())
        {
            System.out.printf("Datacenter: %s; Host: %s; Rack: %s\n",
                    host.getDatacenter(), host.getAddress(), host.getRack());
        }
    }

    private void close()
    {
        cluster.shutdown();
    }

    private Session getSession()
    {
        return cluster.connect("demo");
    }


    public static void main(String[] args)
    {
        CassandraService service = new CassandraService();
        service.connect("127.0.0.1");

        Session session = service.getSession();
        ResultSet rs = session.execute("select * from users");

        Iterator<Row> rows = rs.iterator();

        while (rows.hasNext())
        {
            Row row = rows.next();

            User user = new User();

            user.setId(row.getUUID("id"));
            user.setDisplay(row.getString("display_name"));
            user.setPassword(row.getString("password_hash"));
            user.setUsername(row.getString("username"));

            System.out.println(user);

        }

        ResultSet kvRs = session.execute("select * from kv");

        Iterator<Row> kvRows = kvRs.iterator();

        Kv kv = new Kv();

        while (kvRows.hasNext())
        {
            Row row = kvRows.next();
            if (kv.getId() == null)
            {
                kv.setId(row.getInt("id"));
            }
            kv.addProperty(row.getString("key"), row.getString("val"));
        }
        System.out.println("stuff: " + stringify(kv) + ", " + kv);

        session.shutdown();
        service.close();
    }

    private static class User
    {
        private UUID id;
        private String username;
        private String display;
        private String password;

        private UUID getId()
        {
            return id;
        }

        private void setId(UUID id)
        {
            this.id = id;
        }

        private String getUsername()
        {
            return username;
        }

        private void setUsername(String username)
        {
            this.username = username;
        }

        private String getDisplay()
        {
            return display;
        }

        private void setDisplay(String display)
        {
            this.display = display;
        }

        private String getPassword()
        {
            return password;
        }

        private void setPassword(String password)
        {
            this.password = password;
        }

        @Override
        public String toString()
        {
            return "User{" +
                    "id=" + id +
                    ", username='" + username + '\'' +
                    ", display='" + display + '\'' +
                    ", password='" + password + '\'' +
                    '}';
        }
    }

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
            e.printStackTrace();
            return "";
        }
    }

}

