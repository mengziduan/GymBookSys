package AdminServlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ConnectionFact.ConnectionFactory;
import ConnectionFact.ResourceClose;

@WebServlet("/TestServlet")
public class TestServlet extends HttpServlet{
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Connection conn=null;
		PreparedStatement pstmt=null;	
	    ResultSet rs=null;
	    try{	
			String sql="select * from BookTable";
			conn=ConnectionFactory.getConnection();
			pstmt=conn.prepareStatement(sql);
			rs=pstmt.executeQuery();
			ResultSetMetaData metaData = rs.getMetaData();  
			int columnCount = metaData.getColumnCount();  
			if(rs.next()) {  
				do{
				   // 遍历每一列  
				   for (int i = 1; i <= columnCount; i++) {  
				      String columnName =metaData.getColumnLabel(i);  
				      String value = rs.getString(columnName);  
				      System.out.println(columnName+" "+value);
				    }  
				}while(rs.next());
			} 
	    } catch (SQLException e) {
			e.printStackTrace();
		}finally{
			ResourceClose.close(rs, pstmt, conn);
	    }
	}
}
