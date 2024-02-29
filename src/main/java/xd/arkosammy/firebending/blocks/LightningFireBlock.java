package xd.arkosammy.firebending.blocks;

public final class LightningFireBlock extends CustomFireBlock {

    LightningFireBlock(Settings settings) {
        super(settings);
    }

    @Override
    protected FireSource getFireSource() {
        return FireSource.LIGHTNING;
    }

}
