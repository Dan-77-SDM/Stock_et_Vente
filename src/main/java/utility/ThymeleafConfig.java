package utility;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.ServletContext;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.thymeleaf.web.IWebExchange;
import org.thymeleaf.web.servlet.JakartaServletWebApplication;

public class ThymeleafConfig {

    private static final TemplateEngine templateEngine;

    static {
        templateEngine = new TemplateEngine();

        ClassLoaderTemplateResolver resolver = new ClassLoaderTemplateResolver();
        resolver.setPrefix("templates/");  // Chemin relatif dans le classpath (resources/templates)
        resolver.setSuffix(".html");
        resolver.setTemplateMode("HTML");
        resolver.setCharacterEncoding("UTF-8");
        resolver.setCacheable(false);

        templateEngine.setTemplateResolver(resolver);
    }

    public static void render(HttpServletRequest request, HttpServletResponse response, String templateName) {
        try {
            ServletContext servletContext = request.getServletContext();

            JakartaServletWebApplication app = JakartaServletWebApplication.buildApplication(servletContext);
            IWebExchange webExchange = app.buildExchange(request, response);

            WebContext webContext = new WebContext(webExchange);

            response.setContentType("text/html;charset=UTF-8");

            templateEngine.process(templateName, webContext, response.getWriter());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
