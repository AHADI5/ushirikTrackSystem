package com.ushirikeduc.app.filter;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Predicate;

@Component
public class RouteValidator {
    /*
    * It simply contains a list of open routes strings and checks
    *  if the current request URI is not in the openApiEndpoints list.
    * If not — then the token definitely must be present in the request.
    * Otherwise, — 401 Unauthorized!
    * */
    public  static final List<String> openApiEndpoints = List.of(
            "/api/v1/auth/admin",
            "/api/v1/auth/authenticate",
            "/api/v1/school/schoolAdmin",
            "/eureka"
    );

    public Predicate<ServerHttpRequest > isSecured =
            request -> openApiEndpoints
                    .stream()
                    .noneMatch(uri -> request.getURI().getPath().contains(uri));
}
