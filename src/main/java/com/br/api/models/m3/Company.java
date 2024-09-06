package com.br.api.models.m3;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
public class Company {

	@JsonProperty("CCCONO")
	private String CCCONO;

	@JsonProperty("CCDIVI")
	private String CCDIVI;

	@JsonProperty("CCCONM")
	private String CCCONM;

	@JsonProperty("COMPANY")
	private String COMPANY;


}
