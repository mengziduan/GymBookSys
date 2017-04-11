package UserServlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sun.org.apache.bcel.internal.generic.LALOAD;

import net.sf.json.JSONObject;
import ConnectionFact.ConnectionFactory;
import ConnectionFact.ResourceClose;
import MyUtils.JsonUtils;

/**
 * Servlet implementation class LoadUserPers
 */
@WebServlet("/LoadUserPers")
public class LoadUserPers extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoadUserPers() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setContentType("text/html;charset=UTF-8");	
        PrintWriter out=response.getWriter();
//        System.out.println("LALOAD ");
        String edit1= request.getParameter("user");
        
		String edit =new String(edit1.getBytes("ISO-8859-1"),"UTF-8");
//		System.out.println(edit);
		JSONObject result=JSONObject.fromObject(edit);
		String tel =(String) result.get("Username");
		tel=tel.substring(4);
//		System.out.println(tel+"哈哈");
        Map map =new HashMap();
        String back=null;
        String msg=null;
        JsonUtils js=new JsonUtils();	
		
		Connection conn=null;
		PreparedStatement pstmt=null;	
	    ResultSet rs=null;
	    List ls=new ArrayList();
		try{				
			String sql="select Id ,Name,Tel,Password from Users  where tel='"+tel+"'";
				conn=ConnectionFactory.getConnection();
				pstmt=conn.prepareStatement(sql);
				rs=pstmt.executeQuery();	
				// 获取列数  
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
					            jsonObj.put(columnName, value);  
					        }  
					        ls.add(jsonObj);
					    	}while(rs.next());
					         
					        back=ls.toString();
					    } 
					    else{
					    	msg="failure";
							map.put("msg",msg );
							back=js.mapToJson(map);
					    }
					
					out.write(back);
//					System.out.println(back);
					out.flush();
			}catch(SQLException e){
				/*msg="failure";
				map.put("msg",msg );
				back=js.mapToJson(map);
				  }finally{
				   ResourceClose.close(rs, pstmt, conn);
				   System.out.println(back);*/
				e.printStackTrace();
				   }
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
