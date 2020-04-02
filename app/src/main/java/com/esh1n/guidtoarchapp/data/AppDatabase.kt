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
                    "Медицина"
                    ,
                    true
                ),
                ArticleEntry(
                    "Пожар",
                    "{image:ic_compass}" +
                            "{text:1. При небольшом возгорании попытаться потушить пожар водой, песком, плотной тканью или специальными средствами пожаротушения. Запрещается тушить водой электроприборы.}" +
                            "{text:2. Предупредить о пожаре остальных людей, не допуская паники.}" +
                            "{text:3. При значительном распространении пламени немедленно покинуть помещение. Пользоваться лифтами запрещается.}" +
                            "{text:4. При задымлении путей эвакуации дышать через влажную ткань, передвигаться, пригибаясь к полу. Помнить, что дым очень токсичен!}" +
                            "{text:5. При невозможности покинуть помещение — оставаться в комнате, закрыв окна и двери, привлекать внимание очевидцев через стекло.}" +
                            "{text:6. Встретить пожарных, показать машине удобный путь к очагу возгорания}" +
                            "{text:Продумывая, как действовать при пожаре в случае его самостоятельного тушения, не стоит преуменьшать угрозу даже незначительного возгорания. При подходящих условиях огонь может распространиться очень быстро, а густой дым может проникнуть и через мокрую ткань, лишив сознания за несколько вдохов.}",
                    "Быт"
                    ,
                    true
                ),
                ArticleEntry("Утечка газа",
                    "{text:Первым делом позвони в службу газа.\n" +
                        "Звонить нужно из помещения, не наполненного газом. До приезда аварийной службы:\n" +
                        "- перекрой газопроводный кран\n" +
                        "- проветри помещение(устрой сквозняк)\n" +
                        "- не зажигай огонь, не включай и не выключай никаких электроприборов\n" +
                        " покинь помещение и не заходи в него до исчезновения запаха газа\n" +
                        " \n" +
                        "при появлении у окружающих признаков отравления газом выведи их на свежий воздух и положи так, чтобы голова находилась выше ног. Вызови скорую медицинскую помощь.\n}{image:ic_phone}{text:Быт22}", "Быт", false),
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
                    "Терроризм2",
                    "{text:Text1}{image:ic_compass}{text:Text1}",
                    "Терроризм", false
                )
            )
            wordDao.insertAll(categories)
            articleDao.insertAll(articles)
        }
    }
}
