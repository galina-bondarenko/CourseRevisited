public class Main {
    public static void main(String[] args) {
        Animal[] animals = {new Bird(), new Cat(), new Dog(), new Horse()};
        for (Animal animal : animals) {
            animal.swim(15);
            animal.jump(4);
            animal.run(17);
            animal.run(500);
            animal.run(300);
        }

    }
}
