package com.campanha.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.campanha.domain.Campanha;
import com.campanha.domain.LogCampanha;
import com.campanha.repository.CampRepository;

@RestController
public class CampanhaController {
	
	@Autowired
	private CampRepository campRepository;
		
	/**
	 * Lista todas as campanhas vigentes
	 * @return
	 */
	@RequestMapping(value = "/lista", method = RequestMethod.GET,headers="Accept=application/json")
	public ResponseEntity<List<Campanha>> getCampanhas()
	{
		return new ResponseEntity<List<Campanha>>(campRepository.getAllCampanhas(), HttpStatus.OK);
	}

	/**
	 * Identifica uma Campanha pelo id
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.GET,headers="Accept=application/json")
	public Campanha getCampanhaById(@PathVariable int id)
	{
		return campRepository.getCampanha(id);
	}

	/**
	 * Exlclui uma Campanha pelo id
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/del/{id}", method = RequestMethod.GET,headers="Accept=application/json")
	public void delCampanhaById(@PathVariable int id)
	{
		campRepository.deleteCampanha(id);
	}
	
	/**
	 * Cadastra uma nova campanha
	 * @param campanha
	 * @return
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public List<Campanha> addCampanha(@RequestBody Campanha campanha)
	{
		campRepository.saveCampanha(campanha);
		campRepository.calculateNewFinalDates(campanha);
		
		return campRepository.getAllCampanhas();
	}

	/**
	 * Lista as alterações efetuadas nas campanhas (datas finais de vigência)
	 * @return
	 */
	@RequestMapping(value = "/logs", method = RequestMethod.GET,headers="Accept=application/json")
	public List<LogCampanha> getLogs()
	{
		return campRepository.getAllLogs();
	}
}
