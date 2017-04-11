package UserServlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
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
 * Servlet implementation class UnFinished
 */
@WebServlet("/UnFinished")
public class UnFinished extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UnFinished() {
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
        Map map =new HashMap();
        String back=null;
        String msg=null;
        JsonUtils js=new JsonUtils();	
      //  HttpSession session=request.getSession();
        String user = request.getParameter("user");
//        System.out.print(user+"--");//输出为null
		JSONObject users=JSONObject.fromObject(user);
		String tel=(String) users.get("username");
		int pn = Integer.valueOf(users.get("pn").toString());
		int ps =Integer.valueOf(users.get("ps").toString());
		tel=tel.substring(4);
//		System.out.print("=="+tel);
		
		Connection conn=null;
		PreparedStatement pstmt=null;	
	    ResultSet rs=null;
	    ResultSet countRS=null;
	    List ls=new ArrayList();
		try{				
			
			String sql="select top "+ps+ "Id,GymArea," +
					" Place,SonPlace,SonArea,StartTime,OverTime,Date,Money ,State" +
					" from bookTable where Tel ='"+tel+"' and Id not in(select top "+ps*(pn-1)+" id from bookTable)";
			String countSql="select count(*) from bookTable where Tel = "+tel;
			conn=ConnectionFactory.getConnection();
			pstmt=conn.prepareStatement(sql);
			rs=pstmt.executeQuery();	
			pstmt=conn.prepareStatement(countSql);
		    countRS=pstmt.executeQuery();
		    countRS.next();
		    int countString=countRS.getInt(1);
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
//					System.out.println(back);
					out.write(back);
					out.flush();
			   
			}catch(SQLException e){
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
		doGet(request,response);
	}

}
