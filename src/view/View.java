package view;

import controller.Controller;
import model.enums.TipoAvaliacao;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class View {
    private Controller controller;
    private Scanner scanner;

    public View(Controller controller) {
        this.controller = controller;
        this.scanner = new Scanner(System.in);
    }

    public void exibirMensagem(String mensagem) {
        System.out.println(mensagem);
    }

    public void exibirErro(String erro) {
        System.err.println("ERRO: " + erro);
    }

    public void iniciar() {
        String comando;
        do {
            exibirMenuPrincipal();
            comando = lerString("Digite um comando (ou '0' para sair):").trim();

            processarComando(comando);

        } while (!comando.equals("0"));
    }

    private void exibirMenuPrincipal() {
        System.out.println("\n===== Sistema Acadêmico =====");
        System.out.println("Opções:");
        System.out.println("  professor - Cadastrar Professor");
        System.out.println("  disciplina - Cadastrar Disciplina");
        System.out.println("  plano - Cadastrar Plano de Ensino");
        System.out.println("  manipular plano - Modificar um Plano de Ensino existente");
        System.out.println("  relatorio basico - Exibir Relatórios Básicos");
        System.out.println("  relatorio geral - Exibir Relatório Geral do Sistema");
        System.out.println("  0 - Sair");
        System.out.println("===============================");
    }

    private void processarComando(String comando) {
        switch (comando.toLowerCase()) {
            case "professor":
                cadastrarProfessor();
                break;
            case "disciplina":
                cadastrarDisciplina();
                break;
            case "plano":
                cadastrarPlanoEnsino();
                break;
            case "manipular plano":
                manipularPlanoEnsino();
                break;
            case "relatorio basico":
                exibirRelatoriosBasicos();
                break;
            case "relatorio geral":
                exibirRelatorioGeral();
                break;
            case "0":
                exibirMensagem("Saindo do sistema. Até mais!");
                break;
            default:
                exibirErro("Comando inválido. Tente novamente.");
                break;
        }
    }

    private String lerString(String prompt) {
        System.out.print(prompt + " ");
        return scanner.nextLine();
    }

    private int lerInteiro(String prompt) {
        while (true) {
            try {
                System.out.print(prompt + " ");
                int valor = scanner.nextInt();
                scanner.nextLine();
                return valor;
            } catch (InputMismatchException e) {
                exibirErro("Entrada inválida. Por favor, digite um número inteiro.");
                scanner.nextLine();
            }
        }
    }

    private LocalDate lerData(String prompt) {
        while (true) {
            String dataStr = lerString(prompt + " (formato YYYY-MM-DD):");
            try {
                return LocalDate.parse(dataStr);
            } catch (DateTimeParseException e) {
                exibirErro("Formato de data inválido. Use YYYY-MM-DD.");
            }
        }
    }

    private void cadastrarProfessor() {
        exibirMensagem("\n--- Cadastro de Professor ---");
        String nome = lerString("Nome:");
        String email = lerString("Email:");
        int matricula = lerInteiro("Matrícula:");
        String areaAtuacao = lerString("Área de Atuação:");

        String resultado = controller.cadastrarProfessor(nome, email, matricula, areaAtuacao);
        exibirMensagem(resultado);
    }

    private void cadastrarDisciplina() {
        exibirMensagem("\n--- Cadastro de Disciplina ---");
        String nome = lerString("Nome da Disciplina:");
        String curso = lerString("Curso da Disciplina:");

        String resultado = controller.cadastrarDisciplina(nome, curso);
        exibirMensagem(resultado);
    }

    private void cadastrarPlanoEnsino() {
        exibirMensagem("\n--- Cadastro de Plano de Ensino ---");
        int ano = lerInteiro("Ano:");
        int semestre = lerInteiro("Semestre:");
        String ementa = lerString("Ementa:");
        int matriculaProfessor = lerInteiro("Matrícula do Professor Responsável:");

        String resultado = controller.cadastrarPlanoDeEnsino(ano, semestre, ementa, matriculaProfessor);
        exibirMensagem(resultado);
    }

    private void manipularPlanoEnsino() {
        exibirMensagem("\n--- Modificar Plano de Ensino ---");
        int ano = lerInteiro("Ano do Plano:");
        int semestre = lerInteiro("Semestre do Plano:");
        String ementa = lerString("Ementa do Plano:");

        String subComando;
        do {
            System.out.println("\n--- Opções para o Plano '" + ementa + "' ---");
            System.out.println("  objetivo - Adicionar Objetivo");
            System.out.println("  topico - Adicionar Tópico com Data");
            System.out.println("  peso - Adicionar Peso de Avaliação");
            System.out.println("  desassociar professor - Desassociar Professor");
            System.out.println("  remover objetivo - Remover Objetivo");
            System.out.println("  remover topico - Remover Tópico");
            System.out.println("  0 - Voltar");
            subComando = lerString("Digite uma opção:").trim();

            switch (subComando.toLowerCase()) {
                case "objetivo":
                    String objetivo = lerString("Novo Objetivo:");
                    exibirMensagem(controller.adicionarObjetivo(ano, semestre, ementa, objetivo));
                    break;
                case "topico":
                    String topico = lerString("Novo Tópico:");
                    LocalDate data = lerData("Data do Tópico");
                    exibirMensagem(controller.adicionarTopico(ano, semestre, ementa, topico, data));
                    break;
                case "peso":
                    exibirMensagem("Tipos de Avaliação: TEORICA, PRATICA, GRUPO, INDIVIDUAL");
                    String tipoStr = lerString("Tipo de Avaliação (ex: TEORICA):").toUpperCase();
                    try {
                        TipoAvaliacao tipo = TipoAvaliacao.valueOf(tipoStr);
                        int peso = lerInteiro("Peso (valor inteiro > 0):");
                        exibirMensagem(controller.adicionarPesoAvaliacao(ano, semestre, ementa, tipo, peso));
                    } catch (IllegalArgumentException e) {
                        exibirErro("Tipo de Avaliação inválido. Tente novamente.");
                    }
                    break;
                case "desassociar professor":
                    int matriculaProfDessociar = lerInteiro("Matrícula do professor a desassociar:"); // Ajustado prompt
                    exibirMensagem(controller.dissociarProfessorDePlano(ano, semestre, ementa, matriculaProfDessociar));
                    break;
                case "remover objetivo": // *** CORRIGIDO PARA MINÚSCULAS ***
                    String objetivoRemover = lerString("Objetivo a remover:");
                    exibirMensagem(controller.removerObjetivo(ano, semestre, ementa, objetivoRemover));
                    break;
                case "remover topico":
                    LocalDate dataTopicoRemover = lerData("Data do Tópico a remover:");
                    exibirMensagem(controller.removerTopico(ano, semestre, ementa, dataTopicoRemover));
                    break;
                case "0":
                    exibirMensagem("Voltando ao menu anterior.");
                    break;
                default:
                    exibirErro("Comando inválido. Tente novamente.");
                    break;
            }
        } while (!subComando.equals("0"));
    }

    private void exibirRelatoriosBasicos() {
        String tipoRelatorio;
        do {
            System.out.println("\n--- Exibir Relatórios Básicos ---");
            System.out.println("  professor - Relatório de Professor por Matrícula");
            System.out.println("  disciplina - Relatório de Disciplina por Nome");
            System.out.println("  plano - Relatório de Plano de Ensino Detalhado");
            System.out.println("  0 - Voltar");
            tipoRelatorio = lerString("Digite o tipo de relatório:").trim();

            switch (tipoRelatorio.toLowerCase()) {
                case "professor":
                    int matriculaProf = lerInteiro("Matrícula do Professor:");
                    exibirMensagem(controller.obterRelatorioBasicoProfessor(matriculaProf));
                    break;
                case "disciplina":
                    String nomeDisc = lerString("Nome da Disciplina:");
                    exibirMensagem(controller.obterRelatorioBasicoDisciplina(nomeDisc));
                    break;
                case "plano":
                    int anoPlano = lerInteiro("Ano do Plano:");
                    int semestrePlano = lerInteiro("Semestre do Plano:");
                    String ementaPlano = lerString("Ementa do Plano:");
                    exibirMensagem(controller.obterRelatorioBasicoPlanoEnsino(anoPlano, semestrePlano, ementaPlano));
                    break;
                case "0":
                    exibirMensagem("Voltando ao menu anterior.");
                    break;
                default:
                    exibirErro("Comando inválido. Tente novamente.");
                    break;
            }
        } while (!tipoRelatorio.equals("0"));
    }

    private void exibirRelatorioGeral() {
        exibirMensagem("\n--- Exibindo Relatório Geral do Sistema ---");
        String relatorio = controller.obterRelatorioGeralDoSistema();
        exibirMensagem(relatorio);
    }
}