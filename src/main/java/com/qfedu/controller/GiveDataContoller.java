package com.qfedu.controller;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.qfedu.pojo.User;

import cn.dsna.util.images.ValidateCode;

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
//当前Contoller 用于演示数据流出的操作，比如将数据存放作用域操作，提供给其他资源访问数据

@SuppressWarnings("unused")
@Controller
// 将数据保存到session作用域
// 常用属性有两个一个types，指定类型，将当前类中所有的已经在数据模型中的数据再保存到session作用域里
// 作为了解即可
@SessionAttributes(types = { Integer.class, User.class }, names = { "datas2", "datas3" })
public class GiveDataContoller {

	// 基础，使用原生的api
	public String giveData1(HttpServletRequest request) {
		request.setAttribute("data", 1000);
		return "index";
	}

	// 请求到达业务处理器，执行业务和数据库操作，然后将数据保存作用域，接下来转发

	// 方法返回类型可以是ModelAndView，springmvc会做两个动作，1读取数据保存到request作用域2转发
	public ModelAndView giveData2() {

		ModelAndView mv = new ModelAndView();
		// 向requets作用域保存数据
		mv.addObject("datas2", 1000);
		// 返回ModelAndView也会经过视图解析器，setViewName执行要转发到的jsp的名字
		mv.setViewName("index");

		return mv;
	}

	// 我们可以在入参数添加Map类型的参数,这样在方法体中我们可以将想要保存到Request中的数据保存到map里
	// springmvc会在方法执行完毕之后读取map中的数据，并保存到request作用域
	// springmvc在调用具体的业务处理器代码之前，会创建一个专门保存数据的第一个对象,也就是Model对象。
	// 如果入参数的参数中有类型为Map的变量的时候，会将刚刚创建的保存模型属性的对象引用赋值给参数的变量
	// 其实可以在map,model,ModelMap中三选一，使用任何一个对象均可
	@RequestMapping("/giveData3")
	public String giveData3(Map<String, Object> map, Model model, ModelMap modelMap) {

		// 打印结果都是true
		System.out.println(map == model);
		System.out.println(model == modelMap);
		// 也是保存到Request作用域
		map.put("datas3", 3000);

		return "index";
	}

	// 文件流出一是下载，二是返回图片

	// 现在其实大部分的验证码已经不再有后台返回图片，而是前端js直接生成

	@RequestMapping("/validateImg")
	public void validateImg(HttpServletResponse resp, HttpSession session) throws IOException {
		ValidateCode code = new ValidateCode(80, 30, 4, 20);
		session.setAttribute("code", code.getCode());

		code.write(resp.getOutputStream());

	}

	// 文件下载，有两种方式1使用原生的api,代码与原生servlet一致
	// 2springmvc也提供了对复杂响应的支持，但是代码比原生并没有简化太多。

	@RequestMapping("/download1")
	public void download1(HttpServletResponse response) throws IOException {

		//
		File file = new File("Z:\\windos\\xiaomi\\1.jpg");

		// 告诉客户端需要以附件的形式进行指定名字的文件
		response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(file.getName(), "utf-8"));

		// 表示设置响应头中的content-type,告诉浏览器返回的是文件
		response.setContentType("application/octet-stream");

		// 操作响应体
		response.getOutputStream().write(FileUtils.readFileToByteArray(file));

	}

	// 返回类型还可以是ResponseEntity，ResponseEntity表示响应，里面封装了响应的各个组成部分
	// 一般用于文件下载

	@RequestMapping("/download2")
	public ResponseEntity<byte[]> download2() throws IOException {

		File file = new File("Z:\\windos\\xiaomi\\1.jpg");

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
		headers.setContentDispositionFormData("attachment", file.getName());

		ResponseEntity<byte[]> resp = new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file), headers,
				HttpStatus.OK);

		return resp;
	}

}
