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
import com.google.apphosting.utils.config.ClientDeployYamlMaker.Request;

@SuppressWarnings("serial")
public class ProyectoServlet extends HttpServlet {
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {
		
		String username=req.getParameter("username");
		String password=req.getParameter("password");
		System.out.println(password);
		
		String url = null;
		if (SystemProperty.environment.value() ==
			SystemProperty.Environment.Value.Production) {
			// Connecting from App Engine.
			// Load the class that provides the "jdbc:google:mysql://"
			// prefix.
			try {
				Class.forName("com.mysql.jdbc.GoogleDriver");
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			url =
//			"jdbc:google:mysql://proyectobd/proyecto?user=root2";
			 "jdbc:google:mysql://buscador-1128:proyectobd/proyecto?user=root";
		} else {
			 // Connecting from an external network.
			try {
				Class.forName("com.mysql.jdbc.Driver");
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
//			url = "jdbc:mysql://173.194.239.154:3306/proyecto?user=root2";
			url = "jdbc:mysql://173.194.239.154:3306/proyecto?user=jhon";
		}

		Connection conn = null;
		try {
			conn = DriverManager.getConnection(url);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String name="";
		try {
			ResultSet rs = conn.createStatement().executeQuery(
			"SELECT id, name FROM users WHERE username = '"+username+"' AND password = '"+password+"'");
			
			while (rs.next()) 
			{
				name = rs.getString(2);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		req.setAttribute("usuariologeado",username);
		req.getRequestDispatcher("index.jsp").forward(req, resp);
		
			
	}
}
