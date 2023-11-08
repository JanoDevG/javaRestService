package cl.janodevg.restService.utils;

import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ObtainRequestContextTest {

    @Mock
    private HttpServletRequest httpServletRequest;

    @BeforeEach
    public void setUp() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        ServletRequestAttributes attributes = new ServletRequestAttributes(request);
        RequestContextHolder.setRequestAttributes(attributes);
    }

    @Test
    public void whenObtainRequestAuthorization_thenShouldReturnAuthorizationHeader() {
        String expectedAuthorization = "Bearer token";
        when(httpServletRequest.getHeader("Authorization")).thenReturn(expectedAuthorization);

        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(httpServletRequest));

        String authorization = ObtainRequestContext.obtainRequestAuthorization();

        assertEquals(expectedAuthorization, authorization);
    }

    @Test
    public void whenRequestAuthorizationHeaderIsNull_thenShouldReturnNull() {
        when(httpServletRequest.getHeader("Authorization")).thenReturn(null);

        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(httpServletRequest));

        String authorization = ObtainRequestContext.obtainRequestAuthorization();

        assertNull(authorization);
    }
}
