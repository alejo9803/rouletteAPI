package com.roulette.api.entity;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@RedisHash("Roulette")
public class Roulette {
	@Id
	private Long idRoulette;
	private boolean status;
	private List<Bet> bets=new ArrayList<>();

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

	public List<Bet> getBets() {
		return bets;
	}

	public void setBets(List<Bet> bets) {
		this.bets = bets;
	}

	public void addBetToList(Bet bet) {
		bets.add(bet);
	}
}
