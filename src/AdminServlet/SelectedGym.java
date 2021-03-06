package AdminServlet;

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

import net.sf.json.JSONObject;
import ConnectionFact.ConnectionFactory;
import ConnectionFact.ResourceClose;

/**
 * Servlet implementation class SelectedGym
 */
@WebServlet("/SelectedGym")
public class SelectedGym extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SelectedGym() {
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
//		System.out.print("=========================='");
		
		PreparedStatement pstmt=null;		
		ResultSet rs=null;
		String back;
		Connection conn=null;
	    List ls=new ArrayList();
	    ResultSetMetaData metaData=null;
	    int columnCount;
	    JSONObject jsonObj=null;
		try{	//String sql3="select distinct GymArea,Place,SonPlace,SonArea,Admin from Area";
		    String sql1="select distinct GymArea from Area";
		    conn=ConnectionFactory.getConnection();
			 pstmt=conn.prepareStatement(sql1);
			 rs=pstmt.executeQuery();
			 metaData = rs.getMetaData();  
			 columnCount = metaData.getColumnCount();  
			 if(rs.next()) {  
			    	do{
			        jsonObj = new JSONObject();  
			         
			        // 遍历每一列  
			        for (int i = 1; i <= columnCount; i++) {  
			            String columnName =metaData.getColumnLabel(i);  
			            String value = rs.getString(columnName);  
			            jsonObj.put(columnName, value);  
			        }  
			        ls.add(jsonObj);
			    	}while(rs.next());
			 }
			 
			 String sql2="select distinct Place from Area";
			 conn=ConnectionFactory.getConnection();
			 pstmt=conn.prepareStatement(sql2);
			 rs=pstmt.executeQuery();	
			 metaData = rs.getMetaData();  
			 columnCount = metaData.getColumnCount();  
			 if(rs.next()) {  
			    	do{
			        jsonObj = new JSONObject();  
			         
			        // 遍历每一列  
			        for (int i = 1; i <= columnCount; i++) {  
			            String columnName =metaData.getColumnLabel(i);  
			            String value = rs.getString(columnName);  
			            jsonObj.put(columnName, value);  
			        }  
			        ls.add(jsonObj);
			    	}while(rs.next());
			 }
			 
			 String sql3="select distinct SonPlace from Area";
			 conn=ConnectionFactory.getConnection();
			 pstmt=conn.prepareStatement(sql3);
			 rs=pstmt.executeQuery();	
			 metaData = rs.getMetaData();  
			 columnCount = metaData.getColumnCount();  
			 if(rs.next()) {  
			    	do{
			        jsonObj = new JSONObject();  
			         
			        // 遍历每一列  
			        for (int i = 1; i <= columnCount; i++) {  
			            String columnName =metaData.getColumnLabel(i);  
			            String value = rs.getString(columnName);  
			            jsonObj.put(columnName, value);  
			        }  
			        ls.add(jsonObj);
			    	}while(rs.next());
			 }
			 
			 String sql4="select distinct SonArea from Area";
			 conn=ConnectionFactory.getConnection();
			 pstmt=conn.prepareStatement(sql4);
			 rs=pstmt.executeQuery();	
			 metaData = rs.getMetaData();  
			 columnCount = metaData.getColumnCount();  
			 if(rs.next()) {  
			    	do{
			        jsonObj = new JSONObject();  
			         
			        // 遍历每一列  
			        for (int i = 1; i <= columnCount; i++) {  
			            String columnName =metaData.getColumnLabel(i);  
			            String value = rs.getString(columnName);  
			            jsonObj.put(columnName, value);  
			        }  
			        ls.add(jsonObj);
			    	}while(rs.next());
			 }
			 
			 String sql5="select distinct Admin from Area";
			 conn=ConnectionFactory.getConnection();
			 pstmt=conn.prepareStatement(sql5);		 
			 rs=pstmt.executeQuery();	
			 metaData = rs.getMetaData();  
			 columnCount = metaData.getColumnCount();  
			 if(rs.next()) {  
			    	do{
			        jsonObj = new JSONObject();  
			         
			        // 遍历每一列  
			        for (int i = 1; i <= columnCount; i++) {  
			            String columnName =metaData.getColumnLabel(i);  
			            String value = rs.getString(columnName);  
			            jsonObj.put(columnName, value);  
			        }  
			        ls.add(jsonObj);
			    	}while(rs.next());
			         
			        back=ls.toString();
//			        System.out.println(back);
					out.write(back);
					out.flush();
			    } 
			   else{
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
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
