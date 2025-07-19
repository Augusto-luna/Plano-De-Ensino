package model.entidades;


import model.enums.TipoAvaliacao;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

public class PlanoEnsino {
    private int ano;
    private int semestre;
    private String ementa;
    private Professor professor;

    private HashMap<TipoAvaliacao, Integer> pesosAvaliacoes;
    private HashMap<LocalDate, String> topicoData;

    private ArrayList<String> objetivos;

    public PlanoEnsino(int ano, int semestre, String ementa, Professor professor) {
        this.ano = ano;
        this.semestre = semestre;
        this.ementa = ementa;
        this.professor = professor;
        this.pesosAvaliacoes = new HashMap<>();
        this.topicoData = new HashMap<>();
        this.objetivos = new ArrayList<>();
    }

    public int getAno() {
        return ano;
    }

    public int getSemestre() {
        return semestre;
    }

    public String getEmenta() {
        return ementa;
    }

    public Professor getProfessor() {
        return professor;
    }

    public HashMap<TipoAvaliacao, Integer> getPesosAvaliacoes() {
        return pesosAvaliacoes;
    }

    public HashMap<LocalDate, String> getTopicoData() {
        return topicoData;
    }

    public ArrayList<String> getObjetivos() {
        return objetivos;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

    public void setSemestre(int semestre) {
        this.semestre = semestre;
    }

    public void setEmenta(String ementa) {
        this.ementa = ementa;
    }

    public void setProfessor(Professor professor) {
        this.professor = professor;
    }

    public void setPesosAvaliacoes(HashMap<TipoAvaliacao, Integer> pesosAvaliacoes) {
        this.pesosAvaliacoes = pesosAvaliacoes;
    }

    public void setTopicoData(HashMap<LocalDate, String> topicoData) {
        this.topicoData = topicoData;
    }

    public void setObjetivos(ArrayList<String> objetivos) {
        this.objetivos = objetivos;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n--- Plano de Ensino ---\n");
        sb.append("Ano: ").append(ano).append("\n");
        sb.append("Semestre: ").append(semestre).append("\n");
        sb.append("Ementa: '").append(ementa).append("'\n");
        sb.append("Professor Responsável: ").append(professor != null ? professor.getNome() : "N/A").append("\n");

        sb.append("\nObjetivos:\n");
        if (objetivos.isEmpty()) {
            sb.append("  Nenhum objetivo definido.\n");
        } else {
            objetivos.forEach(obj -> sb.append("  - ").append(obj).append("\n"));
        }

        sb.append("\nTópicos e Datas:\n");
        if (topicoData.isEmpty()) {
            sb.append("  Nenhum tópico com data definido.\n");
        } else {
            topicoData.forEach((data, topico) ->
                    sb.append("  - Data: ").append(data.format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy")))
                            .append(" | Tópico: '").append(topico).append("'\n")
            );
        }

        sb.append("\nPesos das Avaliações (bruto):\n");
        if (pesosAvaliacoes.isEmpty()) {
            sb.append("  Nenhum peso de avaliação definido.\n");
        } else {
            pesosAvaliacoes.forEach((tipo, peso) ->
                    sb.append("  - ").append(tipo).append(": ").append(peso).append("\n")
            );
        }
        sb.append("-----------------------");
        return sb.toString();
    }
}