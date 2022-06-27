package com.gamenet.cruscottofatturazione.models.response;

import java.util.List;

import com.gamenet.cruscottofatturazione.models.RoleUser;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RuoliListOverview {
	private Integer totalCount;
	private List<RoleUser> lines;
}
