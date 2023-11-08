package cl.janodevg.restService.services;

import cl.janodevg.restService.entities.models.User;
import cl.janodevg.restService.repository.UserRepository;
import cl.janodevg.restService.common.exceptions.EmailAlreadyExistsException;
import cl.janodevg.restService.common.exceptions.EmailNotValidException;
import cl.janodevg.restService.common.exceptions.ResourceNotFoundException;
import cl.janodevg.restService.common.exceptions.WeakPasswordException;
import cl.janodevg.restService.common.validations.EmailValidationAnnotation;
import cl.janodevg.restService.utils.ObtainRequestContext;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImplement implements UserService {

    @Autowired
    @SuppressWarnings("unused")
    private UserRepository repository;

    @Transactional(readOnly = true)
    public User findUserByEmail(@NonNull @EmailValidationAnnotation String email) throws ResourceNotFoundException {
        return repository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException(
                "Nothing user register with email: ".concat(email)));
    }

    @Transactional
    public User createUser(@NonNull @Valid User user) throws EmailNotValidException, WeakPasswordException,
            EmailAlreadyExistsException {
        if (repository.findByEmail(user.getEmail()).isPresent()) {
            throw new EmailAlreadyExistsException("The email informed: ".concat(user.getEmail())
                    .concat(" is already registered"));
        }
        user.setCreated(LocalDateTime.now().toString());
        user.setModified(LocalDateTime.now().toString());
        user.setLastLogin(LocalDateTime.now().toString());
        user.setJWT(ObtainRequestContext.obtainRequestAuthorization());
        user.setIsActive(true);
        user.getPhones().get(0).setUser(user);
        return repository.save(user);
    }

    @Transactional
    public User updateUser(@NonNull @Valid User user, @EmailValidationAnnotation String email) throws EmailNotValidException, WeakPasswordException,
            ResourceNotFoundException {
        Optional<User> optionalUser = repository.findByEmail(email);
        if (optionalUser.isPresent()) {
            optionalUser.get().setName(user.getName());
            // update email flow
            if (!user.getEmail().equals(optionalUser.get().getEmail())) { //verify updating email
                optionalUser.get().setEmail(user.getEmail());
            }
            // update password flow
            if (!user.getPassword().equals(optionalUser.get().getPassword())) { // verify updating password
                optionalUser.get().setPassword(user.getPassword());
            }
            updateDateLogin(user);
            optionalUser.get().setIsActive(user.isActive());
            optionalUser.get().setIsActive(user.isActive());
            optionalUser.get().setPhones(user.getPhones());
            user.setModified(LocalDateTime.now().toString());
            user.setJWT(ObtainRequestContext.obtainRequestAuthorization());
            return repository.save(optionalUser.get());
        } else {
            throw new ResourceNotFoundException("User not found with email:  "
                    .concat(user.getEmail()));
        }
    }

    @Transactional
    public void deleteUser(@EmailValidationAnnotation String email) throws ResourceNotFoundException {
        Optional<User> optionalUser = repository.findByEmail(email);
        if (optionalUser.isPresent()) {
            if (!optionalUser.get().isActive()){
                throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "User is already inactive." );
            }
            optionalUser.get().setIsActive(false);
        } else {
            throw new ResourceNotFoundException("Nothing user register with email: "
                    .concat(email));
        }
    }

    private void updateDateLogin(User user) {
        user.setLastLogin(LocalDateTime.now().toString());
    }

}
