package com.gamenet.cruscottofatturazione.models;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HistoryNotificheUtenteSummary
{
	private Integer unreadedCount;
	private Integer readedCount;
	private Integer totalCount;
	private List<HistoryNotificheUtente> listUnreaded;
	private List<HistoryNotificheUtente> listReaded;
}
