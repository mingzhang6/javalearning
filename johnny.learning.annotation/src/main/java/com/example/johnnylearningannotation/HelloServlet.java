package com.example.johnnylearningannotation;

import org.springframework.web.bind.annotation.GetMapping;

import java.io.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

@WebServlet(name = "helloServlet", value = "/hello-servlet")
public class HelloServlet extends HttpServlet {
    private String message;

    public void init() {
        message = "Hello World!";
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");

        // Hello
        PrintWriter out = response.getWriter();
        out.println("<html><body>");
        out.println("<h1>" + message + "</h1>");
        out.println("</body></html>");
    }

    @PrintInvokeLog
    @GetMapping("/print")
    public String printLog(String name, String age) {
        System.out.println("printLog execute...");
        try {
            // 方便看耗时
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return name + " 今年 " + age + " 岁";
    }

    public void destroy() {
    }
}