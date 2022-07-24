package Sweeper;

public enum Box // перечисление для всех возможных состояний поля
{
    ZERO,
    NUM1,
    NUM2,
    NUM3,
    NUM4,
    NUM5,
    NUM6,
    NUM7,
    NUM8,
    BOMB,
    OPENED,
    CLOSED,
    FLAGED,
    BOMBED,
    NOBOMB;
    public Object image; // для хранения картинки поля
 Box getNextNumberBox()
 {
     return Box.values()[this.ordinal() + 1]; // метод возвращает следующий элемент енума Box, стоящий после текущего this
 }
 int getNumber()
 {
     return this.ordinal(); // получаем текущий номер объекта
 }
}
