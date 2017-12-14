package reg.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Date;
import java.util.HashMap;
import java.util.Random;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.cloopen.rest.sdk.CCPRestSmsSDK;

import reg.dao.Info2Repository;
import reg.dao.MobCountRepository;
import reg.domain.Info2;
import reg.domain.MobCount;
import reg.domain.ResultDto;
import reg.service.RedisServiceImpl;

@RestController
@RequestMapping("reg2")
public class Reg2Controller {

	@Resource
	private Info2Repository info2Repository;
	@Resource
	private MobCountRepository mobCountRepository;
	@Autowired
	private RedisServiceImpl redis;

	@RequestMapping(value = "/sms", method = RequestMethod.GET)
	public ResultDto sms(@RequestParam(value = "mob", defaultValue = "") String mob,@RequestParam(value = "type", defaultValue = "218631") String type,
			HttpServletResponse response,	HttpServletRequest request) {
		response.setHeader("Access-Control-Allow-Origin", "*");
		//验证手机号码格式
		if(validMob(mob)){
			String s=redis.get(mob+"delay");
			if("a".equals(s)){
				return new ResultDto(0,"60秒内只能获取一次验证码");
			}
			MobCount mobcount=mobCountRepository.findByMobile(mob);
			//当日手机号获取短信不超过6次
			if(mobcount==null){
				mobcount=new MobCount();
				mobcount.setMobile(mob);
				mobcount.setCount(1);
			}else if(mobcount.getCount()<6){
				mobcount.setCount(mobcount.getCount()+1);
			}else{
				return new ResultDto(0,"一天内只能获取六次验证码");
			}
			//生成随机号
			Random rd=new Random();
			int code=rd.nextInt(8888)+1000;
			if(send(code,mob,type)){
				redis.set(mob, code+"" ,60*5);
				mobCountRepository.save(mobcount);
				redis.set(mob+"delay", "a", 60);
				return new ResultDto(1,"验证码已发送");
			}
		}
		return new ResultDto(0,"获取失败");
	}

	@RequestMapping(value = "/reg", method = RequestMethod.GET)
	public ResultDto reg(@RequestParam(value = "mob", defaultValue = "") String mob,
			@RequestParam(value = "code", defaultValue = "") String code,
			@RequestParam(value = "pwd", defaultValue = "") String pwd,
			@RequestParam(value = "ref", defaultValue = "") String ref,
			@RequestParam(value = "url", defaultValue = "") String url,
			@RequestParam(value = "name", defaultValue = "") String name,
			@RequestParam(value = "d1", defaultValue = "") String d1,
			@RequestParam(value = "d2", defaultValue = "") String d2,
			@RequestParam(value = "d3", defaultValue = "") String d3, HttpServletResponse response,
			HttpServletRequest request) throws UnsupportedEncodingException {
		response.setHeader("Access-Control-Allow-Origin", "*");
		
		String syscode=redis.get(mob);
		if(syscode!=null&&code.equals(syscode)){
			Info2 data=info2Repository.findByMob(mob);
			if(data==null){
				Info2 info = new Info2();
				info.setDate(new Date());
				info.setMob(mob);
				info.setReferer(ref);
				info.setUrl(url);
				// System.out.println(URLDecoder.decode(name,"UTF-8"));
				info.setNickName(URLDecoder.decode(name, "UTF-8"));
				info.setD1(d1);
				info.setD2(d2);
				info.setD3(d3);
				info2Repository.save(info);
				return new ResultDto(1,"注册成功");
			}else{
				return new ResultDto(0,"已注册");
			}
		}
		return new ResultDto(0,"注册失败");
	}
	
	@RequestMapping(value = "/save", method = RequestMethod.GET)
	public ResultDto save(@RequestParam(value = "mob", defaultValue = "") String mob,
			@RequestParam(value = "code", defaultValue = "") String code,
			@RequestParam(value = "pwd", defaultValue = "") String pwd,
			@RequestParam(value = "ref", defaultValue = "") String ref,
			@RequestParam(value = "url", defaultValue = "") String url,
			@RequestParam(value = "name", defaultValue = "") String name,
			@RequestParam(value = "d1", defaultValue = "") String d1,
			@RequestParam(value = "d2", defaultValue = "") String d2,
			@RequestParam(value = "d3", defaultValue = "") String d3, HttpServletResponse response,
			HttpServletRequest request) throws UnsupportedEncodingException {
		response.setHeader("Access-Control-Allow-Origin", "*");
		Info2 data=info2Repository.findByMob(mob);
		if(data==null){
			Info2 info = new Info2();
			info.setDate(new Date());
			info.setMob(mob);
			info.setReferer(ref);
			info.setUrl(url);
			// System.out.println(URLDecoder.decode(name,"UTF-8"));
			info.setNickName(URLDecoder.decode(name, "UTF-8"));
			info.setD1(d1);
			info.setD2(d2);
			info.setD3(d3);
			info2Repository.save(info);
			return new ResultDto(1,"注册成功");
		}
		return new ResultDto(0,"注册失败");
	}

	
	public static boolean send(int mobile_code, String mobile,String type) {
		String search="218631";
		String reg="220330";
		HashMap<String, Object> result = null;
		CCPRestSmsSDK restAPI = new CCPRestSmsSDK();
		restAPI.init("app.cloopen.com", "8883");
		restAPI.setAccount("8a216da85708749401570cc4800d034d", "566be831b8f44344ad99dd400cf3e1d8");
		restAPI.setAppId("8a216da85fc7a0a4015fc8dd7eea0134");
		result = restAPI.sendTemplateSMS(mobile, type , new String[] { "" + mobile_code });
		if ("000000".equals(result.get("statusCode"))) {
			// 正常返回输出data包体信息（map）
			HashMap<String, Object> data = (HashMap<String, Object>) result.get("data");
			Set<String> keySet = data.keySet();
			for (String key : keySet) {
				Object object = data.get(key);
				System.out.println(key + " = " + object);
			}
			;
			return true;
		} else {
			// 异常返回输出错误码和错误信息
			System.out.println("错误码=" + result.get("statusCode") + " 错误信息= " + result.get("statusMsg"));
			return false;
		}
	}
	
	public static boolean validMob(String phone) {
        String regex = "^((13[0-9])|(14[5|7])|(15([0-3]|[5-9]))|(17[013678])|(18[0,5-9]))\\d{8}$";
        if(phone.length() != 11){
//            System.out.println("手机号应为11位数");
            return false;
        }else{
            Pattern p = Pattern.compile(regex);
            Matcher m = p.matcher(phone);
            boolean isMatch = m.matches();
            if(isMatch){
//                System.out.println("您的手机号" + phone + "是正确格式@——@");
                return true;
            } else {
//                System.out.println("您的手机号" + phone + "是错误格式！！！");
                return false;
            }
        }
    }
	
}
