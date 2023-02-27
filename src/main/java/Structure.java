import java.util.List;
import java.util.Optional;

public interface Structure {
    Optional<Wall.Block> findBlockByColor(String color);
    List<Wall.Block> findBlocksByMaterial(String material);
    int count();
}

