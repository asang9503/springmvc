package com.qfedu.annotation;
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

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.core.annotation.AliasFor;

@Retention(RetentionPolicy.RUNTIME)
@Target(value = { ElementType.METHOD, ElementType.TYPE })
public @interface MyRequestMapping {

	// 注解也可以有属性，目的也是用来保存数据，一般来说开发者会将配置信息保存到注解的属性中，然后框架会读取注解
	// 于开发者配置xml文件。框架运行时读取xml配置文件一个道理。

	// 注解中的属性的要求1类型必须只能是基本类型，String,枚举和他们的数组结构
	// 2注解中的属性的写法类似于类中的方法，并且可以有默认值
	@AliasFor("value")
	public String path() default "";

	
	@AliasFor("path")
	public String value() default "";
	
	

}
