package LoginServlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import sun.applet.resources.MsgAppletViewer;

import ConnectionFact.ConnectionFactory;
import ConnectionFact.ResourceClose;

/**
 * Servlet implementation class RegisterServlet
 */
@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RegisterServlet() {
        super();
        
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out=response.getWriter();
		String sql=null;
		String tel = request.getParameter("tel");
		String Name = new String(request.getParameter("name").getBytes("ISO-8859-1"),"UTF-8"); 
		String IsFSD = new String(request.getParameter("optionsRadios").getBytes("ISO-8859-1"),"UTF-8"); 
		String Password = new String(request.getParameter("password").getBytes("ISO-8859-1"),"UTF-8"); 
		if(IsFSD.equals("校内人员")){
			String Major="应用数学";
			String Department = new String(request.getParameter("academy").getBytes("ISO-8859-1"),"UTF-8"); 
			String Grade =new String(request.getParameter("grade").getBytes("ISO-8859-1"),"UTF-8"); 
			String ID=request.getParameter("stuNo");
			sql="insert into users values ('" +Name+"','"+tel+"','"+Password+"','"+IsFSD+"','"+Department
					+"','"+Major+"','"+ID+"','"+Grade+"')";
		}
		else {
			 sql="insert into users values ('" +Name+"','"+tel+"','"+Password+"','"+IsFSD+"',"
					+"null,"+"null,"+"null,"+"null)";
		}
	     
		//HttpSession session=request.getSession();
		PreparedStatement pstmt=null;		
		//ResultSet rs=null;
		Connection conn=null;
		try{		
		//String sql="insert into users values ('" +Name+"','"+tel+"','"+Password+"','"+IsFSD+"','"+Department+"','"+Major+"','"+Grade+"','"+ID+"')";
		conn=ConnectionFactory.getConnection();
		pstmt=conn.prepareStatement(sql);
		pstmt.executeUpdate();

//		System.out.println("注册成功");
		
		response.sendRedirect("index.html");
		
		}
		catch(Exception e)
		{    e.printStackTrace();
			/*System.out.println("注册失败");*/
		}finally{
			ResourceClose.close(pstmt,conn);
			out.close();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
