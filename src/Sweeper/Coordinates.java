package Sweeper;

public class Coordinates // класс координат
{
    public int x; // координаты каждой клетки по горизонтали
    public int y; // координаты каждой клетки по вертикали

    public Coordinates(int x, int y) // конструктор объекта клетки
    {
        this.x = x;
        this.y = y;
    }
@Override
    public boolean equals(Object obj) // переопределение метода сравнения координат двух клеток с помощью сравнения их полей x и y
{
    if(obj instanceof Coordinates)
    {
        return ((Coordinates) obj).x == x && ((Coordinates) obj).y == y;
    }
    return super.equals(obj);
}
}
