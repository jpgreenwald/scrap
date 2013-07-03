package com.swsandbox.mq;

import com.swsandbox.mq.message.Event;
import com.swsandbox.util.JsonUtil;
import org.jeromq.ZMQ;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * User: jgreenwald
 * Date: 6/30/13
 * Time: 10:16 AM
 */
@Path("event")
@ApplicationScoped
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class PushResource
{

    private static final Logger logger = LoggerFactory.getLogger(PushResource.class);
    private ZMQ.Context context;
    private ZMQ.Socket socket;

    @PostConstruct
    public void setupMq()
    {
        logger.info("creating socket for pushing messages");
        context = ZMQ.context(1);
        socket = context.socket(ZMQ.PUSH);
        socket.connect("tcp://localhost:5000");

    }

    @GET
    public Response health()
    {
        return Response.ok().build();
    }

    @POST
    public Response createEvent(Event e)
    {
        socket.send(JsonUtil.stringify(e));
        return Response.ok().build();
    }

    @PreDestroy
    public void destroyMq()
    {
        logger.info("destroying socket for pushing messages");
        socket.close();
        context.term();
    }

}
