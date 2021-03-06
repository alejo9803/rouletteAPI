package com.roulette.api.entity;

import org.springframework.data.annotation.Id;

public class Bet {
	@Id   
	private Long idBet;
	private Long numberToBet;
	private String colorToBet;
	private String idUser;
	private Long valueToBet;

	public Long getIdBet() {
		return idBet;
	}

	public void setIdBet(Long idBet) {
		this.idBet = idBet;
	}

	public Long getNumberToBet() {
		return numberToBet;
	}

	public void setNumberToBet(Long numberToBet) {
		this.numberToBet = numberToBet;
	}

	public String getColorToBet() {
		return colorToBet;
	}

	public void setColorToBet(String colorToBet) {
		this.colorToBet = colorToBet;
	}

	public String getIdUser() {
		return idUser;
	}

	public void setIdUser(String idUser) {
		this.idUser = idUser;
	}

	public Long getValueToBet() {
		return valueToBet;
	}

	public void setValueToBet(Long valueToBet) {
		this.valueToBet = valueToBet;
	}

}
