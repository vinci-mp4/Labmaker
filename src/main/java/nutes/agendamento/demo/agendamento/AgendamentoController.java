package nutes.agendamento.demo.agendamento;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.security.Principal;

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
        List<Agendamento> pendentes = repository.findByStatus("PENDENTE");
        List<Agendamento> aprovados = repository.findByStatus("APROVADO");
        List<Agendamento> negados = repository.findByStatus("NEGADO");

        // Nova linha buscando os concluídos
        List<Agendamento> concluidos = repository.findByStatus("CONCLUIDO");

        model.addAttribute("pendentes", pendentes);
        model.addAttribute("aprovados", aprovados);
        model.addAttribute("negados", negados);

        // Nova linha enviando para a tela
        model.addAttribute("concluidos", concluidos);

        return "painel-tecnico";
    }

    // Rota para apagar o agendamento do banco de dados
    @GetMapping("/deletar/{id}")
    public String deletarAgendamento(@PathVariable Long id,
                                     @RequestParam(required = false, defaultValue = "pendentes") String aba) {
        repository.deleteById(id);
        // Redireciona devolvendo o nome da aba na URL
        return "redirect:/painel?aba=" + aba;
    }

    // A porta da frente do site
    @GetMapping("/")
    public String paginaInicial() {
        // Redireciona o visitante automaticamente para a tela de login do Spring Security
        return "redirect:/login";
    }

    // Rota para a nova tela de login customizada
    @GetMapping("/login")
    public String telaLogin() {
        return "login";
    }

    // 1. Porta para MOSTRAR a tela de formulário (GET)
    @GetMapping("/solicitar")
    public String mostrarFormulario() {
        return "formulario-visitante";
    }

    @PostMapping("/solicitar")
    public String salvarAgendamento(Agendamento agendamento, Principal principal) {
        // Força o sistema a usar o utilizador logado na sessão, anulando fraudes
        agendamento.setNomeUsuario(principal.getName());

        agendamento.setStatus("PENDENTE");
        agendamento.setDataHora(java.time.LocalDateTime.now());

        // Guarda a primeira vez para gerar o ID
        Agendamento salvo = repository.save(agendamento);

        // Lógica do protocolo
        String prefixo = salvo.getMaquina().substring(0, 3).toUpperCase();
        String numeroFormatado = String.format("%04d", salvo.getId());

        salvo.setCodigoProtocolo(prefixo + numeroFormatado);
        repository.save(salvo);

        return "redirect:/solicitar?sucesso";
    }

    // Adicione a importação do @PathVariable lá no topo do arquivo junto com as outras, se o IntelliJ pedir:
    // import org.springframework.web.bind.annotation.PathVariable;

    @GetMapping("/atualizar-status/{id}/{novoStatus}")
    public String atualizarStatus(@PathVariable Long id, @PathVariable String novoStatus,
                                  @RequestParam(required = false, defaultValue = "pendentes") String aba) {
        Agendamento pedido = repository.findById(id).orElse(null);
        if (pedido != null) {
            pedido.setStatus(novoStatus);

            // NOVIDADE: Se o status novo for CONCLUIDO, bate o ponto com a hora exata
            if (novoStatus.equals("CONCLUIDO")) {
                pedido.setDataHoraConclusao(java.time.LocalDateTime.now());
            }

            repository.save(pedido);
        }
        return "redirect:/painel?aba=" + aba;
    }

}