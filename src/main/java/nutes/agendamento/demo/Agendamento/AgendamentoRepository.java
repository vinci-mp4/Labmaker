package nutes.agendamento.demo.Agendamento;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

// Essa linha é tudo o que você precisa!
// Ela diz: "Crie os comandos de Salvar, Deletar e Buscar para a classe Agendamento, cujo ID é um número Long"
public interface AgendamentoRepository extends JpaRepository<Agendamento, Long> {
    // O Spring entende: "Busque uma lista de Agendamentos onde a coluna Status seja igual à palavra que eu passar"
    List<Agendamento> findByStatus(String status);
}