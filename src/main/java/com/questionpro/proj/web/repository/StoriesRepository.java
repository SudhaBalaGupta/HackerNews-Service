package com.questionpro.proj.web.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.questionpro.proj.web.entity.Story;

@Repository
public interface StoriesRepository extends JpaRepository<Story, Long>{

	List<Story> findFirst10ByOrderByEntryDateTimeDesc();
	
	List<Story> findAllByOrderByEntryDateTimeDesc();
}
