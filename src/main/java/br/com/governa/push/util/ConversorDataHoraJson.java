package br.com.governa.push.util;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Converte Datas para um formato amigavel ao Javascript
 */
@Service
public class ConversorDataHoraJson extends JsonSerializer<Date> {

    /**
     * Converte datahoras para javascript, uso nos getters que retornam datas no Domain
     * @param data
     * @param gen
     * @param provider
     * @throws java.io.IOException
     * @throws com.fasterxml.jackson.core.JsonProcessingException
     */
    @Override
    public void serialize(Date data, JsonGenerator gen, SerializerProvider provider)
            throws IOException, JsonProcessingException {
        gen.writeString(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").format(data));
    }

}