package xd.arkosammy.firebending.blocks;

public class FlintAndSteelFireBlock extends CustomFireBlock {

    FlintAndSteelFireBlock(Settings settings) {
        super(settings);
    }

    @Override
    protected FireSource getFireSource() {
        return FireSource.FLINT_AND_STEEL;
    }

}
