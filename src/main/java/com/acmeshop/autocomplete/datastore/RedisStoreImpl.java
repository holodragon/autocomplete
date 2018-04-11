package com.acmeshop.autocomplete.datastore;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;

public class RedisStoreImpl extends AbstractAutocomplete {

	@Autowired
	private StringRedisTemplate redis;
	// private RedisTemplate redis;

	public void addNgram(String ngram, String id, String product) {
		redis.opsForList().leftPush("ngram:" + ngram, id);
		redis.opsForValue().set("prd:" + id, product);
	}

	public Stats stats() {
		throw new Error("not implemented!");
	}

	public Response[] getNgramMatch(String ngram, int max) {
		Response[] names = new Response[0];

		List<String> productIds = redis.opsForList().range("ngram:" + ngram, 0, max);

		if (productIds != null) {
			int records = Math.min(max, productIds.size());
			List<String> ids = productIds.subList(0, records);
			names = new Response[records];
			for (int i = 0; i < names.length; i++) {
				String record = ids.get(i);
				names[i] = new Response(record, redis.opsForValue().get("prd:" + record));
			}
		}
		return names;
	}

}
