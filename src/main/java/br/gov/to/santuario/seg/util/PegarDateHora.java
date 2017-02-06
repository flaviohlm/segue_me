package br.gov.to.santuario.seg.util;
import java.sql.Time;
import java.util.Date;
import org.joda.time.DateTime; 
import org.joda.time.Hours; 


/**
 *
 * @author whelmison.rodrigues
 */
public class PegarDateHora {
    //retorna a data atual
    public Date getDateTime() {
        Date date = new Date();
        return date;
    }
    //retorna o hora atual
    public Time getTime() {
        Time time = new Time(new java.util.Date().getTime());
        return time;
    }
    public Hours getDiferencaTime(DateTime dataFinal, DateTime dataInicio) {
        return Hours.hoursBetween(dataFinal, dataInicio);
    }
}
