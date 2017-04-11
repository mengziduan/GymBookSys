package UserServlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ConnectionFact.ConnectionFactory;
import ConnectionFact.ResourceClose;

import net.sf.json.JSONObject;

/**
 * Servlet implementation class TimeServlet
 */
@WebServlet("/TimeServlet")
public class TimeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TimeServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		//System.out.println("请求已经预定时间!!");
		PrintWriter out=response.getWriter();
		String timeinfo= request.getParameter("placeinfo");
		timeinfo =new String(timeinfo.getBytes("ISO-8859-1"),"UTF-8");
		//System.out.println("请求已经预定时间："+timeinfo+"''");
		JSONObject result=JSONObject.fromObject(timeinfo);
	//	String tel=(String) result.get("Tel");
		String mytime=(String) result.get("Date");
		//System.out.println(mytime);
		String place1=(String) result.get("GymArea");
		String place2=(String) result.get("Place");
		String place3=(String) result.get("SonPlace");
		String place4=(String) result.get("SonArea");	
//		System.out.println(place1+" "+place2+" "+place3+" "+place4);
		PreparedStatement pstmt=null;		
		ResultSet rs=null;
		String back;
		Connection conn=null;
	    List ls=new ArrayList();
		try{	String sql=null;
		    sql="select StartTime,OverTime from booktable where Date=? and GymArea=? and Place=? and Sonplace=? and Sonarea =?";
		    conn=ConnectionFactory.getConnection();
			 pstmt=conn.prepareStatement(sql);
			// pstmt.setString(1, tel);
			 pstmt.setString(1, mytime);
			 pstmt.setString(2, place1);
			 pstmt.setString(3, place2);
			 pstmt.setString(4, place3);
			 pstmt.setString(5, place4);
			 rs=pstmt.executeQuery();	
			  ResultSetMetaData metaData = rs.getMetaData();  
			   int columnCount = metaData.getColumnCount();  
			   JSONObject jsonObj=null;
			 if(rs.next()) {  
			    	do{
			        jsonObj = new JSONObject();  
			         
			        // 遍历每一列  
			        for (int i = 1; i <= columnCount; i++) {  
			            String columnName =metaData.getColumnLabel(i);  
			            String value = rs.getString(columnName);  
			            jsonObj.put(columnName, value.substring(0,5));  
			        }  
			        ls.add(jsonObj);
			    	}while(rs.next());
			         
			        back=ls.toString();
//			        System.out.println(back);
					out.write(back);
					out.flush();
			    } 
			   else{
				   // System.out.println("yyy");
					out.write("加载失败");
					out.flush();
			    }
				
	}catch(Exception e){
		e.printStackTrace();
		
	}finally{
		   ResourceClose.close(rs, pstmt, conn);
		   }
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request,response);
	}

}
