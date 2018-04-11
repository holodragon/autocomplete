package com.acmeshop.autocomplete;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.acmeshop.autocomplete.datastore.IAutocompleteStore;
import com.acmeshop.autocomplete.datastore.LocalJsonStoreImpl;
import com.acmeshop.autocomplete.datastore.LocalStoreImpl;
import com.acmeshop.autocomplete.datastore.MemcacheStoreImpl;
import com.acmeshop.autocomplete.datastore.RedisStoreImpl;

@Configuration
public class ApplicationConfig {

	private Log log = LogFactory.getLog(ApplicationConfig.class);

	//// use -Dspring.profiles.active=cloud to change store

	@Bean
	@ConditionalOnProperty(name = "autocomplete.store.name", havingValue = "local", matchIfMissing = true)
	public IAutocompleteStore localStoreService() {
		log.info("using 'local' store.");
		return new LocalStoreImpl();
	}

	@Bean
	@ConditionalOnProperty(name = "autocomplete.store.name", havingValue = "redis", matchIfMissing = true)
	public IAutocompleteStore redisStoreService() {
		log.info("using 'redis' store.");
		return new RedisStoreImpl();
	}

	@Bean
	@ConditionalOnProperty(name = "autocomplete.store.name", havingValue = "memcache", matchIfMissing = true)
	public IAutocompleteStore memcacheStoreService() {
		log.info("using 'memcache' store.");
		return new MemcacheStoreImpl();
	}

	@Bean
	@ConditionalOnProperty(name = "autocomplete.store.name", havingValue = "local-json", matchIfMissing = true)
	public IAutocompleteStore localJsonStoreService() {
		log.info("using 'local-json' store.");
		return new LocalJsonStoreImpl();
	}

	@Bean
	@ConditionalOnProperty(name = "autocomplete.store.name", havingValue = "redis-json", matchIfMissing = true)
	public IAutocompleteStore localRedisStoreService() {
		log.info("using 'redis-json' store.");
		return new RedisStoreImpl();
	}

	@Bean
	@ConditionalOnProperty(name = "autocomplete.store.name", havingValue = "gcs", matchIfMissing = true)
	public IAutocompleteStore localGCSStoreService() {
		log.info("using 'gcs' store.");
		return new LocalJsonStoreImpl();
	}

}
