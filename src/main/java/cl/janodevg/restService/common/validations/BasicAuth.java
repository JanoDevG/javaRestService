package cl.janodevg.restService.common.validations;

import cl.janodevg.restService.common.exceptions.UnauthorizedException;
import cl.janodevg.restService.utils.ObtainRequestContext;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.client.HttpClientErrorException;

import java.nio.charset.StandardCharsets;
import java.nio.file.AccessDeniedException;
import java.util.Base64;

@Component
public class BasicAuth {

    @Value("${authorizationProfile.user}")
    private String user;

    @Value("${authorizationProfile.password}")
    private String password;

    public void filterRequest() throws AccessDeniedException {
        String authorizationRequest = ObtainRequestContext.obtainRequestAuthorization();
        if (StringUtils.isEmpty(authorizationRequest)) {
            throw new HttpClientErrorException(HttpStatus.UNAUTHORIZED, "Credentials request is missing ");
        } else if (!authorizationRequest.startsWith("Basic ")) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Is required a Basic Auth.");
        }
        try {
            String credentials = new String(Base64.getDecoder()
                    .decode(ObtainRequestContext.obtainRequestAuthorization().replace("Basic ", "")), StandardCharsets.UTF_8);
            final String[] values = credentials.split(":", 2);
            if (values.length != 2 || !validateCredentials(values[0], values[1]))
                throw new UnauthorizedException("User not allowed.");
        } catch (IllegalArgumentException ex) {
            throw new AccessDeniedException("Error decoding token.");
        }
    }

    private boolean validateCredentials(String username, String password) {
        return this.user.equals(username) && this.password.equals(password);
    }
}
