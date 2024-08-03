package com.br.api.models.addon;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
@Table(name = "APPCTL1")
public class UserAuthen {
    // CTL_CONO, CTL_CODE, CTL_GRP, CTL_SEQ, CTL_STS, CTL_UID, CTL_REM
	
	@JsonProperty("CTL_CONO")
	@Column(name="CTL_CONO", length = 10, nullable = false, unique = false)
	private Integer CTLCONO;

	@JsonProperty("CTL_CODE")
	@Column(name="CTL_CODE", length = 50, nullable = false, unique = false)
	private String CTLCODE;

	@Id
	@JsonProperty("CTL_UID")
	@Column(name="CTL_UID", length = 50, nullable = false, unique = false)
	private String CTLUID;
	
	@JsonProperty("CTL_REM")
	@Column(name="CTL_REM", nullable = false)
	private String CTLREM;
	
	@JsonProperty("CTL_REM2")
	@Column(name="CTL_REM2", nullable = false)
	private String CTLREM2;
	
	@JsonProperty("CTL_REM3")
	@Column(name="CTL_REM3", nullable = false)
	private String CTLREM3;
	
	@JsonProperty("CTL_STS")
	@Column(name="CTL_STS", nullable = false)
	private String CTLSTS;
	
	

}
