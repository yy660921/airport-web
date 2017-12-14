package airport.web.demo;

import com.google.common.collect.Lists;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppClassLoader;
import org.eclipse.jetty.webapp.WebAppContext;

import java.util.List;

/**
 * 使用Jetty运行调试Web应用, 在Console输入回车快速重新加载应用.
 *
 * @author calvin
 */
public class DemoServer {

    public static final String CONTEXT = "/";

    public static final int PORT = 8080;
    public static final String[]
        TLD_JAR_NAMES =
        new String[]{"spring-webmvc", "shiro-web", "rapid", "jstl"};

    public static Server createServerInSource(int port, String contextPath) {
        Server server = new Server(port);
        server.setStopAtShutdown(true);
        WebAppContext webContext = new WebAppContext("src/main/webapp", contextPath);
        webContext.setAttribute("org.eclipse.jetty.server.webapp.ContainerIncludeJarPattern",
                                ".*/[^/]*servlet-api-[^/]*\\.jar$|.*/javax.servlet.jsp.jstl-.*\\.jar$|.*/[^/]*taglibs.*\\.jar$");
        org.eclipse.jetty.webapp.Configuration.ClassList
            classlist =
            org.eclipse.jetty.webapp.Configuration.ClassList
                .setServerDefault(server);
        classlist.addBefore("org.eclipse.jetty.webapp.JettyWebXmlConfiguration",
                            "org.eclipse.jetty.annotations.AnnotationConfiguration");
        webContext.setDefaultsDescriptor("org/eclipse/jetty/webapp/webdefault.xml");
        server.setHandler(webContext);
        return server;
    }

    /**
     * 设置除jstl-*.jar外其他含tld文件的jar包的名称. jar名称不需要版本号，如sitemesh, shiro-web
     */
    public static void setTldJarNames(Server server, String... jarNames) {
        WebAppContext context = (WebAppContext) server.getHandler();
        List<String>
            jarNameExprssions =
            Lists.newArrayList(".*/jstl-[^/]*\\.jar$", ".*/.*taglibs[^/]*\\.jar$");
        for (String jarName : jarNames) {
            jarNameExprssions.add(".*/" + jarName + "-[^/]*\\.jar$");
        }
        context.setAttribute("org.eclipse.jetty.server.webapp.ContainerIncludeJarPattern",
                             StringUtils.join(jarNameExprssions, '|'));
    }

    /**
     * Equivalent to JettyFactory.reloadContext from springside-test.
     */
    public static void reloadContext(Server server) throws Exception {
        WebAppContext context = (WebAppContext) server.getHandler();
        System.out.println("[INFO] Application reloading");
        context.stop();
        WebAppClassLoader classLoader = new WebAppClassLoader(context);
        classLoader.addClassPath("target/classes");
        classLoader.addClassPath("target/test-classes");
        context.setClassLoader(classLoader);
        context.start();
        System.out.println("[INFO] Application reloaded");
    }

    public static void main(String[] args) throws Exception {
        org.eclipse.jetty.util.log.Log.setLog(new org.eclipse.jetty.util.log.Slf4jLog());
        // 设定Spring的profile
        System.setProperty("spring.profiles.active", "production");
        // 启动Jetty
        Server server = createServerInSource(PORT, CONTEXT);
        setTldJarNames(server, TLD_JAR_NAMES);
        System.out.println();
        try {
            server.start();
            System.out.println(
                "[INFO] Server running at http://localhost:" + PORT + CONTEXT);
            System.out.println("[HINT] Hit Enter to reload the application quickly");
            // 等待用户输入回车重载应用.
            while (true) {
                char c = (char) System.in.read();
                if (c == '\n') {
                    reloadContext(server);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }
}
