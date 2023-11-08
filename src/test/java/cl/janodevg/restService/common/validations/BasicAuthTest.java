package cl.janodevg.restService.common.validations;

import cl.janodevg.restService.common.exceptions.UnauthorizedException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.client.HttpClientErrorException;

import java.util.Base64;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class BasicAuthTest {

    @InjectMocks
    private BasicAuth basicAuth;

    @BeforeEach
    public void setUp() {
        ReflectionTestUtils.setField(basicAuth, "user", "validUser");
        ReflectionTestUtils.setField(basicAuth, "password", "validPassword");
    }

    @Test
    public void whenAuthorizationHeaderMissing_thenThrowUnauthorized() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        ServletRequestAttributes attributes = new ServletRequestAttributes(request);
        RequestContextHolder.setRequestAttributes(attributes);

        assertThrows(HttpClientErrorException.class, () -> basicAuth.filterRequest());
    }

    @Test
    public void whenAuthorizationHeaderNotBasic_thenThrowBadRequest() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("Authorization", "Bearer token");
        ServletRequestAttributes attributes = new ServletRequestAttributes(request);
        RequestContextHolder.setRequestAttributes(attributes);

        assertThrows(HttpClientErrorException.class, () -> basicAuth.filterRequest());
    }

    @Test
    public void whenCredentialsAreInvalid_thenThrowUnauthorizedException() {
        String encodedCredentials = Base64.getEncoder().encodeToString("invalidUser:invalidPassword".getBytes());
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("Authorization", "Basic " + encodedCredentials);
        ServletRequestAttributes attributes = new ServletRequestAttributes(request);
        RequestContextHolder.setRequestAttributes(attributes);

        assertThrows(UnauthorizedException.class, () -> basicAuth.filterRequest());
    }

    @Test
    public void whenCredentialsAreValid_thenDoNotThrowException() {
        String encodedCredentials = Base64.getEncoder().encodeToString("validUser:validPassword".getBytes());
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("Authorization", "Basic " + encodedCredentials);
        ServletRequestAttributes attributes = new ServletRequestAttributes(request);
        RequestContextHolder.setRequestAttributes(attributes);

        assertDoesNotThrow(() -> basicAuth.filterRequest());
    }

}
