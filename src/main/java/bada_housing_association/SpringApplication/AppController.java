package bada_housing_association.SpringApplication;

import bada_housing_association.Database.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

import static bada_housing_association.SpringApplication.MyErrorController.error409;
import static bada_housing_association.SpringApplication.MyErrorController.error500;

@Configuration
public class AppController implements WebMvcConfigurer {

    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/index").setViewName("index");
        registry.addViewController("/").setViewName("index");
        registry.addViewController("/login").setViewName("login");
    }

    @Controller
    public class DashboardController {

        @Autowired
        private SpoldzielnieDAO spoldzielniaDAO;

        @Autowired
        private AdresDAO adresDAO;

        @Autowired
        private PracownikDAO pracownikDAO;

        @Autowired
        private StanowiskoDAO stanowiskaDAO;

        @RequestMapping(value = {"/main"})
        public String defaultAfterLogin(HttpServletRequest request) {
            if (request.isUserInRole("ADMIN")) {
                return "admin/main";
            } else if (request.isUserInRole("USER")) {
                return "user/main";
            } else {
                return "/index";
            }
        }

        @RequestMapping(value = {"/spoldzielnia"})
        public String viewSpoldzielniaPage(Model model, HttpServletRequest request) {
            try {
                List<Spoldzielnia> spoldzielnie = spoldzielniaDAO.list(true);
                List<Adres> adresy = adresDAO.list(true);

                ArrayList<String> adresyString = new ArrayList<>();
                for (Spoldzielnia spoldzielnia : spoldzielnie) {
                    adresyString.add(adresy.get(spoldzielnia.getNr_adresu() - 1).toString());
                }
                model.addAttribute("spoldzielnie", spoldzielnie);
                model.addAttribute("adresy", adresyString);
                model.addAttribute("length", spoldzielnie.size());

                if (request.isUserInRole("ADMIN")) {
                    return "admin/spoldzielnia_view";
                } else if (request.isUserInRole("USER")) {
                    return "user/spoldzielnia_view";
                } else {
                    return "errors/404";
                }
            } catch (DataAccessException dae) {
                return error500(model);
            }
        }

        @RequestMapping(value = {"/adres"})
        public String viewAdresPage(Model model, HttpServletRequest request) {
            try {
                List<Adres> adresy = adresDAO.list(true);
                model.addAttribute("adresy", adresy);
                if (request.isUserInRole("ADMIN")) {
                    return "admin/adres_view";
                } else if (request.isUserInRole("USER")) {
                    return "user/adres_view";
                } else {
                    return "errors/404";
                }
            } catch (DataAccessException dae) {
                return error500(model);
            }
        }

        @RequestMapping(value = {"/pracownik"})
        public String viewPracownikPage(Model model, HttpServletRequest request) {
            try {
                List<Pracownik> pracownicy = pracownikDAO.list(true);
                model.addAttribute("pracownicy", pracownicy);
                if (request.isUserInRole("ADMIN")) {
                    return "admin/pracownik_view";
                } else if (request.isUserInRole("USER")) {
                    return "user/pracownik_view";
                } else {
                    return "errors/404";
                }
            } catch (DataAccessException dae) {
                return error500(model);
            }
        }

        @RequestMapping(value = {"/stanowisko"})
        public String viewStanowiskoPage(Model model, HttpServletRequest request) {
            try {
                List<Stanowisko> stanowiska = stanowiskaDAO.list(true);
                model.addAttribute("stanowiska", stanowiska);
                if (request.isUserInRole("ADMIN")) {
                    return "admin/stanowisko_view";
                } else if (request.isUserInRole("USER")) {
                    return "user/stanowisko_view";
                } else {
                    return "errors/404";
                }
            } catch (DataAccessException dae) {
                return error500(model);
            }
        }


        //        Dodawanie view
        @RequestMapping(value = {"/stanowisko/add"}, method = RequestMethod.GET)
        public String showNewStanowiskoForm(Model model) {
            model.addAttribute("stanowisko", new Stanowisko());
            return "admin/stanowisko_add"; // path cuz it's a form
        }

        @RequestMapping(value = {"/adres/add"}, method = RequestMethod.GET)
        public String showNewAdresForm(Model model) {
            Adres adres = new Adres();
            model.addAttribute("adres", adres);
            return "admin/adres_add"; // path cuz it's a form
        }

        @RequestMapping(value = {"/spoldzielnia/add"}, method = RequestMethod.GET)
        public String showNewSpoldzielniaForm(Model model) {
            try {
                Spoldzielnia spoldzielnia = new Spoldzielnia();
                model.addAttribute("spoldzielnia", spoldzielnia);
                List<Adres> adresy = adresDAO.list(true);
                model.addAttribute("adresy", adresy);
                return "admin/spoldzielnia_add"; // path cuz it's a form
            } catch (DataAccessException dae) {
                return error500(model);
            }
        }

        @RequestMapping(value = {"/pracownik/add"}, method = RequestMethod.GET)
        public String showNewPracownikForm(Model model) {
            try {
                model.addAttribute("pracownik", new Pracownik());
                List<Spoldzielnia> spoldzielnie = spoldzielniaDAO.list(true);
                List<String> spoldzielnieDisplay = new ArrayList<>();
                for (Spoldzielnia s : spoldzielnie) {
                    Adres adres = adresDAO.get(s.getNr_adresu());
                    spoldzielnieDisplay.add(s.getNazwa() + ", " + adres.toString());
                }
                model.addAttribute("spoldzielnieDisplay", spoldzielnieDisplay);
                model.addAttribute("spoldzielnie", spoldzielnie);
                model.addAttribute("adresy", adresDAO.list(true));
                model.addAttribute("stanowiska", stanowiskaDAO.list(true));

                return "admin/pracownik_add"; // path cuz it's a form
            } catch (DataAccessException dae) {
                return error500(model);
            }

        }

        // Dodawanie post

        @RequestMapping(value = "/adres/add", method = RequestMethod.POST)
        public String addAdres(Model model, @ModelAttribute("adres") Adres adres) {
            try {
                adresDAO.add(adres);
                return "redirect:/adres";
            } catch (DataAccessException dae) {
                return error500(model);
            }
        }

        @RequestMapping(value = "/spoldzielnia/add", method = RequestMethod.POST)
        public String saveSpoldzielnia(Model model, @ModelAttribute("spoldzielnia") Spoldzielnia spoldzielnia) {
            try {
                spoldzielniaDAO.add(spoldzielnia);
                return "redirect:/spoldzielnia";
            } catch (DataAccessException dae) {
                return error500(model);
            }
        }

        @RequestMapping(value = "/pracownik/add", method = RequestMethod.POST)
        public String addPracownik(Model model, @ModelAttribute("pracownik") Pracownik pracownik) {
            try {
                pracownikDAO.add(pracownik);
                return "redirect:/pracownik";
            } catch (DataAccessException dae) {
                return error500(model);
            }
        }

        @RequestMapping(value = "/stanowisko/add", method = RequestMethod.POST)
        public String addStanowisko(Model model, @ModelAttribute("stanowisko") Stanowisko stanowisko) {
            try {
                stanowiskaDAO.add(stanowisko);
                return "redirect:/stanowisko";
            } catch (DataAccessException dae) {
                return error500(model);
            }
        }

        // Edit view

        @RequestMapping(value = "/adres/edit/{id}")
        public String showEditAdresForm(Model model, @PathVariable(name = "id") int nr_adresu) {
            try {
                Adres adres = adresDAO.get(nr_adresu);
                model.addAttribute("adres", adres);
                return "admin/adres_edit"; // path cuz it's a form
            } catch (DataAccessException dae) {
                return error500(model);
            }
        }

        @RequestMapping(value = "/spoldzielnia/edit/{id}")
        public String showEditSpoldzielniaForm(Model model, @PathVariable(name = "id") int nr_spoldzielni) {
            try {
                Spoldzielnia spoldzielnia = spoldzielniaDAO.get(nr_spoldzielni);
                model.addAttribute("spoldzielnia", spoldzielnia);
                List<Adres> adresy = adresDAO.list(true);
                model.addAttribute("adresy", adresy);
                return "admin/spoldzielnia_edit"; // path cuz it's a form
            } catch (DataAccessException dae) {
                return error500(model);
            }
        }

        @RequestMapping(value = "/pracownik/edit/{id}")
        public String showEditPracownikForm(Model model, @PathVariable(name = "id") int nr_pracownika) {
            try {
                Pracownik pracownik = pracownikDAO.get(nr_pracownika);
                Spoldzielnia spoldzielnia = spoldzielniaDAO.get(pracownik.getNr_spoldzielni());
                Adres adres = adresDAO.get(spoldzielnia.getNr_adresu());
                String adresSpoldzielni = spoldzielnia.getNazwa() + ", " + adres;
                model.addAttribute("pracownik", pracownik);
                model.addAttribute("spoldzielnia", adresSpoldzielni);
                model.addAttribute("adresy", adresDAO.list(true));
                model.addAttribute("stanowiska", stanowiskaDAO.list(true));

                return "admin/pracownik_edit"; // path cuz it's a form
            } catch (DataAccessException dae) {
                return error500(model);
            }
        }

        @RequestMapping(value = "/stanowisko/edit/{id}")
        public String showEditStanowiskoForm(Model model, @PathVariable(name = "id") int nr_stanowiska, HttpServletRequest request) {
            try {
                Stanowisko stanowisko = stanowiskaDAO.get(nr_stanowiska);
                model.addAttribute("stanowisko", stanowisko);
                if (request.isUserInRole("ADMIN")) {
                    return "admin/stanowisko_edit";
                } else if (request.isUserInRole("USER")) {
                    return "user/stanowisko_edit";
                } else {
                    return "errors/404";
                }
            } catch (DataAccessException dae) {
                return error500(model);
            }
        }

        // Update

        @RequestMapping(value = "/adres/update", method = RequestMethod.POST)
        public String updateAdres(Model model, @ModelAttribute("adres") Adres adres) {
            try {
                adresDAO.update(adres);
                return "redirect:/adres";
            } catch (DataAccessException dae) {
                return error500(model);
            }
        }

        @RequestMapping(value = "/spoldzielnia/update", method = RequestMethod.POST)
        public String updateSpoldzielnia(Model model, @ModelAttribute("spoldzielnia") Spoldzielnia spoldzielnia) {
            try {
                spoldzielniaDAO.update(spoldzielnia);
                return "redirect:/spoldzielnia";
            } catch (DataAccessException dae) {
                return error500(model);
            }
        }

        @RequestMapping(value = "/pracownik/update", method = RequestMethod.POST)
        public String updatePracownik(Model model, @ModelAttribute("pracownik") Pracownik pracownik) {
            try {
                pracownikDAO.update(pracownik);
                return "redirect:/pracownik";
            } catch (DataAccessException dae) {
                return error500(model);
            }
        }

        @RequestMapping(value = "/stanowisko/update", method = RequestMethod.POST)
        public String updateStanowisko(Model model, @ModelAttribute("stanowisko") Stanowisko stanowisko) {
            try {
                stanowiskaDAO.update(stanowisko);
                return "redirect:/stanowisko";
            } catch (DataAccessException dae) {
                return error500(model);
            }
        }


        // Delete

        @RequestMapping(value = "/spoldzielnia/delete/{id}")
        public String deleteSpoldzielnia(Model model, @PathVariable(name = "id") int nr_spoldzielni) {
            try {
                spoldzielniaDAO.delete(nr_spoldzielni);
                return "redirect:/spoldzielnia";
            } catch (DataIntegrityViolationException dve) {
                return error409(model);
            } catch (DataAccessException dae) {
                return error500(model);
            }
        }

        @RequestMapping(value = "/adres/delete/{id}")
        public String deleteAdres(Model model, @PathVariable(name = "id") int nr_adresu) {
            try {
                adresDAO.delete(nr_adresu);
                return "redirect:/adres";
            } catch (DataIntegrityViolationException dve) {
                return error409(model);
            } catch (DataAccessException dae) {
                return error500(model);
            }
        }

        @RequestMapping(value = "/pracownik/delete/{id}")
        public String deletePracownik(Model model, @PathVariable(name = "id") int nr_pracownika) {
            try {
                pracownikDAO.delete(nr_pracownika);
                return "redirect:/pracownik";
            } catch (DataIntegrityViolationException dve) {
                return error409(model);
            } catch (DataAccessException dae) {
                return error500(model);
            }
        }

        @RequestMapping(value = "/stanowisko/delete/{id}")
        public String deleteStanowisko(Model model, @PathVariable(name = "id") int nr_stanowiska) {
            try {
                stanowiskaDAO.delete(nr_stanowiska);
                return "redirect:/stanowisko";
            } catch (DataIntegrityViolationException dve) {
                return error409(model);
            } catch (DataAccessException dae) {
                return error500(model);
            }
        }


    }

}
