package com.roulette.api.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class RouletteDTO {
	private Long idRoulette;
	private boolean status;
	private List<BetDTO> bets;
	private List<WinnerDTO> winners;
	private Long winnerNumber;

	public Long getWinnerNumber() {
		return winnerNumber;
	}

	public void setWinnerNumber(Long winnerNumber) {
		this.winnerNumber = winnerNumber;
	}

	public List<WinnerDTO> getWinners() {
		return winners;
	}

	public void setWinners(List<WinnerDTO> winners) {
		this.winners = winners;
	}

	public Long getIdRoulette() {
		return idRoulette;
	}

	public void setIdRoulette(Long idRoulette) {
		this.idRoulette = idRoulette;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public List<BetDTO> getBets() {
		return bets;
	}

	public void setBets(List<BetDTO> bets) {
		this.bets = bets;
	}

}
