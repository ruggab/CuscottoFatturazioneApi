package com.gamenet.cruscottofatturazione.entities;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "richiesta_timeline_mail", schema = "dbo")
public class RichiestaTimelineMail {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	private String createUser;
	private Date createDate;
	private String lastModUser;
	private Date lastModDate;
	
	@ManyToOne
	@JoinColumn(name = "timelineId")
	@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class)
	private RichiestaTimeline timelineMail;
	
	@ManyToOne
	@JoinColumn(name = "registroMailId")
	@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class)
	private RegistroEmail mailTimelineAttachment;
}
