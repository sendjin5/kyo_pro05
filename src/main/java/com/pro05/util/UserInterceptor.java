package com.pro05.util;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class UserInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        String sid = (String) session.getAttribute("sid");
        String uri = request.getRequestURI().toLowerCase();

        boolean flag = false;

        if(uri.contains("insert")||uri.contains("edit")||uri.contains("delete")||uri.contains("add")||uri.contains("chat")||uri.contains("myshop")){
            if(sid==null){
                flag = true;
            }
        }


        if(flag){
            request.setAttribute("msg", "로그인 후 이용 가능합니다.");
            request.setAttribute("url","/member/login");
            RequestDispatcher view = request.getRequestDispatcher("/member/alert");
            view.forward(request, response);

            /*PrintWriter out = response.getWriter();
            out.println("");
            out.println("<script>history.go(-1);</script>");*/
            return false;
        }

        return true;
    }
}
