package reg;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

@Configuration  
@EnableAutoConfiguration  
@ConfigurationProperties(prefix = "spring.redis", locations = "classpath:application.properties")  
public class RedisConfig {  
  
    private static Logger logger = Logger.getLogger(RedisConfig.class); 
    
    @Value("${spring.redis.host}")
    private String host;  
  
    private int port;  
  
    @Value("${spring.redis.password}")
    private String password;  
  
    private int timeout;  
      
    @Bean  
    public JedisPoolConfig getRedisConfig(){  
        JedisPoolConfig config = new JedisPoolConfig();  
        return config;  
    }  
      
    @Bean  
    public JedisPool getJedisPool(){  
    	System.out.println("---------------------------");
    	System.out.println(host+","+port);
        JedisPoolConfig config = getRedisConfig();  
        JedisPool pool = new JedisPool(config,host,port,timeout,password);  
        logger.info("init JredisPool ...");  
        return pool;  
    }  
  
    public String getHost() {  
        return host;  
    }  
  
    public void setHostName(String host) {  
        this.host = host;  
    }  
  
    public int getPort() {  
        return port;  
    }  
  
    public void setPort(int port) {  
        this.port = port;  
    }  
  
    public String getPassword() {  
        return password;  
    }  
  
    public void setPassword(String password) {  
        this.password = password;  
    }  
  
    public int getTimeout() {  
        return timeout;  
    }  
  
    public void setTimeout(int timeout) {  
        this.timeout = timeout;  
    }  
}  