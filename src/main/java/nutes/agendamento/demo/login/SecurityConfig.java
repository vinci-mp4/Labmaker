package nutes.agendamento.demo.login;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    // 1. CRIANDO OS USUÁRIOS DE TESTE
    @Bean
    public UserDetailsService usuariosDeTeste() {
        UserDetails visitante = User.withDefaultPasswordEncoder()
                .username("aluno")
                .password("123")
                .roles("USER")
                .build();

        UserDetails tecnico = User.withDefaultPasswordEncoder()
                .username("admin")
                .password("admin")
                .roles("ADMIN") // O crachá de chefe
                .build();

        return new InMemoryUserDetailsManager(visitante, tecnico);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        // Usando o algoritmo padrão de mercado (BCrypt)
        return new org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder();
    }

    // 2. CONFIGURANDO AS REGRAS E ROTAS
    @Bean
    public SecurityFilterChain regrasDeAcesso(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Desliga proteção extra para facilitar o protótipo
                .headers(headers -> headers.frameOptions(frame -> frame.disable())) // Libera a tela do H2 Console

                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/h2-console/**").permitAll() // Banco de dados aberto para todos
                        .requestMatchers("/painel").hasRole("ADMIN")   // Só o técnico entra no painel
                        .requestMatchers("/solicitar").hasAnyRole("USER", "ADMIN") // Visitante entra no formulário
                        .anyRequest().authenticated()
                )

                // 3. A MÁGICA DO REDIRECIONAMENTO

                .formLogin(form -> form
                        .loginPage("/login") // <-- AQUI! Avisamos sobre a página customizada
                        .successHandler((request, response, authentication) -> {
                            for (GrantedAuthority autorizacao : authentication.getAuthorities()) {
                                if (autorizacao.getAuthority().equals("ROLE_ADMIN")) {
                                    response.sendRedirect("/painel");
                                    return;
                                }
                            }
                            response.sendRedirect("/solicitar");
                        })
                        .permitAll()
                );

        return http.build();
    }
}