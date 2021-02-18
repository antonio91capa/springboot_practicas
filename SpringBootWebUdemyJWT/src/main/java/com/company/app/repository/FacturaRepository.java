package com.company.app.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.company.app.model.Factura;

public interface FacturaRepository extends CrudRepository<Factura, Long> {

	@Query("select f from Factura f join fetch f.cliente c join fetch f.items i join fetch i.producto where f.id =?1")
	public Factura fetchByIdWithClienteWithItemFacturaWithProducto(Long id);
}
