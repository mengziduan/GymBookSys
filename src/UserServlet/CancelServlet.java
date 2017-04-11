package UserServlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;
import ConnectionFact.ConnectionFactory;
import ConnectionFact.ResourceClose;
import MyUtils.JsonUtils;

/**
 * Servlet implementation class CancelServlet
 */
@WebServlet("/CancelServlet")
public class CancelServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CancelServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out=response.getWriter();
		String cancel= request.getParameter("order_cancel");
//		System.out.print(cancel);
		Map map =new HashMap();
		JSONObject result=JSONObject.fromObject(cancel);
		String id=(String) result.get("orderid");
		PreparedStatement pstmt=null;		
		ResultSet rs=null; 
		Connection conn=null;
		try{	
			 String sql=null;
			 sql="delete  from booktable where Id='"+id+"'";
		     conn=ConnectionFactory.getConnection();
			 pstmt=conn.prepareStatement(sql);
			 pstmt.executeUpdate();	
//			 System.out.print("取消成功");
			 map.put("state", "success");
			result.putAll(map);
//			System.out.println(result.toString());
//			System.out.print("取消成功");
			out.write(result.toString());
			out.flush();
	}catch(Exception e){
		 map.put("state", "error");
		 result.putAll(map);
//			System.out.println(result.toString());
			out.write(result.toString());
			out.flush();
	}
finally{
	     out.close();
		ResourceClose.close(rs, pstmt, conn);
	} 
	}
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}

}
