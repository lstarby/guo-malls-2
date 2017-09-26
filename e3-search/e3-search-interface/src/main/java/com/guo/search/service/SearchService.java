package com.guo.search.service;

import com.guo.common.pojo.SearchResult;

public interface SearchService {

	SearchResult search(String keyword, int page, int rows)  throws Exception;
}
