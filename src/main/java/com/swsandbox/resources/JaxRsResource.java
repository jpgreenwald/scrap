package com.swsandbox.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

/**
 * User: jgreenwald
 * Date: 6/30/13
 * Time: 7:26 PM
 */
@Path("/test")
public class JaxRsResource
{
    @GET
    public Response foo()
    {
        return Response.ok().build();
    }
}
