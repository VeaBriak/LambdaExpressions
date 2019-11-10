import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Stream;

/**В проекте с сотрудниками с помощью Stream API рассчитать максимальную зарплату тех, кто пришёл в 2017 году.**/

public class Main
{
    private static String staffFile = "data/staff.txt";
    private static String dateFormat = "dd.MM.yyyy";

    public static void main(String[] args) throws ParseException {
        ArrayList<Employee> staff = loadStaffFromFile();

        String string1 = "31.12.2016";
        String string2 = "01.01.2018";
        DateFormat format = new SimpleDateFormat(dateFormat);
        Date date1 = format.parse(string1);
        Date date2 = format.parse(string2);
        System.out.println("Максимальная зарплата среди сотрудников, кто пришёл в 2017 году:");
        staff.stream()
                .filter(e -> e.getWorkStart().after(date1) && e.getWorkStart().before(date2))
                .max(Comparator.comparing(Employee::getSalary))
                .ifPresent(System.out::println);

//                .map(employee -> employee.getSalary())
//                .reduce((s1, s2) -> s1 + s2)
//                .ifPresent(System.out::println);

    }

    private static ArrayList<Employee> loadStaffFromFile()
    {
        ArrayList<Employee> staff = new ArrayList<>();

        try
        {
            List<String> lines = Files.readAllLines(Paths.get(staffFile));
            for(String line : lines)
            {
                String[] fragments = line.split("\t");
                if(fragments.length != 3) {
                    System.out.println("Wrong line: " + line);
                    continue;
                }
                staff.add(new Employee(
                    fragments[0],
                    Integer.parseInt(fragments[1]),
                    (new SimpleDateFormat(dateFormat)).parse(fragments[2])
                ));
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        return staff;
    }
}