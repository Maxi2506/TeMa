package com.example.temaJar.servicios;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CvParserServicio {

    private final ObjectMapper objectMapper = new ObjectMapper();

    public CvParseado parsearCvJson(String textoCrudo) {
        try {
            String jsonLimpio = extraerJsonDelTexto(textoCrudo);
            JsonNode root = objectMapper.readTree(jsonLimpio);

            CvParseado cv = new CvParseado();
            cv.setNombre(getTexto(root, "nombre"));
            cv.setEmail(getTexto(root, "email"));
            cv.setTelefono(getTexto(root, "telefono"));
            cv.setLinkedin(getTexto(root, "linkedin"));
            cv.setGithub(getTexto(root, "github"));
            cv.setDireccion(getTexto(root, "direccion"));
            cv.setResumenProfesional(getTexto(root, "resumen_profesional"));
            cv.setHabilidadesTecnicas(getArrayStrings(root, "habilidades_tecnicas"));
            cv.setExperienciaLaboral(getExperiencias(root));
            cv.setEducacion(getArrayStrings(root, "educacion"));

            return cv;

        } catch (Exception e) {
            CvParseado cv = new CvParseado();
            cv.setResumenProfesional("No se pudo parsear el JSON. Contenido crudo:\n\n" + textoCrudo);
            return cv;
        }
    }

    private String extraerJsonDelTexto(String texto) {
        String limpio = texto.replaceAll("(?s)^```json\\s*", "")
                .replaceAll("(?s)\\s*```$", "")
                .trim();

        int primerLlave = limpio.indexOf('{');
        int ultimaLlave = limpio.lastIndexOf('}');

        if (primerLlave != -1 && ultimaLlave != -1 && primerLlave < ultimaLlave) {
            return limpio.substring(primerLlave, ultimaLlave + 1);
        }
        return limpio;
    }

    private String getTexto(JsonNode root, String campo) {
        return root.has(campo) ? root.get(campo).asText("") : "";
    }

    private List<String> getArrayStrings(JsonNode root, String campo) {
        List<String> lista = new ArrayList<>();
        if (root.has(campo) && root.get(campo).isArray()) {
            for (JsonNode n : root.get(campo)) {
                if (!n.asText("").trim().isEmpty()) {
                    lista.add(n.asText());
                }
            }
        }
        return lista;
    }

    private List<ExperienciaParseada> getExperiencias(JsonNode root) {
        List<ExperienciaParseada> lista = new ArrayList<>();
        if (root.has("experiencia_laboral") && root.get("experiencia_laboral").isArray()) {
            for (JsonNode n : root.get("experiencia_laboral")) {
                ExperienciaParseada exp = new ExperienciaParseada();
                exp.setEmpresa(getTexto(n, "empresa"));
                exp.setPuesto(getTexto(n, "puesto"));
                exp.setPeriodo(getTexto(n, "periodo"));
                lista.add(exp);
            }
        }
        return lista;
    }

    // ============ CLASES INTERNAS ============

    public static class CvParseado {
        private String nombre;
        private String email;
        private String telefono;
        private String linkedin;
        private String github;
        private String direccion;
        private String resumenProfesional;
        private List<String> habilidadesTecnicas = new ArrayList<>();
        private List<ExperienciaParseada> experienciaLaboral = new ArrayList<>();
        private List<String> educacion = new ArrayList<>();

        public String getNombre() { return nombre; }
        public void setNombre(String nombre) { this.nombre = nombre; }
        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }
        public String getTelefono() { return telefono; }
        public void setTelefono(String telefono) { this.telefono = telefono; }
        public String getLinkedin() { return linkedin; }
        public void setLinkedin(String linkedin) { this.linkedin = linkedin; }
        public String getGithub() { return github; }
        public void setGithub(String github) { this.github = github; }
        public String getDireccion() { return direccion; }
        public void setDireccion(String direccion) { this.direccion = direccion; }
        public String getResumenProfesional() { return resumenProfesional; }
        public void setResumenProfesional(String resumenProfesional) { this.resumenProfesional = resumenProfesional; }
        public List<String> getHabilidadesTecnicas() { return habilidadesTecnicas; }
        public void setHabilidadesTecnicas(List<String> habilidadesTecnicas) { this.habilidadesTecnicas = habilidadesTecnicas; }
        public List<ExperienciaParseada> getExperienciaLaboral() { return experienciaLaboral; }
        public void setExperienciaLaboral(List<ExperienciaParseada> experienciaLaboral) { this.experienciaLaboral = experienciaLaboral; }
        public List<String> getEducacion() { return educacion; }
        public void setEducacion(List<String> educacion) { this.educacion = educacion; }
    }

    public static class ExperienciaParseada {
        private String empresa;
        private String puesto;
        private String periodo;

        public String getEmpresa() { return empresa; }
        public void setEmpresa(String empresa) { this.empresa = empresa; }
        public String getPuesto() { return puesto; }
        public void setPuesto(String puesto) { this.puesto = puesto; }
        public String getPeriodo() { return periodo; }
        public void setPeriodo(String periodo) { this.periodo = periodo; }
    }
}