package UserServlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
 * Servlet implementation class Booking
 */
@WebServlet("/Booking")
public class Booking extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Booking() {
		super();

		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub

		response.setContentType("text/html;charset=UTF-8");
		request.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		Map map = new HashMap();
		JsonUtils js = new JsonUtils();
		// System.out.println("ddd");
		// PrintWriter out=response.getWriter();
		// Map map =new HashMap();
		// String back=null;
		// String msg=null;
		// JsonUtils js=new JsonUtils();

		// HttpSession session=request.getSession();
		String txt1 = request.getParameter("txt");
		String txt = new String(txt1.getBytes("ISO-8859-1"), "UTF-8");
		// System.out.println(txt+"--");//输出为null
		JSONObject result = JSONObject.fromObject(txt);
		String date = (String) result.get("Date");
		String gymArea = (String) result.get("GymArea");
		String place = (String) result.get("Place");
		String sonPlace = (String) result.get("SonPlace");
		String sonArea = (String) result.get("SonArea");
		String starTime = (String) result.get("StartTime");
		String overTime = (String) result.get("OverTime");
		String name = (String) result.get("Name");
		String tel = (String) result.get("Tel");
		name = name.substring(10);
		tel = tel.substring(4);

		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式
		String OrderTime = df.format(new Date());// new Date()为获取当前系统时间

		String type = (String) result.get("Type");
		String price = null;
		// System.out.println(type+"--");
		double hou;
		double pri;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			StringToDateTime stdt = new StringToDateTime(starTime, overTime);
			conn = ConnectionFactory.getConnection();
			hou = stdt.calcuTime();
			// System.out.println("booking"+hou);
			String sql1 = "select Price from MainF where Type='" + sonPlace + "'";
			pstmt = conn.prepareStatement(sql1);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				price = rs.getString(1);
				// System.out.println(price+"kkk");
			}
			pri = Double.parseDouble(price);

//			String sql = "insert into BookTable(Name,Tel,Date,GymArea,Place,SonPlace,SonArea,StartTime,OverTime,Price,Money,State) values('"
//					+ name + "','" + tel + "','" + date + "','" + gymArea + "','" + place + "','" + sonPlace + "','"
//					+ sonArea + "','" + starTime + "','" + overTime + "','" + pri + "','" + hou * pri + "','unfinished')";
            
			String sql="insert into BookTable(Name,Tel,Date,GymArea,Place,SonPlace,SonArea,StartTime,OverTime,Price,Money,State,OrderTime) values('"+name+"','"+tel+"','"+date+"','"+gymArea+"','"+place+"','"+sonPlace+"','"+sonArea+"','"+starTime+"','"+overTime+"','"+pri+"','"+hou*pri+"','unfinished','"+OrderTime+"')";
			
//			System.out.println(sql);
			pstmt = conn.prepareStatement(sql);
			pstmt.executeUpdate();
			map.put("msg", "success");
			String back = js.mapToJson(map);
//			System.out.println("booking" + back);
			out.write(back);
			out.flush();
		} catch (SQLException e) {
			 e.printStackTrace();
			map.put("msg", "failure");
			String back = js.mapToJson(map);
//			System.out.println("booking" + back);
			out.write(back);
			out.flush();
		} finally {
			ResourceClose.close(rs, pstmt, conn);
		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
