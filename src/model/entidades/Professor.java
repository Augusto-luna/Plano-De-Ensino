package model.entidades;

public class Professor extends Pessoa{
    private int matricula;
    private String areaAtuacao;

    public Professor(String nome, String email, int matricula, String areaAtuacao){
        super(nome, email);
        this.matricula = matricula;
        this.areaAtuacao = areaAtuacao;
    }

    public int getMatricula() {
        return matricula;
    }

    public void setMatricula(int matricula) {
        this.matricula = matricula;
    }

    public String getAreaAtuacao() {
        return areaAtuacao;
    }

    public void setAreaAtuacao(String areaAtuacao) {
        this.areaAtuacao = areaAtuacao;
    }


    @Override
    public String toString() {
        return "Professor [ " + super.toString() +
                ", Matrícula: " + matricula +
                ", Área de Atuação: '" + areaAtuacao + '\'' +
                " ]";
    }
}
