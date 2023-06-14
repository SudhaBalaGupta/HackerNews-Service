package com.questionpro.proj.web.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.questionpro.proj.web.client.bo.ItemBo;

@FeignClient(name="hackerNewsClient", url = "${hackernews.api.url}")
public interface HackerNewsClient {

	@GetMapping("/topstories.json")
	public List<Integer> fetchToptoriesIds();

	@GetMapping("/item/{itemId}.json")
	public ItemBo fetchItemInfo(@PathVariable Integer itemId);

}
