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
                CategoryEntry("Терроризм", "security"),
                CategoryEntry("Природные катаклизмы", "security"),
                CategoryEntry("Безопасность в сети", "security"),
                CategoryEntry("Насилие", "security"),
                CategoryEntry("Половое воспитание", "security")
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
                    "Холодовая травма",
                    "Холодовая травма – расстройство функций организма из-за понижения температуры тела\n" +
                            "Переохлаждение – все тело\n" +
                            "Обморожение – локально\n" +
                            "\n" +
                            "Признаки:\n" +
                            "\n" +
                            "дрожь, озноб\n" +
                            "потеря чувствительности\n" +
                            "ощущение покалывания или пощипывания\n" +
                            "\n" +
                            "Первая помощь\n" +
                            "\n" +
                            "переместить пострадавшего в тёплое помещение\n" +
                            "укутать подручными средствами\n" +
                            "закрыть сухой повязкой отмороженную часть тела (согревание должно происходить «изнутри» с одновременным восстановлением кровообращения)\n" +
                            "обильное теплое и сладкое питье (согреваем изнутри)\n" +
                            "\n" +
                            "! Пораженные участки нельзя активно согревать (опускать в горячую воду), растирать, массировать, смазывать чем-либо.\n" +
                            "\n" +
                            "Степени обморожения\n" +
                            "\n" +
                            "побеление кожи - 1 степень обморожения\n" +
                            "волдыри - 2 степень обморожения (видно только после отогревания, возможно проявление через 6-12 часов)\n" +
                            "потемнение и отмирание - 3 степень обморожения (видно только после отогревания)",
                    "Медицина",
                    true                ),
                ArticleEntry(
                    "Электрический ожог",
                    "{text:1. Срочно вызови скорую помощь.\n" +
                            "2. Если человек получил травму от источника низкого напряжения (до 220-240 В), например, от бытовой электросети, безопасно отключи питание. Отсоедени человека от источника питания, используя материал, не проводящий электричество: деревянная палочка или деревянный стул.\n" +
                            "3. Не приближайся к человеку, который подключен к источнику высокого напряжения (1000В или более).\n}}",
                    "Медицина",
                    true
                ),
                ArticleEntry(
                    "Перелом/вывих",
                    "{textBold:Признаки вывиха:\n}" +
                            "{text:- изменена форма сустава\n" +
                            "- резкая боль\n" +
                            "- отек\n" +
                            "- невозможность движения\n" +
                            " \n}" +
                            "{textBold:Признаки перелома:\n}" +
                            "{text:- внешнее укорочение конечности\n" +
                            "- изменение формы конечности\n" +
                            "- боль\n" +
                            "- невозможность движения\n" +
                            "- может быть нарушение целостности кожи, кровь\n" +
                            "\n}" +
                            "{textBold:Что делать?\n}" +
                            "{text:- останови кровотечение,если оно есть\n" +
                            "- иммобилизируй конечность(обеспечь неподвижность)\n" +
                            "- при подозрении на перелом позвоночника не переворачивать, не двигать пострадавшего!!!\n" +
                            "- приложи холод на 10 минут\n" +
                            "- не дай больному переохладиться (укрой одеялом, помоги занять удобное положение)\n" +
                            "\n}" +
                            "{textBold:При невозможности немедленно вызвать врача наложи шину(при переломе конечности)\n}" +
                            "{text:- останови кровотечение\n" +
                            "- дай обезболивающее(Нурофен, Найз и т.п.)\n" +
                            "- наложи шину с двух сторон от поврежденной конечности, зафиксируй бинтом выше и ниже области перелома\n" +
                            "- длина шины минимум на 1 сустав выше и ниже перелома\n" +
                            "- при переломе бедра: шины от подмышечной впадины до пятки и от паха до пятки\n" +
                            "- при отсутствии шины фиксируй здоровую ногу к больной, поврежденную руку к туловищу\n" +
                            "\n}" +
                            "{textBold:Шина: доска, палка, лыжа и т.п.\n}",
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
                            "- если повреждена конечность, приподними выше уровня туловища\n} +" +
                            "{textBold:Носовое кровотечение:\n}" +
                            "{text:- занять положение сидя, наклонить голову вперед\n" +
                            "- если кровотечение продолжается более 20 минут обратиться за медицинской помощью.}",
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
                    "Остановился лифт",
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
                ),
                ArticleEntry(
                    "Землетрясение",
                    "Если ты находишься в помещении, когда начинаются толчки:\n" +
                            "▪Если есть возможность – выбегай на улицу на открытый участок, подальше от домов, деревьев.\n" +
                            "▪Если ты находишься в многоэтажном доме, открой дверь.\n" +
                            "▪Ложись к стене/в дверном проеме, закрой голову и шею руками.\n" +
                            "▪Если ты в кровати, оставайся в ней, закрой голову подушкой.\n" +
                            "НЕ НАХОДИТЬСЯ рядом с окнами, висящими предметами, высокой мебелью\n" +
                            "Если ты находишься на улице:\n" +
                            "1. Найди открытый участок, подальше от домов, линий электропередач, деревьев.\n" +
                            "2. Если уйти подальше от здания нет возможности, встань в дверной проем\n" +
                            "3. После землетрясения:\n" +
                            "4. Окажи первую помощь нуждающимся. \n" +
                            "5. Освободи попавших в легкоустранимые завалы.\n" +
                            "6. Включи радио\n" +
                            "Спускаясь по лестнице, убедись в ее прочности. \n" +
                            "Не подходи к явно поврежденным зданиям.\n" +
                            "Будь готов к сильным повторным толчкам.\n" +
                            "Не выдумывай и не передавай никаких слухов о возможных повторных толчках. \n" +
                            "\n" +
                            "Если ты в завале, спокойно оцени обстановку, по возможности окажи себе первую помощь. \n" +
                            "Постарайся установить связь с людьми, зажигать огонь нельзя, а трубы и батареи можно использовать для подачи сигнала, стуча по ним. Экономь силы.\n",
                    "Природные катаклизмы",
                    true
                ),
                ArticleEntry(
                    "Лесной пожар",
                    "Если ты увидел возгорание в лесу, необходимо сразу же сообщить об этом, даже если горит совсем маленький участок. Можно позвонить по единому номеру вызова экстренных оперативных служб – 112, а также на прямую линию лесной охраны – 8(800)100-94-00.\n" +
                            "\n" +
                            "Операторам нужно максимально точно назвать место пожара. Нужно сообщить, что именно горит – деревья, трава, дымится почва.\n" +
                            "\n" +
                            "При обнаружении лесного пожара:\n" +
                            "- не стоит лезть в огонь и пытаться потушить возгорание, лучше отойти от него подальше и дождаться прибытия служб, занимающихся тушением.\n" +
                            "- уходить от пожара нужно поперек ветра. \n" +
                            "- сделать влажную повязку на лицо/дышать через дополнительный слой ткани.\n" +
                            "- не прятаться в ямах, оврагах и на деревьях\n" +
                            "\n" +
                            "Для предотвращения появления лесного пожара необходимо соблюдать общие правила безопасности:\n" +
                            "\n" +
                            "• не разводить костер в лесу, если же он разведен в безопасном месте, то не уходить, оставив его непотушенным\n" +
                            "• не использовать легковоспламеняющиеся жидкости\n" +
                            "• не выжигать траву и солому\n" +
                            "• не бросать в лесу горящие спички, окурки\n",
                    "Природные катаклизмы",
                    false
                ),
                ArticleEntry(
                    "Перегрев",
                    "\n" +
                            "Тепловой удар \n" +
                            "— сильный перегрев организма\n" +
                            "\n" +
                            "Симптомы \n" +
                            "- Высокая температура тела (40 С и выше).\n" +
                            "- Жажда.\n" +
                            "- Отсутствие потоотделения. При тепловом ударе, вызванном жаркой погодой, кожа становится горячей и сухой на ощупь. А при тепловом ударе, вызванном напряженной физической работой, кожа обычно влажная, липкая.\n" +
                            "- Покраснение кожного покрова\n" +
                            "- Учащенное дыхание\n" +
                            "- Повышение чсс \n" +
                            "- Головная боль\n" +
                            "- Реже судороги, галлюцинации, потеря сознания.\n" +
                            "\n" +
                            "Первая помощь:\n" +
                            "\n" +
                            "Перенести пострадавшего в тень или прохладное помещение и положить его, приподняв голову.\n" +
                            "\n" +
                            "Снять одежду, ослабить ремень.\n" +
                            "\n" +
                            "Приложить к голове холодный компресс. Тело обтереть холодной водой, начиная с области сердца, или обернуть влажной простыней.\n" +
                            "\n" +
                            "Напоить прохладной водой.\n" +
                            "\n" +
                            "В случае потери сознания возбудить дыхание нашатырным спиртом и перевернуть пострадавшего на бок.\n",
                    "Медицинв",
                    false
                ),
                ArticleEntry(
                    "Пищевое отравление",
                    "Признаки пищевого отравления:\n" +
                            "- Тошнота\n" +
                            "- Рвота\n" +
                            "- Диарея\n" +
                            "- Боли в желудке\n" +
                            "- Повышение/понижение температуры\n" +
                            "\n" +
                            "Первая помощь\n" +
                            "1. Много пей, постоянно маленькими глотками.\n" +
                            "2. Не принимай препараты от диареи.\n" +
                            "Обязательно вызови врача, если:\n" +
                            "- Симптомы продолжаются более 3х дней\n" +
                            "- В стуле появилась кровь\n" +
                            "- Болеет ребенок или пожилой человек\n" +
                            "Вызвать скорую помощь при:\n" +
                            "Подозрениях на отравление грибами, химикатами, ботулином\n" +
                            "Если пострадавший - ребенок или пожилой человек\n",
                    "Медицина",
                    false
                )
            )
            wordDao.insertAll(categories)
            articleDao.insertAll(articles)
        }
    }
}
