package myClasses;

import java.time.LocalDate;
import java.util.*;
import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args)  {
        try {
            ArrayList<Book> data;
            FileWriter writer = new FileWriter("out.txt");//вывод данных в файл
            data = readData("data_computer_book.txt");//чтение данных из файла
            System.out.println("\n-------Список всех книг-------\n");
            for(Book ob: data){
                System.out.println(ob);
            }
            System.out.println("\n-------Поиск книги по параметрам-------\n");
            Scanner sc = new Scanner(System.in);
            System.out.print("Введите название книги: ");
            String name = sc.nextLine();
            System.out.print("Введите вид книги: ");
            String sort = sc.nextLine();
            System.out.print("Введите диапазон цены через дефис: ");
            String value = sc.nextLine();
            if(value.contains("-") && (value.matches("^[0-9]+\\W[0-9]+$"))){
                String [] numbers;
                numbers = value.split("-");
                long count = data.stream().filter(ob-> ob.title.contains(name)).filter(ob ->ob.type.toLowerCase(Locale.ROOT).equals(sort.toLowerCase(Locale.ROOT))).filter(ob-> (Double.parseDouble(numbers[0])<= ob.price && Double.parseDouble(numbers[1])>= ob.price)).count();
                if(count == 0) {
                    System.out.println("\nСовпадений не найдено");
                }
                else{
                    System.out.println("\n-------Результат поиска,отсортированный по отношению качество/цена-------\n");
                    List<Book> sorted_data= data.stream().filter(ob-> ob.title.contains(name)).filter(ob ->ob.type.toLowerCase(Locale.ROOT).equals(sort.toLowerCase(Locale.ROOT))).filter(ob-> (Double.parseDouble(numbers[0])<= ob.price && Double.parseDouble(numbers[1])>= ob.price)).sorted(Comparator.comparing(Book::getQuality).thenComparing(Book::getPrice)).collect(Collectors.toList());
                    for(Book ob: sorted_data)
                        System.out.println(ob);
                    writer.write("-------Результат поиска,отсортированный по отношению качество/цена-------\n\n");
                    for(Book ob: sorted_data){
                        writer.write(String.valueOf(ob));
                        writer.write("\n");
                    }
                }
            }
            else{
                System.out.println("Диапазон цены введен не правильно, попробуйте еще раз");
                return;
            }
            System.out.println("\n-------Результат поиска(упорядоченный по фамилии автора) книг, в описании которых упомянут автор(авторы)-------[Могут встречаться ошибки, из-за того что не только Фамилия Имя, идущие подряд, начинаются с большой буквы]\n");
            Pattern pattern = Pattern.compile("[A-Z][a-z]+\\s[A-Z][a-z]+");
            List<Book> sorted_data_author = data.stream().filter(ob -> {
                Matcher matcher = pattern.matcher(ob.description);
                return matcher.find();
            }).sorted(Comparator.comparing(Book::sortAuthorSurname)).collect(Collectors.toList());
            for(Book ob: sorted_data_author)
                System.out.println(ob);
            writer.write("\n-------Результат поиска(упорядоченный по фамилии автора) книг, в описании которых упомянут автор(авторы)-------[Могут встречаться ошибки, из-за того что не только Фамилия Имя, идущие подряд, начинаются с большой буквы]\n\n");
            for(Book ob: sorted_data_author){
                writer.write(String.valueOf(ob));
                writer.write("\n");
            }
            writer.close();
        }
        catch (IOException e){
            System.out.println("File Error");
        }
        catch (Exception e){
            System.out.println("Error");
        }
    }

    public static ArrayList<Book> readData(String fileName)  {
        ArrayList<Book> set_of_books = new ArrayList<>(10000);
        try {
            Scanner sc = new Scanner(new File(fileName));
            sc.useLocale(Locale.ENGLISH);
            if(!sc.hasNext()) {
                throw new IOException();
            }
            while (sc.hasNext()) {
                if(sc.hasNextDouble()) {
                    Book book_check = new Book();
                    book_check.quality = sc.nextDouble();

                    sc.nextLine();
                    sc.nextLine();

                    book_check.title = sc.nextLine();

                    book_check.description = sc.nextLine();
                    sc.nextLine();

                    book_check.type = sc.nextLine();
                    if(sc.hasNextDouble()) {
                        book_check.price = sc.nextDouble();
                        set_of_books.add(book_check);
                        if(sc.hasNext()){

                           sc.nextLine();
                        }
                        else break;
                    }
                }
                else throw new IOException();
            }
            sc.close();
        }
        catch (IOException e) {
            System.out.println("File Error");
            System.exit(0);
        }
        return set_of_books;
    }
}
