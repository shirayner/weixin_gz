package com.ray.weixin.gz.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.ray.weixin.gz.config.Env;
import com.ray.weixin.gz.service.tempmaterial.TempMaterialService;
import com.ray.weixin.gz.util.AuthHelper;

/**
 * Servlet implementation class UploadImgServlet
 */
public class UploadImgServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UploadImgServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String mediaId=request.getParameter("serverId");
		System.out.println("serverId:"+mediaId);

		try {
			String accessToken=AuthHelper.getAccessToken(Env.APP_ID, Env.APP_SECRET);
			
			//String savePath=System.getProperty("user.dir").replaceAll("\\\\", "/")+"/WebContent/img/"+mediaId+".png"; 
			String fileDir=request.getSession().getServletContext().getRealPath("").replaceAll("\\\\", "/")+"/img/"; 
			System.out.println("fileDir:"+fileDir);

			//2.调用业务类，获取临时素材
			TempMaterialService.getTempMaterial(accessToken, mediaId, fileDir);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		PrintWriter out = response.getWriter(); 
		out.print("HHHHHHHHHH");  
		out.close();  
		out = null;  
		
		
	}

}
