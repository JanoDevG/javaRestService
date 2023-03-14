package cl.janodevg.restService.services;

import cl.janodevg.restService.entities.models.User;
import cl.janodevg.restService.repository.UserRepository;
import cl.janodevg.restService.services.exceptions.EmailAlreadyExistsException;
import cl.janodevg.restService.services.exceptions.EmailNotValidException;
import cl.janodevg.restService.services.exceptions.ResourceNotFoundException;
import cl.janodevg.restService.services.exceptions.WeakPasswordException;
import cl.janodevg.restService.utils.EmailValidator;
import cl.janodevg.restService.utils.PasswordValidator;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    public List<User> findAllUsers() {
        return repository.findAll();
    }

    public User findByEmail(String email) throws ResourceNotFoundException {
        return repository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException(
                "no se encuentra registrado algún usuario con el email: ".concat(email)));
    }

    public User createdUser(User user) throws EmailNotValidException, WeakPasswordException,
            EmailAlreadyExistsException {
        if (EmailValidator.isValid(user.getEmail())) { // verify email format
            if (repository.findByEmail(user.getEmail()).isPresent()) {
                throw new EmailAlreadyExistsException("el correo informado: ".concat(user.getEmail())
                        .concat(" ya se encuentra registrado."));
            }
            if (!PasswordValidator.isValid(user.getPassword())) { // verify password
                throw new WeakPasswordException("contraseña no cumple formato mínimo deseado.");
            }
            return repository.save(user);
        } else {
            throw new EmailNotValidException("formato de correo no es válido.");
        }
    }

    public User updateUser(User user, String email) throws EmailNotValidException, WeakPasswordException, ResourceNotFoundException {
        Optional<User> optionalUser = repository.findByEmail(email);
        if (optionalUser.isPresent()) {
            optionalUser.get().setName(user.getName());
            // update email flow
            if (!user.getEmail().equals(optionalUser.get().getEmail())) { //verify updating email
                if (EmailValidator.isValid(user.getEmail())) { // verify email format
                    optionalUser.get().setEmail(user.getEmail());
                } else {
                    throw new EmailNotValidException("formato de correo no es válido.");
                }
            }
            // update password flow
            if (!user.getPassword().equals(optionalUser.get().getPassword())) { // verify updating password
                if (PasswordValidator.isValid(user.getPassword())) { // verify a valid password
                    optionalUser.get().setPassword(user.getPassword());
                } else {
                    throw new WeakPasswordException("La contraseña nueva es muy débil.");
                }
            }
            return repository.save(optionalUser.get());
        } else {
            throw new ResourceNotFoundException("no se registra ningún usuario con el email: "
                    .concat(user.getEmail()));
        }
    }

    public void deleteUser(String email) {
        Optional<User> optionalUser = repository.findByEmail(email);
        if (optionalUser.isPresent()) {
            repository.delete(optionalUser.get());
        } else {
            throw new ResourceNotFoundException("No se encuentra registrado algún usuario con el email: "
                    .concat(email));
        }
    }

}