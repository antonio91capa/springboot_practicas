package com.company.app.controller;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestWrapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.company.app.model.Cliente;
import com.company.app.service.ClienteService;
import com.company.app.service.UploadFileService;
import com.company.app.util.PageRender;

@Controller
@SessionAttributes("cliente")
public class ClienteController {

	private final Logger log = LoggerFactory.getLogger(getClass());

	@Autowired
	private ClienteService clienteService;

	@Autowired
	private UploadFileService uploadFileService;
	
	@Autowired
	private MessageSource messageSource;

	/*
	 * @RequestMapping(value="/listar", method=RequestMethod.GET) public String
	 * listar(Model model) { model.addAttribute("titulo", "Listado de clientes");
	 * model.addAttribute("clientes", clienteService.findAll()); return "listar"; }
	 */

	@GetMapping("/")
	public String index() {
		return "index";
	}

	@RequestMapping(value = "/listar", method = RequestMethod.GET)
	public String listar(@RequestParam(name = "page", defaultValue = "0") int page, 
			Model model, Authentication authentication, HttpServletRequest request,
			Locale locale) {
		
		if(authentication != null) {
			log.info("Hola, usuario autenticado. Tu username es: ".concat(authentication.getName()));
		}
		
		Authentication auth=SecurityContextHolder.getContext().getAuthentication();
		
		if(auth != null) {
			log.info("Utilizando forma estatica SecurityContextHolder.getContext().getAuthentication(): Usuario autenticado: ".concat(auth.getName()));
		}
		
		if(hasRole("ROLE_ADMIN")) {
			log.info("Hola: ".concat(auth.getName()).concat(", tiene acceso"));
		}else {
			log.info("Hola: ".concat(auth.getName()).concat(", no tiene acceso"));
		}
		
		SecurityContextHolderAwareRequestWrapper securityContext=new SecurityContextHolderAwareRequestWrapper(request, "");
		
		if(securityContext.isUserInRole("ROLE_ADMIN")) {
			log.info("Forma usando SecurityContextHolderAwareRequestWrapper: Hola ".concat(auth.getName()).concat(" tiene acceso"));
		}else {
			log.info("Forma usando SecurityContextHolderAwareRequestWrapper: Hola ".concat(auth.getName()).concat(" no tiene acceso"));
		}
		
		if(request.isUserInRole("ROLE_ADMIN")) {
			log.info("Forma usando HttpServletRequest: Hola ".concat(auth.getName()).concat(" tiene acceso"));
		}else {
			log.info("Forma usando HttpServletRequest: Hola ".concat(auth.getName()).concat(" no tiene acceso"));
		}
		
		Pageable pageRequest = PageRequest.of(page, 5);
		Page<Cliente> clientes = clienteService.findAll(pageRequest);
		PageRender<Cliente> pageRender = new PageRender<Cliente>("/listar", clientes);

		model.addAttribute("titulo", messageSource.getMessage("text.cliente.listar.titulo", null, locale));
		model.addAttribute("clientes", clientes);
		model.addAttribute("page", pageRender);
		return "listar";
	}
	
	@GetMapping(value = "/upload/{filename:.+}")
	public ResponseEntity<Resource> verFoto(@PathVariable String filename) {

		Resource recurso = null;
		try {
			recurso = uploadFileService.load(filename);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + recurso.getFilename() + "\"")
				.body(recurso);
	}

	@GetMapping(value = "/ver/{id}")
	public String ver(@PathVariable(value = "id") Long id, Model model, 
			RedirectAttributes flash, Locale locale) {
		
//		Cliente cliente = clienteService.findOne(id);
		Cliente cliente = clienteService.findByIdWithFacturas(id);
		if (cliente == null) {
			flash.addFlashAttribute("error", messageSource.getMessage("text.cliente.flash.db.error", null, locale));
			return "redirect:/listar";
		}
		model.addAttribute("cliente", cliente);
		model.addAttribute("titulo", messageSource.getMessage("text.cliente.detalle.titulo", null, locale).concat(": ").concat(cliente.getNombre()));

		return "ver";
	}

	@GetMapping("/create")
	public String crear(Map<String, Object> map, Locale locale) {
		Cliente cliente = new Cliente();
		map.put("titulo", messageSource.getMessage("text.cliente.form.titulo.crear", null, locale));
		map.put("cliente", cliente);
		return "form";
	}

	@PostMapping("/save")
	public String guardar(@Valid Cliente cliente, BindingResult result, Model model, SessionStatus status,
			RedirectAttributes attr, @RequestParam("file") MultipartFile foto, Locale locale) {
		
		if (result.hasErrors()) {
			model.addAttribute("titulo", messageSource.getMessage("text.cliente.flash.db.error", null, locale));
			return "form";
		}

		if (!foto.isEmpty()) {
			if (cliente.getId() != null && cliente.getId() > 0 && cliente.getFoto() != null
					&& cliente.getFoto().length() > 0) {
				uploadFileService.delete(cliente.getFoto());
			}

			String uniqueFileName = null;
			try {
				uniqueFileName = uploadFileService.copy(foto);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			attr.addFlashAttribute("info", messageSource.getMessage("text.cliente.flash.foto.subir.success", null, locale)+"'"+uniqueFileName+"'");
			log.info("Cliente guardado correctamente");
			cliente.setFoto(uniqueFileName);
		}
		
		String mensaje = (cliente.getId() != null) ? messageSource.getMessage("text.cliente.flash.editar.success", null, locale) : messageSource.getMessage("text.cliente.flash.crear.success", null, locale);

		clienteService.save(cliente);
		status.setComplete();
		attr.addFlashAttribute("success", mensaje);
		return "redirect:listar";
	}

	@GetMapping(value = "/edit/{id}")
	public String editar(@PathVariable(value = "id") Long idCliente, Map<String, Object> map, 
				RedirectAttributes attr, Locale locale) {
		
		Cliente cli = null;
		if (idCliente > 0) {
			cli = clienteService.findOne(idCliente);
			if (cli == null) {
				attr.addFlashAttribute("error", messageSource.getMessage("text.cliente.flash.db.error", null, locale));
				return "redirect:/listar";
			}
		} else {
			attr.addFlashAttribute("error", messageSource.getMessage("text.cliente.flash.id.error", null, locale));
			return "redirect:/listar";
		}
		map.put("cliente", cli);
		map.put("titulo", messageSource.getMessage("text.cliente.form.titulo.editar", null, locale));
		return "form";
	}

	@GetMapping("/delete/{id}")
	public String eliminar(@PathVariable("id") Long idCliente, RedirectAttributes attr, Locale locale) {
		
		if (idCliente > 0) {
			Cliente cliente = clienteService.findOne(idCliente);
			clienteService.delete(idCliente);
			attr.addFlashAttribute("sucess", messageSource.getMessage("text.cliente.flash.foto.eliminar.success", null, locale));

			if (uploadFileService.delete(cliente.getFoto())) {
				attr.addFlashAttribute("info", String.format(messageSource.getMessage("text.cliente.flash.foto.eliminar.success", null, locale), cliente.getFoto()));
			}
		}
		return "redirect:/listar";
	}
	
	private boolean hasRole(String role) {
		SecurityContext context=SecurityContextHolder.getContext();
		
		if(context == null) {
			return false;
		}
		
		Authentication auth=context.getAuthentication();
		
		if(auth == null) {
			return false;
		}
		
		Collection<? extends GrantedAuthority> authorities=auth.getAuthorities();
		
		return authorities.contains(new SimpleGrantedAuthority(role));
	}
}
