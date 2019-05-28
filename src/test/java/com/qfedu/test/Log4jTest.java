package com.qfedu.test;
//                       .::::.

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

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
//log4j主要有三个功能1控制日志输出的目的地，可以是控制台，文件，数据库

import org.junit.Test;

//2可以控制日志输出的格式，比如只要异常信息不要时间。或者时间，异常信息，还有异常发生的位置

//3控制日志输出等级。控制部分日志的输出

//使用主要是配置属性文件

//使用场景1提供给spring或者mybatis，因为这些框架的日志输出功能就是依靠log4j的。
//2开发者和也可以在自己的项目中提供给自己使用

public class Log4jTest {

	@Test
	public void test1() throws IOException {
		// 需求输出日志到控制台
		// 之前可以直接使用syso的方式，缺点1不能控制输出或者关闭输出2不能控制输出的目的地

		InputStream in = Log4jTest.class.getClassLoader().getResourceAsStream("log4j.properties");

		Properties pro = new Properties();
		pro.load(in);
		// 读取配置文件
		PropertyConfigurator.configure(pro);
		// 以上是配置log4j需要的配置文件的相关路径，可以省略，省略的话默认读取classpath下的log4j.properties文件

		// 进行输出，info方法表示输出info等级的日志
		// 对应的常用有四个分别是debug,info,warn,error
		Logger.getLogger(Log4jTest.class).info("你好");

		Logger.getLogger(Log4jTest.class).error("我不好");

	}
}
