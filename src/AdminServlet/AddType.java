package AdminServlet;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.apache.catalina.ha.backend.Sender;

import com.sun.xml.internal.bind.v2.schemagen.xmlschema.Particle;

import net.sf.json.JSONObject;
import ConnectionFact.ConnectionFactory;
import ConnectionFact.ResourceClose;
import MyUtils.JsonUtils;

/**
 * Servlet implementation class AddType
 */
@WebServlet("/AddType")
@MultipartConfig
public class AddType extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddType() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@SuppressWarnings({ "static-access", "rawtypes", "unchecked" })
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out=response.getWriter();
	    request.setCharacterEncoding("utf-8");
		//文件的处理：
		 //获取文件部件part
	    String Type=request.getParameter("Type");
		String Address=request.getParameter("GymArea");
		String Price=request.getParameter("Price");;
	    System.out.print("开始上传");
		Part part=request.getPart("Picture");
		String root=request.getServletContext().getRealPath("/imgs");
		String headname=part.getHeader("content-disposition");
		String ext=headname.substring(headname.lastIndexOf("."),headname.length()-1);
		String ranString=UUID.randomUUID().toString();
		String filename=root+"\\"+ranString+ext;
		System.out.println(filename);
		 part.write(filename);
		 System.out.println("上传成功"+filename.length());
		 filename="imgs/"+ranString+ext;
         filename=filename.replace("\\", "/");
         System.out.println(filename);
		String back=null;
		Map map =new HashMap();
		String msg=null;
		JsonUtils js=new JsonUtils();
		PreparedStatement pstmt=null;		
		ResultSet rs=null;
		Connection conn=null;
		try{
			String sql1="select Type,Address from MainF where Type='"+Type+"' and Address='"+Address+"'";
			conn=ConnectionFactory.getConnection();
			pstmt=conn.prepareStatement(sql1);
			rs=pstmt.executeQuery();
			if(rs.next()) {
				System.out.println("-_______________________________________________________");
				msg="exsit";
				String delSql="delete from MainF  where Type='"+Type+"' and Address='"+Address+"'";
				conn=ConnectionFactory.getConnection();
				pstmt=conn.prepareStatement(delSql);
				pstmt.executeUpdate();
				
			}
		String sql="insert into MainF(Type,Address,Price,Picture) values('"+Type+"','"+Address+"','"+Price+"',' "+filename+"')";
		conn=ConnectionFactory.getConnection();
		pstmt=conn.prepareStatement(sql);
		pstmt.executeUpdate();
		msg="success";
		response.sendRedirect("indexGymType.html");	
		map.put("msg",msg );
		back=js.mapToJson(map);
		out.write(back);
		out.flush();
		
		}
		catch(Exception e)
		{
			e.printStackTrace();
		/*	msg="failure";
			map.put("msg",msg );
			back=js.mapToJson(map);
			out.write(back);
			out.flush();*/
		}finally{
			ResourceClose.close(pstmt,conn);
			out.close();
			System.out.println("33"+back);
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
