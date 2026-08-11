package com.example.temaJar.controllers;

import jakarta.servlet.http.HttpServletRequest; // SOLO este import de Request
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller // Importante para que Spring encuentre este controlador
public class ErroresController implements ErrorController {

    @RequestMapping(value = "/error", method = { RequestMethod.GET, RequestMethod.POST })
    public ModelAndView renderErrorPage(HttpServletRequest httpRequest) {

        ModelAndView errorPage = new ModelAndView("error");
        String errorMsg = "";
        int httpErrorCode = getErrorCode(httpRequest);

        switch (httpErrorCode) {
            case 400:
                errorMsg = "El recurso solicitado no existe.";
                break;
            case 401:
                errorMsg = "No se encuentra autorizado.";
                break;
            case 403:
                errorMsg = "No tiene permisos para acceder al recurso.";
                break;
            case 404:
                errorMsg = "El recurso solicitado no fue encontrado.";
                break;
            case 500:
                errorMsg = "Ocurrió un error interno.";
                break;
            default:
                errorMsg = "Ocurrió un error inesperado.";
                break;
        }

        errorPage.addObject("codigo", httpErrorCode);
        errorPage.addObject("mensaje", errorMsg);
        return errorPage;
    }

    private int getErrorCode(HttpServletRequest httpRequest) {
        // CAMBIO CLAVE: Usar jakarta en lugar de javax
        Object status = httpRequest.getAttribute("jakarta.servlet.error.status_code");
        return status != null ? (Integer) status : 0;
    }
}
