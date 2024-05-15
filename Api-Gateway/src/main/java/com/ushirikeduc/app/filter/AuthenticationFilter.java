package com.ushirikeduc.app.filter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.util.UriComponentsBuilder;

import java.security.Key;
import java.util.function.Function;

@Component
@Slf4j
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {

    @Autowired
    private RouteValidator validator ;
    @Autowired
    private RestTemplate restTemplate;
    private static final String SECRET_KEY = "D76BA0C99405CDB23F0B18613970A7352A429E051344AF053523915D977B5DDDB5F5523800DCEEDF2317765ACEC9666EAE06975EE45CA9CD09DA200D209B24ADAF115AE937FD267E1C0AA7B76BDBC0EDE0A1FAB0AB4AC6BE3ABE2B4D8CED7A83205ABF3E8AAE0EEFFAF2F93DF9908C605654F31C686F4D05A5C2239F1770606C";
    public AuthenticationFilter(){
        super(Config.class);
    }
    @Override
    public GatewayFilter apply(Config config) {

        return ((exchange,chain) -> {
            //Contains header  ?

            if (validator.isSecured.test(exchange.getRequest())) {
                if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                    throw  new RuntimeException("missing authorization header");
                }
                String authHeader = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
                if (authHeader!=null && authHeader.startsWith("Bearer ")){
                    authHeader = authHeader.substring(7);
                }
                log.info("header" + authHeader);

                    RestTemplate restTemplate = new RestTemplate();
                // Define the base URL
                String baseUrl = "http://localhost:8850/api/v1/auth/validate";

                // Create HttpHeaders and set any custom headers (if needed)
                HttpHeaders headers = new HttpHeaders();
                headers.set("Accept", "application/json");

                // Build the complete URL with query parameters
                String url = UriComponentsBuilder.fromHttpUrl(baseUrl)
                        .queryParam("token", authHeader) // Add the token parameter
                        .toUriString();

                // Make the GET request
                ResponseEntity<String> response = restTemplate.exchange(
                        url,
                        HttpMethod.GET,
                        new HttpEntity<>(headers), // Include headers if necessary
                        String.class
                );

                //Passing user information in the request to the target microservice
                this.populateRequestWithHeaders(exchange,authHeader);
                // Get the response body
                String responseBody = response.getBody();

                //printing the response body
                log.info(responseBody);
            }
            return chain.filter(exchange);
        });
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .setSigningKey(getSignInkey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
    private Key getSignInkey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public void populateRequestWithHeaders(ServerWebExchange exchange , String token ) {
        exchange.getRequest().mutate()
                .header("username" , extractUsername(token))
                .header("token" , token)
                .build();
    }

    public static class Config{

    }
}
