package com.example.temaJar.servicios;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;
import java.util.*;

@Service
public class ChatServicio {

    // Tu API Key AQ. (formato nuevo de Google, válida)
    private final String API_KEY = "AQ.Ab8RN6IfnQ98ytl7dwYhUfrXzp4Zwcn1bJz9ID-_iLfSDzAKrg";

    // MODELO FUNCIONANDO: gemini-3.5-flash (GA, estable, probado con tu key)
    private final String URL = "https://generativelanguage.googleapis.com/v1beta/models/gemini-3.5-flash:generateContent";

    private final ObjectMapper objectMapper = new ObjectMapper();

    // ================== 1. ANALIZAR CV (ya existente) ==================

    public String procesarTextoConIA(String textoExtraido) {
        RestTemplate restTemplate = new RestTemplate();

        String textoLimpio = textoExtraido.trim();
        if (textoLimpio.length() > 8000) {
            textoLimpio = textoLimpio.substring(0, 8000);
        }

        Map<String, Object> body = new HashMap<>();
        Map<String, Object> content = new HashMap<>();
        Map<String, Object> part = new HashMap<>();

        part.put("text",
                "Eres un experto en reclutamiento IT. Analiza este CV y devuelve UNICAMENTE un JSON valido. " +
                        "Sin texto introductorio, sin explicaciones, sin markdown, sin notas adicionales. " +
                        "Formato obligatorio: {\"nombre\":\"...\",\"email\":\"...\",\"telefono\":\"...\",\"linkedin\":\"...\",\"github\":\"...\",\"direccion\":\"...\",\"resumen_profesional\":\"...\",\"habilidades_tecnicas\":[\"...\"],\"experiencia_laboral\":[{\"empresa\":\"...\",\"puesto\":\"...\",\"periodo\":\"...\"}],\"educacion\":[\"...\"]} " +
                        "CV: " + textoLimpio
        );
        content.put("parts", Collections.singletonList(part));
        body.put("contents", Collections.singletonList(content));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("x-goog-api-key", API_KEY);

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);

        try {
            System.out.println("--- ENVIANDO A GEMINI 3.5 FLASH (ANALISIS) ---");
            ResponseEntity<String> response = restTemplate.postForEntity(URL, request, String.class);
            return extraerTextoDeRespuestaGemini(response.getBody());
        } catch (Exception e) {
            throw new RuntimeException("Fallo al comunicarse con Gemini: " + e.getMessage());
        }
    }

    // ================== 2. ADAPTAR CV (NUEVO) ==================

    /**
     * Adapta un CV ya analizado para una categoría específica.
     */
    /**
     * Adapta un CV ya analizado para una categoría específica.
     */
    public String adaptarCvParaCategoria(String textoCvAnalizado, String categoria) {
        RestTemplate restTemplate = new RestTemplate();

        String prompt = "Eres un experto en reclutamiento IT. " +
                "Adapta el siguiente CV analizado para enfocarlo 100% hacia la categoría: " + categoria + ". " +
                "Resalta las habilidades y experiencias más relevantes para esta especialidad. " +
                "Reescribe el resumen profesional orientado a esta categoría. " +
                "DEVUELVE ÚNICAMENTE UN JSON VÁLIDO. Sin texto introductorio, sin explicaciones, sin markdown. " +
                "Formato exacto obligatorio con TODOS estos campos: " +
                "{\"nombre\":\"...\",\"email\":\"...\",\"telefono\":\"...\",\"linkedin\":\"...\",\"github\":\"...\",\"direccion\":\"...\",\"resumen_profesional\":\"...\",\"habilidades_tecnicas\":[\"...\"],\"experiencia_laboral\":[{\"empresa\":\"...\",\"puesto\":\"...\",\"periodo\":\"...\"}],\"educacion\":[\"...\"]} " +
                "CV analizado: " + textoCvAnalizado;

        if (prompt.length() > 12000) {
            prompt = prompt.substring(0, 12000);
        }

        Map<String, Object> body = new HashMap<>();
        Map<String, Object> content = new HashMap<>();
        Map<String, Object> part = new HashMap<>();

        part.put("text", prompt);
        content.put("parts", Collections.singletonList(part));
        body.put("contents", Collections.singletonList(content));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("x-goog-api-key", API_KEY);

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);

        try {
            System.out.println("--- ENVIANDO A GEMINI 3.5 FLASH (ADAPTACION: " + categoria + ") ---");
            ResponseEntity<String> response = restTemplate.postForEntity(URL, request, String.class);
            return extraerTextoDeRespuestaGemini(response.getBody());
        } catch (Exception e) {
            throw new RuntimeException("Fallo al adaptar con Gemini: " + e.getMessage());
        }
    }

    // ================== PRIVADO: PARSEAR RESPUESTA ==================

    private String extraerTextoDeRespuestaGemini(String jsonCrudo) {
        try {
            JsonNode root = objectMapper.readTree(jsonCrudo);

            if (root.has("error")) {
                String errorMsg = root.path("error").path("message").asText("Error desconocido de Gemini");
                throw new RuntimeException("Gemini respondió con error: " + errorMsg);
            }

            JsonNode candidates = root.path("candidates");
            if (candidates.isArray() && candidates.size() > 0) {
                JsonNode firstCandidate = candidates.get(0);
                JsonNode parts = firstCandidate.path("content").path("parts");
                if (parts.isArray() && parts.size() > 0) {
                    String texto = parts.get(0).path("text").asText();
                    System.out.println("--- TEXTO EXTRAÍDO DE GEMINI (primeros 300 chars) ---");
                    System.out.println(texto.substring(0, Math.min(texto.length(), 300)));
                    System.out.println("------------------------------------------------------");
                    return texto;
                }
            }
            throw new RuntimeException("No se encontró texto en la respuesta de Gemini");
        } catch (Exception e) {
            throw new RuntimeException("Error al parsear respuesta de Gemini: " + e.getMessage());
        }
    }
}