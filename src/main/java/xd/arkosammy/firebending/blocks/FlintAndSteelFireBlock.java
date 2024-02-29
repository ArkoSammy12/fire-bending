package xd.arkosammy.firebending.blocks;

public class FlintAndSteelFireBlock extends CustomFireBlock {

    public FlintAndSteelFireBlock(Settings settings) {
        super(settings);
    }

    @Override
    public FireSource getFireSource() {
        return FireSource.FLINT_AND_STEEL;
    }

}
