package com.qfedu.restful;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.qfedu.pojo.ResponseVo;
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
@RequestMapping("/api/v1")
public class RestController {

	//@Autowired
	
	//private ResponseVo<User> vo;
	// @GetMapping("/user")
	@RequestMapping(path = "/user/{id}", method = RequestMethod.GET)
	public @ResponseBody ResponseVo<User> getUser(@PathVariable("id") Integer id) {

		System.out.println(id);
		// 执行查询

		User user = new User();
		user.setUsername("pfdu");
		user.setPassword("123");

		List<User> list = new ArrayList<User>();

		list.add(user);

		ResponseVo<User> vo = ResponseVo.OK();

		
		vo.setData(list);
		return vo;
	}

	// @PostMapping("/user")
	@RequestMapping(path = "/user", method = RequestMethod.POST)
	public @ResponseBody ResponseVo addUser() {
		// 执行新增
		return null;
	}
	
	@RequestMapping(path = "/user/{id}", method = RequestMethod.DELETE)
	public @ResponseBody ResponseVo<User> deleteUser(@PathVariable("id") Integer id) {
		// 执行删除
		System.out.println(id);
		
		ResponseVo<User> vo = new ResponseVo<User>();

		vo.setCode(1001);
		vo.setMsg("delete success");
		
		
		return vo;
	}

}
