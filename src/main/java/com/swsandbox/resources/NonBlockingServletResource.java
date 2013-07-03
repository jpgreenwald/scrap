package com.swsandbox.resources;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * User: jgreenwald
 * Date: 6/30/13
 * Time: 8:01 PM
 */
@WebServlet(urlPatterns = "/servlet/async", asyncSupported = true)
public class NonBlockingServletResource extends HttpServlet
{
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        // start async
        final AsyncContext ac = req.startAsync();
        final ServletInputStream input = req.getInputStream();

        input.setReadListener(new ReadListener()
        {
            @Override
            public void onDataAvailable()
            {
                try
                {
                    int len = -1;
                    byte b[] = new byte[1024];
                    while (input.isReady()
                            && (len = input.read(b)) != -1)
                    {
                        String data = new String(b, 0, len);
                        System.out.println("--> " + data);
                    }
                }
                catch (IOException ex)
                {
                    //
                }
            }

            @Override
            public void onAllDataRead()
            {
                ac.complete();
            }

            @Override
            public void onError(Throwable t)
            {
            }
        });
    }
}
