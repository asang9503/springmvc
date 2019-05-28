package com.qfedu.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.NamedThreadLocal;
import org.springframework.web.servlet.HandlerInterceptor;
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
//
//自定义拦截器，计算每个业务处理器执行的消耗时间，如果超过5秒则认为代码需要优化修改
//使用拦截器，在进入业务处理器之前记录一个时间点，在处理器执行完毕之后再记录时间，结束减去开始时间就是耗时事时间
//拦截器也是aop思想的一种具体实现
//和过滤器最大的区别是过滤器可以过滤所有类型的请求。但是拦截器只是针对业务处理器
public class ExcuteTimeInterceptor implements HandlerInterceptor {

	// 在多线程的情况下出现一个bug，执行时间不准确，如果访问sleep最短也是5000毫秒才是正常数据
	// 拦截器是单例的

	// 场景描述:00:00客户端a访问sleep。00:01客户端b访问sleep
	// 00:05客户端a访问的sleep线程访问完毕，用当前的时间startime。计算出来的是4秒钟。

	// 这就是一个线程不安全的问题。
	// 对于线程安全问题，很容易想到同步锁，但是效率极低，这里不推荐使用。

	// 解决方法可以使用线程副本，ThreadLocal。
	// 我们操作的变量不再是一个直接声明类中的属性了，因为这样所有的线程都共享他，都可以对他进行操作。
	// 使用ThreadLocal之后，将数据保存在副本中，这样每一个线程想要操作他，ThreadLocal负责为线程提供一个变量的副本，线程对变量任何的操作都是对副本的改变，不会影响其他的线程副本
	private long stratTime;
	// 与ThreadLocal使用一致，是springmvc提供的一个ThreadLocal的子类
	private NamedThreadLocal<Long> tl = new NamedThreadLocal<Long>("time");

	// 一个请求完整的结束，也就是响应完毕之后的方法
	@Override
	public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
		System.out.println("afterCompletion");

	}

	// 后置处理，在离开处理器之后会执行的方法
	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3)
			throws Exception {

		System.out.println("postHandle");
		long endTime = System.currentTimeMillis();
		// 从thread里读取数据，读取的是当前线程存放到thread中的变量副本
		long currentStartTime = tl.get();

		System.out.println("执行时间" + (endTime - currentStartTime));

	}

	// 预处理，在进入业务处理器之前会执行preHandle方法
	// 返回是一个布尔值，如果是false表示拦截，如果是true则表示继续调用下面的controller
	@Override
	public boolean preHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2) throws Exception {

		System.out.println("preHandle");

		stratTime = System.currentTimeMillis();
		// stratTime放到threadlocal之后会和当前线程进行绑定，也就是为当前线程创建一个独立的stratTime变量
		tl.set(stratTime);
		return true;
	}

}
