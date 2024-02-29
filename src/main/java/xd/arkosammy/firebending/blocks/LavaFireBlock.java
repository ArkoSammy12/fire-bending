package xd.arkosammy.firebending.blocks;

public class LavaFireBlock extends CustomFireBlock {

    public LavaFireBlock(Settings settings) {
        super(settings);
    }

    @Override
    public FireSource getFireSource() {
        return FireSource.LAVA;
    }

}
