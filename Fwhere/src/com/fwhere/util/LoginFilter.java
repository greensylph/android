 package com.fwhere.util;
 
 import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
 
 public class LoginFilter
   implements Filter
 {
   protected FilterConfig config = null;
 
   public void destroy()
   {
   }
 
   public void doFilter(ServletRequest arg0, ServletResponse arg1, FilterChain arg2)
     throws IOException, ServletException
   {
     HttpServletRequest request = (HttpServletRequest)arg0;
     HttpServletResponse response = (HttpServletResponse)arg1;
 
     if (request.getSession().getAttribute("loginSessionBean") != null)
     {
       arg2.doFilter(request, response);
     }
     else if ((request.getRequestURI().endsWith("login.htm")) || 
       (request.getRequestURI().endsWith("bottom.htm")) || 
       (request.getRequestURI().endsWith("top.htm")))
     {
       arg2.doFilter(request, response);
     }
     else if ((request.getParameter("no") != null) && 
       (request.getParameter("pwd") != null) && 
       (request.getRequestURI().endsWith("validLogin.htm")))
     {
       arg2.doFilter(request, response);
     }
     else if (request.getRequestURI().indexOf("ecm") != -1)
     {
       ServletOutputStream out = response.getOutputStream();
       String outHtml = "<script language=\"javascript\">if(parent.parent.parent.parent!=null)parent.parent.parent.parent.location.href='" + 
         request.getContextPath() + "/login.htm';" + 
         "if(parent.parent.parent!=null)parent.parent.parent.location.href='" + 
         request.getContextPath() + "/login.htm';" + 
         "if(parent.parent!=null)parent.parent.location.href='" + 
         request.getContextPath() + "/login.htm';" + 
         "else if(parent!=null)parent.location.href='" + 
         request.getContextPath() + "/login.htm';" + 
         " else window.location.href='" + request.getContextPath() + 
         "/login.htm';" + "</script>";
 
       out.print(outHtml);
       out.flush();
       out.close();
     } else {
       arg2.doFilter(request, response);
     }
   }
 
   public void init(FilterConfig arg0)
     throws ServletException
   {
     this.config = arg0;
   }
 }

