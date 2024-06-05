import io.ktor.application.*;
import io.ktor.features.*;
import io.ktor.html.*;
import io.ktor.http.content.*;
import io.ktor.request.*;
import io.ktor.response.*;
import io.ktor.routing.*;
import kotlinx.html.*;
import io.ktor.routing.StaticContentRoute;
import repository.DatabaseUserRepository;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import org.jetbrains.annotations.NotNull;
import java.io.File;
import java.sql.SQLException;

public class Application {
    public static void main(String[] args) {
        io.ktor.server.netty.EngineMain.main(args);
    }

    public static void module(Application application) {
        DatabaseUserRepository userRepository;
        try {
            userRepository = new DatabaseUserRepository();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to initialize DatabaseUserRepository", e);
        }

        application.install(DefaultHeaders.class);
        application.install(CallLogging.class);

        application.routing(routing -> {
            routing.get("/login", context -> {
                ApplicationCall call = context.getCall();
                call.respondHtml(html -> {
                    html.head(() -> html.title("Iniciar Sesión"));
                    html.body(() -> {
                        html.h2("Iniciar Sesión");
                        html.form("/loginUser", FormMethod.post, form -> {
                            form.input(InputType.text, null, null, input -> {
                                input.name("username");
                                input.placeholder("Nombre de Usuario");
                                input.required(true);
                            });
                            form.input(InputType.password, null, null, input -> {
                                input.name("password");
                                input.placeholder("Contraseña");
                                input.required(true);
                            });
                            form.button(ButtonType.submit, button -> button.text("Iniciar Sesión"));
                        });
                    });
                });
            });

            routing.post("/loginUser", context -> {
                ApplicationCall call = context.getCall();
                Parameters params = call.receiveParameters();
                String username = params.getOrFail("username");
                String password = params.getOrFail("password");

                boolean loggedIn = userRepository.authenticate(username, password);
                if (loggedIn) {
                    call.respondRedirect("/principal.html");
                } else {
                    call.respondRedirect("/loginFailure");
                }
            });

            routing.get("/loginSuccess", context -> {
                ApplicationCall call = context.getCall();
                call.respondHtml(html -> {
                    html.head(() -> html.title("Inicio de Sesión Exitoso"));
                    html.body(() -> {
                        html.h1("Inicio de Sesión Exitoso");
                        html.p("¡Bienvenido de vuelta!");
                    });
                });
            });

            routing.get("/loginFailure", context -> {
                ApplicationCall call = context.getCall();
                call.respondHtml(html -> {
                    html.head(() -> html.title("Inicio de Sesión Fallido"));
                    html.body(() -> {
                        html.h1("Inicio de Sesión Fallido");
                        html.p("Credenciales inválidas. Por favor, inténtalo de nuevo.");
                    });
                });
            });
            routing.route("/", () -> {
                routing.static("/", "resources");
                routing.defaultResource("login.html", "resources");
            });                    
        });
    }
}
