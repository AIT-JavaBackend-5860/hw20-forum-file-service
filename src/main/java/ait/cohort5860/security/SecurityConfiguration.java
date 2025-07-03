package ait.cohort5860.security;

import ait.cohort5860.accounting.model.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.expression.WebExpressionAuthorizationManager;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final CustomWebSecurity webSecurity;

    @Bean
    SecurityFilterChain getSecurityFilterChain(HttpSecurity http) throws Exception {

        http.httpBasic(Customizer.withDefaults()); // включение базоваой дефл.авторизации
        http.csrf(csrf -> csrf.disable()); // кроссайтовая защита, не https использовать нельзя

        http.cors(Customizer.withDefaults());
        http.authorizeHttpRequests( // снятие защиты

                authorize -> authorize
                        // .anyRequest().permitAll() - все разрешить(к примеру)
                        .requestMatchers(HttpMethod.POST, "/account/register", "/forum/posts/**") // на регистрацию
                        .permitAll() // пустить всех

                        .requestMatchers("/account/user/{login}/role/{role}") // добавлять роли
                        .hasRole(Role.ADMINISTRATOR.name()) // должен юзер должен иметь роль админа

                        .requestMatchers(HttpMethod.PATCH, "/account/user/{login}", "/forum/post/{id}/comment/{login}") // апдейтить можно только свой профиль
                        .access(new WebExpressionAuthorizationManager("#login == authentication.name"))

                        .requestMatchers(HttpMethod.POST, "/forum/post/{author}") // только автор от своего имени может добавлять пост
                        .access(new WebExpressionAuthorizationManager("#author == authentication.name"))

                        //.requestMatchers(HttpMethod.PATCH, "/forum/post/{id}/comment/{author}") // только автор от своего имени может создавать/изменять комментарии
                        //.access(new WebExpressionAuthorizationManager("#author == authentication.name"))

                        //.requestMatchers(HttpMethod.PATCH, "/forum/post/{postId}/comment/{commenter}") // только под своим именем комментатор может оставить комментарий
                        //.access(new WebExpressionAuthorizationManager("#commenter == authentication.name"))

                        .requestMatchers(HttpMethod.DELETE, "/account/user/{login}")
                        .access(new WebExpressionAuthorizationManager("#login == authentication.name or hasRole('ADMINISTRATOR')"))


                        .requestMatchers(HttpMethod.PATCH, "/forum/post/{id}")
                        .access(((authentication, context) ->
                                new AuthorizationDecision(webSecurity.checkPostAuthor(context.getVariables().get("id"),
                                        authentication.get().getName()))))

                        .requestMatchers(HttpMethod.DELETE, "/forum/post/{id}")
                        .access((authentication, context) -> {
                            boolean isAuthor = webSecurity.checkPostAuthor(context.getVariables().get("id"),
                                    authentication.get().getName());
                            boolean isModerator = context.getRequest().isUserInRole(Role.MODERATOR.name());
                            return new AuthorizationDecision(isAuthor || isModerator);
                        })

                        .anyRequest() // все
                        .authenticated()); // проверяется



            return http.build();
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }



    @Bean
    UrlBasedCorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("https://example.com"));
        configuration.setAllowedOrigins(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("GET","POST","PUT","DELETE","OPTIONS","PATCH","HEAD"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

}
