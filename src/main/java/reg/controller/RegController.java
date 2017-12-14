package reg.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import reg.dao.InfoRepository;
import reg.domain.Info;
import reg.util.HttpUtil;  

@Controller
@RequestMapping("reg")
public class RegController {

	@Resource
	private InfoRepository infoRepository;

	@RequestMapping(value = "/sms", method = RequestMethod.GET)
	@ResponseBody
	public String sms(@RequestParam(value = "mob", defaultValue = "") String mob,
			@RequestParam(value = "ref", defaultValue = "") String ref,
			@RequestParam(value = "url", defaultValue = "") String url,
			HttpServletResponse response,HttpServletRequest request) {
		response.setHeader("Access-Control-Allow-Origin", "*");
		String s="action=qzh_lbpnb&mobile="+ new String(Base64.encodeBase64(mob.getBytes()));
		String re=HttpUtil.httpPost("http://stock.hkfg518.com/api/act_verifycode.ashx", s);
		re=re.replace("callback(", "").replace(");", "");
		return re;
	}
	
	
	@RequestMapping(value = "/reg", method = RequestMethod.GET)
	@ResponseBody
	public String reg(@RequestParam(value = "mob", defaultValue = "") String mob,
			@RequestParam(value = "code", defaultValue = "") String code,
			@RequestParam(value = "pwd", defaultValue = "") String pwd,
			@RequestParam(value = "ref", defaultValue = "") String ref,
			@RequestParam(value = "url", defaultValue = "") String url,
			@RequestParam(value = "name", defaultValue = "") String name,
			@RequestParam(value = "d1", defaultValue = "") String d1,
			@RequestParam(value = "d2", defaultValue = "") String d2,
			@RequestParam(value = "d3", defaultValue = "") String d3,
			HttpServletResponse response,HttpServletRequest request) throws UnsupportedEncodingException {
		response.setHeader("Access-Control-Allow-Origin", "*");
		String s="action=qzh_lbpnb&mobile="+ new String(Base64.encodeBase64(mob.getBytes()))+"&code="+code+"&password="+pwd;
		String re=HttpUtil.httpPost("http://stock.hkfg518.com/api/act_register.ashx", s);
//		System.out.println("re: " +re);
		re=re.replace("callback(", "").replace(");", "");
//		System.out.println("re: " +re);
		if(re.indexOf("\"code\":0")!=-1){
			Info info =new Info();
			info.setDate(new Date());
			info.setMob(mob);
			info.setReferer(ref);
			info.setUrl(url);
//			System.out.println(URLDecoder.decode(name,"UTF-8"));
			info.setNickName(URLDecoder.decode(name,"UTF-8"));
			info.setD1(d1);
			info.setD2(d2);
			info.setD3(d3);
			infoRepository.save(info);
		}
		return re;
	}
	
}
