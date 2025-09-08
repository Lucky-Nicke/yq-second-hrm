package Filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

@WebFilter(urlPatterns = "/*")
public class EncodingFilter implements Filter {
    private String Encoding;
    private FilterConfig FilterConfig;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.FilterConfig = filterConfig;
        this.Encoding = "UTF-8";
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        if (Encoding != null) {
            servletRequest.setCharacterEncoding(Encoding);
            servletResponse.setCharacterEncoding(Encoding);
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
        this.Encoding = null;
        this.FilterConfig = null;
    }
}
