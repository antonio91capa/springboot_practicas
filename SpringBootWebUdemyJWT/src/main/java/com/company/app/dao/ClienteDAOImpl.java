package com.company.app.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.company.app.model.Cliente;

@Repository
public class ClienteDAOImpl implements ClienteDAO {
	
	@PersistenceContext
	private EntityManager em;
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Cliente> allClientes() {
		return em.createQuery("from Cliente").getResultList();
	}

	@Override
	public void save(Cliente cliente) {
		// TODO Auto-generated method stub
		if(cliente.getId() != null && cliente.getId() > 0) {
			em.merge(cliente);
		}else {
			em.persist(cliente);
		}
	}

	@Override
	public Cliente findByIdCliente(Long idCliente) {
		// TODO Auto-generated method stub
		return em.find(Cliente.class, idCliente);
	}

	@Override
	public void delete(Long idCliente) {
		// TODO Auto-generated method stub
		Cliente cliente=findByIdCliente(idCliente);
		em.remove(cliente);
	}

}
