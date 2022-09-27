public interface Explodable
{
    void applyExplosionEffects(AbstractPlayer playerAfflicted, boolean firstHit);
    void applyExplosionEffects(MapTile tileAfflicted, boolean firstHit);
    void detonate();
}
