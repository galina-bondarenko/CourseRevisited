public abstract class Animal {
    private float run;
    private float swim;
    private float jump;
    private String title;

    protected Animal(float run, float swim, float jump, String title) {
        this.run = run;
        this.swim = swim;
        this.jump = jump;
        this.title = title;
    }

    public void run(float length) {
        System.out.print(title);
        System.out.print(" пробежала: ");
        System.out.println(length <= run);
    }

    public void swim(float length) {
        System.out.print(title);
        System.out.print(" проплыла: ");
        System.out.println(length <= swim);
    }

    public void jump(float length) {
        System.out.print(title);
        System.out.print(" прыгнула: ");
        System.out.println(length <= jump);
    }
}
