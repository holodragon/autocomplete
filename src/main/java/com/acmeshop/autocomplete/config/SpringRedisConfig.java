package com.acmeshop.autocomplete.config;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;

import com.acmeshop.autocomplete.loader.ProductJsonLoader;

@Configuration
public class SpringRedisConfig {

	private Log log = LogFactory.getLog(SpringRedisConfig.class);

	
	@Value("${autocomplete.store.redis.host}")
	private String redisHost;
	@Value("${autocomplete.store.redis.port}")
	private int redisPort;
	@Value("${autocomplete.store.redis.password:#{null}}")
	private String redisPassword;

	@Bean
	public JedisConnectionFactory connectionFactory() {
		JedisConnectionFactory connectionFactory = new JedisConnectionFactory();
		connectionFactory.setHostName(redisHost);
		connectionFactory.setPort(redisPort);
		log.info(redisPassword);
		connectionFactory.setPassword(redisPassword);
		return connectionFactory;
	}

	// @Bean
	// public RedisTemplate<String, Object> redisTemplate() {
	// RedisTemplate<String, Object> redisTemplate = new RedisTemplate<String,
	// Object>();
	// redisTemplate.setConnectionFactory(connectionFactory());
	// redisTemplate.setKeySerializer(new StringRedisSerializer());
	// return redisTemplate;
	// }
	//
	// @Bean
	// public StringRedisTemplate strRedisTemplate() {
	// StringRedisTemplate redisTemplate = new StringRedisTemplate();
	// redisTemplate.setConnectionFactory(connectionFactory());
	// return redisTemplate;
	// }
}