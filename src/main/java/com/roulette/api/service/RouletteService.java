package com.roulette.api.service;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.roulette.api.dto.BetDTO;
import com.roulette.api.dto.RouletteDTO;
import com.roulette.api.dto.WinnerDTO;
import com.roulette.api.entity.Bet;
import com.roulette.api.entity.Roulette;
import com.roulette.api.repository.IRouletteRepository;

@Service
public class RouletteService {

	@Autowired
	private IRouletteRepository iRouletteRepository;
	@Autowired
	HttpServletRequest requestUrl;
	@Autowired
	ObjectMapper objectMapper;

	public Roulette saveRoulette() {
		Roulette roulette = new Roulette();

		return iRouletteRepository.save(roulette);
	}

	public String openRoulette(Long id) {
		Roulette rouletteNew = iRouletteRepository.findById(id).orElse(null);
		if (rouletteNew == null)
			return "Operación denegada, no se encontró la ruleta";
		else if (rouletteNew.isStatus()) {

			return "Operación denegada, el estado de la ruleta ya esta abierto";
		}
		rouletteNew.setStatus(true);
		iRouletteRepository.save(rouletteNew);

		return "Operación exitosa, el estado de la ruleta ha cambiado ";

	}

	public BetDTO performBet(BetDTO betDTO) {
		boolean rouletteStatus = isRouletteStatus(betDTO.getIdRoulette());
		BetDTO betDTONew = null;
		betDTO.setIdUser(requestUrl.getHeader("idUser"));
		if (rouletteStatus) {
			if (isValidBet(betDTO)) {
				betDTONew = saveBet(betDTO);

				return betDTONew;
			}
		}

		return betDTONew;
	}

	public RouletteDTO closeRulet(Long idRoulette) {
		RouletteDTO rouletteDTO=null;
		List<WinnerDTO> winnersDTO = new ArrayList<WinnerDTO>();
		Long winningNumber = (long) (Math.random() * 37);
		Roulette rouletteNew = iRouletteRepository.findById(idRoulette).orElse(null);
		if (rouletteNew != null && rouletteNew.isStatus()) {
			rouletteNew.setStatus(false);
			iRouletteRepository.save(rouletteNew);
			List<BetDTO> listBetDTO = objectMapper.convertValue(rouletteNew.getBets(),
					new TypeReference<List<BetDTO>>() {
					});
			winnersDTO = selectWinner(listBetDTO, winningNumber);
			rouletteDTO = objectMapper.convertValue(rouletteNew, RouletteDTO.class);
			rouletteDTO.setWinners(winnersDTO);
			rouletteDTO.setWinnerNumber(winningNumber);
		}

		return rouletteDTO;
	}

	public List<RouletteDTO> getAllRoulettes() {
		List<Roulette> listRoulettes = new ArrayList<Roulette>();
		iRouletteRepository.findAll().forEach(listRoulettes::add);
		List<RouletteDTO> listRoulettesDTO = objectMapper.convertValue(listRoulettes,
				new TypeReference<List<RouletteDTO>>() {
				});

		return listRoulettesDTO;
	}

	private List<WinnerDTO> selectWinner(List<BetDTO> listBetDTO, Long winningNumber) {
		List<WinnerDTO> winners = new ArrayList<WinnerDTO>();
		WinnerDTO winnerDTOnew = null;
		for (BetDTO betDTO : listBetDTO) {

			if (isBetNumber(betDTO) && isWinningNumber(betDTO, winningNumber)) {
				winnerDTOnew = new WinnerDTO(betDTO.getValueToBet() * 5, betDTO.getIdUser());

			} else if (isColorBet(betDTO) && isWinningColor(betDTO, winningNumber)) {
				winnerDTOnew = new WinnerDTO((long) (betDTO.getValueToBet() * 1.8), betDTO.getIdUser());
			}
			if (winnerDTOnew != null)
				winners.add(winnerDTOnew);
		}

		return winners;
	}

	private boolean isColorBet(BetDTO betDTO) {

		return (betDTO.getColorToBet() != null) ? true : false;
	}

	private boolean isWinningColor(BetDTO betDTO, Long winningNumber) {
		boolean isWinningColor = false;
		if (betDTO.getColorToBet().equalsIgnoreCase("rojo") && winningNumber % 2 == 0) {
			isWinningColor = true;
		} else if (betDTO.getColorToBet().equalsIgnoreCase("negro") && winningNumber % 2 != 0) {
			isWinningColor = true;
		}

		return isWinningColor;
	}

	private boolean isBetNumber(BetDTO betDTO) {

		return (betDTO.getNumberToBet() != null) ? true : false;
	}

	private boolean isWinningNumber(BetDTO betDTO, Long winningNumber) {

		return (betDTO.getNumberToBet() == winningNumber) ? true : false;
	}

	private boolean isRouletteStatus(Long idRoulette) {
		Roulette roulette = iRouletteRepository.findById(idRoulette).orElse(null);
		boolean status = false;
		if (roulette != null) {
			if (roulette.isStatus()) {
				status = true;
			}
		}

		return status;
	}

	private boolean isValidBet(BetDTO betDTO) {
		boolean isValid = false;
		if (betDTO.getColorToBet() != null && betDTO.getNumberToBet() != null) {
			isValid = false;
		} else if (betDTO.getValueToBet() > 10000) {
			isValid = false;
		} else if (betDTO.getColorToBet() != null && isValidColor(betDTO)) {
			isValid = true;
		} else if (betDTO.getNumberToBet() != null && isValidNumber(betDTO)) {
			isValid = true;
		}

		return isValid;
	}

	private boolean isValidColor(BetDTO betDTO) {

		return (betDTO.getColorToBet().equalsIgnoreCase("rojo") || betDTO.getColorToBet().equalsIgnoreCase("negro"))
				? true
				: false;
	}

	private boolean isValidNumber(BetDTO betDTO) {

		return (betDTO.getNumberToBet() >= 0 && betDTO.getNumberToBet() <= 36) ? true : false;
	}

	private BetDTO saveBet(BetDTO betDTO) {
		Roulette rouletteNew = iRouletteRepository.findById(betDTO.getIdRoulette()).orElse(null);
		Bet bet = objectMapper.convertValue(betDTO, Bet.class);
		rouletteNew.addBetToList(bet);
		iRouletteRepository.save(rouletteNew);

		return betDTO;
	}
}
