package se1app.facade;

import io.javalin.Javalin;
import io.javalin.http.staticfiles.Location;
import java.nio.file.Paths;
import io.javalin.plugin.rendering.vue.VueComponent;


/** The Javalin web server. */
public class Webserver {

  private Javalin _server;  // Javalin web server process.
  private int _port;        // Web server port.


  /** Create a new web server.
   * @param port Web server port.
   * @param htmlDir HTML directory for static content delivery. May be set to 'null' to disable it! */
  public Webserver(int port, String htmlDir) {
    _port = port;
    _server = Javalin.create(
      config -> {
        config.enableWebjars();
        config.enableCorsForAllOrigins();
        if (htmlDir != null) {
          var staticPath = Paths.get(System.getProperty("user.dir"), htmlDir).toString();
          config.addStaticFiles(staticPath, Location.EXTERNAL);
        }
        config.defaultContentType = "application/json";
        config.showJavalinBanner = false;
      }
    );
    defineRoutes();
  }


  /** Start the web server. */
  public void start() {
    _server.start(_port);
  }


  /** Stop the web server. */
  public void stop() {
    _server.stop();
  }


  /** Define backend routes (REST endpoints). */
  private void defineRoutes() {
    new CustomerController(_server);
    //_server.get("/", ctx -> ctx.result("Hello World"));
    new SpaceController(_server);
    _server.get("/", new VueComponent("<landing-comp></landing-comp>"));
    _server.get("/home/:user-id", new VueComponent("<home-comp></home-comp>"));
    _server.get("/test", ctx -> ctx.result("Hello World"));

  }
}
