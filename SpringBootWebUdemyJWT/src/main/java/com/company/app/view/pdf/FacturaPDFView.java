package com.company.app.view.pdf;

import java.awt.Color;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.view.document.AbstractPdfView;

import com.company.app.model.Factura;
import com.company.app.model.ItemFactura;
import com.lowagie.text.Document;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

@Component("factura/ver")
public class FacturaPDFView extends AbstractPdfView {

	@Autowired
	private MessageSource messageSource;
	
	@Autowired
	private LocaleResolver localeResolver;
	
	@Override
	protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		Factura factura = (Factura) model.get("factura");
		
		Locale locale=localeResolver.resolveLocale(request);
		MessageSourceAccessor menssage=getMessageSourceAccessor();

		PdfPTable table1 = new PdfPTable(1);
		table1.setSpacingAfter(20);
		PdfPCell cell = null;
		cell = new PdfPCell(new Phrase(messageSource.getMessage("text.factura.ver.datos.cliente", null, locale)));
		cell.setBackgroundColor(new Color(184, 218, 255));
		cell.setPadding(8f);

		table1.addCell(cell);
		table1.addCell(factura.getCliente().getNombre() + " " + factura.getCliente().getApellido());
		table1.addCell(factura.getCliente().getEmail());

		PdfPTable table2 = new PdfPTable(1);
		table2.setSpacingAfter(20);
		cell = new PdfPCell(new Phrase(messageSource.getMessage("text.factura.ver.datos.factura", null, locale)));
		cell.setBackgroundColor(new Color(195, 230, 203));
		cell.setPadding(8f);
		table2.addCell(cell);
		table2.addCell(menssage.getMessage("text.cliente.factura.folio")+": " + factura.getId());
		table2.addCell(menssage.getMessage("text.cliente.factura.descripcion")+": " + factura.getDescripcion());
		table2.addCell(menssage.getMessage("text.cliente.factura.fecha")+": " + factura.getFecha_registro());

		document.add(table1);
		document.add(table2);

		PdfPTable table3 = new PdfPTable(4);
		table3.setWidths(new float[] { 3.5f, 1, 1, 1 });
		table3.addCell("Producto");
		table3.addCell("Precio");
		table3.addCell("Cantidad");
		table3.addCell("Total");

		for (ItemFactura item : factura.getItems()) {
			table3.addCell(item.getProducto().getNombre());
			table3.addCell(item.getProducto().getPrecio().toString());
			
			cell = new PdfPCell(new Phrase(item.getCantidad().toString()));
			cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			table3.addCell(cell);
			
			table3.addCell(item.calcularImporte().toString());
		}

		cell = new PdfPCell(new Phrase("Total: "));
		cell.setColspan(3);
		cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
		table3.addCell(cell);
		table3.addCell(factura.getTotal().toString());

		document.add(table3);
	}

}
