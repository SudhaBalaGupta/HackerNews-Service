package com.questionpro.proj.web.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.questionpro.proj.web.entity.Story;
import com.questionpro.proj.web.service.StoriesService;
import com.questionpro.proj.web.service.bo.CommentBo;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("hackernews/api")
public class StoriesApi {

	@Autowired
	private StoriesService service;

	@ApiOperation(value = "To fetch 10 latest stories from hacker news", tags = "Stories")
	@GetMapping("/top-stories")
	public List<Story> getTopStories() {
		return service.topStories();

	}

	@ApiOperation(value = "To fetch all of past stories", tags = "Stories")
	@GetMapping("/past-stories")
	public List<Story> getPastStories() {
		return service.pastStories();
	}

	@ApiOperation(value = "To fetch 10 top level comments for a story", tags = "Stories")
	@GetMapping("/comments/{storyId}")
	public List<CommentBo> getComments(@PathVariable("storyId") Integer storyId) {
		return service.commentsByStoryId(storyId);
	}
}
