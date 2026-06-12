package nutes.agendamento.demo.agendamento;

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
    private String codigoProtocolo;
    private LocalDateTime dataHoraConclusao;

    // Todo agendamento nasce como pendente até um técnico mudar isso
    private String status = "PENDENTE";

    // 4. Construtor Vazio (O Spring Boot exige isso para funcionar nos bastidores)
    public Agendamento() {
    }

    public String getCodigoProtocolo() { return codigoProtocolo; }

    public void setCodigoProtocolo(String codigoProtocolo) { this.codigoProtocolo = codigoProtocolo; }

    public LocalDateTime getDataHoraConclusao() { return dataHoraConclusao; }

    public void setDataHoraConclusao(LocalDateTime dataHoraConclusao) { this.dataHoraConclusao = dataHoraConclusao; }

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

    public String getNomeUsuario() {
        return nomeUsuario;
    }

    public void setNomeUsuario(String nomeUsuario) {
        this.nomeUsuario = nomeUsuario;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }

    public String getMaquina() {
        return maquina;
    }

    public void setMaquina(String maquina) {
        this.maquina = maquina;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }
}