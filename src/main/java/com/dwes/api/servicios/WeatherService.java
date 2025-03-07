package com.dwes.api.servicios;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.dwes.api.entidades.Ingrediente;
import com.dwes.api.entidades.Jabon;
import com.dwes.api.entidades.enumerados.TipoDePiel;

@Service
public class WeatherService {
	private final String API_KEY = "f1a29e26706b4b4fabb81937250703";
	private final String API_URL = "http://api.weatherapi.com/v1/current.json";
	
	public Map<String, Object> obtenerClima(String ciudad) {
		String url = API_URL + "?key=" + API_KEY + "&q=" + ciudad;
		
		RestTemplate restTemplate = new RestTemplate();
		return restTemplate.getForObject(url, Map.class);
	}
}