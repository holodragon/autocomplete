package com.acmeshop.autocomplete.loader;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import com.acmeshop.autocomplete.datastore.AbstractAutocomplete.Stats;
import com.acmeshop.autocomplete.datastore.IAutocompleteStore;
import com.acmeshop.pojo.Product;
import com.google.appengine.repackaged.com.google.gson.Gson;
import com.google.appengine.repackaged.com.google.gson.GsonBuilder;
import com.google.appengine.repackaged.com.google.gson.stream.JsonReader;

/**
 * ProductLoader
 * 
 * @author mborges
 *
 */

@Component
public class ProductJsonLoader implements CommandLineRunner {

	private Log log = LogFactory.getLog(ProductJsonLoader.class);

	@Value("${autocomplete.json.load}")
	private Resource loadFile;

	@Autowired
	private IAutocompleteStore store;

	// For init process with -Drun.arguments="anything"
	public void run(String... args) throws Exception {

		if (args.length > 0) {
			log.info("seeding cache...");
			load();
		} else {
			return;
		}

		for (String arg : args) {
			log.info(arg);
		}

	}

	public Stats load() throws Exception {

		try {
			Reader in = new InputStreamReader(loadFile.getInputStream(), "UTF-8");
			JsonReader reader = new JsonReader(in);
			Gson gson = new GsonBuilder().create();

			// Read file in stream mode
			reader.beginArray();
			int total = 0;
			while (reader.hasNext()) {
				// Read data into object model
				Product product = gson.fromJson(reader, Product.class);
				store.addProduct(product.getSku(), product.getName() == null ? "" : product.getName());
				total++;
				if (total % 100 == 0) {
					log.info(String.format("loaded %d records...", total));
				}
				// break;
			}
			reader.close();
			if (in != null) {
				in.close();
			}
			log.info(String.format("LOAD PRODUCTS: Total records: %d ", total));
		} catch (UnsupportedEncodingException ex) {
			log.info("LOAD PRODUCTS: Product source not encoded in UTF-8");
		} catch (IOException ex) {
			log.info("LOAD PRODUCTS: IO Exception");
		}
		return store.stats();
	}

} // class
