package com.guo.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.guo.common.utils.FastDFSClient;
import com.guo.common.utils.JsonUtils;

/**
 * 图片上传Controller
 * <p>Title: PictureController</p>
 * <p>Description: </p>
 * @author	guo
 * @date	23 Sep 201717:10:29
 * @version 1.0
 */
@Controller
public class PictureController {
	
	@Value("${IMAGE_SERVER_URL}")
	private String IMAGE_SERVER_URL;
	
	@RequestMapping("/pic/upload")
	@ResponseBody
	public String uploadFile(MultipartFile uploadFile) {
		
		try {
			//把图片上传到服务器
			FastDFSClient client = new FastDFSClient("classpath:conf/client.conf");
			//取得文件扩展名
			String originalFilename = uploadFile.getOriginalFilename();
			String extName = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
			//得到一个图片的地址和文件名
			String url = client.uploadFile(uploadFile.getBytes(), extName);
			//补充完整的文件名
			url = IMAGE_SERVER_URL + url ;
			//封装到Map中返回
			Map result = new HashMap<>();
			result.put("error", 0);
			result.put("url", url);
			return JsonUtils.objectToJson(result);
		} catch (Exception e) {
			e.printStackTrace();
			Map result = new HashMap<>();
			result.put("error", 1);
			result.put("message", "上传图片失败，请联系管理员");
			return JsonUtils.objectToJson(result);
		}
	}

}
