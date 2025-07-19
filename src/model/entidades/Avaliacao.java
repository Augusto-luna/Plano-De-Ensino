package model.entidades;

public class Avaliacao {
    private int peso;

    public Avaliacao(int peso){
        this.peso = peso;
    }

    public int getPeso() {
        return peso;
    }

    public void setPeso(int peso) {
        this.peso = peso;
    }

    @Override
    public String toString() {
        return "Avaliação [ Peso: " + peso + "% ]";
    }

}
