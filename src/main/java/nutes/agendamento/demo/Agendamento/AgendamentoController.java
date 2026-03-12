package nutes.agendamento.demo.Agendamento;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

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
}