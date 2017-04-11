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
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;
import ConnectionFact.ConnectionFactory;
import ConnectionFact.ResourceClose;
import MyUtils.JsonUtils;

/**
 * Servlet implementation class ShowArea
 */
@WebServlet("/ShowArea")
public class ShowArea extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ShowArea() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html;charset=UTF-8");
		
		String load1= request.getParameter("admininfo");
		String load =new String(load1.getBytes("ISO-8859-1"),"UTF-8");
//		System.out.println("加载场地信息"+load);
		JSONObject result=JSONObject.fromObject(load);
		String power =(String) result.get("Power");
		//System.out.println("加载场地信息"+power);
		String Username =(String) result.get("Username");	
		Username=Username.substring(10);
//		System.out.println("加载场地信息"+Username);
		
		int pn = Integer.valueOf(result.get("pn").toString());
		int ps =Integer.valueOf(result.get("ps").toString());
		
		Map map =new HashMap();
		PrintWriter out=response.getWriter();  	

		String back;
		
		String msg=null;
		JsonUtils js=new JsonUtils();
		//测试是否从前端获取值
		
		//HttpSession session=request.getSession();
		PreparedStatement pstmt=null;		
		ResultSet rs=null;
		ResultSet countRS=null;
		Connection conn=null;
		List ls=new ArrayList();
		try{
			String sql=null;
			if(power.equals("A")){
				//System.out.println("加载场地信息0000");
				sql = "select top "+ps+" ID,GymArea,Place,SonPlace,SonArea,Admin,status from Area where Id not in ( select top "+ps*(pn-1)+" Id from Area)";
			}
			if(power.equals("B")){
				sql = "select top "+ps+" ID,GymArea,Place,SonPlace,SonArea,Admin,status from Area where Id not in ( select top "+ps*(pn-1)+" Id from Area) and Admin='"+Username+"'";
			}
		    conn=ConnectionFactory.getConnection();
		    
		    String countSql="select count(*) from Area";
			pstmt=conn.prepareStatement(countSql);
		    countRS=pstmt.executeQuery();
		    countRS.next();
		    int countString=countRS.getInt(1);
		    
			pstmt=conn.prepareStatement(sql);
			rs=pstmt.executeQuery();
			
			// 获取列数  
			   ResultSetMetaData metaData = rs.getMetaData();  
			   int columnCount = metaData.getColumnCount();  
			   JSONObject jsonObj=null;
			  // byte[] by;
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
			    	JSONObject tempObject = new JSONObject();
			    	tempObject.put("count", countString);
			    	ls.add(tempObject);
			        back=ls.toString();
			    } 
			    else{
			    	msg="failure";
					map.put("msg",msg );
					map.put("reason","页面加载失败");
					back=js.mapToJson(map);
			    }
		
			//System.out.println(back);
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
