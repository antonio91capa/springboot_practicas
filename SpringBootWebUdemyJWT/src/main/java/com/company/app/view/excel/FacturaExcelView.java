package com.company.app.view.excel;

import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.view.document.AbstractXlsxView;

import com.company.app.model.Factura;
import com.company.app.model.ItemFactura;

@Component("factura/ver.xlsx")
public class FacturaExcelView extends AbstractXlsxView {
	
	@Autowired
	private MessageSource messageSource;
	
	@Autowired
	private LocaleResolver localeResolver;

	@Override
	protected void buildExcelDocument(Map<String, Object> model, Workbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		response.setHeader("Content-Disposition", "attachment; filename=\"factura_view.xlsx\" ");
		Factura factura=(Factura) model.get("factura");
		
		Locale locale=localeResolver.resolveLocale(request);
		MessageSourceAccessor menssage=getMessageSourceAccessor();
		
		Sheet sheet=workbook.createSheet("Factura de Venta");
		Row row=sheet.createRow(0);
		Cell cell=row.createCell(0);
		
		cell.setCellValue(messageSource.getMessage("text.factura.ver.datos.cliente", null, locale));
		row=sheet.createRow(1);
		cell=row.createCell(0);
		cell.setCellValue(factura.getCliente().getNombre()+" "+factura.getCliente().getApellido());
		
		row=sheet.createRow(2);
		cell=row.createCell(0);
		cell.setCellValue(factura.getCliente().getEmail());
		
		sheet.createRow(4).createCell(0).setCellValue(messageSource.getMessage("text.factura.ver.datos.factura", null, locale));
		sheet.createRow(5).createCell(0).setCellValue(menssage.getMessage("text.cliente.factura.folio")+": "+factura.getId());
		sheet.createRow(6).createCell(0).setCellValue(menssage.getMessage("text.cliente.factura.descripcion")+": "+factura.getDescripcion());
		sheet.createRow(7).createCell(0).setCellValue(menssage.getMessage("text.cliente.factura.fecha")+": "+factura.getFecha_registro());
		
		CellStyle theadStyle=workbook.createCellStyle();
		theadStyle.setBorderBottom(BorderStyle.MEDIUM);
		theadStyle.setBorderTop(BorderStyle.MEDIUM);
		theadStyle.setBorderRight(BorderStyle.MEDIUM);
		theadStyle.setBorderLeft(BorderStyle.MEDIUM);
		theadStyle.setFillForegroundColor(IndexedColors.GOLD.index);
		theadStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		
		CellStyle tbodyStyle=workbook.createCellStyle();
		tbodyStyle.setBorderBottom(BorderStyle.THIN);
		tbodyStyle.setBorderTop(BorderStyle.THIN);
		tbodyStyle.setBorderRight(BorderStyle.THIN);
		tbodyStyle.setBorderLeft(BorderStyle.THIN);
		
		Row header=sheet.createRow(9);
		header.createCell(0).setCellValue(menssage.getMessage("text.factura.form.item.nombre"));
		header.createCell(1).setCellValue(menssage.getMessage("text.factura.form.item.precio"));
		header.createCell(2).setCellValue(menssage.getMessage("text.factura.form.item.cantidad"));
		header.createCell(3).setCellValue(menssage.getMessage("text.factura.form.item.total"));
		
		header.getCell(0).setCellStyle(theadStyle);
		header.getCell(1).setCellStyle(theadStyle);
		header.getCell(2).setCellStyle(theadStyle);
		header.getCell(3).setCellStyle(theadStyle);
		
		int rownum=10;
		for (ItemFactura items : factura.getItems()) {
			Row fila=sheet.createRow(rownum++);
			cell=fila.createCell(0);
			cell.setCellValue(items.getProducto().getNombre());
			cell.setCellStyle(tbodyStyle);
			
			cell=fila.createCell(1);
			cell.setCellValue(items.getProducto().getPrecio());
			cell.setCellStyle(tbodyStyle);
			
			cell=fila.createCell(2);
			cell.setCellValue(items.getCantidad());
			cell.setCellStyle(tbodyStyle);
			
			cell=fila.createCell(3);
			cell.setCellValue(items.calcularImporte());
			cell.setCellStyle(tbodyStyle);
		}
		
		Row filatotal=sheet.createRow(rownum);
		cell=filatotal.createCell(2);
		cell.setCellValue(menssage.getMessage("text.factura.form.item.total"));
		cell.setCellStyle(tbodyStyle);
		
		cell=filatotal.createCell(3);
		cell.setCellValue(factura.getTotal());
		cell.setCellStyle(tbodyStyle);
		
	}
}
