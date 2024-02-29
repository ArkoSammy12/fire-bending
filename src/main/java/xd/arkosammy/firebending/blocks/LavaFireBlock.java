package xd.arkosammy.firebending.blocks;

public class LavaFireBlock extends CustomFireBlock {

    LavaFireBlock(Settings settings) {
        super(settings);
    }

    @Override
    protected FireSource getFireSource() {
        return FireSource.LAVA;
    }

}
