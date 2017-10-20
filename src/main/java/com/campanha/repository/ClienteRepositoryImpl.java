package com.campanha.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import com.campanha.domain.Campanha;
import com.campanha.domain.Cliente;
import com.mongodb.WriteResult;

public class ClienteRepositoryImpl implements ClienteRepository {

	@Autowired
	private CampRepository campRepository;
	
	MongoTemplate mongoTemplate;

	public void setMongoTemplate(MongoTemplate mongoTemplate) {
		this.mongoTemplate = mongoTemplate;
	}

	/**
	 * Saves a {@link Cliente}.
	 */
	public void saveCliente(Cliente cliente) {
		mongoTemplate.insert(cliente);
	}

	/**
	 * Get all Clientes.
	 */
	public List<Cliente> getAllClientes() {
		return mongoTemplate.findAll(Cliente.class);
	}
	
	/**
	 * Gets a {@link Cliente} for a particular id.
	 */
	public Cliente getCliente(int id) {
		return mongoTemplate.findOne(new Query(Criteria.where("id").is(id)),
				Cliente.class);
	}
	
	/**
	 * Gets a {@link Cliente} for a particular email.
	 */
	public Cliente getClienteByEmail(String email) {
		return mongoTemplate.findOne(new Query(Criteria.where("email").is(email)),
				Cliente.class);
	}
	
	/**
	 * Associa as campanhas vigentes ao {@link Cliente}.
	 */
	public Cliente associaClientesCampanhas(int idCliente) {
		
		Cliente cliente = getCliente(idCliente);
		List<Campanha> listCampanhas = campRepository.getAllCampanhasVigByTime(cliente.getIdTimeCoracao());
		
		if (listCampanhas != null && !listCampanhas.isEmpty()) 
			updateCliente(idCliente, listCampanhas);

		return cliente;
	}
	
	/**
	 * Updates a {@link Cliente} name for a particular id.
	 */
	public WriteResult updateCliente(int id, List<Campanha> campanhas) {
			
		return mongoTemplate.updateFirst(
				new Query(Criteria.where("id").is(id)),
				Update.update("campanhas", campanhas), Cliente.class);
	}
	
	/**
	 * Create a {@link Cliente} collection if the collection does not already
	 * exists
	 */
	public void createCollection() {
		if (!mongoTemplate.collectionExists(Cliente.class)) {
			mongoTemplate.createCollection(Cliente.class);
		}
	}

	/**
	 * Drops the {@link Cliente} collection if the collection does already exists
	 */
	public void dropCollection() {
		if (mongoTemplate.collectionExists(Cliente.class)) {
			mongoTemplate.dropCollection(Cliente.class);
		}
	}
}
