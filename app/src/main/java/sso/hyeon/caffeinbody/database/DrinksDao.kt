package sso.hyeon.caffeinbody.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface DrinksDao {
    @Insert
    fun insert(drink: Drinks)

    @Insert
    fun insertAll(vararg drink: Drinks)

    /*@Update
    fun update(drink: Drinks)*/

    @Delete
    fun delete(drink: Drinks)

    /*@Query("SELECT * FROM Drinks order by favorite desc, madeBy asc, drinkName asc")
    fun getAll(): LiveData<List<Drinks>>*/

    /////////////////////////
    @Query("select * from Drinks where madeBy like :something order by favorite desc, drinkName asc")
    fun selectDrinkMadeBy(something: String): List<Drinks>


    @Query("select * from Drinks where id like :something")
    fun selectByID(something: Int): Drinks

    /*@Query("select count(*) from Drinks")
    fun selectCount(): Int*/

    @Query("select * from Drinks where drinkName like :something order by favorite desc, madeBy asc, drinkName asc")
    fun selectDrinkName(something: String): List<Drinks>

    /*@Query("select * from Drinks where category like :something order by favorite desc, madeBy asc, drinkName asc")
    fun selectDrinkCategory(something: String): List<Drinks>*/

    @Query("select * from Drinks where category like :something order by favorite desc, madeBy asc, drinkName asc")
    fun getDrinkCategory(something: String): LiveData<List<Drinks>>
//////////////
    @Query("select * from Drinks where iscafe like :something  order by favorite desc, madeBy asc, drinkName asc")
    fun selectiscafe(something: Boolean): LiveData<List<Drinks>>


    @Query("select * from Drinks where iscafe like :something and madeBy not like \"논카페인\" order by favorite desc, madeBy asc, drinkName asc ")
    fun selectiscafeNoLive(something: Boolean): List<Drinks>


    @Query("select * from Drinks where caffeine_caffeine1 BETWEEN 1 AND :something")
    fun recommendcaffeine(something: Double): List<Drinks>

    @Query("select * from Drinks where madeBy like :something")
    fun recommendnoncaffeine(something: String):  List<Drinks>


    @Query("select * from Drinks where favorite = :something")//favorite 항목만 반환(true/false)
    fun selectFavorite(something: Boolean): List<Drinks>



    //*************************
    @Query("update Drinks set favorite = :something where id = :name")//favorite 항목만 변경(true/false)
    fun updateFavorite(something: Boolean, name: Int)


    @Query("select * from Drinks where drinkName like :name and madeBy like :made and category like :cat")
    fun selectIntersect(name: String, made: String, cat: String): List<Drinks>

    /*@Query("select * from Drinks where drinkName = :something")
    fun selectOne(something: String): Drinks*/


    @Query("select * from Drinks where iscafe like :iscaf and madeBy like :made order by favorite desc, madeBy asc, drinkName asc")
    fun selectAllConditions(iscaf: Boolean, made: String): LiveData<List<Drinks>>


    @Query("select * from Drinks where iscafe like :iscaf and madeBy like :made order by favorite desc, madeBy asc, drinkName asc")
    fun selectAllConditionsNoLive(iscaf: Boolean, made: String): List<Drinks>

}