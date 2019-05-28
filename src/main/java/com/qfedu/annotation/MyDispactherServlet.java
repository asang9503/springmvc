package com.qfedu.annotation;
//                       .::::.

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;

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

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//此servlet用来模拟核心控制器的功能
public class MyDispactherServlet extends HttpServlet {

	private Map<String, MappingInfo> map;

	// init会在初始化的时候执行，并且执行一次，所以在这里扫描注解
	// init执行完毕可以得到路径和方法的映射关系
	public void init() throws ServletException {

		map = new HashMap<String, MappingInfo>();

		// 根据包名获取包下所有的类
		// 对于一个web应用，他的classpath是在当前项目编译成class文件之后部署到服务器上的目录下

		String classpath = this.getServletContext().getRealPath("WEB-INF/classes/");
		System.out.println(classpath);
		// 指定classpath
		PackageUtil.classpath = classpath;
		// 包下所有的类的包名和类名的集合
		List<String> classes = PackageUtil.getClassName("com.qfedu");
		System.out.println(classes);

		// 挑选带有@Cotroller注解的类，因为目的是获取@ReuqstMapping中的数据，ReuqstMapping只在带有Cotroller注解的类中

		for (String classname : classes) {

			try {
				Class cls = Class.forName(classname);
				// 获取修饰类的MyContoller注解对象
				MyContoller mc = (MyContoller) cls.getAnnotation(MyContoller.class);
				// 如果为空表示当前循环的类不带有MyContoller注解，也就是不是业务处理器
				if (mc != null) {
					// 获取类中所有的方法
					Method[] ms = cls.getMethods();
					// 对所有的方法进行遍历，获取哪些带有@RequstMapping的方法
					for (Method m : ms) {

						// 挨个获取每一个方法是否拥有MyRequestMapping注解
						MyRequestMapping requestMapping = m.getAnnotation(MyRequestMapping.class);

						if (requestMapping != null) {
							// 不等于null表示当前方法是一个可以用于接受请求的方法

							// 得到用户配置的路径
							String path = requestMapping.path();
							// 拿到路径之后需要做一个映射关系，键是路径，值是路径对应的方法（哪一个类的哪一个方法）

							MappingInfo mi = new MappingInfo();
							mi.setClassName(cls.getName());
							mi.setMethodName(m.getName());

							map.put(path, mi);
						}

					}

				}

			} catch (ClassNotFoundException e) {

				e.printStackTrace();
			}

		}

		System.out.println(map);
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 当前servlet的路径是"/"也就是访问非jsp都会到达此servlet
		// servlet现在的工作就是根据请求的路径，将请求分发给对应业务处理器

		// 先获取当前请求路径
		String uri = req.getRequestURI();
		System.out.println(uri);
		int index = uri.lastIndexOf("/");
		String key = uri.substring(index);
		System.out.println(key);
		MappingInfo info = map.get(key);

		if (info != null) {
			// 从info中可以获取当前要指定的类和类中的方法的名字，通过反射执行
			try {
				Class cls = Class.forName(info.getClassName());

				Method m = cls.getMethod(info.getMethodName());

				// 通过反射执行方法
				// 第一个参数是方法所需要的一个类的实例对象
				// 返回值是方法的返回数据
				String reslut = (String) m.invoke(cls.newInstance(), null);

				if (reslut.startsWith("redirect:")) {

					resp.sendRedirect(reslut.replace("redirect:", ""));

				} else {
					// 执行完毕方法之后还需要根据方法的返回值进行跳转
					req.getRequestDispatcher("WEB-INF/views/" + reslut + ".jsp").forward(req, resp);

				}

			} catch (Exception e) {

				e.printStackTrace();
			}

		}

	}
}
