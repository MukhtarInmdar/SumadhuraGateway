package com.sumadhura.sumadhuragateway.dto;

import java.io.Serializable;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Setter
@Getter
@ToString
public class ReferFriendWrapper extends Result implements Serializable {
	
	private static final long serialVersionUID = 2993834464380053100L;
	private List<ReferFriendDto> referFriendDto;
	private List<StateDTO> stateList;
	private List<String> bhkList;
	private List<Flat> flatBhkSbuaList;
	private String referencesSecurityPolices;
}
