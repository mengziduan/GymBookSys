package AdminServlet;

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

import net.sf.json.JSONObject;
import ConnectionFact.ConnectionFactory;
import ConnectionFact.ResourceClose;
import MyUtils.JsonUtils;

/**
 * Servlet implementation class LookIntoOrders
 */
@WebServlet("/LookIntoOrders")
public class LookIntoOrders extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LookIntoOrders() {
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
        String order1= request.getParameter("orderInfo");
		String order =new String(order1.getBytes("ISO-8859-1"),"UTF-8");
		//System.out.println("-----------order"+order);
		JSONObject result=JSONObject.fromObject(order);
		String State =(String) result.get("State");
		String endTime =(String) result.get("endTime");
		String beginTime =(String) result.get("beginTime");
        String back=null;
        String msg=null;
        JsonUtils js=new JsonUtils();	
		
		Connection conn=null;
		PreparedStatement pstmt=null;	
	    ResultSet rs=null;
	    List ls=new ArrayList();
	    String sql=null;
		try{				
			if(State.equals("请选择")){
				if(!beginTime.equals("")){
//					System.out.println("1");
					sql="select Id,Name,Tel,GymArea," +
							"Place,SonPlace,SonArea,StartTime,OverTime,Date,Money,State,OrderTime"+
							" from BookTable where Date>='"+beginTime+"' and Date<='"+endTime+"'";
					//sql="select Id,Name,Tel,GymArea," +
					//		"Place,SonPlace,SonArea,StartTime,OverTime,Date,Money,State,OrderTime"+
					//		" from BookTable ";
				}else{
//					System.out.println("2");
					sql="select Id,Name,Tel,GymArea," +
							"Place,SonPlace,SonArea,StartTime,OverTime,Date,Money,State,OrderTime"+
							" from BookTable ";
					//sql="select Id,Name,Tel,GymArea," +
					//		"Place,SonPlace,SonArea,StartTime,OverTime,Date,Money,State,OrderTime"+
					//		" from BookTable where Date>='"+beginTime+"' and Date<='"+endTime+"'";
				}
			}else if(State.equals("已完成")){
				if(beginTime.equals("")){
//					System.out.println("3");
					sql="select Id,Name,Tel,GymArea," +
							"Place,SonPlace,SonArea,StartTime,OverTime,Date,Money,State,OrderTime"+
							" from BookTable where State='finished'";
				}else{
//					System.out.println("4");
					sql="select Id,Name,Tel,GymArea," +
							"Place,SonPlace,SonArea,StartTime,OverTime,Date,Money,State,OrderTime"+
							" from BookTable where State='finished' and Date>='"+beginTime+"' and Date<='"+endTime+"'";
				}
			}else if(State.equals("未完成")){
				if(beginTime.equals("")){
//					System.out.println("5");
					sql="select Id,Name,Tel,GymArea," +
							"Place,SonPlace,SonArea,StartTime,OverTime,Date,Money,State,OrderTime"+
							" from BookTable where State='unfinished'";
				}else{
//					System.out.println("6");
					sql="select Id,Name,Tel,GymArea," +
							"Place,SonPlace,SonArea,StartTime,OverTime,Date,Money,State,OrderTime"+
							" from BookTable where State='unfinished' and Date>='"+beginTime+"' and Date<='"+endTime+"'";
				}
			}
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
					out.flush();
			}catch(SQLException e){
				msg="failure";
				map.put("msg",msg );
				back=js.mapToJson(map);
				  }finally{
				   ResourceClose.close(rs, pstmt, conn);
//				   System.out.println(back);
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
