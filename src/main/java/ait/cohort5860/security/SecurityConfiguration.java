package ait.cohort5860.security;

import ait.cohort5860.accounting.model.Role;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.expression.WebExpressionAuthorizationManager;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Bean
    SecurityFilterChain getSecurityFilterChain(HttpSecurity http) throws Exception {

        http.httpBasic(Customizer.withDefaults()); // включение базоваой дефл.авторизации
        http.csrf(csrf -> csrf.disable()); // кроссайтовая защита, не https использовать нельзя


        http.authorizeHttpRequests( // снятие защиты

                authorize -> authorize
                        // .anyRequest().permitAll() - все разрешить(к примеру)
                .requestMatchers("/account/register", "/forum/posts/**") // на регистрацию
                    .permitAll() // пустить всех

                .requestMatchers("/account/user/{login}/role/{role}") // добавлять роли
                    .hasRole(Role.ADMINISTRATOR.name()) // должен юзер должен иметь роль админа

                .requestMatchers(HttpMethod.PATCH, "/account/user/{login}") // апдейтить можно только свой профиль
                    .access(new WebExpressionAuthorizationManager("#login == authentication.name"))

                .requestMatchers(HttpMethod.POST, "/forum/post/{author}") // только автор от своего имени может добавлять пост
                    .access(new WebExpressionAuthorizationManager("#author == authentication.name"))

                .requestMatchers(HttpMethod.PATCH, "/forum/post/{id}/comment/{author}") // только автор от своего имени может создавать/изменять комментарии
                    .access(new WebExpressionAuthorizationManager("#author == authentication.name"))

                //.requestMatchers(HttpMethod.PATCH, "/forum/post/{postId}/comment/{commenter}") // только под своим именем комментатор может оставить комментарий
                    //.access(new WebExpressionAuthorizationManager("#commenter == authentication.name"))

                .requestMatchers(HttpMethod.DELETE, "/account/user/{login}")
                    .access(new WebExpressionAuthorizationManager("#login == authentication.name or hasRole('ADMINISTRATOR')"))


                .anyRequest() // все
                .authenticated()); // проверяется

        return http.build();
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


}
