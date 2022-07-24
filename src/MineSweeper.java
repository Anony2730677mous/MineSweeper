import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Locale;
import Sweeper.Box;
import Sweeper.Coordinates;
import Sweeper.Game;
import Sweeper.Ranges;

public class MineSweeper extends JFrame // добавляем библиотеку для визуализации программы в виде JFrame
{
    private JPanel panel; // создается панель для отрисовки содержимого
    private JLabel label;
    private final int COLS = 9; // количество столбцов
    private final int ROWS = 9; // количество строк
    private final int BOMBS = 10; // общее количество мин на поле
    private final int IMAGE_SIZE = 50; // размер картинки
    private Game game; // импорт игрового поля из соответствующего класса
    public static void main(String[] args) {
        new MineSweeper(); // создаем экземпляр класса
    }
    private MineSweeper() // создаем конструктор
    {
        //Ranges.setSize(new Coordinates(COLS, ROWS)); // устанавливаем размер игрового поля
        game = new Game(COLS, ROWS, BOMBS); // инициализируем экземпляр игрового поля
        game.start(); // запускаем расстановку мин
        setImages(); // установка картинок в поле
        initLabel(); // установка метки состояния игры
        initPanel(); // инициализация панели
        initFrame(); // инициализация окна
    }
    private void initLabel() // добавление состояния игры
    {
        label = new JLabel("Welcome!");
        add(label, BorderLayout.SOUTH); // добавляем состояние игры вниз всего окна
    }
    private void initPanel() // метод, инициализирующий панель для отрисовки
    {
        panel = new JPanel() // создаем экземпляр панели, а внутри создается анонимный класс с методом paintComponent
        {
            @Override
            protected void paintComponent(Graphics g)// метод будет вызываться каждый раз, когда нужно будет нарисовать форму картинки
            {
                super.paintComponent(g);
                for(Coordinates coordinates: Ranges.getAllCoordinates())
                {
                    g.drawImage((Image) game.getBox(coordinates).image, coordinates.x*IMAGE_SIZE, coordinates.y*IMAGE_SIZE, this); // ordinal - используется для порядкового номера в списке
                }
            }
        };
        panel.addMouseListener(new MouseAdapter() // метод, отслеживающий мышь
        {
            @Override
            public void mousePressed(MouseEvent e) {
                int x = e.getX() / IMAGE_SIZE; // отслеживание того, где находится курсор мыши по горизонтали
                int y = e.getY() / IMAGE_SIZE; // отслеживание того, где находится курсор мыши по вертикали
                Coordinates coordinates = new Coordinates(x, y); // координаты, где мышь была щелкнута
                if(e.getButton() == MouseEvent.BUTTON1)
                    game.pressLeftButton(coordinates); // вызывается метод отслеживающий нажатия левой кнопки
                if(e.getButton() == MouseEvent.BUTTON3)
                    game.pressRightButton(coordinates); // вызывается метод отслеживающий нажатия средней кнопки
                if(e.getButton() == MouseEvent.BUTTON2)
                    game.start(); // вызывается метод для перезапуска игры
                label.setText(getMessage()); // метод, выводящий сообщения в зависимости от текущего состояния игры
                panel.repaint(); // происходит перерисовка окошка для отображения изменений
            }
        });
        panel.setPreferredSize(new Dimension(Ranges.getSize().x*IMAGE_SIZE, Ranges.getSize().y*IMAGE_SIZE)); // устанавливаем размер панели, передавая в качесте параметра новый экземпляр класса Dimension
        add(panel); // добавления панели в форму окна add приходит из класса JFrame
    }
    private void initFrame() // метод, инициализирующий начальные параметры окна программы
    {

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); // для закрытия программы применяем константу WindowConstants.EXIT_ON_CLOSE
        setTitle("MineSweeper Java"); // устанавливаем заголовок для программы
        setResizable(false); // установка размера окна
        setVisible(true); // установка видимости окошка
        setIconImage(getImage("icon")); // установка иконки для программы
        pack(); // устанавливаем минимальный размер, который достаточен для отображения всех компонентов
        setLocationRelativeTo(null); // установка окошка программы по центру
    }
    private void setImages() //установка всех картинок
    {
        for(Box box: Box.values())
        {
            box.image = getImage(box.name().toLowerCase()); // устанавливаем картинку для каждого имени из класса-перечисления Box
        }
    }
    private Image getImage(String name) // метод, получающий картинки из ресурса по ее имени
    {
        String fileName = "img/" + name + ".png"; // подготовка картинки по ее имени
        //для папки Resource устанавливем значение Mark Directory as Resources Root
        ImageIcon icon = new ImageIcon(getClass().getResource(fileName)); // создание экземпляра ImageIcon на основе переданной картинки из папки, отмеченной как Resources Root
        return icon.getImage(); // метод возвращает найденную картинку как объект класса Image
    }
    private String getMessage()
    {
        switch (game.getState())
        {
            case PLAYED: return "Идет процесс разминирования!";
            case BOMBED: return "Неудача! Мина взорвалась!!!";
            case WINNER: return "Победа! Все мины обезврежены!!!";
            default: return "Game Play";
        }
    }
}
