package LoginServlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import MyUtils.JsonUtils;

/**
 * Servlet implementation class exitServlet
 */
@WebServlet("/ExitServlet")
public class ExitServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ExitServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out=response.getWriter();
		Map map =new HashMap();
		JsonUtils js=new JsonUtils();
//		System.out.println("llll");
		Cookie cookie_name = new Cookie("username", null);
		Cookie cookie_tel = new Cookie("tel", null);
		Cookie cookie_power = new Cookie("power", null);
		cookie_name.setMaxAge(0);
		 cookie_tel.setMaxAge(0);
		 cookie_power.setMaxAge(0);
			response.addCookie(cookie_name);
			response.addCookie(cookie_tel);
			response.addCookie(cookie_power);
			//System.out.println(back);
			 map.put("msg", "success");
			String back=js.mapToJson(map);			 
			out.write(back);
			out.flush();
			out.close();
		/*Cookie[] cookies=request.getCookies();

		for(Cookie cookie: cookies){
       System.out.println("00");
		cookie.setMaxAge(0);

		cookie.setPath("/");

		response.addCookie(cookie);

		}*/
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
