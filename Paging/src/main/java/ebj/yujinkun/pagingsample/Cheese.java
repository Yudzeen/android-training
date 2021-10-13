package ebj.yujinkun.pagingsample;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Objects;

@Entity
public class Cheese {

    @PrimaryKey(autoGenerate = true)
    int id;

    String name;

    public Cheese(int id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cheese cheese = (Cheese) o;
        return id == cheese.id &&
                Objects.equals(name, cheese.name);
    }

}
