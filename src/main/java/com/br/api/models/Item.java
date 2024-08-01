package com.br.api.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
public class Item {

	@JsonProperty("MMCONO")
	private Integer MMCONO;

	@JsonProperty("MMITNO")
	private String MMITNO;

	@JsonProperty("MMFUDS")
	private String MMFUDS;

	@JsonProperty("MMUNMS")
	private String MMUNMS;

	@JsonProperty("MMSTAT")
	private String MMSTAT;

}
