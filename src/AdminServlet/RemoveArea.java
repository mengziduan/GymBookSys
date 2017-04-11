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
 * Servlet implementation class RemoveArea
 */
@WebServlet("/RemoveArea")
public class RemoveArea extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RemoveArea() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@SuppressWarnings({ "resource", "null" })
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out=response.getWriter();
		String cancel1= request.getParameter("placeinfo");
		String cancel =new String(cancel1.getBytes("ISO-8859-1"),"UTF-8");
		//System.out.println(cancel);
		JSONObject result=JSONObject.fromObject(cancel);
		String id =(String) result.get("ID");
		
		Map map =new HashMap();
		String msg=null;
		JsonUtils js=new JsonUtils();
		
		PreparedStatement pstmt=null;		
		ResultSet rs=null;
		Connection conn=null;
		try{	
			
			String sql=null;
			//sql="delete from Area where GymArea='"+gymArea+"' and Place='"+place+"' and SonPlace='"+sonPlace+"' and SonArea='"+sonArea+"'";
			sql="delete from Area where ID="+id;
			conn=ConnectionFactory.getConnection();
			pstmt=conn.prepareStatement(sql);
			pstmt.executeUpdate();
			msg="success";
			map.put("msg",msg );
			String back=js.mapToJson(map);
			out.write(back);
			out.flush();
	}catch(Exception e){
		//System.out.println("exception");
		msg="failure";
		map.put("msg",msg );
		String back=js.mapToJson(map);
		out.write(back);
		out.flush();
	}
finally{
	     out.close();
		ResourceClose.close(rs, pstmt, conn);
		//System.out.println(msg);
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
