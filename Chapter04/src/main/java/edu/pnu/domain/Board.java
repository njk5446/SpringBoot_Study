package edu.pnu.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Board {
	// primary key로 설정하는 어노테이션
	@Id
	// Auto increment (Mysql,h2-> identity | Oracle -> Sequence)
	@GeneratedValue(strategy = GenerationType.IDENTITY)		
	private Long seq;
	private String title;
	private String writer;
	private String content;
	// Date 관련 필드에 설정 (타입을 Date로, Timestamp 등으로 타입 변경 가능)
	@Temporal(TemporalType.DATE)
	private Date createDate;
	private Long cnt;
	
}
