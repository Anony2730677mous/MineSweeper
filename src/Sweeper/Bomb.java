package Sweeper;

import java.util.Random;

class Bomb // класс для заполнения нижнего игрового слоя
{
    private Matrix bombMap;
    private  int totalBomb; // общее количество мин на карте

    Bomb(int totalBomb)
    {
        this.totalBomb = totalBomb;
        fixBombCount();
    }
    void start() // размещение мин на карте
    {
        bombMap = new Matrix(Box.ZERO);
        for (int i = 0; i < totalBomb; i++) // размещение всех мин в цикле
        {
            placeBomb();
        }

    }
    private void fixBombCount() // метод, определяющий максимальное количество мин на карте
    {
        int maxBombs = Ranges.getSize().x * Ranges.getSize().y/ 2; // максимальное количество клеток поля с бомбами
        if(totalBomb > maxBombs)
        {
            totalBomb = maxBombs;
        }
    }
    private void placeBomb() // размещение одной мины на клетку
    {
        while (true)
        {
            Coordinates coordinates = Ranges.getRandomCoordinates(); // размещение мины в случайной координате
            if (Box.BOMB == bombMap.get(coordinates)) // если координата содержит мину, размещение мин продолжается
                continue;
            bombMap.set(coordinates, Box.BOMB);
            incremNumbersAroundBomb(coordinates); // размещение цифр вокруг мины
            break;
        }

    }
    Box get(Coordinates coordinates)
    {
        return bombMap.get(coordinates);
    }
    private void incremNumbersAroundBomb(Coordinates coordinates) // метод, увеличивающий цифры, показывающие количество мин, вокруг мин
    {
        for(Coordinates around: Ranges.getCoordAround(coordinates))
        {
            if(Box.BOMB !=bombMap.get(around)) // если в координате нет мины
            bombMap.set(around, bombMap.get(around).getNextNumberBox());// устанавливаем цифру, следующую за текущей цифрой в классе-енуме Box,
        }
    }
    int getTotalBombs()
    {
        return totalBomb;
    }
}
