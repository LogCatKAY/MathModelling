# MathModelling
МГТУ ИУ7 "Математическое моделирование" лабораторные работы

Лабораторные работы выполнены в виде приложений для платформы Android.
Пробные приложения.

В каждой лабораторной работе в большей степени смотрю на возможности Android с оговоркой, что сдаваться работа должна все-таки в срок.
В каждой лабораторной работе можно в принципе сделать что-то "лучше".

# Лаба 1
1) Интерполирование функции (полиномом Ньютона) по заданной таблице
2) Поиск корня функции обратной интерполяцией

По Android
Ничего особенного. Смотрю как работать с ресурсами и изображениями. С настройками выдвижной клавиатуры (например, чтобы в горизонтальной ориентации устройства не был занят весь экран). Попытка встроить дровер (Drawer) - не очень получилось, впоследствии все-таки понял, как надо (в других работах).
Определил интересный способ (на будущее) работы с фрагментами - класс SingleFragmentActivity.

# Лаба 2
Аппроксимация функции (наилучшее среднеквадратичное приближение)

По Android
- динамически можно создавать таблицу (добавлять и удалять строки),
- есть несколько заранее подготовленных таблиц для удобства (чтобы графики были красивые),
- сама таблица может прокручиваться, если слишком длинная (тут есть простор для оптимизации потребления памяти для больших таблиц),
- работа по сохранению состояния таблицы и элементов таблицы при переворотах устройства и переходах на другую активити,
- работа с библиотекой GraphView

# Лаба 3
Поиск верхнего предела интегрирования в функции Лапласа по заданному значению функции

По Android (старался ответить на следующие вопросы)
- как надо (и можно ли) работать со многими фрагментами при одной активити-хосте
- как сделать Drawer в стиле Material (хотя бы начать, т.к. решение в 1-ой лабораторной работе не очень хорошее)
- как можно работать с SQLite базой данных (она в принципе не нужна, но интересно попробовать - в приложении несколько таблиц, которые показываются в зависимости от того, с какого фрагмента был совершен переход)
- как можно работать с RecyclerView и CardView (элементы из таблиц SQLite выводятся через RecyclerView)
- уменьшение шага в цикле и вынесение рассчетов в HandlerThread с отображением прогресса в UI Thread (работа с интерфейсами)

# Лаба 4
Поиск производной разными способами
а) Значения односторонних разностей
б) Значения центральных разностей
в) Вторая производная
г) Значение в крайнем левом узле
д) Значение в крайнем правом узле
е) Значения по формулам Рунге
г) Вырвнивание переменных

По Android:
- ничего особенного, но пробовал некоторые возможности Kotlin
