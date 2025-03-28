package net.ryan.primalworld.entity;

public abstract class Needs {
        protected int hunger;
        protected int thirst;
        protected int maxHunger;
        protected int maxThirst;

        public Needs(int maxHunger, int maxThirst) {
            this.maxHunger = maxHunger;
            this.maxThirst = maxThirst;
            this.hunger = maxHunger;  // Começa cheio
            this.thirst = maxThirst;  // Começa saciado
        }

        // Método para reduzir fome e sede com o tempo
        public void updateNeeds() {
            if (hunger > 0) {
                hunger--;
            }
            if (thirst > 0) {
                thirst--;
            }
        }

        // Métodos para verificar se o mob está com fome ou sede
        public boolean isHungry() {
            return hunger < maxHunger / 2;
        }

        public boolean isThirsty() {
            return thirst < maxThirst / 2;
        }

        // Métodos para alimentar e dar água
        public void eat(int foodAmount) {
            hunger = Math.min(hunger + foodAmount, maxHunger);
        }

        public void drink(int waterAmount) {
            thirst = Math.min(thirst + waterAmount, maxThirst);
        }

        // Métodos abstratos que serão implementados nas subclasses
        public abstract void findFood();
        public abstract void findWater();
    }
