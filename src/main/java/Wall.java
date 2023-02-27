import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Wall implements Structure {

    private List<Block> blocks;

    public Wall(List<Block> blocks) {
        this.blocks = blocks;
    }

    @Override
    public Optional<Block> findBlockByColor(String color) {
        return blocks.stream()
                .filter(block -> block.getColor().equals(color))
                .findFirst()
                .or(() -> blocks.stream()
                        .filter(block -> block instanceof CompositeBlock)
                        .map(block -> (CompositeBlock) block)
                        .flatMap(compositeBlock -> compositeBlock.getBlocks().stream())
                        .filter(block -> block.getColor().equals(color))
                        .findFirst());
    }

    @Override
    public List<Block> findBlocksByMaterial(String material) {
        return blocks.stream()
                .filter(block -> block.getMaterial().equals(material))
                .flatMap(block -> {
                    if (block instanceof CompositeBlock) {
                        return ((CompositeBlock) block).getBlocks().stream();
                    } else {
                        return Stream.of(block);
                    }
                })
                .collect(Collectors.toList());
    }

    @Override
    public int count() {
        return blocks.stream()
                .mapToInt(block -> {
                    if (block instanceof CompositeBlock) {
                        return ((CompositeBlock) block).getBlocks().size();
                    } else {
                        return 1;
                    }
                })
                .sum();
    }

    interface Block {
        String getColor();

        String getMaterial();
    }

    interface CompositeBlock extends Block {
        List<Block> getBlocks();
    }

}
