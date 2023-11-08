package cl.janodevg.restService.utils;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public final class ObtainRequestContext {

    ObtainRequestContext(){
        throw new UnsupportedOperationException("Utility class must not be instanced.");
    }

    public static String obtainRequestAuthorization(){
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        assert requestAttributes != null;
        HttpServletRequest request = requestAttributes.getRequest();
        return request.getHeader("Authorization");
    }
}
