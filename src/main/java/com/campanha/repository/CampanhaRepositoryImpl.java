package com.campanha.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import com.campanha.domain.Campanha;
import com.campanha.domain.LogCampanha;
import com.campanha.util.Util;
import com.mongodb.WriteResult;

public class CampanhaRepositoryImpl implements CampRepository {

	MongoTemplate mongoTemplate;

	public void setMongoTemplate(MongoTemplate mongoTemplate) {
		this.mongoTemplate = mongoTemplate;
	}

	/**
	 * Identifica todas as Campanhas.
	 */
	public List<Campanha> getAllCampanhas() {
		
		Query query = new Query();
		query.addCriteria(Criteria.where("dataInicial").lte(new Date()));
		query.addCriteria(Criteria.where("dataFinal").gte(new Date()));
		query.with(new Sort(Sort.Direction.ASC,"dataInicial"));
		
		return mongoTemplate.find(query, Campanha.class);
	}
	
	/**
	 * Identifica todas as Campanhas por Time do coração 
	 */
	public List<Campanha> getAllCampanhasVigByTime(int idTimeCoracao) {
		
		Query query = new Query();
		query.addCriteria(Criteria.where("dataInicial").lte(new Date()));
		query.addCriteria(Criteria.where("dataFinal").gte(new Date()));
		query.addCriteria(Criteria.where("idTimeCoracao").is(idTimeCoracao));

		query.with(new Sort(Sort.Direction.ASC,"dataInicial"));
		
		return mongoTemplate.find(query, Campanha.class);
	}
	
	/**
	 * Identifica todas as Campanhas por vigência
	 */
	public List<Campanha> getCampanhasIntervalo(Date dataInicial, Date dataFinal) {
		
		Query query = new Query();
		query.addCriteria(Criteria.where("dataInicial").gte(Util.dateLessOne(dataInicial)));
		query.addCriteria(Criteria.where("dataFinal").lte(dataFinal));
		query.with(new Sort(Sort.Direction.ASC,"dataFinal"));

		return mongoTemplate.find(query, Campanha.class);
	}

	/**
	 * Salva uma {@link Campanha}.
	 */
	public void saveCampanha(Campanha campanha) {
		mongoTemplate.insert(campanha);
	}

	/**
	 * Identifica uma {@link Campanha} para um id particular 
	 */
	public Campanha getCampanha(int id) {
		return mongoTemplate.findOne(new Query(Criteria.where("id").is(id)),
				Campanha.class);
	}

	/**
	 * Atualiza a Data final de uma {@link Campanha} para um id específico.
	 */
	public WriteResult updateCampanha(int id, Date dataFinal) {
		
		LogCampanha logCampanha = new LogCampanha(id, getCampanha(id).getDataFinal(), dataFinal);
		saveLogCampanha(logCampanha);
			
		return mongoTemplate.updateFirst(
				new Query(Criteria.where("id").is(id)),
				Update.update("dataFinal", dataFinal), Campanha.class);
	}

	/**
	 * Apaga uma {@link Campanha} para um id específico.
	 */
	public void deleteCampanha(int id) {
		mongoTemplate
				.remove(new Query(Criteria.where("id").is(id)), Campanha.class);
	}

	/**
	 * Create a {@link Campanha} collection if the collection does not already
	 * exists
	 */
	public void createCollection() {
		if (!mongoTemplate.collectionExists(Campanha.class)) {
			mongoTemplate.createCollection(Campanha.class);
		}
		
		if (!mongoTemplate.collectionExists(LogCampanha.class)) {
			mongoTemplate.createCollection(LogCampanha.class);
		}
	}

	/**
	 * Drops the {@link Campanha} collection if the collection does already exists
	 */
	public void dropCollection() {
		if (mongoTemplate.collectionExists(Campanha.class)) {
			mongoTemplate.dropCollection(Campanha.class);
		}
		
		if (mongoTemplate.collectionExists(LogCampanha.class)) {
			mongoTemplate.dropCollection(LogCampanha.class);
		}
	}
	
	/**
	 * Calcula as datas finais das campanhas existentes para aquele período de vigência
	 * 
	 */
	public void calculateNewFinalDates(Campanha campanha) {

		List<Campanha> campsInterval = getCampanhasIntervalo(campanha.getDataInicial(), campanha.getDataFinal());
		
		for (Campanha campInterval : campsInterval) {
			
			Date dtFinal = campInterval.getDataFinal();
			boolean hasSameDate = true;

			while (hasSameDate) {
				
				dtFinal = Util.datePlusOne(dtFinal);
	
				for (Campanha campInterval2 : campsInterval) {
				
					hasSameDate = Util.isSameDate(dtFinal, campInterval2.getDataFinal());
					if (hasSameDate)
						break;
				}
				
				if (!hasSameDate && (campanha.getId() != campInterval.getId())) {
					campInterval.setDataFinal(dtFinal);
					updateCampanha(campInterval.getId(), dtFinal);
				}
			}
			

		}
	}
	
	//-------------------------------------------------------------------------------------------
	// LOGS
	//-------------------------------------------------------------------------------------------

	/**
	 * Saves a {@link LogCampanha}.
	 */
	public void saveLogCampanha(LogCampanha logCampanha) {
		mongoTemplate.insert(logCampanha);
	}
	
	/**
	 * Get all Campanhas changes.
	 */
	public List<LogCampanha> getAllLogs() {
		
		Query query = new Query();
		query.with(new Sort(Sort.Direction.ASC, "sysDate"));
		
		return mongoTemplate.find(query, LogCampanha.class);
	}
}
