package model.entidades;

public class Disciplina {
    private String nome;
    private String curso;

    public Disciplina(String nome, String curso){
        this.nome = nome;
        this.curso = curso;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCurso() {
        return curso;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }

    @Override
    public String toString() {
        return "Disciplina [ " +
                "Nome: '" + nome + '\'' +
                ", Curso: '" + curso + '\'' +
                " ]";
    }
}
