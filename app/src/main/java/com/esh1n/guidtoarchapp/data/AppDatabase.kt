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
                    "Первая помощь при ожоге",
                    "{image:ic_ozog}" +
                            "{textBold:1. Прерви действие повреждающего агента как можно быстрее: потуши огонь водой, накрой одеялом, отключи электричество(в случае воздействия эл.тока)}" +
                            "{text:2. Убери одежду из зоны ожога. Не пытайся снять прилипшую к зоне повреждения одежду: это может привести к большему повреждению! }" +
                            "{text:3. Удали украшения, ремни и другие ограничительные предметы, особенно вокруг обожженных участков и шеи. Сгоревшие участки быстро отекают.}" +
                            "{text:4. Охлади ожог прохладной или теплой проточной водой в течение 20 минут. Никогда не используй лед, воду со льдом или любые кремы или жирные вещества, такие как масло(они могут увеличить глубину и зону повреждения).}" +
                            "{text:5.Держи себя или пострадавшего в тепле(за исключением зоны ожога).Общее переохлаждение до 35С и ниже может быть опасно.}" +
                            "{textBold:6. Прикрой область ожога. Используй прохладную влажную повязку или чистую ткань.}" +
                            "{text:7. Подними рану выше уровня сердца, если это возможно.}"
                    ,
                    "Медицина"
                ),
                ArticleEntry(
                    "Медицина2",
                    "{text:Медицина21}{image:ic_compass}{text:Медицина}",
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
                            "{text:6. Встретить пожарных, показать машине удобный путь к очагу возгорания}" +
                            "{text:Продумывая, как действовать при пожаре в случае его самостоятельного тушения, не стоит преуменьшать угрозу даже незначительного возгорания. При подходящих условиях огонь может распространиться очень быстро, а густой дым может проникнуть и через мокрую ткань, лишив сознания за несколько вдохов.}",
                    "Быт"
                ),
                ArticleEntry("Быт2", "{text:Быт21}{image:ic_phone}{text:Быт22}", "Быт"),
                ArticleEntry("Терроризм1", "{text:Text1}{image:ic_phone}{text:Text1}", "Терроризм"),
                ArticleEntry(
                    "Терроризм2",
                    "{text:Text1}{image:ic_compass}{text:Text1}",
                    "Терроризм"
                )
            )
            wordDao.insertAll(categories)
            articleDao.insertAll(articles)
        }
    }
}
