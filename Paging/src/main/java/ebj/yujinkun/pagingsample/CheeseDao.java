package ebj.yujinkun.pagingsample;

import androidx.paging.DataSource;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
interface CheeseDao {

    @Query("SELECT COUNT(*) FROM Cheese")
    int count();

    @Query("SELECT * FROM Cheese ORDER BY name COLLATE NOCASE ASC")
    DataSource.Factory<Integer, Cheese> getAllCheesesByName();

    @Insert
    void insert(List<Cheese> cheeses);

    @Insert
    void insert(Cheese cheese);

    @Delete
    void delete(Cheese cheese);

}
