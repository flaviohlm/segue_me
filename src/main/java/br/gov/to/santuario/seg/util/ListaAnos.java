package br.gov.to.santuario.seg.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author flavio.madureira
 */
public class ListaAnos {
    
    public static ArrayList<Integer> getListaAnos(){
        ArrayList<Integer> listaAnos = new ArrayList();
        Date data = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        String year = sdf.format(data);
        
        for(int i = 2014; i <=Integer.parseInt(year); i++){
            listaAnos.add(0, i);
        }
        
        return listaAnos;
    }
    
    public static Integer getAnoAtual(){        
        Date data = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        String year = sdf.format(data);
        
//        if(getMesAtual() < 4){
            return Integer.parseInt(year)-1;
//        }        
//        return Integer.parseInt(year);
    }
    
    public static Integer getMesAtual(){
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM");
        return Integer.parseInt(dateFormat.format(date));
    }
    
    public static Integer getMesAtualFromDate(Date date){        
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM");
        return Integer.parseInt(dateFormat.format(date));
    }
    
    public static String getMesAtualStringFromDate(Date date) throws ParseException{        
        Integer mes = getMesAtualFromDate(date);
        
        return converterMesNumeroParaString(mes.toString());
    }
        
    public static String converterMesNumeroParaString(String mes) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("MM");
        Date mesDate = sdf.parse(mes);
        sdf.applyPattern("MMMM");		
        return sdf.format(mesDate);
    }
    
    public static Date getDateFromString(String data) throws ParseException{  //Formato exemplo 2015-01-15      
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        
        Date date = df.parse(data);
        
        return date;
    }
    
    public static Integer getAnoAtualReal(){        
        Date data = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        String year = sdf.format(data);
        
         return Integer.parseInt(year);
    }
    
}
