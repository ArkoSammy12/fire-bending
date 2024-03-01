package xd.arkosammy.firebending.blocks;

public class ExplosionFireBlock extends CustomFireBlock {
    public ExplosionFireBlock(Settings settings) {
        super(settings);
    }

    @Override
    protected FireSource getFireSource() {
        return FireSource.EXPLOSION;
    }
}
