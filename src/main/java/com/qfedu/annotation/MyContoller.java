package com.qfedu.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

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
// 用自己定义的注解来模拟实现springmvc的原理


//@Retention，@Target叫做元注解，又叫做注解的注解
//RUNTIME表示注解不会被丢弃，在运行状态下注解也会生效，一般自定义注解都使用这种生命周期
@Retention(RetentionPolicy.RUNTIME)
//TYPE表示当前声明的注解可以修饰类，接口和枚举
@Target(ElementType.TYPE)
public @interface MyContoller {

}
