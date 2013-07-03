package com.swsandbox.mq;

import com.swsandbox.mq.message.Event;
import com.swsandbox.util.JsonUtil;
import org.jeromq.ZMQ;

/**
 * User: jgreenwald
 * Date: 6/30/13
 * Time: 11:48 AM
 */
public class PushProducer
{
    public static void main(String[] args)
    {
        Long s = System.currentTimeMillis();

        ZMQ.Context context = ZMQ.context(1);
        ZMQ.Socket socket = context.socket(ZMQ.PUSH);
        socket.connect("tcp://*:5000");

        int c = 10000000;

        Event foo = new Event();
        foo.setId("abc-12324124-213124124");
        foo.setName("login");
        foo.setValue("123455222");

        for (int i = 0; i < c; i++)
        {
            socket.send(JsonUtil.stringify(foo));
        }
        socket.close();
        context.term();

        Long e = System.currentTimeMillis();
        System.out.println("Time Elapsed: " + (e - s));
        System.out.println("Per second: " + (c/((e-s)/1000.0)));
    }
}
