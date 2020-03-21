package com.esh1n.guidtoarchapp.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@Database(entities = [CategoryEntry::class, ArticleEntry::class], version = 3)
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
                            "{textBold:Первая помощь\n}" +
                            "{text:\n" +
                            "1. Потуши огонь: залей водой, закрой одеялом.\n" +
                            "2. Убери одежду от зоны ожога. Не отрывай, если прилипло!\n" +
                            "3. Сними украшения, ремень рядом с зоной ожога.\n" +
                            "3. Поливай ожог прохладной или теплой проточной водой не менее 20 минут.\n" +
                            "4. НЕ используй для обработки ожога масло, крем, лед, ледяную воду!\n" +
                            "5. Держи пострадавшего в тепле.\n" +
                            "6. Покрой область ожога прохладной влажной повязкой или чистой тканью.\n" +
                            "7. Подними рану выше уровня сердца, если это возможно.\n" +
                            "\n}" +
                            "{textBold:Вызови скорую, если:\n}" +
                            "{text:\n" +
                            "- кожа выглядит сухой и жесткой\n" +
                            "- поврежденная область кажется обугленной или имеет пятно белого, коричневого или черного цвета\n" +
                            "- повреждение больше 8 см в диаметре или покрывает руку, ногу, лицо, пах, ягодицы\n" +
                            "- ожог химический или электрический\n" +
                            "- есть признаки шока: холодная, липкая кожа, повышенное потоотделение, учащенное дыхание, слабость или головокружение\n" +
                            "- возраст пострадавшего менее 5 лет или более 60, есть серьезные сопутствующие заболевания\n}"
                    ,
                    "Медицина"
                ),
                ArticleEntry(
                    "Электрический ожог",
                    "{text:1. Срочно вызвать скорую помощь.\n" +
                            "2. Если травма от источника напряжения до 220-240В - от бытовой электросети, отключи питание. Отсоедени человека от источника питания, используя подручный материал: деревянная палочка или деревянный стул.\n" +
                            "3. Не приближайся к человеку, который подключен к источнику высокого напряжения (1000В или более).\n}{image:ic_compass}{text:Медицина}",
                    "Медицина"
                ),
                ArticleEntry(
                    "Пожар",
                    "{image:ic_compass}" +
                            "{text:1. При небольшом возгорании попытаться потушить пожар водой, песком, плотной тканью или специальными средствами пожаротушения. Запрещается тушить водой электроприборы.}" +
                            "{text:2. Предупредить о пожаре остальных людей, не допуская паники.}" +
                            "{text:3. При значительном распространении пламени немедленно покинуть помещение. Пользоваться лифтами запрещается.}" +
                            "{text:4. При задымлении путей эвакуации дышать через влажную ткань, передвигаться, пригибаясь к полу. Помнить, что дым очень токсичен!}" +
                            "{text:5. При невозможности покинуть помещение — оставаться в комнате, закрыв окна и двери, привлекать внимание очевидцев через стекло.}" +
                            "{text:6. Встретить пожарных, показать машине удобный путь к очагу возгорания\n}" +
                            "{text:Продумывая, как действовать при пожаре в случае его самостоятельного тушения, не стоит преуменьшать угрозу даже незначительного возгорания. При подходящих условиях огонь может распространиться очень быстро, а густой дым может проникнуть и через мокрую ткань, лишив сознания за несколько вдохов.}",
                    "Быт"
                ),
                ArticleEntry(
                    "Быт2",
                    "{text: 1}" +
                            "{image:ic_phone}", "Быт"),
                ArticleEntry("Стрельба",
                    "{text:- Не бегите. Если все же бежать в полный рост - бегите зигзагом.\n" +
                        "- Ложитесь. Лицом вниз, руки и ноги согнуты – так вы защитите самые крупные артерии.\n" +
                        "- Найдите безопасное место. Подальше от окон, стеклянных предметов. В доме - ванная комната. Если есть возможность – забаррикадируйте двери, лучше лечь на дно ванны – так вы максимально защищены от пуль.\n " +
                            "- На улице спрятаться можно за выступы зданий, колонны, памятники. Нельзя прятаться рядом со стеклянными витринами, автомобилями.\n" +
                        "- Передвигайтесь короткими перебежками, перекатыванием, ползком. Передвигайтесь только в том случае, когда надо перебраться в безопасное место.\n}" +
                            "{image:ic_phone}" +
                            "{text:.}",
                    "Терроризм"),
                ArticleEntry(
                    "Захват в заложники",
                    "{text:Если вы оказались в заложниках, рекомендуем придерживаться следующих правил поведения:\n" +
                            "\n- неожиданное движение или шум могут повлечь жестокий отпор со стороны террористов \n" +
                            "- будьте готовы к применению террористами повязок на глаза, кляпов, наручников или веревок\n" +
                            "- переносите лишения, оскорбления и унижения, не смотрите преступникам в глаза (для нервного человека это сигнал к агрессии)\n" +
                            "- не пытайтесь оказывать сопротивление, не проявляйте ненужного героизма, пытаясь разоружить бандита или прорваться к выходу или окну\n" +
                            "- если вас заставляют выйти из помещения, говоря, что вы взяты в заложники, не сопротивляйтесь\n" +
                            "- при необходимости выполняйте требования преступников, не противоречьте им, не рискуйте жизнью окружающих и своей собственной\n" +
                            "- в случае, когда необходима медицинская помощь, говорите спокойно и кратко, ничего не предпринимайте, пока не получите разрешения.\n" +
                            "\nВо время проведения спецслужбами операции по вашему освобождению неукоснительно соблюдайте следующие требования:\n" +
                            "\n - лежите на полу лицом вниз, голову закройте руками и не двигайтесь\n" +
                            "- ни в коем случае не бегите навстречу сотрудникам спецслужб или от них, так как они могут принять вас за преступника\n" +
                            "- если есть возможность, держитесь подальше от проемов дверей и окон.\n}",
                    "Терроризм"
                ),
                        ArticleEntry(
                        "Взрыв",
                "{text:1) Сохраняйте спокойствие.}" +
                        "{text: 2) Если есть угроза повторного взрыва - найдите укрытие. Если времени нет - ложитесь на землю, прикрыв голову.}" +
                        "{text:3)Позвоните 103}" +
                        "{text:4) Осторожно осмотрите себя. Не пытайтесь сразу встать. \n" +
                        "5) Если у вас есть даже незначительные повреждения - окажите себе помощь. \n" +
                        "6) Окажите первую помощь тяжелораненым.}" +
                        "{image:}",
               "Терроризм")

            )
            wordDao.insertAll(categories)
            articleDao.insertAll(articles)
        }
    }
}
