//ProyectoServlet

package com.unsa.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.*;

import com.google.appengine.api.utils.SystemProperty;

import java.sql.*;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

@SuppressWarnings("serial")
public class UserServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
	
	}
	
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {

//		req.setAttribute("usuariologeado", name);
//		req.getRequestDispatcher("login.jsp").forward(req, resp);
		
						
		String name=req.getParameter("name");
		String lastname=req.getParameter("lastname");
		String age=req.getParameter("age");
		String gender=req.getParameter("gender");
		String country=req.getParameter("country");
		String language=req.getParameter("language");
		String interest=req.getParameter("interest");
		String instruction=req.getParameter("instruction");
		String username=req.getParameter("username");
		String password=req.getParameter("password");
			
		String url = null;
		
		if (SystemProperty.environment.value() ==
			SystemProperty.Environment.Value.Production) {
			// Connecting from App Engine.
			// Load the class that provides the "jdbc:google:mysql://"
			// prefix.
			try {
				Class.forName("com.mysql.jdbc.GoogleDriver");
//				Class.forName("com.mysql.jdbc.Driver");
				
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			url =
			//"jdbc:google:mysql://proyectobd/proyecto?user=root2";
			  "jdbc:google:mysql://buscador-1128:proyectobd/proyecto?user=root";

		} else {
			 // Connecting from an external network.
			try {
				Class.forName("com.mysql.jdbc.Driver");
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			url = "jdbc:mysql://173.194.239.154:3306/proyecto?user=jhon";
		}

		Connection conn = null;
		try {
			conn = DriverManager.getConnection(url);
			if(conn == null){
				System.err.println(">>> NULL");
			}else{
				System.err.println(">>> NOT NULL");			
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.err.println(">>> e.printStackTrace()" + e.getMessage());
			
		}
		
		try {
			
			
			
			String sql = "INSERT INTO `users` (`name`,`lastname`,`age`, `gender`,`country`, `language`, `interest`, `instruction`, `username`,`password`) VALUES ('"+name+"', '"+lastname+"','"+age+"','"+gender+"','"+country+"','"+language+"','"+interest+"','"+instruction+"','"+username+"','"+password+"')";
			System.err.println(sql);
			
			int rs = conn.createStatement().executeUpdate(sql);
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.err.println(">>> e.printStackTrace()" + e.getMessage());
			
		}
		req.setAttribute("usuariologeado", name);
		req.getRequestDispatcher("index.jsp").forward(req, resp);
//		resp.setContentType("text/html");
//		resp.getWriter().println("<html>");
//		resp.getWriter().println("<head>");
//		resp.getWriter().println("</head>");
//		resp.getWriter().println("<body>");
//		resp.getWriter().println("<p>Usuario insertado exitosamente!</p>");
//		resp.getWriter().println("<p><a href='/users'>Regresar</a></p>");
//		resp.getWriter().println("</body>");
//		resp.getWriter().println("</html>");

	}
}
