package com.example.temaJar.servicios;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;
import java.util.*;

@Service
public class ChatServicio {

    private final String API_KEY = "AIzaSyCsf9Z30eu4GvuwSWKlaPMwJNJ4rE0GTaE";

    private final String URL = "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.5-flash-lite:generateContent?key=" + API_KEY;

    public String procesarTextoConIA(String textoExtraido) {
        RestTemplate restTemplate = new RestTemplate();

        // 1. Limpieza básica del texto de entrada
        String textoLimpio = textoExtraido.replaceAll("[^a-zA-Z0-9 áéíóúÁÉÍÓÚñÑ,.]", " ").trim();
        if (textoLimpio.length() > 3000) {
            textoLimpio = textoLimpio.substring(0, 3000);
        }

        // 2. Construcción del cuerpo para el POST
        Map<String, Object> body = new HashMap<>();
        Map<String, Object> content = new HashMap<>();
        Map<String, Object> part = new HashMap<>();

        part.put("text", "Eres un experto en reclutamiento IT. Analiza el siguiente texto de un CV y extrae: Nombre completo y un Perfil Profesional resumido. Formato de respuesta: JSON puro, sin bloques de código. Texto: " + textoLimpio);
        content.put("parts", Collections.singletonList(part));
        body.put("contents", Collections.singletonList(content));

        // 3. Headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);

        try {
            System.out.println("--- ENVIANDO A GEMINI 2.5 FLASH ---");
            ResponseEntity<String> response = restTemplate.postForEntity(URL, request, String.class);

            // --- AQUÍ LLAMAMOS AL MÉTODO DE LIMPIEZA ---
            return limpiarRespuesta(response.getBody());

        } catch (Exception e) {
            System.err.println("ERROR EN LLAMADA: " + e.getMessage());
            return "{\"error\": \"Error al conectar con la IA: " + e.getMessage() + "\"}";
        }
    }

    private String limpiarRespuesta(String respuestaBruta) {
        try {
            // 1. Buscamos donde empieza el JSON del perfil (el primer '{' después de "text")
            int indiceText = respuestaBruta.indexOf("\"text\":");
            if (indiceText != -1) {
                int inicioJson = respuestaBruta.indexOf("{", indiceText);

                // 2. Buscamos el final del JSON interno.
                // Como el JSON interno termina con un '}', y luego sigue el JSON de Google,
                // buscamos el primer '}' que aparece después del inicio, o usamos una lógica de conteo.
                // Una forma simple es buscar la secuencia }" que cierra el campo text.
                int finJson = respuestaBruta.indexOf("}\"", inicioJson);

                if (finJson != -1) {
                    // Sumamos 1 para incluir la llave de cierre '}'
                    String recorte = respuestaBruta.substring(inicioJson, finJson + 1);

                    // 3. Limpiamos los escapes de comillas y saltos de línea
                    return recorte.replace("\\\"", "\"").replace("\\n", " ").trim();
                }
            }
        } catch (Exception e) {
            System.err.println("Error al extraer JSON interno: " + e.getMessage());
        }
        return respuestaBruta;
    }
}