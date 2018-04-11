package com.acmeshop.autocomplete.loader;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.nio.channels.Channels;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.acmeshop.autocomplete.datastore.AbstractAutocomplete.Stats;
import com.acmeshop.autocomplete.datastore.IAutocompleteStore;
import com.acmeshop.pojo.Product;
import com.google.appengine.repackaged.com.google.gson.Gson;
import com.google.appengine.repackaged.com.google.gson.GsonBuilder;
import com.google.appengine.repackaged.com.google.gson.stream.JsonReader;
import com.google.appengine.tools.cloudstorage.GcsFilename;
import com.google.appengine.tools.cloudstorage.GcsInputChannel;
import com.google.appengine.tools.cloudstorage.GcsService;
import com.google.appengine.tools.cloudstorage.GcsServiceFactory;
import com.google.appengine.tools.cloudstorage.RetryParams;

/**
 * ProductLoader
 * 
 * @author mborges
 *
 */

@Component
public class ProductGCSLoader implements CommandLineRunner {

	private Log log = LogFactory.getLog(ProductGCSLoader.class);

	@Value("${autocomplete.gcs.load}")
	private String gcsUri;

	@Autowired
	private IAutocompleteStore store;

	private final GcsService gcsService = GcsServiceFactory.createGcsService(new RetryParams.Builder()
			.initialRetryDelayMillis(10).retryMaxAttempts(10).totalRetryPeriodMillis(15000).build());
	// private final RawGcsService gcsService =
	// LocalRawGcsServiceFactory.createLocalRawGcsService();

	private static final int BUFFER_SIZE = 2 * 1024 * 1024;

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
		log.info("myauto: LOAD DATA");
		GcsFilename fileName = getFileName(gcsUri);

		try {
			GcsInputChannel readChannel = gcsService.openPrefetchingReadChannel(fileName, 0, BUFFER_SIZE);
			log.info("myauto: LOAD DATA, readChannel = " + readChannel);
			Reader in = new InputStreamReader(Channels.newInputStream(readChannel), "UTF-8");
			log.info("myauto: LOAD DATA, in = " + in);
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

	private GcsFilename getFileName(String uri) {
		String[] splits = uri.split("/", 4);
		if (!splits[0].equals("") || !splits[1].equals("gcs")) {
			throw new IllegalArgumentException(
					"The URL is not formed as expected. " + "Expecting /gcs/<bucket>/<object>");
		}
		return new GcsFilename(splits[2], splits[3]);
	}
} // class
