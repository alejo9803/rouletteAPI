package com.roulette.api.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.roulette.api.dto.BetDTO;
import com.roulette.api.dto.RouletteDTO;
import com.roulette.api.entity.Roulette;
import com.roulette.api.service.RouletteService;

@RestController
public class RouletteController {

	@Autowired
	private RouletteService rouletteService;

	@PostMapping("/roulette")
	public ResponseEntity<?> createRoulette() {
		Roulette rouletteNew = null;
		HashMap<String, Object> response = new HashMap<>();
		try {
			rouletteNew = rouletteService.saveRoulette();
		} catch (Exception e) {
			response.put("message", "Ha ocurrido un error interno");

			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("message", "La ruleta ha sido creado con exito");
		response.put("rouletteId", rouletteNew.getIdRoulette());

		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}

	@PutMapping("/roulette/{id}")
	public ResponseEntity<?> openRoulette(@PathVariable Long id) {
		String responseMessage = "";
		HashMap<String, Object> response = new HashMap<>();
		try {
			responseMessage = rouletteService.openRoulette(id);
		} catch (Exception e) {
			response.put("message", "Ha ocurrido un error interno");

			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("Mensaje", responseMessage);

		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}

	@PostMapping("/perform_bet")
	public ResponseEntity<?> performBet(@RequestBody BetDTO betDTO) {
		HashMap<String, Object> response = new HashMap<>();
		BetDTO betDTONew = null;
		try {
			betDTONew = rouletteService.performBet(betDTO);
			if (betDTONew != null) {
				response.put("message", "La apuesta ha sido creado con exito");
				response.put("betId", betDTONew.getIdBet());

				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
			}
			response.put("message", "No se pudo registrar la apuesta, por favor revise los valores de entrada");

			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			response.put("message", "Ha ocurrido un error interno");

			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@PutMapping("/close_roulette/{id}")
	public ResponseEntity<?> closeRulet(@PathVariable Long id) {
		HashMap<String, Object> response = new HashMap<>();
		RouletteDTO rouletteDTO = null;
		try {
			rouletteDTO = rouletteService.closeRulet(id);

			if (rouletteDTO != null) {
				response.put("message", "La ruleta ha sido cerrada con exito");
				response.put("roulette", rouletteDTO);

				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
			}
			response.put("message",
					"La ruleta no pudo ser cerrada, revise si la ruleta deseada ya ha sido cerrada con anterioridad");

			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			response.put("message", "Ha ocurrido un error interno");

			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/all_roulettes")
	public ResponseEntity<?> listRoulettes() {
		HashMap<String, Object> response = new HashMap<>();
		List<RouletteDTO> listRouletteDTO = new ArrayList<RouletteDTO>();
		try {
			listRouletteDTO = rouletteService.getAllRoulettes();
			response.put("listRoulettes", listRouletteDTO);

			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);

		} catch (Exception e) {
			response.put("message", "Ha ocurrido un error interno");

			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
