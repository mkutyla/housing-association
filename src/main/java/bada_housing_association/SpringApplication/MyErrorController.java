package bada_housing_association.SpringApplication;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

@Controller
public class MyErrorController implements ErrorController {
    @RequestMapping("/error")
    public String handleError(HttpServletRequest request) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        if (status != null) {
            Integer statusCode = Integer.valueOf(status.toString());
            if (statusCode == HttpStatus.FORBIDDEN.value()) {
                return "errors/403";
            } else if (statusCode == HttpStatus.NOT_FOUND.value()) {
                return "errors/404";
            } else if (statusCode == HttpStatus.CONFLICT.value()) {
                return "errors/409";
            } else if (statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
                return "errors/500";
            } else if (statusCode == HttpStatus.GATEWAY_TIMEOUT.value()) {
                return "errors/504";
            } else {
                return "errors/other";
            }
        }
        return "errors/other";
    }

    public static String error500(Model model) {
        model.addAttribute("description",
                "Utracono połączenie z bazą danych");
        return "errors/500_custom";
    }

    public static String error409(Model model) {
        model.addAttribute("description",
                "Naruszono integralność bazy danych - nie można usunąć rekordu");
        return "errors/409_custom";
    }
}
