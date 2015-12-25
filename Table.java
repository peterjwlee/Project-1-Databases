package db61b;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import static db61b.Utils.*;

/** A single table in a database.
 *  @author Peter*/
class Table implements Iterable<Row> {
    /** A new Table named NAME whose columns are give by COLUMNTITLES,
     *  which must be distinct (else exception thrown). */
    Table(String name, String[] columnTitles) {
        ArrayList<String> container = new ArrayList<>();
        for (int i = 0; i < columnTitles.length; i += 1) {
            if (container.contains(columnTitles[i])) {
                throw error("no duplicate columns");
            } else {
                container.add(columnTitles[i]);
            }
        }
        _name = name;
        _titles = columnTitles;
        _rows = new HashSet<Row>();
    }

    /** A new Table named NAME whose column names are give by COLUMNTITLES. */
    Table(String name, List<String> columnTitles) {

        this(name, columnTitles.toArray(new String[columnTitles.size()]));
    }

    /** Return the number of columns in this table. */
    int numColumns() {
        return _titles.length;
    }

    /** Returns my name. */
    String name() {
        return _name;
    }

    /** Returns a TableIterator over my rows in an unspecified order. */
    TableIterator tableIterator() {
        return new TableIterator(this);
    }

    /** Returns an iterator that returns my rows in an unspecfied order. */
    @Override
    public Iterator<Row> iterator() {
        return _rows.iterator();
    }

    /** Return the title of the Kth column.  Requires 0 <= K < columns(). */
    String title(int k) {
        return _titles[k];
    }

    /** Return the number of the column whose title is TITLE, or -1 if
     *  there isn't one. */
    int columnIndex(String title) {
        for (int i = 0; i < _titles.length; i += 1) {
            if (_titles[i].equals(title)) {
                return i;
            }
        }
        return -1;
    }

    /** Return the number of Rows in this table. */
    int size() {
        return _rows.size();
    }

    /** Add ROW to THIS if no equal row already exists.  Return true if anything
     *  was added, false otherwise. */
    boolean add(Row row) {
        if (row.size() != _titles.length) {
            throw error("Row length must equal length of column titles.");
        }
        return _rows.add(row);
    }

    /** Read the contents of the file NAME.db, and return as a Table.
     *  Format errors in the .db file cause a DBException. */
    static Table readTable(String name) {
        BufferedReader input;
        Table table;
        input = null;
        table = null;
        try {
            input = new BufferedReader(new FileReader(name + ".db"));
            String header = input.readLine();
            if (header == null) {
                throw error("missing header in DB file");
            }
            Pattern pattern = Pattern.compile("[\\p{Alpha}_]\\w*");
            String[] columnNames = header.split(",");
            for (int i = 0; i < columnNames.length; i += 1) {
                columnNames[i] = columnNames[i].trim();
                Matcher match = pattern.matcher(columnNames[i]);
                if (!match.matches()) {
                    throw error("invalid format");
                }
            }
            table = new Table(name, columnNames);
            while (input.ready()) {
                String lines = input.readLine();
                String[] rowNames = lines.split(",");
                for (int i = 0; i < rowNames.length; i += 1) {
                    rowNames[i] = rowNames[i].trim();
                }
                Row tableRow = new Row(rowNames);
                table.add(tableRow);
            }
        } catch (FileNotFoundException e) {
            throw error("could not find %s.db", name);
        } catch (IOException e) {
            throw error("problem reading from %s.db", name);
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    /* Ignore IOException */
                }
            }
        }
        return table;
    }

    /** Write the contents of TABLE into the file NAME.db. Any I/O errors
     *  cause a DBException. */
    void writeTable(String name) {
        PrintStream output;
        output = null;
        try {
            String sep;
            sep = "";
            output = new PrintStream(name + ".db");
            for (int i = 0; i < _titles.length; i += 1) {
                if (i == (_titles.length - 1)) {
                    output.print(_titles[i].trim());
                } else {
                    output.print(_titles[i].trim() + ",");
                }
            }
            output.println();
            Iterator iterators = _rows.iterator();
            while (iterators.hasNext()) {
                Row store = (Row) iterators.next();
                for (int i = 0; i < store.size(); i += 1) {
                    if (i == (store.size() - 1)) {
                        output.print(store.get(i).trim());
                    } else {
                        output.print(store.get(i).trim() + ",");
                    }
                }
                output.println();
            }
        } catch (IOException e) {
            throw error("trouble writing to %s.db", name);
        } finally {
            if (output != null) {
                output.close();
            }
        }
    }

    /** Print my contents on the standard output, separated by spaces
     *  and indented by two spaces. */
    void print() {
        Iterator print = _rows.iterator();
        while (print.hasNext()) {
            System.out.print("  ");
            Row storePrint = (Row) print.next();
            for (int i = 0; i < storePrint.size(); i += 1) {
                System.out.print(storePrint.get(i));
                System.out.print(" ");
            }
            System.out.println();
        }
    }

    /** My name. */
    private final String _name;
    /** My column titles. */
    private String[] _titles;
    /** My hash set of rows. */
    private HashSet<Row> _rows;
}


