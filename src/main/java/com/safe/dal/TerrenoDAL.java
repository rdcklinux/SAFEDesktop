/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.safe.dal;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.safe.entity.SoliEvalTer;
import com.safe.entity.Usuario;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;
import sun.text.resources.FormatData;


public class TerrenoDAL extends DAL {
    
    public ArrayList<SoliEvalTer> getAllEvaluaciones() throws UnirestException, ParseException {
        String url = getURI("evaluaciones/list.json");
        HttpResponse<JsonNode> jsonResponse = Unirest.get(url).asJson();
        JSONArray array = jsonResponse.getBody().getArray();
        ArrayList<SoliEvalTer> collection = new ArrayList<>();
        SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd");
        for(int i=0; i<array.length(); i++){
            SoliEvalTer solicitud = new SoliEvalTer();
            JSONObject item = array.getJSONObject(i);
            solicitud.setFechacreacion(dt.parse(item.getString("creado")));
            solicitud.setFechaderivacion(dt.parse(item.getString("derivado")));
            Usuario tecnico = new Usuario();
            tecnico.setNombresusuario(item.getString("tecnico"));
            solicitud.setTecnico(tecnico);
            solicitud.setTipovisitteridtipovister(item.getInt("tipo"));
            solicitud.setEstadosolievalter(item.getInt("estado"));
            solicitud.setPdf(item.getString("pdf"));
            collection.add(solicitud);
        }
        
        return collection;
    }
}
