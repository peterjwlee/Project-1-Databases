package db61b;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by PeterLee on 10/14/15.
 */
public class Db61bTests {

    @Test
    public void testRow() {
        Row r = new Row(new String[] {"Hi", "this", "is", "Peter", "Lee."});
        assertEquals(5, r.size());
        assertEquals(r.get(3), "Peter");
        assertEquals(r.get(1), "this");
        assertEquals(r.get(4), "Lee.");
        Row blank = new Row(new String[0]);
        assertEquals(0, blank.size());
    }
    @Test
    public void testAdd() {
        Table table = new Table("Students",
                new String[] {"SID", "Lastname", "Firstname"});
        Row r = new Row(new String[] {"101", "Knowles", "Jason"});
        table.add(r);
        assertEquals(1, table.size());
        r = new Row(new String[] {"Kendrick", "Computer", "Science"});
        table.add(r);
        assertEquals(2, table.size());
        assertEquals(1, table.columnIndex("Lastname"));
        assertEquals(-1, table.columnIndex("Peter"));
        Table table1 = new Table("Peter", new String[]{});
        assertEquals(0, table1.size());
    }

    @Test
    public void testTableIterator() {
        Table iterTables = new Table("Sam",
                new String[] {"Stay", "with", "me"});
        Row peter = new Row(new String[] {"Hi", "Peter", "Lee"});
        iterTables.add(peter);
        Row lee = new Row(new String[] {"dr", "dre", "California"});
        iterTables.add(lee);
        Row jin = new Row(new String[] {"Kendrick", "Lamar", "vibes"});
        iterTables.add(jin);
        TableIterator tables = new TableIterator(iterTables);
        Row row2 = tables.next();
        assertEquals(row2 == peter || row2 == lee || row2 == jin, true);
        Row row3 = tables.next();
        assertEquals(row3 == peter || row3 == lee || row3 == jin, true);
        tables.next();
        assertEquals(tables.hasRow(), false);
        tables.reset();
        assertEquals(tables.hasRow(), true);
    }

    public static void main(String[] args) {
        System.exit(ucb.junit.textui.runClasses(Db61bTests.class));
    }
}
