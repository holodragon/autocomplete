package com.acmeshop.autocomplete.apis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.acmeshop.autocomplete.datastore.AbstractAutocomplete.Response;
import com.acmeshop.autocomplete.datastore.AbstractAutocomplete.Stats;
import com.acmeshop.autocomplete.datastore.IAutocompleteStore;
import com.acmeshop.autocomplete.loader.ProductJsonLoader;

@RestController
public class AutocompleteApis {
	@Autowired
	private IAutocompleteStore store;

	@Autowired
	// private ProductGCSLoader loader;
	private ProductJsonLoader loader;
	// private ProductLoader loader;

	/////////////////////////////////
	// RestController
	/////////////////////////////////

	@RequestMapping("/autocomplete")
	public Response[] autocomplete(String query,
			@RequestParam(value = "max", required = false, defaultValue = "3") int max) {
		return store.getNgramMatch(query.toLowerCase(), max);
	}

	@RequestMapping("/add")
	public String addProduct(String id, String product) {
		store.addProduct(id, product);
		return "success";
	}

	@RequestMapping("/load")
	public Stats seed() throws Exception {
		return loader.load();
	}

	/**
	 * <a href=
	 * "https://cloud.google.com/appengine/docs/flexible/java/how-instances-are-managed#health_checking">
	 * App Engine health checking</a> requires responding with 200 to
	 * {@code /_ah/health}.
	 */
	@RequestMapping("/_ah/health")
	public String healthy() {
		// Message body required though ignored
		return "Still surviving.";
	}

}
