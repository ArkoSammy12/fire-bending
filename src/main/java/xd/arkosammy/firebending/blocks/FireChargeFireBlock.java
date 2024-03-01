package xd.arkosammy.firebending.blocks;

public class FireChargeFireBlock extends CustomFireBlock {
    public FireChargeFireBlock(Settings settings) {
        super(settings);
    }

    @Override
    FireSource getFireSource() {
        return FireSource.FIRE_CHARGE;
    }
}
