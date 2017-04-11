package UserServlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;
import ConnectionFact.ConnectionFactory;
import ConnectionFact.ResourceClose;
import MyUtils.JsonUtils;

/**
 * Servlet implementation class EditUserPers
 */
@WebServlet("/EditUserPers")
public class EditUserPers extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EditUserPers() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out=response.getWriter();
//		System.out.println("lala>>>");
		String edit1= request.getParameter("userinfo");
		String edit =new String(edit1.getBytes("ISO-8859-1"),"UTF-8");
//		System.out.println(edit);
		JSONObject result=JSONObject.fromObject(edit);
		String Name =(String) result.get("Name");
		/*String Email =(String) result.get("Email");*/
		String Tel =(String) result.get("Tel");
		
		String Password =(String) result.get("Password");	
		String id =(String) result.get("Id");
		Map map =new HashMap();
		String msg=null;
		JsonUtils js=new JsonUtils();		
		PreparedStatement pstmt=null;		
		ResultSet rs=null;
		Connection conn=null;
		try{	
			String sql=null;
		sql="update Users set Name='"+Name+"'  ,Tel='"+Tel+"',Password='"+Password+"' where id="+id;
			conn=ConnectionFactory.getConnection();
			pstmt=conn.prepareStatement(sql);
			pstmt.executeUpdate();
			Cookie cookie_name = new Cookie("username", null);
			Cookie cookie_tel = new Cookie("tel", null);
			Cookie cookie_power = new Cookie("power", null);
			cookie_name.setMaxAge(0);
			 cookie_tel.setMaxAge(0);
			 cookie_power.setMaxAge(0);
				response.addCookie(cookie_name);
				response.addCookie(cookie_tel);
				response.addCookie(cookie_power);
			msg="success";
			map.put("msg",msg);
			String back=js.mapToJson(map);
			out.write(back);
			out.flush();
			out.close();
	}catch(Exception e){
//		System.out.println("exception");
		msg="failure";
		map.put("msg",msg );
		String back=js.mapToJson(map);
		out.write(back);
		out.flush();
	}
finally{
	     out.close();
		ResourceClose.close(rs, pstmt, conn);
//		System.out.println(msg);
	} 
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request,response);
	}

}
