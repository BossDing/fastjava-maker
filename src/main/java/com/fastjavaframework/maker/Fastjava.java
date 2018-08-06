package com.fastjavaframework.maker;

import com.fastjavaframework.exception.ThrowException;
import com.fastjavaframework.maker.support.html.*;
import com.fastjavaframework.maker.support.servlet.*;
import com.fastjavaframework.util.VerifyUtils;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * @author wangshuli
 */
@WebServlet(urlPatterns = "/fastjava")
public class Fastjava extends HttpServlet {

	private static final long serialVersionUID = 1L;

	/**
	 * 模块
	 */
	private final String MODULE_MAPPER = "mapperHelper";
	private final String MODULE_MAPPER_READ = "mapperHelper_readPath";
	private final String MODULE_MAPPER_CREATE = "mapperHelper_createFile";
	private final String MODULE_MODULE = "moduleHelper";
	private final String MODULE_CACHE = "cacheHelper";
	private final String MODULE_API = "apiHelper";
	private final String MODULE_API_CREATE = "apiHelper_createDoc";
	private final String MODULE_API_SAVE = "apiHelper_saveAsHTML";
	private final String MODULE_QUARTZ = "quartzHelper";

	/**
	 * 参数
	 */
	private final String PARAM_TABLE_INFO = "tableInfos";
	private final String PARAM_MAPPER_PATH = "mapperPath";
	private final String PARAM_PROJECT_PATH = "projectPath";
	private final String PARAM_METHOD_TYPE = "methodType";
	private final String PARAM_MODEL = "model";
	private final String PARAM_CONTROLLER_CHK = "controllerChkHiden";
	private final String PARAM_PUBLIC_URL = "publicUrl";

