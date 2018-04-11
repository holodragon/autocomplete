package com.acmeshop.autocomplete.datastore;

import com.acmeshop.autocomplete.datastore.AbstractAutocomplete.Stats;

public interface IAutocompleteStore extends IAutocomplete {
	
	void addProduct(String id, String product);
	void addNgram(String ngram, String id, String product);
	Stats stats();

}
