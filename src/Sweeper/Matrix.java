package Sweeper;

class Matrix // для заполнения клеток поля
{
    private Box[][] matrix;
    Matrix(Box defaultBox) //
    {
        matrix = new Box[Ranges.getSize().x][Ranges.getSize().y];
        for(Coordinates coordinates: Ranges.getAllCoordinates())
        {
            matrix[coordinates.x][coordinates.y] = defaultBox;
        }
    }
    Box get(Coordinates coordinates) // получаем координаты клетки
    {
        if(Ranges.inRange(coordinates))
        return matrix[coordinates.x][coordinates.y];
        return null;
    }
    void set(Coordinates coordinates, Box box) // устанавливаем координаты клетки
    {
        if(Ranges.inRange(coordinates))
        matrix[coordinates.x][coordinates.y] = box;
    }
}
