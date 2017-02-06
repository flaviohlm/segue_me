/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.gov.to.santuario.seg.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRPdfExporter;

public class RelatorioUtil {

    public final static String SUBREPORT_DIR = "SUBREPORT_DIR";
    public final static String DIR = "/relatorios";

    private HashMap parametros(ArrayList<Object> lista) {
        HashMap parameters = new HashMap();
        if (!lista.isEmpty()) {
            Iterator<Object> iterator = lista.iterator();
            while (iterator.hasNext()) {
                parameters.put(iterator.next(), iterator.next());
            }
        }
        return parameters;
    }
    
    public void imprimeRelatorio(String diretorio, ArrayList<Object> listaParametros, Connection con, String nomeRelatorio) throws IOException, SQLException {

        try {
            listaParametros.add(SUBREPORT_DIR);
            listaParametros.add(getRealPath(DIR) + "/");

            FacesContext facesContext = FacesContext.getCurrentInstance();

            facesContext.responseComplete();

            ServletContext scontext = (ServletContext) facesContext.getExternalContext().getContext();

            JasperPrint jasperPrint = JasperFillManager.fillReport(scontext.getRealPath(diretorio), parametros(listaParametros), con);

            ByteArrayOutputStream baos = new ByteArrayOutputStream();

            JRPdfExporter exporter = new JRPdfExporter();

            exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);

            exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, baos);

            exporter.exportReport();

            byte[] bytes = baos.toByteArray();

            if (bytes != null && bytes.length > 0) {
                HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
                response.setContentType("application/pdf");
                response.setHeader("Content-disposition", "inline; filename=\""+nomeRelatorio+".pdf\"");
                response.setContentLength(bytes.length);
                ServletOutputStream outputStream = response.getOutputStream();
                outputStream.write(bytes, 0, bytes.length);
                outputStream.flush();
                outputStream.close();
            }
        } catch (Exception e) {
            e.printStackTrace();

        }

    }

    public void imprimeRelatorio(String diretorio, ArrayList<Object> listaParametros, JRDataSource dataSource) throws IOException, SQLException {

        try {
            FacesContext facesContext = FacesContext.getCurrentInstance();

            listaParametros.add(SUBREPORT_DIR);
            listaParametros.add(getRealPath(DIR) + "/");

            JasperPrint jasperPrint = JasperFillManager.fillReport(getRealPath(diretorio), parametros(listaParametros), dataSource);

            ByteArrayOutputStream baos = new ByteArrayOutputStream();

            JRPdfExporter exporter = new JRPdfExporter();

            exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);

            exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, baos);

            exporter.exportReport();

            byte[] bytes = baos.toByteArray();

            if (bytes != null && bytes.length > 0) {
                HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
                response.setContentType("application/pdf");
                response.setHeader("Content-disposition", "inline; filename=\"relatorio.pdf\"");
                response.setContentLength(bytes.length);
                ServletOutputStream outputStream = response.getOutputStream();
                outputStream.write(bytes, 0, bytes.length);
                outputStream.flush();
                response.getOutputStream().flush();
                response.getOutputStream().close();
                outputStream.close();
            }
        } catch (Exception e) {

            e.printStackTrace();

        }

    }

    public String getRealPath(String diretorio) {
        ServletContext sc = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
        return sc.getRealPath(diretorio);
    }
}
