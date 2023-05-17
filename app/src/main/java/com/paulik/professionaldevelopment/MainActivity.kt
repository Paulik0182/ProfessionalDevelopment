package com.paulik.professionaldevelopment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.transform
import kotlinx.coroutines.flow.zip
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    lateinit var flowOne: Flow<String>
    lateinit var flowTwo: Flow<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        getFlow()
        startFlow()
        setupFlows()
        zipFlows()
//        combineFlows()
//        transformFlow()
        catchError()
        findViewById<Button>(R.id.button_search).setOnClickListener {
            startActivity(Intent(this, SearchActivity::class.java))
        }
    }

    /**возвращает поток данных, который последовательно испускает числовые значения от 0 до 10.
     * Метод getFlow в Retrofit является альтернативой RxJava - библиотеке, которая также
     * позволяет работать с асинхронными потоками данных.
     * В Kotlin Flow реализована концепция Coroutine. */
    private fun getFlow(): Flow<Int> = flow {
        Log.d("@@@", "Start flow")
        (0..10).forEach {
            delay(500) // установили задержку в созданом потоке
            Log.d("@@@", "MainActivity - getFlow -> Emitting $it")
            emit(it)
            /** метод emit используемый в потоке использует оператор send, для того, чтобы передать данные из потока. */
        }
    }
        .map { it * 2 } // Каждое число умножается на два в операторе map, после чего поток переключается на Dispatchers.Default
        .flowOn(Dispatchers.Default)

    /** метод flowOn переводит выполнение потока на другой поток, если в потоке будет продолжение
     * с корутином, то она будет выполнена на том потоке, на котором завершится метод flowOn.
     *
     * Оператор flowOn используется в данном случае для переключения выполнения потока на поток
     * Dispatchers.Default. Это означает, что оператор map, который умножает каждый элемент потока
     * на два, будет выполнен на потоке Dispatchers.Default, а не на потоке, где был запущен
     * исходный код.
     *
     * Dispatchers.Default - это диспетчер перенаправляет выполнение сопрограмм на пул потоков,
     * созданный в соответствии с количеством процессоров в системе.
     * Если в системе есть, четыре процессора, то к пулу потоков будет добавлено четыре потока,
     * на которых будут выполняться сопрограммы из диспетчера Dispatchers.Default.
     * Таким образом, этот диспетчер может эффективно использовать доступные ресурсы процессора
     * и ускорить выполнение операций.
     * Использование Dispatchers.Default не всегда является оптимальным решением. Если выполняете
     * операции с вводом-выводом, тогда оптимальным использовать диспетчер Dispatchers.IO,
     * оптимизированный для ввода-вывода и блокировок.*/

    private fun startFlow() {
        findViewById<Button>(R.id.button).setOnClickListener {
            CoroutineScope(Dispatchers.Main).launch {// launch { } запускает новую корутину в контексте созданного CoroutineScope.
                //import kotlinx.coroutines.flow.collect
                getFlow().collect {
                    Log.d("@@@", "startFlow -> ${it.toString()}")
                }
            }
        }
        /** Внутри функции создается экземпляр CoroutineScope с заданным контекстом
         * Dispatchers.Main, из которого запускается корутин с помощью функции launch.
         * Контекст Dispatchers.Main является контекстом, в котором корутины запускаются
         * на главном потоке (UI-потоке) приложения Android.
         *
         *  Функция getFlow(), которая возвращает Flow<t>, т.е. поток данных, где тип данных
         *  T - это Int. Затем, с помощью функции collect(), происходит получение элементов
         *  потока данных и их обработка.
         *
         *  В итоге, функция startFlow() использует функцию getFlow(), который формирует поток
         *  данных в виде чисел и отправляет их в поток данных. Каждый элемент этого потока
         *  отображается в логе.*/
    }

    /** Создает два flow (потока) данных. */
    private fun setupFlows() {
        flowOne = flowOf(
            "Юрий",
            "Александр",
            "Иван"
        ).flowOn(Dispatchers.Default)

        flowTwo = flowOf(
            "Гагарин",
            "Пушкин",
            "Грозный"
        ).flowOn(Dispatchers.Default)

        /** flowOf() - функция создания flow, которая принимает на вход элементы потока и
         * возвращает flow, содержащий эти элементы. В данном случае создается flow с тремя
         * элементами".
         *
         * flowOn() - функция, которая позволяет указать диспатчер, на котором будет происходить
         * выполнение flow. В данном случае flow будет выполняться на диспатчере Dispatchers.Default.
         *
         * Dispatchers.Default, в данном случае нет необходимости выполнять поток на особом
         * диспатчере, например в главном потоке. Dispatchers.Default используется по умолчанию
         * и оптимален для обработки "легких" задач, таких как, например, создание потоков с
         * небольшим количеством данных.
         *
         * Оба созданных потока могут объединяться для выполнения параллельно, с помощью функций
         * combine() и zip(), или использоваться последовательно в цепочке операторов для
         * преобразования данных.*/
    }

    /** Функция запускает новую корутину для объединения созданных двух flow, используя функцию zip().*/
    private fun zipFlows() {
        findViewById<Button>(R.id.button).setOnClickListener {
            CoroutineScope(Dispatchers.Main).launch {
                flowOne.zip(flowTwo)
                { firstString, secondString ->
                    "$firstString $secondString"
                }.collect {
                    Log.d("@@@", it)
                }
            }
        }
        /** flowOne.zip(flowTwo) - функция zip() объединяет два потока данных flowOne и flowTwo в
         * один, при этом элементы первого и второго потоков соответственно попарно будут комбинироваться.
         *
         * { firstString, secondString -> "$firstString $secondString" } - это лямбда-выражение,
         * которое принимает два элемента из попарно сопоставленных потоков flowOne и flowTwo,
         * объединяет их в строку и возвращает значение типа String. Это значение будет содержать
         * результат объединения элементов.
         *
         * .collect { Log.d("@@@", it) } - функция collect() позволяет получать элементы из
         * объединенного потока данных и обрабатывать их. Log.d() выводит каждый элемент.
         *
         * Таким образом, функция zipFlows() объединяет два созданных потока данных flowOne и
         * flowTwo в один, каждый раз комбинируя элементы из соответствующих потоков попарно и
         * выводя их результат в журнал логов в корутине, запущенной в главном потоке приложения.*/
    }

    /** используем два потока данных, но на этот раз используем функцию combine() вместо zip().*/
    private fun combineFlows() {
        findViewById<Button>(R.id.button).setOnClickListener {
            CoroutineScope(Dispatchers.Main).launch {
                flowOne.combine(flowTwo)
                { firstList, secondList ->
                    combineResult(listOf(firstList), listOf(secondList))
                }.collect {
                    Log.d("@@@", it.toString())
                }
            }
        }
        /** { firstList, secondList -> combineResult(firstList, secondList) } - это
         * лямбда-выражение принимает два элемента из потоков flowOne и flowTwo, возвращает их
         * объединение в виде нового элемента, используя функцию combineResult().
         *
         * Выполнение программы прекратится, как только данные закончатся хотя бы в одном потоке.
         * То есть, если в одном потоке будет три строки, а в другом — пять, программа завершится
         * объединением третьих строк. */
    }

    private fun combineResult(firstList: List<String>, secondList: List<String>): List<String> {
        val resultList = mutableListOf<String>()
        resultList.addAll(firstList)
        resultList.addAll(secondList)
        return resultList
    }

    /** преобразем элементы потока при помощи оператора transform()*/
    private fun transformFlow() {
        CoroutineScope(Dispatchers.Main).launch {
            flowOne.transform {
                emit("$it!")
            }.collect {
                Log.d("@@@", it)
            }
        }
        /** flowOne.transform { emit("$it!") } - функция transform() принимает на вход
         * лямбда-выражение, которое преобразует каждый элемент из потока flowOne, добавляя в
         * его конец символ !, с помощью функции emit().*/
    }

    /** Используем несколько функций для работы с потоком данных*/
    private fun catchError() {
        CoroutineScope(Dispatchers.Main).launch {
            (1..5).asFlow()
                .map {
                    //выбрасывается ошибка, если значение == 3
                    check(it != 3) { "Значение == $it" } //текст ошибки
                    it * it
                }
                .onCompletion {
                    Log.d("@@@", "catchError() -> onCompletion - $it")
                }
                .catch { e ->
                    Log.d("@@@", "catchError() .catch -> Ошибка: $e")
                }
                .collect {
                    Log.d("@@@", "catchError() .collect -> $it")
                }
        }

        /**
         * Функция asFlow() помогает сконвертировать некоторые типы данных во Flow
         * asFlow() - создает поток данных из коллекции, в данном случае - из диапазона
         * целых чисел 1..5.
         *
         * map { } - применяет лямбда-выражение к каждому элементу в потоке данных и возвращает
         * поток с преобразованными значениями. Здесь выполняется проверка на равенство
         * элемента 3, и если оно выполняется, то выбрасывается исключение с соответствующим текстом.
         *
         * onCompletion { } - вызывается после завершения работы потока данных, в данном случае,
         * выводит сообщение в лог о том, что работа потока завершилась.
         *
         * catch { } - перехватывает исключения, выброшенные в потоке данных, и передает их
         * для последующей обработки. Здесь перехватывается исключение kotlin.IllegalStateException,
         * вызванное в проверке на равенство элемента 3 в лямбда-выражении функции map.
         * Никогда не пытайтесь поймать и обработать ошибку потока с помощью блока try-catch.
         *
         * collect { } - получает элементы из потока данных и обрабатывает их в коде. В данном
         * случае, выводит в лог каждый полученный элемент из потока данных.
         *
         * Вывод, данная функция показывает, как можно обрабатывать ошибки, возникающие в потоках
         * данных, с помощью функции catch(). При выполнении операции возведения элементов из
         * диапазона целых чисел в квадрат, функция check() проверяет наличие элемента со
         * значением 3 в потоке данных и, в случае нахождения такого элемента, выбрасывает
         * исключение, содержащее сообщение с этим значением. При обработке потока данных
         * функция catch() перехватывает это исключение и выводит его сообщение в лог.
         * Исключение перехватывается только при поступлении ошибки во время работы потока данных,
         * и затем выполнение кода продолжается нормально. Функция onCompletion() выводит
         * сообщение в лог при завершении работы потока данных.*/
    }
}

/** функции - zip(), combine() и transform() - демонстрирует различные способы работы с потоками
 * данных в Kotlin.
 *
 * zip() - для объединения элементов из двух потоков данных, чтобы они формировали пары.
 *
 * combine() - для объединения двух потоков данных с помощью лямбда-выражения и функции,
 * которая объединяет элементы.
 *
 * transform() - для преобразования элементов в потоке данных, например, добавляя к ним символ !.
 *
 * Существуют и другие функции и операторы, которые можно использовать для дальнейшей обработки
 * и фильтрации потоков данных.*/