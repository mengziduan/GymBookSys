package AdminServlet;

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

import net.sf.json.JSONObject;
import ConnectionFact.ConnectionFactory;
import ConnectionFact.ResourceClose;
import MyUtils.JsonUtils;

/**
 * Servlet implementation class AddAdmin
 */
@WebServlet("/AddAdmin")
public class AddAdmin extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddAdmin() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//System.out.println("addadmin");
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out=response.getWriter();
		String add1= request.getParameter("admininfo");
		String add =new String(add1.getBytes("ISO-8859-1"),"UTF-8");
//		System.out.println(add);
		JSONObject result=JSONObject.fromObject(add);
		String Name =(String) result.get("Name");
		String Tel =(String) result.get("Tel");
		String Email =(String) result.get("Email");
		String Password ="111111";
		String back=null;
		Map map =new HashMap();
		String msg=null;
		JsonUtils js=new JsonUtils();
		
		PreparedStatement pstmt=null;		
		ResultSet rs=null;
		Connection conn=null;
		try{
			String sql1="select Name,Tel from Admin where Name='"+Name+"' or Tel='"+Tel+"'";
			conn=ConnectionFactory.getConnection();
			pstmt=conn.prepareStatement(sql1);
			rs=pstmt.executeQuery();
			if(rs.next()) {
//				System.out.println("-----------");
				msg="exsit";				        
			}else{
			
		String sql="insert into Admin(Name,Tel,Password,Email,Power) values('"+Name+"','"+Tel+"','"+Password+"','"+Email+"','B')";
		conn=ConnectionFactory.getConnection();
		pstmt=conn.prepareStatement(sql);
		pstmt.executeUpdate();
		msg="success";
			}
		map.put("msg",msg );
		back=js.mapToJson(map);
		out.write(back);
		out.flush();
		}
		catch(Exception e)
		{
			//e.printStackTrace();
			msg="failure";
			map.put("msg",msg );
			back=js.mapToJson(map);
			out.write(back);
			out.flush();
		}finally{
			ResourceClose.close(pstmt,conn);
			out.close();
//			System.out.println(back);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request,response);
	}

}
