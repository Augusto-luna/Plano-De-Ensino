package controller;

import model.Model;
import model.enums.TipoAvaliacao;
import model.entidades.Disciplina;
import model.entidades.PlanoEnsino;
import model.entidades.Professor;
import model.entidades.Pessoa;

import java.time.LocalDate;
import java.util.InputMismatchException;

public class Controller {
    private Model model;

    public Controller(Model model) {
        this.model = model;
    }

    public String obterRelatorioBasicoProfessor(int matriculaProfessor) {
        Professor professor = model.buscarProfessorPorMatricula(matriculaProfessor);
        if (professor != null) {
            return model.obterRelatorioProfessor(professor);
        } else {
            return "Erro: Professor com matrícula " + matriculaProfessor + " não encontrado.";
        }
    }

    public String obterRelatorioBasicoDisciplina(String nomeDisciplina) {
        Disciplina disciplina = model.buscarDisciplinaPorNome(nomeDisciplina);
        if (disciplina != null) {
            return model.obterRelatorioDisciplina(disciplina);
        } else {
            return "Erro: Disciplina '" + nomeDisciplina + "' não encontrada.";
        }
    }

    public String obterRelatorioBasicoPlanoEnsino(int ano, int semestre, String ementaPlano) {
        PlanoEnsino plano = model.buscarPlanoEnsino(ano, semestre, ementaPlano);
        if (plano != null) {
            return model.obterRelatorioCompletoPlanoEnsino(plano);
        } else {
            return "Erro: Plano de ensino '" + ementaPlano + "' não encontrado.";
        }
    }

    public String obterRelatorioGeralDoSistema() {
        return model.gerarRelatorioSistemaCompleto();
    }

    public String cadastrarProfessor(String nome, String email, int matricula, String areaAtuacao) {
        try{Professor professor = model.adicionarProfessor(nome, email, matricula, areaAtuacao);
            return model.obterRelatorioProfessor(professor);

        }catch (IllegalArgumentException e) {
            return "Erro: " + e.getMessage();
        }
    }

    public String cadastrarPlanoDeEnsino(int ano, int int1, String ementa, int matriculaProfessor) {
        Professor professor = model.buscarProfessorPorMatricula(matriculaProfessor);
        if (professor == null) {
            return "Erro: Professor com matrícula " + matriculaProfessor + " não encontrado para associar ao plano de ensino.";
        }
        PlanoEnsino plano = model.adicionarPlanoEnsino(ano, int1, ementa, professor);
        if (plano != null) {
            return "Plano de ensino '" + ementa + "' criado com sucesso.";
        } else {
            return "Erro: Não foi possível criar o plano de ensino.";
        }
    }

    public String adicionarObjetivo(int ano, int semestre, String ementaPlano, String objetivo) {
        PlanoEnsino plano = model.buscarPlanoEnsino(ano, semestre, ementaPlano);
        if (plano != null) {
            model.adicionarObjetivoAoPlano(plano, objetivo);
            return "Objetivo adicionado ao plano '" + ementaPlano + "'.";
        } else {
            return "Erro: Plano de ensino '" + ementaPlano + "' não encontrado para adicionar objetivo.";
        }
    }

    public String adicionarTopico(int ano, int semestre, String ementaPlano, String topico, LocalDate data) {
        PlanoEnsino plano = model.buscarPlanoEnsino(ano, semestre, ementaPlano);
        if (plano != null) {
            model.adicionarTopicoDataAoPlano(plano, topico, data);
            return "Tópico '" + topico + "' adicionado ao plano '" + ementaPlano + "'.";
        } else {
            return "Erro: Plano de ensino '" + ementaPlano + "' não encontrado para adicionar tópico.";
        }
    }

    public String adicionarPesoAvaliacao(int ano, int semestre, String ementaPlano, TipoAvaliacao tipo, int pesoBruto) {
        PlanoEnsino plano = model.buscarPlanoEnsino(ano, semestre, ementaPlano);
        if (plano != null) {
            model.adicionarPesoAvaliacaoAoPlano(plano, tipo, pesoBruto);
            return "Peso " + pesoBruto + " para " + tipo + " adicionado ao plano '" + ementaPlano + "'.";
        } else {
            return "Erro: Plano de ensino '" + ementaPlano + "' não encontrado para adicionar peso de avaliação.";
        }
    }

    public String cadastrarDisciplina(String nome, String curso) {
        Disciplina disciplina = model.adicionarDisciplina(nome, curso);
        if (disciplina != null) {
            return model.obterRelatorioDisciplina(disciplina);
        } else {
            return "Erro: Não foi possível cadastrar a disciplina.";
        }
    }

    public String dissociarProfessorDePlano(int ano, int semestre, String ementaPlano, int matriculaProfessor) {
        PlanoEnsino plano = model.buscarPlanoEnsino(ano, semestre, ementaPlano);
        if (plano == null) {
            return "Erro: Plano de ensino '" + ementaPlano + "' não encontrado.";
        }
        Professor professor = model.buscarProfessorPorMatricula(matriculaProfessor);
        if (professor == null) {
            return "Erro: Professor com matrícula " + matriculaProfessor + " não encontrado.";
        }

        boolean sucesso = model.dissasociarProfessordePlano(plano, professor);
        if (sucesso) {
            return "Professor " + professor.getNome() + " desassociado do plano '" + ementaPlano + "' com sucesso.";
        } else {
            return "Erro: O professor " + professor.getNome() + " não estava associado ao plano '" + ementaPlano + "' ou a desassociação falhou.";
        }
    }

    public String removerObjetivo(int ano, int semestre, String ementaPlano, String objetivoParaRemover) {
        PlanoEnsino plano = model.buscarPlanoEnsino(ano, semestre, ementaPlano);
        if (plano == null) {
            return "Erro: Plano de ensino '" + ementaPlano + "' não encontrado.";
        }
        boolean sucesso = model.removerObjetivoDePlano(plano, objetivoParaRemover);
        if (sucesso) {
            return "Objetivo '" + objetivoParaRemover + "' removido do plano '" + ementaPlano + "' com sucesso.";
        } else {
            return "Erro: Objetivo '" + objetivoParaRemover + "' não encontrado no plano '" + ementaPlano + "'.";
        }
    }

    public String removerTopico(int ano, int semestre, String ementaPlano, LocalDate dataTopicoParaRemover){
        PlanoEnsino plano = model.buscarPlanoEnsino(ano, semestre, ementaPlano);
        if (plano == null){
            return "Tópico da data " + dataTopicoParaRemover + " removido do plano" + ementaPlano + " com sucesso";
        }
        else {
            return "Erro: Topico da data " + dataTopicoParaRemover + "não encontrado no plano " + ementaPlano + ".";
        }
    }
}