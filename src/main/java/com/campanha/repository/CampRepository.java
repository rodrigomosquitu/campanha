package com.campanha.repository;

import java.util.Date;
import java.util.List;

import com.campanha.domain.Campanha;
import com.campanha.domain.LogCampanha;
import com.mongodb.WriteResult;

public interface CampRepository {

	public List<Campanha> getAllCampanhas();

	public List<Campanha> getAllCampanhasVigByTime(int idTimeCoracao);

	public List<Campanha> getCampanhasIntervalo(Date dataInicial, Date dataFinal);

	public void saveCampanha(Campanha tree);

	public Campanha getCampanha(int id);

	public WriteResult updateCampanha(int id, Date dataFinal);

	public void deleteCampanha(int id);

	public void createCollection();

	public void dropCollection();

	public void calculateNewFinalDates(Campanha campanha);
	
	//-------------------------------------------------------------------------------------------
	// LOGS
	//-------------------------------------------------------------------------------------------
	
	public void saveLogCampanha(LogCampanha logCampanha);

	public List<LogCampanha> getAllLogs();
}
