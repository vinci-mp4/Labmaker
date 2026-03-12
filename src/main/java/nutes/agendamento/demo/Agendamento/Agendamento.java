package nutes.agendamento.demo.Agendamento;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDateTime;

// 1. A Mágica do Spring começa aqui com as "Anotações" (esses termos com @)
@Entity
public class Agendamento {

    // 2. Definindo o Identificador Único (O "RG" do agendamento)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 3. Os dados do Agendamento em si
    private String nomeUsuario;
    private LocalDateTime dataHora;
    private String maquina;
    private String material;

    // Todo agendamento nasce como pendente até um técnico mudar isso
    private String status = "PENDENTE";

    // 4. Construtor Vazio (O Spring Boot exige isso para funcionar nos bastidores)
    public Agendamento() {
    }

    // 5. Getters e Setters (As "portas" para acessar ou modificar os dados)
    // Abaixo está apenas um exemplo de como eles são. Na vida real, você teria para todos os campos.

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    // ... (Imagine que o resto dos Getters e Setters estão aqui para economizar espaço) ...
}