package xd.arkosammy.firebending.blocks;

public final class LightningFireBlock extends CustomFireBlock {

    public LightningFireBlock(Settings settings) {
        super(settings);
    }

    @Override
    public FireSource getFireSource() {
        return FireSource.LIGHTNING;
    }

}
