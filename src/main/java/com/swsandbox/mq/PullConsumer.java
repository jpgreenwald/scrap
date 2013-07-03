package com.swsandbox.mq;

import org.jeromq.ZMQ;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * User: jgreenwald
 * Date: 6/30/13
 * Time: 11:19 AM
 */
public class PullConsumer
{
    private static final Logger logger = LoggerFactory.getLogger(PullConsumer.class);

    public static void main(String[] args)
    {
        ZMQ.Context context = ZMQ.context(1);
        ZMQ.Socket socket = context.socket(ZMQ.PULL);
        socket.bind("tcp://*:5000");

        int i = 0;
        while (!Thread.interrupted())
        {
            i++;
            String s = socket.recvStr();
            if (i % 100 == 0)
            {
                System.out.println("100");
            }
        }
        socket.close();
        context.term();
    }

}
