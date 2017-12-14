package reg.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

	
@Service  
public class RedisServiceImpl {  
  
	private static Logger logger = Logger.getLogger(RedisServiceImpl.class);  
	  
    @Autowired  
    private JedisPool jedisPool;  
      
    public Jedis getResource() {  
        return jedisPool.getResource();  
    }  
  
    @SuppressWarnings("deprecation")  
    public void returnResource(Jedis jedis) {  
        if(jedis != null){  
        	jedis.close();
//        	jedisPool.returnResource(jedis);  
        }  
    }  
  
    public void set(String key, String value,int times) {  
        Jedis jedis=null;  
        try{  
            jedis = getResource();  
            jedis.setex(key,times, value);  
            logger.info("Redis set success - " + key + ", value:" + value);  
        } catch (Exception e) {  
            e.printStackTrace();  
            logger.error("Redis set error: "+ e.getMessage() +" - " + key + ", value:" + value);  
        }finally{  
            returnResource(jedis);  
        }  
    }  
      
    public String get(String key) {  
        String result = null;  
        Jedis jedis=null;  
        try{  
            jedis = getResource();  
            result = jedis.get(key);  
            logger.info("Redis get success - " + key + ", value:" + result);  
        } catch (Exception e) {  
            e.printStackTrace();  
            logger.error("Redis get error: "+ e.getMessage() +" - " + key + ", value:" + result);  
        }finally{  
            returnResource(jedis);  
        }  
        return result;  
    }   

}
