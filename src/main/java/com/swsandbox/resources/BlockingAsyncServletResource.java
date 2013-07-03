package com.swsandbox.resources;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * User: jgreenwald
 * Date: 6/30/13
 * Time: 7:51 PM
 */
@WebServlet(urlPatterns = "/servlet/async2", asyncSupported = true)
public class BlockingAsyncServletResource extends HttpServlet
{
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        //do nothing
    }
}
