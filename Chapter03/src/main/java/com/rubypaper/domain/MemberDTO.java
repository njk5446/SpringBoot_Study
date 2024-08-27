package com.rubypaper.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MemberDTO {
	private Integer id;
	private String name;
	private String pass;
	private java.sql.Date regidate;
}
