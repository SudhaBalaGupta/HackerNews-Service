package com.questionpro.proj.web.service;

import java.sql.Timestamp;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.questionpro.proj.web.client.HackerNewsClient;
import com.questionpro.proj.web.client.bo.ItemBo;
import com.questionpro.proj.web.entity.Story;
import com.questionpro.proj.web.repository.StoriesRepository;
import com.questionpro.proj.web.service.bo.CommentBo;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class StoriesServiceImpl implements StoriesService {

	
	private static final int SIZE = 10;

	@Autowired
	private HackerNewsClient hnClient;

	@Autowired
	private StoriesRepository repository;

	@Cacheable(cacheNames = "topStories", key = "'topStories'",
			sync = true, cacheManager = "cacheManager")
	@Override
	public List<Story> topStories() {

		List<Integer> topStories = hnClient.fetchToptoriesIds();

		if (ObjectUtils.isEmpty(topStories)) {
			// fallback to DB to fetch last added records.
			return repository.findFirst10ByOrderByEntryDateTimeDesc();
		}

		List<Story> stories = topStories.stream()
				.limit(SIZE)
				.map(ts -> hnClient.fetchItemInfo(ts))
				.map(it -> {
					Story s = new Story();
					s.setStoryId(it.getId());
		
					if (Objects.nonNull(it.getTime())) {
						s.setTime(Timestamp.from(it.getTime()));
					}
		
					s.setUser(it.getBy());
					s.setScore(it.getScore());
					s.setTitle(it.getTitle());
					s.setUrl(it.getUrl());
					return s;
				})
				.collect(Collectors.toList());

		repository.saveAll(stories);

		log.info("sending response now");
		return stories;
	}

	// No need to cache as this will not hit the downstream API, 
	// also this response is expected to big so it's not good for cache.
	@Override
	public List<Story> pastStories() {
		return repository.findAllByOrderByEntryDateTimeDesc();
	}

	@Cacheable(cacheNames = "commentsByStoryId", key = "'commentsByStoryId'", 
			sync = true, cacheManager = "cacheManager")
	@Override
	public List<CommentBo> commentsByStoryId(Integer storyId) {
		ItemBo itemBo = hnClient.fetchItemInfo(storyId);
		if (!"story".equals(itemBo.getType())) {
			throw new RuntimeException("Invalid Input");
		}

		List<Integer> kids = ObjectUtils.isEmpty(itemBo.getKids()) 
					? Collections.emptyList() 
							: itemBo.getKids();

		return kids.stream()
				.map(comm -> hnClient.fetchItemInfo(comm))
				.filter(it -> !ObjectUtils.isEmpty(it.getKids()))
				.sorted(Comparator.comparingInt(item -> item.getKids().size()))
				.limit(SIZE)
				.map(comm -> {
					CommentBo bo = new CommentBo();
					bo.setText(comm.getText());
					bo.setUserHandle(comm.getBy());
					return bo;
				})
				.collect(Collectors.toList());

	}

}
