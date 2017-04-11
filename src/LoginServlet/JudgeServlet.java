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

//import com.sun.webkit.BackForwardList;

import net.sf.json.JSONObject;

import ConnectionFact.ConnectionFactory;
import ConnectionFact.ResourceClose;
import MyUtils.JsonUtils;

/**
 * Servlet implementation class JudgeServlet
 */
@WebServlet("/JudgeServlet")
public class JudgeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public JudgeServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out=response.getWriter();
		String sql=null;
		Map map =new HashMap();
		String tel = request.getParameter("tel");
//		System.out.println(tel+"----------");
		PreparedStatement pstmt=null;		
		ResultSet rs=null;
		Connection conn=null;
		JsonUtils js=new JsonUtils();
		try{		
		 sql="select * from users where tel='"+tel+"'";
		 conn=ConnectionFactory.getConnection();
		 pstmt=conn.prepareStatement(sql);
		 rs=pstmt.executeQuery();	
		 if(rs.next()){
			 map.put("msg", "exist");			   
		 }
		 else{
			 map.put("msg", "allow");
		 }	
		 String back=js.mapToJson(map);
			//System.out.println(back);
			out.write(back);
			out.flush();
			}catch(Exception e){
				e.printStackTrace();
				
			}
		finally{
			    out.close();
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
