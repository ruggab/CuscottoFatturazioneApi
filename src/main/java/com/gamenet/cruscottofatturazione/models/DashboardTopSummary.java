package com.gamenet.cruscottofatturazione.models;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DashboardTopSummary
{
	private Integer firstBoxValue;
	private Date firstBoxDetail;

	private Integer secondBoxValue;
	private Integer secondBoxDetail;

	private Integer thirdBoxValue;
	private Double thirdBoxDetail;

	private Integer fourthBoxValue;
	private Date fourthBoxDetail;

	private Integer fifthBoxValue;
	private Double fifthBoxDetail;
}
