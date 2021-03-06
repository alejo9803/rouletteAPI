package com.roulette.api.dto;

import java.util.ArrayList;
import java.util.List;

public class WinnerDTO {

	private Long totalValue;
	private String idUser;

	public WinnerDTO(Long totalValue, String idUser) {
		this.totalValue = totalValue;
		this.idUser = idUser;
	}

	public WinnerDTO() {

	}

	public Long getTotalValue() {
		return totalValue;
	}

	public void setTotalValue(Long totalValue) {
		this.totalValue = totalValue;
	}

	public String getIdUser() {
		return idUser;
	}

	public void setIdUser(String idUser) {
		this.idUser = idUser;
	}

}
