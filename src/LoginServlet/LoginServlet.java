package LoginServlet;

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
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		Map map =new HashMap();
		PrintWriter out=response.getWriter();//ͨ���������ͻ���������Ӧ
		String txt = request.getParameter("txt");
//		System.out.print(txt);
		JSONObject result=JSONObject.fromObject(txt);
		String tel=(String) result.get("tel");
		String password =(String) result.get("password");
		String type =(String) result.get("type");
//		System.out.println(tel+"--"+password);
		String msg=null;
		JsonUtils js=new JsonUtils();
		//测试是否从前端获取值
		//String Identify = request.getParameter("identify");	
		HttpSession session=request.getSession();
		PreparedStatement pstmt=null;		
		ResultSet rs=null;
		Connection conn=null;
		String power=null;
		try{
			String sql=null;
			if(type.equals("user")){
			 sql="select * from users where Tel='"+tel+"' and Password='"+password+"'";
			
			}
			else {
			 sql="select * from admin where Tel='"+tel+"' and Password='"+password+"'";
			 }
		   conn=ConnectionFactory.getConnection();
			pstmt=conn.prepareStatement(sql);
			rs=pstmt.executeQuery();		
			if(rs.next()){		  
				session.setAttribute("tel",rs.getString(3) );
				session.setAttribute("username",rs.getString(2));
			  //  session.setAttribute("message", "");
				/*if (Identify.equals("user")) {
					response.sendRedirect(request.getContextPath()+"/PersonalCenter.jsp");
				}
				if (rs.getString(5).equals("A")) {
					response.sendRedirect(request.getContextPath()+"/AdminMain.html");
					
				}else if (rs.getString(5).equals("B")){
					response.sendRedirect(request.getContextPath()+"/AdminMain.html");
				}	
						*/
				if(type.equals("user")){
					power="C";
				}
				else power=rs.getString(5);
			    map.put("msg", "success");
			    map.put("name", rs.getString(2));
			    map.put("power", power);
			    Cookie cookie_tel=new Cookie("tel",tel);
				//设置Cookie属性
				cookie_tel.setMaxAge(1*24*60*60);
				//发送Cookie对象
				 response.addCookie(cookie_tel);
		       	 Cookie cookie_name=new Cookie("username",URLEncoder.encode(rs.getString(2), "utf-8"));
				// Cookie cookie_name=new Cookie("username",rs.getString(2));
				 cookie_name.setMaxAge(1*24*60*60);
		       	response.addCookie(cookie_name);
		       	Cookie cookie_power=new Cookie("power",power);
		       	 cookie_name.setMaxAge(1*24*60*60);
		       	response.addCookie(cookie_power);
			}
			else{
				//登录错误返回登录页面
				//session.setAttribute("message", "登录错误");
				//response.sendRedirect("Login.jsp");
				msg="failure";
				map.put("msg",msg );
				map.put("reason","账户或密码有误");
			}
			String back=js.mapToJson(map);
//			System.out.println(back);
			out.write(back);
			out.flush();
			}catch(Exception e){
				e.printStackTrace();
				
			}
		finally{out.close();
				ResourceClose.close(rs, pstmt, conn);
				map.clear();
			} 
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
