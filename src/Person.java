public class Person {
    private String fio;
    private int salary;
    private int age;

    public Person(String fio, int salary, int age) {
        this.fio = fio;
        this.salary = salary;
        this.age = age;
    }

    public String getFio() {
        return fio;
    }

    public int getSalary() {
        return salary;
    }

    public int getAge() {
        return age;
    }

    public void print() {
        System.out.print(getFio());
        System.out.print("; ");
        System.out.print(getAge());
        System.out.println(". ");
    }

    public void incSalary(int a) {
        salary = salary + a;
    }

    public static void inc5000(Person[] persons) {
        for (Person person : persons) {
            if (person.getAge() > 45) {
                person.incSalary(5000);
            }
        }
    }

    public static void main(String[] args) {
        Person[] persons = new Person[5];
        persons[0] = new Person("Иванов Петр Михайлович", 35000, 48);
        persons[1] = new Person("Селиванова Ольга Владимировна", 30000, 32);
        persons[2] = new Person("Диванов Альберт Рюрикович", 20000, 29);
        persons[3] = new Person("Абдуррахманов Дурахман Рахметович", 17000, 44);
        persons[4] = new Person("Зельдман Зинаида Степановна", 50000, 64);

        for (Person person : persons) {
            if (person.age > 40) {
                person.print();
            }
        }

        printAll(persons);
        inc5000(persons);
        printAll(persons);
    }

    private static void printAll(Person[] persons) {
        for (Person person : persons) {
            System.out.print(person.getSalary());
            System.out.print(": ");
            person.print();
        }
    }
}
