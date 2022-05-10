package dk.sdu.mmmi.swe.gtg.enemyai.services;

import com.badlogic.gdx.math.Vector2;
import dk.sdu.mmmi.swe.gtg.common.data.Entity;

public interface IEnemyFactory {
    Entity createEnemy(Vector2 position);
}
