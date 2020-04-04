package com.esh1n.guidtoarchapp.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@Database(entities = [CategoryEntry::class, ArticleEntry::class], version = 4)
public abstract class AppDatabase : RoomDatabase() {
    abstract fun wordDao(): CategoryDao
    abstract fun articlesDao(): ArticleDao


    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                // Create database here
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "Word_database"
                ).addCallback(WordDatabaseCallback(scope))
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }

    private class WordDatabaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {

        override fun onOpen(db: SupportSQLiteDatabase) {
            super.onOpen(db)
            INSTANCE?.let { database ->
                scope.launch(Dispatchers.IO) {
                    populateDatabase(database.wordDao(), database.articlesDao())
                }
            }
        }

        suspend fun populateDatabase(wordDao: CategoryDao, articleDao: ArticleDao) {
            wordDao.deleteAll()

            val categories = arrayListOf(
                CategoryEntry("Медицина", "medicine"),
                CategoryEntry("Быт", "human"),
                CategoryEntry("Терроризм", "security")
            )
            val articles = arrayListOf(
                ArticleEntry(
                    "Ожог",
                    "{image:ic_ozog}" +
                            "{text:1. Потуши огонь: залей водой, накрой одеялом.\n" +
                            "2. Убери одежду от зоны ожога. Не отрывай, если прилипло!\n" +
                            "3. Сними украшения, ремень рядом с зоной ожога.\n" +
                            "3. Поливай ожог прохладной проточной водой 20 минут.\n" +
                            "4. Не используй для обработки ожога масло, крем, лед, ледяную воду!\n" +
                            "5. Держи пострадавшего в тепле.\n" +
                            "6. Покрой область ожога прохладной влажной повязкой или чистой тканью.\n" +
                            "7. Подними рану выше уровня сердца, если это возможно.\n" +
                            "\n"+
                            "Вызови скорую, если:\n" +
                            "\n" +
                            "- поврежденная область кажется обугленной или имеет пятно белого, коричневого или черного цвета\n" +
                            "- повреждение по размеру больше указательного пальца или покрывает руку, ногу, лицо, пах, ягодицы\n" +
                            "- ожог химический или электрический\n" +
                            "- есть признаки шока: холодная, липкая кожа, повышенное потоотделение, учащенное дыхание, слабость или головокружение\n" +
                            "- возраст пострадавшего менее 5 лет или более 60, есть серьезные сопутствующие заболевания\n}"
                    ,
                    "Медицина",
                    true
                ),
                ArticleEntry(
                    "Электрический ожог",
                    "{text:1. Срочно вызови скорую помощь.\n" +
                            "2. Если человек получил травму от источника низкого напряжения (до 220-240 В), например, от бытовой электросети, безопасно отключи питание. Отсоедени человека от источника питания, используя материал, не проводящий электричество: деревянная палочка или деревянный стул.\n" +
                            "3. Не приближайся к человеку, который подключен к источнику высокого напряжения (1000В или более).\n}}",
                    "Медицина",
                    true
                ),
                ArticleEntry(
                    "Кровотечение",
                    "{text:Определи как течет кровь:\n" +
                            "- пульсирующая струя алого цвета, течет быстро = артериальное кровотечение\n" +
                            "- кровь течет медленно, темная = венозное кровотечение\n" +
                            "- кровь течет не активно, возможно, каплями = капиллярное кровотечение\n}" +
                            "{textBold:Артериальное кровотечение: из конечности\n}" +
                            "{text:- если возможно, подними конечность(руку/ногу)\n" +
                            "- найди подручное средство для создания жгута\n" +
                            "- наложи жгут выше уровня кровотечения\n" +
                            "- наложи жгут на одежду или положи на голую кожу ткань/одежду (чтобы не травмировать кожу жгутом)\n" +
                            "- первые 2 оборота жгута-тугие\n" +
                            "- напиши записку со временем наложения жгута (на бумажке, жгуте или коже)\n" +
                            "- ослабь жгут на 10-15 минут: через 90 минут в теплом помещении и через 60 минут в холодном\n" +
                            "- на время ослабления жгута прижми место кровотечения пальцем/тампоном\n" +
                            "- если скорая помощь еще не приехала, наложи жгут повторно на 1-2см выше предыдущего места\n" +
                            "\n" +
                            "Жгут - скрученный отрез ткани, ремень, резиновый шнур или любой иной подручный материал.\n}\n" +
                            "{textBold:Артериальное кровотечение: туловище/голова\n}" +
                            "{text:- При кровотечениях в области лица – нажми большим пальцем на угол нижней челюсти.\n" +
                            "- В случае кровотечения из головы – надави на область височной кости впереди уха.\n" +
                            "- В области плечевого сустава – прижми подключичную артерию к ребру.\n" +
                            "- Кисть – прижми плечевую артерию к кости со стороны плеча.\n" +
                            "- Если нарушена целостность бедренной артерии – кулаком надави на лобковую кость в области паха.\n}\n" +
                            "{textBold:Венозное кровотечение:\n}" +
                            "{text:- закрой рану несколькими слоями бинта, салфеток или любым чистым отрезком ткани. Сверху положи вату, если есть\n" +
                            "- туго зафиксируй все с помощью бинта, платка или отрезка ткани нужной ширины. \n" +
                            "- если повреждена конечность, подними ее выше уровня тела и зафиксируй\n" +
                            "- если повреждена конечность и после тампонирования и тугого бинтования кровотечение продолжается, наложи жгут\n}\n" +
                            "{textBold:Капиллярное кровотечение:\n}" +
                            "{text:- очисть рану\n" +
                            "- обработай антисептиком\n" +
                            "- наложи стерильную повязку(марля)\n" +
                            "- если повреждена конечность, приподними выше уровня туловища\n}",
                    "Медицина",
                    true
                ),
                ArticleEntry(
                    "Пожар",
                    "{text:1. При небольшом возгорании попытаться потушить пожар водой, песком, плотной тканью или специальными средствами пожаротушения. Запрещается тушить водой электроприборы.}" +
                            "{text:2. Предупредить о пожаре остальных людей, не допуская паники.}" +
                            "{text:3. При значительном распространении пламени немедленно покинуть помещение. Пользоваться лифтами запрещается.}" +
                            "{text:4. При задымлении путей эвакуации дышать через влажную ткань, передвигаться, пригибаясь к полу. Помнить, что дым очень токсичен!}" +
                            "{text:5. При невозможности покинуть помещение — оставаться в комнате, закрыв окна и двери, привлекать внимание очевидцев через стекло.}" +
                            "{text:6. Встретить пожарных, показать машине удобный путь к очагу возгорания}" +
                            "{text:Продумывая, как действовать при пожаре в случае его самостоятельного тушения, не стоит преуменьшать угрозу даже незначительного возгорания. При подходящих условиях огонь может распространиться очень быстро, а густой дым может проникнуть и через мокрую ткань, лишив сознания за несколько вдохов.}",
                    "Быт",
                    true
                ),
                ArticleEntry(
                    "Лифт",
                    "{textBold:Если лифт остановился:\n}" +
                            "{text:- не поддавайся панике;\n" +
                            "- не пытайся самостоятельно открыть двери лифта\n" +
                            "- вызови диспетчера и выслушайте указания по дальнейшим действиям\n}" +
                            "{textBold:Если оборвался трос лифта:\n}" +
                            "{text:- нажми кнопку «Стоп»\n" +
                            "- прижмись спиной к стенке кабины, сядь на корточки, упрись руками в пол\n" +
                            "- не пытайся связаться с диспетчером(береги время)\n" +
                            "- не нажимай на кнопку, открывающую двери;\n" +
                            "- чем ты будешь менее подвижен в момент остановки, тем меньше пострадаешь: на дне шахты расположены амортизаторы.}",
                    "Быт",
                    false
                ),

                ArticleEntry("Утечка газа",
                    "{text:Первым делом позвони в службу газа.\n" +
                        "Звонить нужно из помещения, не наполненного газом. До приезда аварийной службы:\n" +
                        "- перекрой газопроводный кран\n" +
                        "- проветри помещение(устрой сквозняк)\n" +
                        "- не зажигай огонь, не включай и не выключай никаких электроприборов\n" +
                        " покинь помещение и не заходи в него до исчезновения запаха газа\n" +
                        " \n" +
                        "При появлении у окружающих признаков отравления газом выведи их на свежий воздух и положи так, чтобы голова находилась выше ног. Вызови скорую медицинскую помощь.\n}",
                    "Быт",
                    false
                ),
                ArticleEntry(
                    "Взрыв",
                    "{text:\n" +
                            "1) Сохраняй спокойствие.\n" +
                            "2) Если есть угроза повторного взрыва - найди укрытие. Если времени нет - ложись на землю, прикрыв голову.\n" +
                            "3) ПОЗВОНИ 112.\n" +
                            "4) Осторожно осмотри себя. Не пытайся сразу встать.\n" +
                            "5) Если у тебя незначительные повреждения - окажи себе помощь.\n" +
                            "6) Окажи первую помощь тяжелораненным.}",
                    "Терроризм",
                    false
                ),
                ArticleEntry(
                    "Захват в заложники",
                    "{textBold:Если ты оказался в заложниках, придерживайся следующих правил:\n}" +
                            "{text:- не двигайся\n" +
                            "- не ведись на провокации\n" +
                            "- не сопротивляйся\n" +
                            "- выполняй требования террористов\n" +
                            "- ничего не предпринимай, пока не получишь разрешения\n}" +
                            "{textBold:Во время проведения спецслужбами операции по вашему освобождению неукоснительно соблюдай следующие требования:\n}" +
                            "{text:- лежите на полу лицом вниз, голову закройте руками и не двигайтесь\n" +
                            "- не беги навстречу сотрудникам спецслужб или от них, так как они могут принять тебя за преступника\n" +
                            "-  держитесь подальше от проемов дверей и окон\n}",
                    "Терроризм",
                    false
                ),
                ArticleEntry(
                    "Стрельба",
                    "{textBold:Если началась стрельба:\n}" +
                            "{text:- не беги. Если все же бежать, единственный шанс на спасение бежать зигзагом.\n" +
                            "- ложись. Лицом вниз, руки и ноги согнуты – так ты защитишь самые крупные артерии.\n" +
                            "- найди безопасное место. Подальше от окон, стеклянных предметов. Если есть возможность – беги в ванную, забаррикадируйся, лучше лечь на дно ванны. На улице спрятаться можно за выступы зданий, колонны, памятники. Нельзя прятаться рядом со стеклянными витринами, автомобилями.\n" +
                            "- передвигайся короткими перебежками, перекатыванием, ползком.\n}",
                    "Терроризм",
                    false
                )
            )
            wordDao.insertAll(categories)
            articleDao.insertAll(articles)
        }
    }
}