	private Map<String,String> replaceMap = new HashMap<>();

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
		fastJava(req,resp);
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
		fastJava(req,resp);
	}
	
	/**
	 * 设置子页面
	 * 默认进入生成器页面
	 * @param req HttpServletRequest
	 * @return 子页面html
	 */
	private void fastJava(HttpServletRequest req, HttpServletResponse resp) {
		WebApplicationContext  ctx = WebApplicationContextUtils.getWebApplicationContext(req.getSession().getServletContext());
		replaceMap = this.getReplaceMap(req);	//获取前台页面的值
		
		boolean isReHtml = true;	//是否返回html，下载文件不返回
		
		//调用方法类型
		String methodType = null==req.getParameter(PARAM_METHOD_TYPE)?MODULE_MAPPER:req.getParameter(PARAM_METHOD_TYPE);
		
		StringBuffer returnJs = new StringBuffer("<script>"); //临时返回前台js
		String subHTML = "";//返回子页面html路径
		
		//调用方法 将返回的参数装入replaceMap，与前台原有数据一并返回；默认进入生成器页面并读取表信息
		//生成器
		if(methodType.startsWith(MODULE_MAPPER)) {
			subHTML = new MapperHelperHtml().html();

			// 读取路径
			if(MODULE_MAPPER_READ.equals(methodType)) {
				replaceMap.putAll(new MapperHelper().readPath(req.getParameter(PARAM_PROJECT_PATH)));
			} else if(MODULE_MAPPER_CREATE.equals(methodType)) {
				// 生成文件
				new MapperHelper().operatorFile(req.getParameter(PARAM_MODEL), ctx, req.getParameterMap());
				returnJs.append("alert('生成成功，请刷新项目！');");
			}

			//读取表信息
			if(null == req.getParameter(PARAM_TABLE_INFO)) {
				replaceMap.putAll(new MapperHelper().makeTableInfo(ctx));
			}
		} else if(methodType.startsWith(MODULE_MODULE)) {
			// 模块管理
			subHTML = new ModuleHelperHtml().html();
		} else if(methodType.startsWith(MODULE_CACHE)) {
			// 缓存管理
			CacheHelper cacheHelper = new CacheHelper();
			subHTML = new CacheHelperHtml().html();

			//读取配置文件路径
			if(MODULE_CACHE.equals(methodType)) {
				replaceMap.putAll(cacheHelper.readPath(req.getParameter(PARAM_PROJECT_PATH)));
			}
			
			//读取实体缓存信息
			if(null != replaceMap.get(PARAM_MAPPER_PATH)) {
				replaceMap.putAll(cacheHelper.cacheInfo(replaceMap.get(PARAM_MAPPER_PATH),ctx));
			}
		} else if(methodType.startsWith(MODULE_API)) {
			// api管理
			ApiHelper apiHelper = new ApiHelper();
			subHTML = new ApiHelperHtml().html();
			
			//读取配置文件路径
			if(MODULE_API.equals(methodType)) {
				replaceMap.putAll(apiHelper.readPath(req.getParameter(PARAM_PROJECT_PATH)));
			} else if(MODULE_API_CREATE.equals(methodType)) {
				// 生成文档
				replaceMap.putAll(apiHelper.createDoc(req.getParameter(PARAM_CONTROLLER_CHK),req.getParameter(PARAM_PUBLIC_URL)));
			} else if(MODULE_API_SAVE.equals(methodType)) {
				// 保存html
				// 临时文件路径
				String filePath = apiHelper.saveAsHTML(replaceMap.get("bodyHTML"));
				
		        // 下载文件
				if(!VerifyUtils.isEmpty(filePath)) {
			        InputStream in = null;
			        OutputStream out = null;
			        
					try {
						resp.setHeader("Pragma", "No-cache");
						resp.setHeader("Cache-Control", "No-cache");
						resp.setDateHeader("Expires", 0);
						resp.setHeader("Content-Disposition", "attachment;filename=\"api_doc.html\"");

						in = new FileInputStream(filePath);
						int len = 0;
						byte[] buf = new byte[1024];
						out = resp.getOutputStream();
						while ((len = in.read(buf)) > 0) {
							out.write(buf, 0, len);
						}

						isReHtml = false;
					} catch (Exception e) {
						returnJs.append("alert('保存html出错！');");
					} finally {
						try {
							if (in != null) {
								in.close();
							}
							if (out != null) {
								out.close();
							}
						} catch (IOException e) {
							returnJs.append("alert('保存html出错！');");
						}
					}
				}
			}

			//获取controller
			replaceMap.putAll(apiHelper.getController(replaceMap.get("controllerPath"),replaceMap.get("controllerChkHiden")));
		} else if(methodType.startsWith(MODULE_QUARTZ)) {
			// 定时任务管理
			subHTML = new QuartzHelperHtml().html();
		}
		
		returnJs.append("</script>");

		//返回前台html
		if(isReHtml) {
			//加入js
			subHTML += returnJs.toString();

			//将子页面拼接入框架页
			String indexHTML = new IndexHtml().html();
			String subPage = "<div id=\"subPage\">";
			String[] indexHTMLs = indexHTML.split(subPage);
			String fastjavaHTML = indexHTMLs[0] + subPage + subHTML + indexHTMLs[1];

			//根据replaceMap替换子页面的值
			for(String key : replaceMap.keySet()) {
				fastjavaHTML = fastjavaHTML.replace("var " + key, "var " + key + "=" + replaceMap.get(key));
			}

			//返回html
			resp.setCharacterEncoding("UTF-8");
			resp.setHeader("content-type","text/html;charset=UTF-8");
			try {
				PrintWriter out = resp.getWriter();
				out.print(fastjavaHTML);
				out.flush();
				out.close();
			} catch (IOException e) {
				throw new ThrowException("生成html错误：" + e.getMessage());
			}
		}
	}
	
	/**
	 * 获取前台页面的值
	 * 前台的值，装入replaceMap，最后返回前台(跳转servlet，前台页面值不变)
	 * @param req HttpServletRequest
	 * @return 前台的值Map<String,String>
	 */
	private Map<String,String> getReplaceMap(HttpServletRequest req) {
		Map<String,String> replaceMap = new HashMap<>();
		// 前台不加双引号的变量
		String notSignProperty = PARAM_TABLE_INFO;
		
		for (String key : req.getParameterMap().keySet()) {
			StringBuffer value = new StringBuffer("");
			if(req.getParameterMap().get(key).length > 0) {
				//添加值开始的双引号，tableInfos数组格式，值不加""号
				if(notSignProperty.indexOf(key) == -1) {
					value.append("\"");
				}
				
				//转换编码
				String paramVal = "";
				try {
					byte[] arrayStr = req.getParameterMap().get(key)[0].getBytes("iso-8859-1");
					paramVal = new String(arrayStr, "UTF-8");
				} catch (UnsupportedEncodingException e) {
					throw new ThrowException("转换参数编码错误：" + e.getMessage());
				}
						
				//格式化路径中的"\"符号
				value.append(paramVal.replaceAll("\\\\", "\\\\\\\\"));
				
				//添加值结尾的双引号，tableInfos数组格式，值不加""号
				if(notSignProperty.indexOf(key) == -1) {
					value.append("\"");
				}
			}
			replaceMap.put(key, value.toString());
		}
		
		return replaceMap;
	}
}
