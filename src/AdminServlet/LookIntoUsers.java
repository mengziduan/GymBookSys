package AdminServlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
 * Servlet implementation class LookIntoUsers
 */
@WebServlet("/LookIntoUsers")
public class LookIntoUsers extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LookIntoUsers() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html;charset=UTF-8");
		//Map map =new HashMap();
		PrintWriter out=response.getWriter();//ͨ���������ͻ���������Ӧ
		String userinfo1 = request.getParameter("userinfo");
		String userinfo =new String(userinfo1.getBytes("ISO-8859-1"),"UTF-8");
		//System.out.println(userinfo);
		JSONObject result=JSONObject.fromObject(userinfo);	
		String Isfsd =(String) result.get("Isfsd");
//		System.out.println(Isfsd);
		String Grade =(String) result.get("Grade");
//		System.out.println(Grade);
		String back = null;
		//String msg=null;
		JsonUtils js=new JsonUtils();
		//测试是否从前端获取值
		
		//HttpSession session=request.getSession();
		PreparedStatement pstmt=null;		
		ResultSet rs=null;
		Connection conn=null;
		List ls=new ArrayList();
		String sql=null;
		try{	
			if(Isfsd.equals("请选择")){
				if(Grade.equals("请选择")){
					sql="select Id,Name,Tel,Isfsd,College,Grade,StuID from Users ";
				}else{
					sql="select Id,Name,Tel,Isfsd,College,Grade,StuID from Users where Grade='"+Grade+"'";
				}
			}else{
				if(("请选择").equals(Grade)){
					sql="select Id,Name,Tel,Isfsd,College,Grade,StuID from Users where Isfsd='"+Isfsd+"'";
				}else if(Isfsd.equals("校外人员")){
					sql="select Id,Name,Tel,Isfsd,College,Grade,StuID from Users where Isfsd='"+Isfsd+"'";
				}else{
					sql="select Id,Name,Tel,Isfsd,College,Grade,StuID from Users where Isfsd='"+Isfsd+"' and Grade='"+Grade+"'";
				}
			}
				
		    conn=ConnectionFactory.getConnection();
			pstmt=conn.prepareStatement(sql);
			rs=pstmt.executeQuery();
			
			// 获取列数  
			   ResultSetMetaData metaData = rs.getMetaData();  
			   int columnCount = metaData.getColumnCount();  
			   JSONObject jsonObj=null;
			  // byte[] by;
			   // 遍历ResultSet中的每条数据  
			    if(rs.next()) {  
//			    	System.out.println("测试");
			    	do{
			        jsonObj = new JSONObject();  
			         
			        // 遍历每一列  
			        for (int i = 1; i <= columnCount; i++) {  
			            String columnName =metaData.getColumnLabel(i);  
			            String value = rs.getString(columnName);  
			            System.out.println(value);
			            jsonObj.put(columnName, value);  
			        }  
			        ls.add(jsonObj);
			    	}while(rs.next());
			         
			        back=ls.toString();
			    } 
			   // else{
			    	//msg="failure";
					//map.put("msg",msg );
					//back=js.mapToJson(map);
			   // }
		
//			System.out.println(back);
			out.write(back);
			out.flush();
			}catch(Exception e){
				e.printStackTrace();
				//msg="failure";
				//map.put("msg",msg );
				//back=js.mapToJson(map);
				
			}
		finally{out.close();
				ResourceClose.close(rs, pstmt, conn);
				//map.clear();
				//System.out.println(back);
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
