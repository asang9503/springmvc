package com.qfedu.exceptionhandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

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
//  目的:在项目中出现了异常之后需要对异常进行捕获，捕获之后进行日志的保存和页面的跳转
//  实现方式1 自定义类继承HandlerExceptionHandler接口
//HandlerExceptionHandler接口就是springmvc提供的捕获异常功能的接口
public class AllExceptionHandler implements HandlerExceptionResolver {

	// resolveException方法会在当前项目任何的异常（后台500异常）出现的时候执行
	// 是一个全局的异常处理方法
	// 此方法在捕获异常之后可以转发和携带数据到指定提示页面
	@Override

	public ModelAndView resolveException(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2,
			Exception arg3) {
		// 异常保存的信息一般有:时间，异常发生的位置，错误出现的路径，保存用户信息，地区地址，以及其他额外字段

		// 时间:使用数据库的now函数或者java的date类来获取当前时间。
		// 异常发生的位置:可以通过Log4j
		// 路径:通过request对象可以获取当前请求的路径
		// 用户信息:通过request可以获取用户的ip地址。还可以通过request获取session再获取当前登录的用户的信息
		// 地区地址:通过ip可以查询用户的地址。
		// 异常信息:通过Exception参数获取
		// Logger.getLogger(this.getClass()).error(message);

		System.out.println("发生了异常");
		System.out.println(arg0.getRequestURI());
		System.out.println(arg3.getMessage());

		// 第三个参数类型是HandlerMethod类型的对象，里面封装了当前错误发生的类和其他的错误信息
		System.out.println(((HandlerMethod) arg2).getBean().getClass().getName());
		System.out.println(((HandlerMethod) arg2).getMethod().getName());
		// 这里处理跳转以外还可以做日志处理

		ModelAndView mv = new ModelAndView();
		mv.setViewName("error");
		mv.addObject("exmsg", arg3.getMessage());
		return mv;
	}

}
