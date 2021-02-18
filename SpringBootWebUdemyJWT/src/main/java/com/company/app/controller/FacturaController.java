package com.company.app.controller;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.company.app.model.Cliente;
import com.company.app.model.Factura;
import com.company.app.model.ItemFactura;
import com.company.app.model.Producto;
import com.company.app.service.ClienteService;

@Controller
@RequestMapping("/factura")
@SessionAttributes("factura") // La sesion se va mantener activa hasta que el que se haya procesado y termine
								// todo el proceso de la factura junto con el cliente
public class FacturaController {

	private final Logger log = LoggerFactory.getLogger(getClass());

	@Autowired
	private ClienteService clienteService;
	
	@Autowired
	private MessageSource messageSource;

	@GetMapping("/form/{clienteId}")
	public String crear(@PathVariable(value = "clienteId") Long idCliente, Map<String, Object> map,
			RedirectAttributes flash, Locale locale) {

		Cliente cliente = clienteService.findOne(idCliente);
		
		if (cliente == null) {
			flash.addFlashAttribute("error", messageSource.getMessage("text.cliente.flash.db.error", null, locale));
			return "redirect:/listar";
		}

		Factura factura = new Factura();
		factura.setCliente(cliente);

		map.put("factura", factura);
		map.put("titulo", messageSource.getMessage("text.factura.form.titulo", null, locale));
		return "factura/form";
	}

	@GetMapping(value = "/cargar-productos/{term}", produces = { "application/json" })
	public @ResponseBody List<Producto> cargarProductos(@PathVariable String term) {
		return clienteService.findByName(term);
	}

	@PostMapping("/save")
	public String guardar(@Valid Factura factura, BindingResult result,
			@RequestParam(name = "item_id[]", required = false) Long[] itemId,
			@RequestParam(name = "cantidad[]", required = false) Integer[] cantidad, RedirectAttributes flash,
			SessionStatus status, Model model, Locale locale) {

		if (result.hasErrors()) {
			model.addAttribute("titulo",  messageSource.getMessage("text.factura.form.titulo", null, locale));
			return "factura/form";
		}

		if (itemId == null || itemId.length == 0) {
			model.addAttribute("titulo",  messageSource.getMessage("text.factura.form.titulo", null, locale));
			model.addAttribute("error",  messageSource.getMessage("text.factura.flash.lineas.error", null, locale));
			return "factura/form";
		}

		for (int i = 0; i < itemId.length; i++) {
			Producto producto = clienteService.findProductoById(itemId[i]);

			ItemFactura item = new ItemFactura();
			item.setCantidad(cantidad[i]);
			item.setProducto(producto);

			factura.addItemFactura(item);
			log.info("ID: " + itemId[i].toString() + ", cantidad: " + cantidad[i].toString());
		}

		clienteService.saveFactura(factura);
		status.setComplete();

		flash.addFlashAttribute("success",  messageSource.getMessage("text.factura.flash.crear.success", null, locale));
		return "redirect:/ver/" + factura.getCliente().getId();
	}
	
	@GetMapping("/ver/{id}")
	public String verFactura(@PathVariable("id") Long idFactura, RedirectAttributes flash,
			Model model, Locale locale) {
		
		Factura factura= clienteService.fetchByIdWithClienteWithItemFacturaWithProducto(idFactura); 
//		Factura factura= clienteService.findFacturaById(idFactura);
		
		if(factura == null) {
			flash.addFlashAttribute("error", messageSource.getMessage("text.factura.flash.db.error", null, locale));
			log.info("Error. No hay factura");
			return "redirect:/listar";
		}
		
		model.addAttribute("factura", factura);
		model.addAttribute("titulo", String.format(messageSource.getMessage("text.factura.ver.titulo", null, locale), factura.getDescripcion()));
		return "factura/ver";
	}
	
	@GetMapping("/delete/{id}")
	public String eliminar(@PathVariable(value="id") Long id, RedirectAttributes flash,
			Locale locale) {
		Factura factura=clienteService.findFacturaById(id);
		
		if(factura != null) {
			clienteService.deleteFactura(id);
			flash.addFlashAttribute("success",  messageSource.getMessage("text.factura.flash.eliminar.success", null, locale));
			return "redirect:/ver/"+factura.getCliente().getId();
		}
		flash.addFlashAttribute("error",  messageSource.getMessage("text.factura.flash.db.error", null, locale));
		return "redirect:/listar";
	}

}
