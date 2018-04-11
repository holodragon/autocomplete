package com.acmeshop.autocomplete.datastore;

import com.acmeshop.autocomplete.datastore.AbstractAutocomplete.Response;

public interface IAutocomplete {

	Response[] getNgramMatch(String ngram, int max);

}