package net.ryan.primalworld.entity;

public class CarnivoroMedio extends Needs {

    public CarnivoroMedio() {
        super(100, 100);  // Exemplo de valores para fome e sede
    }

    @Override
    public void findFood() {
        // Lógica para o carnívoro médio procurar alimento (caçar, etc.)
        System.out.println("Procurando por presas médias...");
    }

    @Override
    public void findWater() {
        // Lógica para procurar água
        System.out.println("Procurando água...");
    }
}
