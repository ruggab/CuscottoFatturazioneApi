package com.gamenet.cruscottofatturazione.entities;

import java.util.Date;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
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
@Table(name = "richiesta_timeline", schema = "dbo")
public class RichiestaTimeline
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String titolo;
	private String messaggio;
	private String icon;
	private String colorClass;
	
	private String createUser;
	private Date createDate;
	private String lastModUser;
	private Date lastModDate;
	
	private String ipAddress;
	
	@ManyToOne
	@JoinColumn(name = "richiestaId")
	@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class)
	private RichiestaCliente richiestaTimeline;
	
	@OneToMany(mappedBy = "timelineAttachment", fetch = FetchType.LAZY)	
	@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class)
	private Set<RichiestaTimelineAttachment> attachmentTimeline;
	
	@OneToMany(mappedBy = "timelineMail", fetch = FetchType.LAZY)	
	@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class)
	private Set<RichiestaTimelineMail> mailTimeline;
}
