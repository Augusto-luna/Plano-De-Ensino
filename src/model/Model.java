package model;


import model.entidades.Disciplina;
import model.entidades.PlanoEnsino;
import model.entidades.Professor;
import model.entidades.Avaliacao;
import model.enums.TipoAvaliacao;

import java.time.LocalDate;
import java.util.*;

public class Model {
    private List<Professor> professores;
    private List<Disciplina> disciplinas;
    private List<PlanoEnsino> planosDeEnsino;

    public Model() {
        this.professores = new ArrayList<>();
        this.disciplinas = new ArrayList<>();
        this.planosDeEnsino = new ArrayList<>();
    }

    public Professor adicionarProfessor(String nome, String email, int matricula, String areaAtuacao) {
        for (Professor p: professores){
            if( p.getMatricula()==matricula){
                throw new IllegalArgumentException("Erro: Matricula " + matricula + " já cadastrada para o professor " + p.getNome());
            }
        }

        Professor novoProfessor = new Professor(nome, email, matricula, areaAtuacao);
        this.professores.add(novoProfessor);
        return novoProfessor;
    }

    public PlanoEnsino adicionarPlanoEnsino(int ano, int semestre, String ementa, Professor professor) {
        if (professor == null) {
            return null;
        }
        PlanoEnsino novoPlano = new PlanoEnsino(ano, semestre, ementa, professor);
        this.planosDeEnsino.add(novoPlano);
        return novoPlano;
    }

    public void adicionarObjetivoAoPlano(PlanoEnsino planoEnsino, String objetivo) {
        if (planoEnsino != null && this.planosDeEnsino.contains(planoEnsino)) {
            planoEnsino.getObjetivos().add(objetivo);
        }
    }

    public Disciplina adicionarDisciplina(String nome, String curso) {
        Disciplina novaDisciplina = new Disciplina(nome, curso);
        this.disciplinas.add(novaDisciplina);
        return novaDisciplina;
    }

    public void adicionarTopicoDataAoPlano(PlanoEnsino planoEnsino, String topico, LocalDate data) {
        if (planoEnsino != null && this.planosDeEnsino.contains(planoEnsino)) {
            planoEnsino.getTopicoData().put(data, topico);
        }
    }

    public void adicionarPesoAvaliacaoAoPlano(PlanoEnsino planoEnsino, TipoAvaliacao tipo, int pesoBruto) {
        if (planoEnsino != null && this.planosDeEnsino.contains(planoEnsino)) {
            if (pesoBruto <= 0) {
                return;
            }
            planoEnsino.getPesosAvaliacoes().put(tipo, pesoBruto);
        }
    }

    public Professor buscarProfessorPorMatricula(int matricula) {
        for (Professor p : professores) {
            if (p.getMatricula() == matricula) {
                return p;
            }
        }
        return null;
    }

    public Disciplina buscarDisciplinaPorNome(String nome) {
        for (Disciplina d : disciplinas) {
            if (d.getNome().equalsIgnoreCase(nome)) {
                return d;
            }
        }
        return null;
    }

    public PlanoEnsino buscarPlanoEnsino(int ano, int semestre, String ementa) {
        for (PlanoEnsino p : planosDeEnsino) {
            if (p.getAno() == ano && p.getSemestre() == semestre && p.getEmenta().equalsIgnoreCase(ementa)) {
                return p;
            }
        }
        return null;
    }

    public Map<TipoAvaliacao, Double> calcularPorcentagensAvaliacoes(PlanoEnsino planoEnsino) {
        if (planoEnsino == null || planoEnsino.getPesosAvaliacoes().isEmpty()) {
            return Collections.emptyMap();
        }

        int somaTotalPesos = 0;
        for (Integer peso : planoEnsino.getPesosAvaliacoes().values()) {
            somaTotalPesos += peso;
        }

        Map<TipoAvaliacao, Double> porcentagens = new HashMap<>();
        if (somaTotalPesos == 0) {
            return Collections.emptyMap();
        }

        for (Map.Entry<TipoAvaliacao, Integer> entry : planoEnsino.getPesosAvaliacoes().entrySet()) {
            TipoAvaliacao tipo = entry.getKey();
            int pesoBruto = entry.getValue();
            double porcentagem = ((double) pesoBruto / somaTotalPesos) * 100.0;
            porcentagens.put(tipo, porcentagem);
        }
        return porcentagens;
    }

    public String obterRelatorioProfessor(Professor professor) {
        if (professor == null) {
            return "--- Relatório de Professor ---\nProfessor não encontrado.\n------------------------------\n";
        }
        StringBuilder sb = new StringBuilder();
        sb.append("--- Relatório de Professor ---\n");
        sb.append("Nome: ").append(professor.getNome()).append("\n");
        sb.append("Email: ").append(professor.getEmail()).append("\n");
        sb.append("Matrícula: ").append(professor.getMatricula()).append("\n");
        sb.append("Área de Atuação: ").append(professor.getAreaAtuacao()).append("\n");
        sb.append("------------------------------\n");
        return sb.toString();
    }

    public String obterRelatorioDisciplina(Disciplina disciplina) {
        if (disciplina == null) {
            return "--- Relatório de Disciplina ---\nDisciplina não encontrada.\n------------------------------\n";
        }
        StringBuilder sb = new StringBuilder();
        sb.append("--- Relatório de Disciplina ---\n");
        sb.append("Nome: ").append(disciplina.getNome()).append("\n");
        sb.append("Curso: ").append(disciplina.getCurso()).append("\n");
        sb.append("-------------------------------\n");
        return sb.toString();
    }

