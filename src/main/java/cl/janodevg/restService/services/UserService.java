package cl.janodevg.restService.services;

import cl.janodevg.restService.entities.models.User;
import cl.janodevg.restService.common.validations.EmailValidationAnnotation;
import jakarta.validation.Valid;
import org.springframework.lang.NonNull;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Validated
public interface UserService {

    List<User> findAllUsers();
    User findUserByEmail(@NonNull @EmailValidationAnnotation String email);
    User createUser(@NonNull @Valid User user);
    User updateUser(@NonNull @Valid User user, @EmailValidationAnnotation String email);
    void deleteUser(@EmailValidationAnnotation String email);


}
