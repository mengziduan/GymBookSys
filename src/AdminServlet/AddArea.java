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
 * Servlet implementation class AddArea
 */
@WebServlet("/AddArea")
public class AddArea extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddArea() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out=response.getWriter();
//		System.out.print("=========================");
		String add1= request.getParameter("placeinfo");
		String add =new String(add1.getBytes("ISO-8859-1"),"UTF-8");
//		System.out.println("add测试"+add);
		String back=null;
		JSONObject result=JSONObject.fromObject(add);
		String gymArea =(String) result.get("GymArea");
		String place =(String) result.get("Place");
		String sonPlace =(String) result.get("SonPlace");
		String sonArea =(String) result.get("SonArea");
		String admin =(String) result.get("Admin");
		String status =(String) result.get("status");
		//HttpSession session=request.getSession();
		
		Map map =new HashMap();
		String msg=null;
		JsonUtils js=new JsonUtils();
		
		PreparedStatement pstmt=null;		
		ResultSet rs=null;
		Connection conn=null;
		try{	
			String sql1="select GymArea,Place,SonPlace,SonArea from Area where gymArea='"+gymArea+"' and place='"+place+"' and sonPlace='"+sonPlace+"' and sonArea='"+sonArea+"'";
			conn=ConnectionFactory.getConnection();
			pstmt=conn.prepareStatement(sql1);
			rs=pstmt.executeQuery();
			if(rs.next()) {
//				System.out.println("-----------");
				msg="exsit";				        
			}else{
//				System.out.println("==========");
				String sql="insert into Area(GymArea,Place,SonPlace,SonArea,Admin,status) values('"+gymArea+"','"+place+"','"+sonPlace+"','"+sonArea+"','"+admin+"','"+status+"')";
		        conn=ConnectionFactory.getConnection();
		        pstmt=conn.prepareStatement(sql);
		        pstmt.executeUpdate();
		        msg="success";		
			}			
			map.put("msg",msg);
		    back=js.mapToJson(map);
		    out.write(back);
		    out.flush();
		//response.sendRedirect("Gym.html");
		}
		catch(Exception e)
		{/*
			msg="failure";
			map.put("msg",msg );
			back=js.mapToJson(map);
			out.write(back);
			out.flush();*/
	        e.printStackTrace();
		}finally{
			ResourceClose.close(pstmt,conn);
			out.close();
//			System.out.println("lbb"+back);
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
