package Sweeper;

import java.util.ArrayList;
import java.util.Random;

public class Ranges // класс для построения игрового поля
{
    private static Coordinates size;
    private static ArrayList<Coordinates> allCoordinates; // список для всех координат
    private static Random random = new Random();
    public static Coordinates getSize() // метод, получающий размеры игрового поля
    {
        return size;
    }
    public static ArrayList<Coordinates> getAllCoordinates() // метод, возвращающий список всех координат
    {
        return allCoordinates;
    }
    public static void setSize(Coordinates _size) // метод, устанавливающий размеры игрового поля
    {
        size = _size;
        allCoordinates = new ArrayList<Coordinates>();
        for (int y = 0; y < size.y; y++)
        {
            for (int x = 0; x < size.x; x++) {
                allCoordinates.add(new Coordinates(x, y)); // заполняем список всех координат
            }
        }
    }
    static boolean inRange(Coordinates coordinates) // проверка координат на правильный диапазон
    {
        return coordinates.x >=0 && coordinates.x < size.x && coordinates.y >=0 && coordinates.y < size.y;
    }
    static Coordinates getRandomCoordinates() // возвращение случайной координаты для установки мины
    {
        return new Coordinates(random.nextInt(size.x), random.nextInt(size.y)); // генерация случайной координаты
    }
    static ArrayList<Coordinates> getCoordAround(Coordinates coordinates) // метод проверяет координаты вокруг клетки текущей клетки
    {
        Coordinates around;
        ArrayList<Coordinates> list = new ArrayList<>(); // список координат, которые будут вокруг
        for(int x = coordinates.x-1; x <= coordinates.x + 1; x++) // в цикле перебираются координаты, начиная от клетки на одну левее и заканчивая клеткой на одну правее текущей
        {
            for (int y = coordinates.y -1; y <= coordinates.y + 1; y++)
            {
                if(inRange(around = new Coordinates(x, y))) // проверяем, входит ли координата в заданный диапазон. Сразу идет создание, присвоение и вызов в методе координаты around
                {
                    if(!around.equals(coordinates)) // текущая окружающая клетка не должна быть равна проверяемой клетке
                    {
                        list.add(around); // добавляем координату в список
                    }
                }
            }
        }
        return list;
    }

}
