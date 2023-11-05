package br.com.fiap.epictask.Marcas;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.fiap.epictask.user.User;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/marca")
public class MarcasController {

    @Autowired
    MarcasService service;

    @Autowired
    MessageSource message;

    @GetMapping
    public String index(Model model, @AuthenticationPrincipal OAuth2User user){
        model.addAttribute("username", user.getAttribute("name"));
        model.addAttribute("avatar_url", user.getAttribute("avatar_url"));
        model.addAttribute("marcas", service.findAll());
        return "marca/index";
    }

    @GetMapping("/delete/{id}")
    public String delete (@PathVariable Long id, RedirectAttributes redirect) {
        if (service.delete(id)) {
            redirect.addFlashAttribute("success", "marca apagada com sucesso");
            return "redirect:/marca";
        } else {
            redirect.addFlashAttribute("error", "marca Não encontrada");
        }
        return "redirect:/marca";
    }

        @GetMapping("new")
    public String form(Marcas marca, Model model, @AuthenticationPrincipal OAuth2User user){
        model.addAttribute("username", user.getAttribute("name"));
        model.addAttribute("avatar_url", user.getAttribute("avatar_url"));
        return "marca/form";
    }

    @PostMapping
    public String create(@Valid Marcas marca,  RedirectAttributes redirect) {
        service.save(marca);
        redirect.addFlashAttribute("success", "marca salva com sucesso");
        return "redirect:/marca";

    }

        @GetMapping("/inc/{id}")
    public String incrementStatus(@PathVariable Long id, RedirectAttributes redirect){
        if (!service.increment(id)){
            redirect.addFlashAttribute("error", "Erro ao alterar status da marca");
        }
        return "redirect:/marca";
    }  
    
    @GetMapping("/dec/{id}")
    public String decrementStatus(@PathVariable Long id, RedirectAttributes redirect){
        if (!service.decrement(id)){
            redirect.addFlashAttribute("error", "Erro ao alterar status da marca");
        }
        return "redirect:/marca";
    }

    @GetMapping("/catch/{id}")
    public String catchMarca(@PathVariable Long id, @AuthenticationPrincipal OAuth2User user){
        service.catchMarca(id, User.convert(user));
        return "redirect:/marca";
    }



}
