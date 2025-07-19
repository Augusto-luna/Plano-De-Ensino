package model.entidades;


public abstract class Pessoa {
    private String nome;
    private String email;

    public Pessoa(String nome, String email){
        setNome(nome);
        setEmail(email);
 
    }

    public Pessoa() {

    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome não pode ser vazio.");
        }else if (nome.length() > 100) {
            throw new IllegalArgumentException("Nome não pode ter mais de 100 caracteres.");
        }else if (!nome.matches("[a-zA-ZÀ-ÿ\\s]+")) {
            throw new IllegalArgumentException("Nome só pode conter letras e espaços.");
        }else{
            this.nome = nome;
        }
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Nome: '" + nome + '\'' +
                ", Email: '" + email + '\'';
    }
}
