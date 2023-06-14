package com.questionpro.proj.web.client.bo;

import java.time.Instant;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class ItemBo {

	private Integer id;
	private String by;
	private Integer score;
	private List<Integer> kids;
	@JsonFormat(shape = JsonFormat.Shape.NUMBER)
	private Instant time;
	private String title;
	private String type;
	private Integer parent;
	private String text;
	private String url;

}
