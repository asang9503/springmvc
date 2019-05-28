package com.qfedu.controller;

import java.io.File;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.qfedu.pojo.User;

//                       .::::.
//                     .::::::::.
//                    :::::::::::
//                 ..:::::::::::'
//              '::::::::::::'
//                .::::::::::
//           '::::::::::::::..
//                ..::::::::::::.
//              ``::::::::::::::::
//               ::::``:::::::::'        .:::.
//              ::::'   ':::::'       .::::::::.
//            .::::'      ::::     .:::::::'::::.
//           .:::'       :::::  .:::::::::' ':::::.
//          .::'        :::::.:::::::::'      ':::::.
//         .::'         ::::::::::::::'         ``::::.
//     ...:::           ::::::::::::'              ``::.
//    ```` ':.          ':::::::::'                  ::::..
//                       '.:::::'                    ':'````..
//
//

@Controller

// 为当前类中所有的业务处理器提供个统一的上一层级路径
@RequestMapping("/user")
public class GetDataController {

	@RequestMapping("/getRequest1")
	public String getData1(HttpServletRequest request) {
		request.getParameter("username");
		return "index";
	}

	// 入参数添加参数，参数的个数和类型没有要求，按照需求可以自由添加
	// 当前想要获取两个前端表单数据，那么我们就直接在入参数直接创建两个参数
	// springmvc会自动读取表单数据的值，并且赋值给带有@RequestParam注解的参数，注解中需要指定表单中键的值
	// 并且会做自动类型的转换，需要注意可能出现类型转换异常。

	@RequestMapping("/getRequest2")
	public String getData2(@RequestParam(name = "username", required = true, defaultValue = "pfdu") String name,
			@RequestParam("password") String pass, @RequestParam("age") int age) {

		System.out.println(name);
		System.out.println(pass);
		System.out.println(age);
		return "index";
	}

	// 也可以直接省略@RequestParam，但是要求参数的变量名一定要和表单的键一致
	// 缺少@RequestParam提供的是否必选，默认值这些功能
	@RequestMapping("/getRequest3")
	public String getData3(String username, String password, int age) {

		System.out.println(username);
		System.out.println(password);
		System.out.println(age);
		return "index";
	}

	// springmvc还封装了获取请求中其他部分数据的功能，比如请求头
	// 入参处@RequestHeader会让springmvc调用方法的同时读取请求头中的数据并且赋值给紧挨着的参数变量
	@RequestMapping("/getRequest4")
	public String getData4(@RequestHeader("Cookie") String cookies) {

		System.out.println(cookies);

		return "index";
	}

	// 还可以直接获取某一个cookie的值
	// @CookieValue指定某一个cookie的键名
	@RequestMapping("/getRequest5")
	public String getData5(@CookieValue("JSESSIONID") String cookies) {

		System.out.println(cookies);

		return "index";
	}

	// 可以在入参数使用pojo的形式获取表单数据
	// 如果前端表单数据过多，以单个参数的形式分别声明在入参数会造成代码臃肿，
	// 将所有参数封装成pojo对象
	@RequestMapping("/getRequest6")
	public String getData6(User user) {

		System.out.println(user);

		return "index";
	}

	// 如果要求在业务处理器中操作session,application等对象的时候。
	// 我们可以通过requets对象获取
	// 入参数还可以是HttpSession，HttpServletResponse
	@RequestMapping("/getRequest7")
	public void getData7(HttpServletRequest request, HttpSession session, HttpServletResponse resp) throws IOException {
		// 获取session
		request.getSession();
		// 获取application
		request.getServletContext();

		resp.getWriter().print("support json to ajax");

		// 方法的返回类型可以是void，如果是void的话意味着springmvc在执行完毕业务逻辑之后不会帮助开发者进行转发或者重定向的响应
		// 一般只有手动操作HttpServletResponse对象的时候才返回类型为void.

		// return "index";

		// 请求中的乱码问题
		// tomcat5之前setCharacterEncoding只能解决post中文乱码
		// get需要修改tomcat的配置文件。
		// 但是5之后setCharacterEncoding可以修改get和post乱码

		// request.setCharacterEncoding("utf-8");

	}

	// 请求中的乱码问题
	// tomcat5之前setCharacterEncoding只能解决post中文乱码
	// get需要修改tomcat的配置文件。
	// 但是5之后setCharacterEncoding可以修改get和post乱码

	// request.setCharacterEncoding("utf-8");

	// 原生servlet中文件上传比较复杂，但是在springmvc中其实就是数据的流入的一部门，代码与数据流入的风格一样
	// 与普通input几乎一样，可以指定@RequestParam注解，只是变量的类型一定是MultipartFile即可，springmvc会自动将请求中的文件保存到MultipartFile中
	@RequestMapping("/getRequest8")
	public String getData8(User user, @RequestParam("photo") MultipartFile[] mFiles)
			throws IllegalStateException, IOException {
		System.out.println(user);
		// 如果多文件上传则直接使用数组，在对其循环遍历即可
		for (MultipartFile mFile : mFiles) {

			String type = mFile.getContentType();
			long size = mFile.getSize();
			String name = mFile.getName();
			String fileName = mFile.getOriginalFilename();

			System.out.println("类型:" + type);
			System.out.println("大小:" + size);
			System.out.println("表单的键:" + name);
			System.out.println("文件名字:" + fileName);

			File dir = new File("Z:/windos/xiaomi/" + fileName);
			mFile.transferTo(dir);
			// 作业:保存文件的文件夹应该按照日期进行创建，每天一个文件夹，方便管理文件
			

		}

		return "index";
	}
	
	
	//测试的，故意线程休眠五秒，用于测试拦截器统计所有处理器的执行时间
	@RequestMapping("/sleep")
	public String testSleep() throws InterruptedException{
		
		
		Thread.sleep(5000);
		
		return "index";
	}

}
