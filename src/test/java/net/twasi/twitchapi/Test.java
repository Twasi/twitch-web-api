package net.twasi.twitchapi;

public class Test {

    @org.junit.jupiter.api.Test
    public void test() {
        String[] thisIsNull = null;
        String[] thisIsEmtpy = new String[3];

        for(String str : thisIsNull) {
            System.out.println(str);
        }

        System.out.println(thisIsNull);
        System.out.println(thisIsEmtpy);
    }

}
