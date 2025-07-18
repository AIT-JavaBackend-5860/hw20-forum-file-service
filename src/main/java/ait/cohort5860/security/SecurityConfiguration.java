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

        http.cors(Customizer.withDefaults()); // дефолтный бин добавить в настройки и все разрешить
        http.authorizeHttpRequests( // снятие защиты

                authorize -> authorize
                        // .anyRequest().permitAll() - все разрешить(к примеру)

                        // begin of hw_20 task1
                        .requestMatchers(HttpMethod.POST, "/files/upload").permitAll()
                        .requestMatchers(HttpMethod.GET, "/files/download/**").permitAll()
                        // end of hw_20 task1

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

                        // Удаление файла - только автор поста или модератор
                        .requestMatchers(HttpMethod.DELETE, "/forum/post/{id}")
                        .access((authentication, context) -> {
                            boolean isAuthor = webSecurity.checkPostAuthor(context.getVariables().get("id"),
                                    authentication.get().getName());
                            boolean isModerator = context.getRequest().isUserInRole(Role.MODERATOR.name());
                            return new AuthorizationDecision(isAuthor || isModerator);
                        })


                        // begin of hw_20 task2
                        // Получить список файлов для поста — только для аутентифицированных пользователей
                        .requestMatchers(HttpMethod.GET, "/forum/post/{id}/files")
                        .authenticated()

                        // Скачать файл — только для аутентифицированных пользователей
                        .requestMatchers(HttpMethod.GET, "/forum/file/{id}/download")
                        .authenticated()

                        // Загрузка файла к посту — только автор поста или модератор
                        .requestMatchers(HttpMethod.POST, "/forum/post/{id}/upload")
                        .access((authentication, context) -> {
                            boolean isAuthor = webSecurity.checkPostAuthor(context.getVariables().get("id"), authentication.get().getName());
                            boolean isModerator = context.getRequest().isUserInRole(Role.MODERATOR.name());
                            return new AuthorizationDecision(isAuthor || isModerator);
                        })

                        // Удаление файла — только автор поста-владельца файла или модератор
                        .requestMatchers(HttpMethod.DELETE, "/forum/file/{id}")
                        .access((authentication, context) -> {
                            boolean isAuthor = webSecurity.checkFileAuthor(context.getVariables().get("id"), authentication.get().getName());
                            boolean isModerator = context.getRequest().isUserInRole(Role.MODERATOR.name());
                            return new AuthorizationDecision(isAuthor || isModerator);
                        })
                        // end of hw_20 task2

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
        //configuration.setAllowedOrigins(Arrays.asList("https://example.com"));
        configuration.setAllowedOrigins(Arrays.asList("*")); // все сайты разрешены
        configuration.setAllowedMethods(Arrays.asList("GET","POST","PUT","DELETE","OPTIONS","PATCH","HEAD"));
        configuration.setAllowedHeaders(Arrays.asList("*")); // все заголовки разрешены
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

}
