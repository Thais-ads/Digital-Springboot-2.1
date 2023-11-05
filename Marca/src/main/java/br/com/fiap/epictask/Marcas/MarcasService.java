package br.com.fiap.epictask.Marcas;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import br.com.fiap.epictask.user.User;
import br.com.fiap.epictask.user.UserService;

@Service
public class MarcasService {

    @Autowired
    MarcasRepository repository;

    @Autowired
    UserService userService;

    public List<Marcas> findAll(){
        return repository.findAll();
    }

    public boolean delete(Long id) {
        var marca = repository.findById(id);
        if(marca.isEmpty()) return false;

        repository.deleteById(id);
        return true;
    }

    public void save(Marcas marca) {
        repository.save(marca);
    }

    public boolean increment(Long id) {
        var optionalmarca = repository.findById(id);

        if (optionalmarca.isEmpty()) return false;

        var marca = optionalmarca.get();

        if (marca.getStatus() == null) marca.setStatus(0);
        if (marca.getStatus() == 100) return false;

        marca.setStatus(marca.getStatus() + 10);

        if (marca.getStatus() == 100){
            var user = (OAuth2User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            userService.addScore(marca.getScore(), Long.valueOf(user.getName()));
        }

        repository.save(marca);
        return true;

    }

    public boolean decrement(Long id) {
        var optionalmarca = repository.findById(id);

        if (optionalmarca.isEmpty()) return false;

        var marca = optionalmarca.get();

        if (marca.getStatus() == null || marca.getStatus() == 0) return false;

        marca.setStatus(marca.getStatus() - 10);
        repository.save(marca);
        return true;
    }

    public boolean catchMarca(Long id, User user) {
        var optionalTask = repository.findById(id);

        if (optionalTask.isEmpty()) return false;

        var marca = optionalTask.get();

        if (marca.getUser() != null) return false;

        marca.setUser(user);

        repository.save(marca);
        return true;
        
    }
    
}
