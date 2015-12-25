package db61b;

/**
 * Created by PeterLee on 10/17/15.
 */
public class PrintTest {
    public static void main(String[] args) {
        Table dumb = new Table("Smith", new String[] {"Peter", "hi", "ROFL"});
        Row peter = new Row(new String[] {"Peter", "Peter", "Lee"});
        Row lee = new Row(new String[] {"dr", "dre", "California"});
        Row jin = new Row(new String[] {"Kendrick", "Lamar", "vibes"});
        Row wesley = new Row(new String[] {"Ice", "Cube", "NWA"});
        Row daniel = new Row(new String[] {"LOLOLOLOL", "HIIHIHI", "WDDUP"});
        Row audrey = new Row(new String[] {"Watch", "Me", "NAYNAY"});
        Row drake = new Row(new String[] {"RESPECT", "BOY", "RESPECTT"});
        dumb.add(peter);
        dumb.add(lee);
        dumb.add(jin);
        dumb.add(wesley);
        dumb.add(daniel);
        dumb.add(audrey);
        dumb.add(drake);
        dumb.writeTable("Tables");
        Table read = Table.readTable("Tables");
        read.print();
        System.out.println();
        dumb.print();
        Table dumb1 = new Table("Peter",
                new String[] {"POPPP", "Milan", "Vinay"});
        Row a = new Row(new String[] {"pepper", "salt", "spice"});
        Row b = new Row(new String[] {"puppy", "dog", "cat"});
        Row c = new Row(new String[] {"goat", "lol", "what"});
        dumb1.add(a);
        dumb1.add(b);
        dumb1.add(c);
        dumb1.writeTable("Tables1");
        Table read1 = Table.readTable("Tables1");
        System.out.println();
        read1.print();
        System.out.println();
        dumb1.print();
    }
}

