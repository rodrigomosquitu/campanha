package com.campanha.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.campanha.domain.Campanha;
import com.campanha.domain.Cliente;
import com.campanha.exception.CampanhaException;
import com.campanha.exception.ErrorResponse;
import com.campanha.repository.CampRepository;
import com.campanha.repository.ClienteRepository;

@RestController
public class ClienteController {
	
	@Autowired
	private ClienteRepository clienteRepository;

	@Autowired
	private CampRepository campRepository;
	
	/**
	 * Cadastra um novo cliente
	 * @param cliente
	 * @return
	 */
	@RequestMapping(value = "/addcliente", method = RequestMethod.POST)
	public ResponseEntity<List<?>> addCliente(@RequestBody Cliente cliente) throws CampanhaException
	{
		Cliente clienteEmail = clienteRepository.getClienteByEmail(cliente.getEmail());
		
		if (clienteEmail != null) {
			if (clienteEmail.getCampanhas() == null)
			{
				List<Campanha> listCampanhas = campRepository.getAllCampanhasVigByTime(cliente.getIdTimeCoracao());
				return new ResponseEntity<List<?>>(listCampanhas, HttpStatus.OK);
			}
			else
				throw new CampanhaException("Usuário já cadastrado com esse email!");
		}
		
		clienteRepository.saveCliente(cliente);
		return new ResponseEntity<List<?>>(clienteRepository.getAllClientes(), HttpStatus.OK);
	}

	/**
	 * @param ex
	 * @return
	 */
	@ExceptionHandler(CampanhaException.class)
    public ResponseEntity<ErrorResponse> exceptionHandler(Exception ex) {
        ErrorResponse error = new ErrorResponse();
        error.setErrorCode(HttpStatus.PRECONDITION_FAILED.value());
        error.setMessage(ex.getMessage());
        return new ResponseEntity<ErrorResponse>(error, HttpStatus.OK);
    }

	/**
	 * Associa as campanhas vigentes do time do coração ao cliente
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/associa/{idcliente}", method = RequestMethod.GET,headers="Accept=application/json")
	public Cliente associaClientesCampanhas(@PathVariable int idcliente)
	{
		return clienteRepository.associaClientesCampanhas(idcliente);
	}
	
	/**
	 * Listagem de todos os clientes
	 * @return
	 */
	@RequestMapping(value = "/clientes", method = RequestMethod.GET,headers="Accept=application/json")
	public List<Cliente> getAllClientes()
	{
		return clienteRepository.getAllClientes();
	}
	
}
