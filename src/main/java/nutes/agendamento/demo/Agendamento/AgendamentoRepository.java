package nutes.agendamento.demo.Agendamento;

import org.springframework.data.jpa.repository.JpaRepository;

// Essa linha é tudo o que você precisa!
// Ela diz: "Crie os comandos de Salvar, Deletar e Buscar para a classe Agendamento, cujo ID é um número Long"
public interface AgendamentoRepository extends JpaRepository<Agendamento, Long> {
}