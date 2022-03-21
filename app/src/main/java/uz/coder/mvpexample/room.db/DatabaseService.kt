package uz.coder.mvpexample.room.db

import androidx.room.*
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import uz.coder.mvpexample.models.books.books.BookX

@Dao
interface DatabaseService {
    @Query("select * from BookX")
     fun getAll(): Flowable<List<BookX>>

    @Insert
     fun add(myClass: BookX): Single<Long>

    @Query("DELETE FROM BookX WHERE book_image = :url")
     fun delete(url: String): Completable

}
