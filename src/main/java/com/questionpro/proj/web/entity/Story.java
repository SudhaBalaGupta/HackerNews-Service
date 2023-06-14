package com.questionpro.proj.web.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
@Entity
@Table(name = "stories")
public class Story {

	@Id
	@GeneratedValue
	@Column(nullable = false)
	@JsonIgnore
	private Long id;

	@Column(name = "story_id", unique = true,nullable = false)
	private Integer storyId;

	private String user;
	private Integer score;
	private Timestamp time;
	private String title;
	private String url;
	
	@JsonIgnore
	@Column(name = "entry_date_time", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	private Timestamp entryDateTime;
}
