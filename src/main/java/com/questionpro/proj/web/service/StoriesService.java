package com.questionpro.proj.web.service;

import java.util.List;

import com.questionpro.proj.web.entity.Story;
import com.questionpro.proj.web.service.bo.CommentBo;

public interface StoriesService {

	List<Story> topStories();
	
	List<Story> pastStories();
	
	List<CommentBo> commentsByStoryId(Integer storyId);
}
