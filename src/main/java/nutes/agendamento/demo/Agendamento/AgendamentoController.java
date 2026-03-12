package nutes.agendamento.demo.Agendamento;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class AgendamentoController {

    // 1. Chamando o Repositório para trabalhar junto com o Garçom
    private final AgendamentoRepository repository;

    public AgendamentoController(AgendamentoRepository repository) {
        this.repository = repository;
    }

    // 2. Quando o usuário digitar o endereço do site (Ex: localhost:8080/painel)
    @GetMapping("/painel")
    public String mostrarPainel(Model model) {
        // O garçom vai no banco de dados e pega TODOS os agendamentos
        List<Agendamento> todosOsAgendamentos = repository.findAll();

        // Ele coloca esses dados em uma "bandeja" chamada 'agendamentos' para a tela poder usar
        model.addAttribute("agendamentos", todosOsAgendamentos);

        // Ele devolve a página HTML chamada "painel-tecnico.html"
        return "painel-tecnico";
    }

    // 1. Porta para MOSTRAR a tela de formulário (GET)
    @GetMapping("/solicitar")
    public String mostrarFormulario() {
        return "formulario-visitante";
    }

    // 2. Porta para RECEBER e SALVAR os dados do formulário (POST)
    @PostMapping("/solicitar")
    public String salvarAgendamento(Agendamento agendamento) {
        // O Spring Boot é inteligente: ele pega os dados do HTML e já monta o objeto "agendamento" pra gente.

        // Forçamos o status inicial como PENDENTE por segurança
        agendamento.setStatus("PENDENTE");

        // Adicionamos a data e hora exata de agora automaticamente
        agendamento.setDataHora(java.time.LocalDateTime.now());

        // Mandamos o repositório salvar de verdade no banco de dados H2
        repository.save(agendamento);

        // Redireciona o visitante para a tela do painel só pra gente ver a mágica funcionando na hora
        return "redirect:/painel";
    }
}