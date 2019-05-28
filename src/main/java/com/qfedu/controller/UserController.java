package com.qfedu.controller;
//                       .::::.

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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
//处理用户请求，代码上用来代替之前的servlet
//
//@Controller声明的类都是一个处理器，处理器就是处理用户请求的类
//同时他也会成为一个spring容器中的bean
@Controller
public class UserController {

	// 书写一个方法处理用户的请求

	// 方法的要求
	// 1权限是public
	// 2返回类型暂时就认定必须为String
	// 3方法名自定义
	// 4方法要是用一个注解进行修饰
	// 5入参数可以按照需求扩展参数
	// @RequestMapping属性中是路径，表示访问此路径的时候会执行其下部的方法
	// 下面的方法就是用于接受用户请求的代码区域，类似doGet或者doPost的功能
	// 默认此方法既处理get请求又处理post请求
	@RequestMapping(path = "/login", method = RequestMethod.GET)
	// @GetMapping("/login") 与上面的mappring等价
	public String login() {

		int i =1/0;
		System.out.println("处理了登录请求");

		// return这里是用于返回给视图解析器的，视图解析器会根据返回的字符串加上前缀和后缀进行转发操作
		return "index";
	}

	// springmvc比较直接使用servlet优点，减少servlet的创建，因为使用springmvc之后只有一个servlet。
	// 一个@Controller类中可以有多个带有@RequestMapping的方法，减少类的创建，将某个模块的所有请求全部定在一个类中

	@RequestMapping("/resgist")
	public String regist(HttpServletRequest req) {

		System.out.println("处理注册");

		// 注册成功重定向到test.html
		// 只要返回的字符串以redirect开头那么就表示重定向跳转，:后是跳转的路径
		// 路径可以是相对也可以是绝对，绝对以"/"开头。这里的"/"和之前普通的重定向中"/"不同，这里表示达到项目的根目录
		return "redirect:/test.html";
	}

}
