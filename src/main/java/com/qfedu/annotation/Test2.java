package com.qfedu.annotation;

import java.lang.reflect.Method;

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

public class Test2 {

	// 排除警告
	@SuppressWarnings("unused")
	private int i;

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return super.toString();
	}

	// @Deprecated用来修饰哪些不再推荐使用的过期方法
	// @Deprecated

	// 使用注解，并为注解的属性赋值，写法是属性名=属性值。
	// 如果直接赋值，没有键，那么默认是为value属性赋值的
	@MyRequestMapping(path = "nihao", value = "")
	private void test() {

	}

	private void test(String name) {

	}

	public static void main(String[] args) throws NoSuchMethodException, SecurityException {

		// 注解本身其实就是一个修饰的符号，里面通过属性保存了一些数据，功能就像一个xml一样。
		// 框架会读取注解中数据，然后通过解析注解中的数据为方法或者属性附加功能。

		// 接下来的代码就是解析注解，用到的技术是反射。
		// 先得到注解所在类的类对象

		Class<Test2> cls = Test2.class;
		// cls.getAnnotations() 类对象有一系列的获取Annotation的方法，因为注解也是可以修饰类的

		// 获取我们注解所在的方法的Method对象
		// 如果方法是私有的，需要使用Declared系列的方法
		Method test = cls.getDeclaredMethod("test");

		MyRequestMapping mm = test.getAnnotation(MyRequestMapping.class);

		String path = mm.path();
		System.out.println(path);
	}

}
