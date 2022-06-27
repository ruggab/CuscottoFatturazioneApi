package com.gamenet.cruscottofatturazione.models.response;

import java.util.List;

import com.gamenet.cruscottofatturazione.models.RoleVoceMenu;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RoleVoceMenuOverview {
	private Integer totalCount;
	private List<RoleVoceMenu> lines;
}
