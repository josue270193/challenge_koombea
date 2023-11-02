package app.josue.challenge_koombea.config;

import app.josue.challenge_koombea.handler.ScrapperHandler;
import app.josue.challenge_koombea.handler.UserHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;

@Configuration
public class RouteConfig {

  @Bean
  public RouterFunction<ServerResponse> userRoutes(UserHandler userHandler) {
    return RouterFunctions
        .route(POST("/user/register").and(accept(MediaType.APPLICATION_JSON)), userHandler::register)
        .andRoute(POST("/user/login").and(accept(MediaType.APPLICATION_JSON)), userHandler::login)
        .andRoute(GET("/user/info").and(accept(MediaType.APPLICATION_JSON)), userHandler::getInfo)
        ;
  }

  @Bean
  public RouterFunction<ServerResponse> scrapperRoutes(ScrapperHandler scrapperHandler) {
    return RouterFunctions
        .route(POST("/scrapper/search").and(accept(MediaType.APPLICATION_JSON)), scrapperHandler::search)
        .andRoute(POST("/scrapper/add").and(accept(MediaType.APPLICATION_JSON)), scrapperHandler::add)
        ;
  }

}
