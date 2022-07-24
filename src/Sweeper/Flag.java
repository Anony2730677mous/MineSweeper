package Sweeper;

class Flag // класс для заполнения верхнего игрового слоя
{
    private Matrix flagMap;
    private int countOfClosedBox; // число закрытых клеток
    void start()
    {
        flagMap = new Matrix(Box.CLOSED); // инициализация поля проходит с закрытыми ячейками
        countOfClosedBox = Ranges.getSize().x * Ranges.getSize().y; // количесто закрытых клеток в начале игры

    }
    Box get(Coordinates coordinates)
    {
        return flagMap.get(coordinates);
    }
    public void setOpenedBox(Coordinates coordinates) // метод, устанавливающий клетке значение "открыта"
    {
        flagMap.set(coordinates, Box.OPENED); // при нажатии левой кнопки устанавливается поле OPENED
        countOfClosedBox--; // при открытии очередной клетки уменьшаем их количество
    }
    private void setFlagedBox(Coordinates coordinates)
    {
        flagMap.set(coordinates, Box.FLAGED); // при нажатии левой кнопки устанавливается поле FLAGED
    }
    void  toggleFlagedBox(Coordinates coordinates) // установка/снятие флага при нажатии правой кнопки в зависимости от того, что уже установлено
    {
        switch (flagMap.get(coordinates))
        {
            case FLAGED : setClosedBox(coordinates);
            break;
            case CLOSED : setFlagedBox(coordinates);
            break;
        }
    }
    private void setClosedBox(Coordinates coordinates) // установка клетки в положение "закрыта"
    {
        flagMap.set(coordinates, Box.CLOSED);
    }
    int getCountOfClosedBox() // метод возвращает количество закрытых клеток
    {
        return countOfClosedBox;
    }
    void setBombedBox(Coordinates coordinates)
    {
        flagMap.set(coordinates, Box.BOMBED);
    }
    void setOpenToCloseBombBox(Coordinates coordinates)
    {
        if(flagMap.get(coordinates) == Box.CLOSED) // если координата закрыта
        {
            flagMap.set(coordinates, Box.OPENED); // координату открываем
        }
    }
    void setNoBombToFlagedSafe(Coordinates coordinates)
    {
        if(flagMap.get(coordinates) == Box.FLAGED) // если координата помечена флажком
        {
            flagMap.set(coordinates, Box.NOBOMB); // устанавливаем флажок, что мины нет
        }
    }


    int getFlagedBoxAround(Coordinates coordinates)
    {
        int count = 0;
        for (Coordinates around: Ranges.getCoordAround(coordinates)) // в цикле перебираем координаты вокруг клетки
        {
            if(flagMap.get(around) == Box.FLAGED) // если на клетке ест флаг
                count++; // увеличиваем счетчик
        }
        return count;
    }
}