    public boolean dissasociarProfessordePlano(PlanoEnsino planoEnsino, Professor professor){
        if (planoEnsino == null || !this.planosDeEnsino.contains(planoEnsino)){
            return false;
        }
        if (planoEnsino.getProfessor() != null && planoEnsino.getProfessor().equals(professor)){
            planoEnsino.setProfessor(null);
            return true;
        }
        return false;
    }

    public boolean removerObjetivoDePlano(PlanoEnsino planoEnsino, String objetivo){
        if(planoEnsino == null || !this.planosDeEnsino.contains(planoEnsino)){
            return false;
        }
        return planoEnsino.getObjetivos().remove(objetivo);
    }

    public boolean removerTopicoDePlano(PlanoEnsino planoEnsino, LocalDate dataTopico){
        if(planoEnsino == null || !this.planosDeEnsino.contains(planoEnsino)){
            return false;
        }
        return planoEnsino.getTopicoData().remove(dataTopico)!=null;
    }

    public String obterRelatorioCompletoPlanoEnsino(PlanoEnsino planoEnsino) {
        if (planoEnsino == null || !this.planosDeEnsino.contains(planoEnsino)) {
            return "--- Relatório de Plano de Ensino ---\nPlano de Ensino não encontrado ou não gerenciado.\n--------------------------------------\n";
        }

        StringBuilder sb = new StringBuilder();
        sb.append("--- Relatório Detalhado do Plano de Ensino ---\n");
        sb.append("Ano: ").append(planoEnsino.getAno()).append("\n");
        sb.append("Semestre: ").append(planoEnsino.getSemestre()).append("\n");
        sb.append("Ementa: '").append(planoEnsino.getEmenta()).append("'\n");
        sb.append("Professor Responsável: ").append(planoEnsino.getProfessor() != null ? planoEnsino.getProfessor().getNome() : "N/A").append("\n");

        sb.append("\nObjetivos:\n");
        if (planoEnsino.getObjetivos().isEmpty()) {
            sb.append("  Nenhum objetivo definido.\n");
        } else {
            for (String obj : planoEnsino.getObjetivos()) {
                sb.append("  - ").append(obj).append("\n");
            }
        }

        sb.append("\nTópicos e Datas:\n");
        if (planoEnsino.getTopicoData().isEmpty()) {
            sb.append("  Nenhum tópico com data definido.\n");
        } else {
            for (Map.Entry<LocalDate, String> entry : planoEnsino.getTopicoData().entrySet()) {
                sb.append("  - Data: ").append(entry.getKey().format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy")))
                        .append(" | Tópico: '").append(entry.getValue()).append("'\n");
            }
        }

        sb.append("\nPesos Brutos das Avaliações (definidos pelo professor):\n");
        if (planoEnsino.getPesosAvaliacoes().isEmpty()) {
            sb.append("  Nenhum peso de avaliação bruto definido.\n");
        } else {
            for (Map.Entry<TipoAvaliacao, Integer> entry : planoEnsino.getPesosAvaliacoes().entrySet()) {
                sb.append("  - ").append(entry.getKey()).append(": ").append(entry.getValue()).append("\n");
            }
        }

        sb.append("\n--- Porcentagens Calculadas das Avaliações ---\n");
        Map<TipoAvaliacao, Double> porcentagens = calcularPorcentagensAvaliacoes(planoEnsino);
        if (porcentagens.isEmpty()) {
            sb.append("  Nenhum peso de avaliação definido ou cálculo inválido.\n");
        } else {
            for (Map.Entry<TipoAvaliacao, Double> entry : porcentagens.entrySet()) {
                sb.append(String.format("  - %s: %.2f%%\n", entry.getKey(), entry.getValue()));
            }
        }
        sb.append("--------------------------------------------------\n");
        return sb.toString();
    }

    public String gerarRelatorioSistemaCompleto() {
        StringBuilder sb = new StringBuilder();
        sb.append("===================================================\n");
        sb.append("           RELATÓRIO COMPLETO DO SISTEMA ACADÊMICO\n");
        sb.append("===================================================\n");

        sb.append("\n### Professores Cadastrados ###\n");
        if (professores.isEmpty()) {
            sb.append("Nenhum professor cadastrado.\n");
        } else {
            for (Professor prof : professores) {
                sb.append(obterRelatorioProfessor(prof)).append("\n");
            }
        }

        sb.append("\n### Disciplinas Cadastradas ###\n");
        if (disciplinas.isEmpty()) {
            sb.append("Nenhuma disciplina cadastrada.\n");
        } else {
            for (Disciplina disc : disciplinas) {
                sb.append(obterRelatorioDisciplina(disc)).append("\n");
            }
        }

        sb.append("\n### Planos de Ensino Detalhados ###\n");
        if (planosDeEnsino.isEmpty()) {
            sb.append("Nenhum plano de ensino cadastrado.\n");
        } else {
            for (PlanoEnsino plano : planosDeEnsino) {
                sb.append(obterRelatorioCompletoPlanoEnsino(plano)).append("\n");
            }
        }

        sb.append("===================================================\n");
        sb.append("                 FIM DO RELATÓRIO\n");
        sb.append("===================================================\n");
        return sb.toString();
    }

    public List<Professor> getProfessores() {
        return new ArrayList<>(professores);
    }

    public List<Disciplina> getDisciplinas() {
        return new ArrayList<>(disciplinas);
    }

    public List<PlanoEnsino> getPlanosDeEnsino() {
        return new ArrayList<>(planosDeEnsino);
    }

}