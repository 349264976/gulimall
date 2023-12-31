package com.atguigu.gulimall.gulimaillthirdparty;

import com.aliyun.oss.OSSClient;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = {GulimaillThirdPartyApplication.class})
class GulimaillThirdPartyApplicationTests {
	@Test
	void contextLoads() {
	}
	@Autowired
	OSSClient ossClient;
	@Test
	public void testUpload() throws FileNotFoundException {

//        // Endpoint以杭州为例，其它Region请按实际情况填写。
//        String endpoint = "oss-cn-beijing.aliyuncs.com";
//        // 云账号AccessKey有所有API访问权限，建议遵循阿里云安全最佳实践，创建并使用RAM子账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建。
//        String accessKeyId = "LTAI4FwvfjSycd1APnuG9bjj";
//        String accessKeySecret = "O6xaxyiWfSIitcOkSuK27ju4hXT5Hl";
//
//        // 创建OSSClient实例。
//        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

		// 上传文件流。
		InputStream inputStream = new FileInputStream("C:\\Users\\李开恩\\Pictures\\Saved Pictures\\cmy.jpg");

		ossClient.putObject("gulimall-lke", "hahaha.jpg", inputStream);

		// 关闭OSSClient。
		ossClient.shutdown();

		System.out.println("上传完成...");
	}
}
