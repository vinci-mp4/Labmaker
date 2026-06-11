package nutes.agendamento.demo.Usuario;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    // Ensina o Spring a buscar um usuário pelo nome de login
    Optional<Usuario> findByUsername(String username);
}
