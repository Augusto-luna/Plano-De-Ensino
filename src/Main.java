
import model.Model;
import controller.Controller;
import view.View;

public class Main {
    public static void main(String[] args) {
        Model sistemaModel = new Model();
        Controller sistemaController = new Controller(sistemaModel);
        View sistemaView = new View(sistemaController);

        sistemaView.iniciar();
    }
}