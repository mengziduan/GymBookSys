package UserServlet;

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
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import ConnectionFact.ConnectionFactory;
import ConnectionFact.ResourceClose;
import MyUtils.JsonUtils;

/**
 * Servlet implementation class MainServlet
 */
@WebServlet("/MainServlet")
public class MainServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MainServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html;charset=UTF-8");
		Map map =new HashMap();
		//System.out.println("hh");
		PrintWriter out=response.getWriter();//ͨ���������ͻ���������Ӧ
		String txt = request.getParameter("txt");
		JSONObject result=JSONObject.fromObject(txt);    	
		String back=null;
		String msg=null;
		JsonUtils js=new JsonUtils();		
		HttpSession session=request.getSession();
		PreparedStatement pstmt=null;		
		ResultSet rs=null;
		Connection conn=null;
		List ls=new ArrayList();		
		try{	
			String sql="select * from MainF ";
			//System.out.println("hh");
		    conn=ConnectionFactory.getConnection();
			pstmt=conn.prepareStatement(sql);
			rs=pstmt.executeQuery();
			// 获取列数  
			   ResultSetMetaData metaData = rs.getMetaData();  
			   int columnCount = metaData.getColumnCount();  
			   JSONObject jsonObj=null;
			   // 遍历ResultSet中的每条数据  
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
					map.put("reason","没有订单信息");
					back=js.mapToJson(map);
			    }	    
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
		// TODO Auto-generated method stub
		doGet(request,response);
	}

}
