package Sweeper;

public class Game // для описания и реализации игровых процессов
{
    private Bomb bomb; // расположение мин на поле
    private Flag flag; // расположение флагов на поле
    private GameState state; // игровое состояние

    public GameState getState() // получение текущего состояния игры
    {
        return state;
    }

    public Game(int cols, int rows, int bombs)
    {
        Ranges.setSize(new Coordinates(cols, rows));
        bomb = new Bomb(bombs);
        flag = new Flag();

    }

    public Box getBox(Coordinates coordinates) // метод для размещения ? в каком либо месте экрана
    {
        if(flag.get(coordinates) == Box.OPENED) // если на клетке есть OPENED(верхний слой закрыт)
        return bomb.get(coordinates); // то возвращаем то, что находится под этой клеткой
        else // иначе если верхний слой открыт
        return flag.get(coordinates); // возвращаем то, что находится сверху(показываем нижний слой)
    }

    public void start(){
        //bombMap = new Matrix(Box.ZERO); // пока все поля заняты пустыми клетками
        bomb.start(); // инициализируем установку мин
        flag.start(); // инициализируем установку клеток поверх мин
        state = GameState.PLAYED; // начальное значение - играем
    }
    public  void pressLeftButton(Coordinates coordinates) // при нажатии левой кнопки
    {
        if(gameOver()) // если конец игры, то выходим
            return;
        openedBox(coordinates); // вызывается метод, определяющий логику поведения игры в зависмости от состояния клетки
        checkWin(); // проверка состояния игры на победу
    }
    private void checkWin() // проверка на выигрыш
    {
        if(state == GameState.PLAYED)
        {
            if(flag.getCountOfClosedBox() == bomb.getTotalBombs()) // если количество закрытых клеток такое же, как и количество установленных мин
                state = GameState.WINNER;
        }
    }
    private void openedBox(Coordinates coordinates) // открываем клетку
    {
        switch (flag.get(coordinates))
        {
            case OPENED: setOpenToCloseBoxAroundNumber(coordinates); return; //
            case FLAGED: return;
            case CLOSED:
                switch (bomb.get(coordinates))
                {
                    case ZERO: openBoxAround(coordinates); return; // при открытии пустой клетки открываются остальные пустые вокруг
                    case BOMB: openBomb(coordinates); return;
                    default:   flag.setOpenedBox(coordinates); return; // установка координат для открытия клетки
                }
        }
    }
    private void openBomb(Coordinates coordinates)
    {
        state = GameState.BOMBED; // в случае открытия поля с миной
        flag.setBombedBox(coordinates); // передача координат установленной мины
        for(Coordinates coord: Ranges.getAllCoordinates()) // открываем все оставшиеся установленные мины в случае проигрыша
        {
            if(bomb.get(coord) == Box.BOMB)
            {
                flag.setOpenToCloseBombBox(coord); // устанавливаем клетку с открытой на закрытую в случае проигрыша
            }
            else
            {
                flag.setNoBombToFlagedSafe(coord); // устанавливаем флажок безопасности на клетку
            }
        }
    }
    private void openBoxAround(Coordinates coordinates) // октрываем клетки вокруг
        {
            flag.setOpenedBox(coordinates);
            for(Coordinates around: Ranges.getCoordAround(coordinates))
            {
                openedBox(around); // рекурсивный вызов метода openedBox
            }
        }
    public void pressRightButton(Coordinates coordinates) // при нажатии правой кнопки
    {
        flag.toggleFlagedBox(coordinates); // перестановка флага(снят/убран) при нажатии правой кнопки
    }

    private boolean gameOver()
    {
        if(state == GameState.PLAYED)
            return false;
        start();
        return true;
    }

        void setOpenToCloseBoxAroundNumber(Coordinates coordinates) // открывает закрытые клетки вокруг числа
    {
       if(bomb.get(coordinates) != Box.BOMB)
       {

           if(flag.getFlagedBoxAround(coordinates) == bomb.get(coordinates).getNumber())
           {
               for (Coordinates around: Ranges.getCoordAround(coordinates))
                   if(flag.get(around) == Box.CLOSED )
                       openedBox(around); // открытие закрытой пустой клетки при открытых других
           }
       }
    }

}

