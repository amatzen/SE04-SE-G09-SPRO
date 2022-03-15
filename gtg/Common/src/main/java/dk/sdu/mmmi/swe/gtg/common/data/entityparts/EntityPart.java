/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.swe.gtg.common.data.entityparts;

import dk.sdu.mmmi.swe.gtg.common.data.Entity;
import dk.sdu.mmmi.swe.gtg.common.data.GameData;

/**
 *
 * @author Alexander
 */
public interface EntityPart {
    void processEntity(GameData gameData, Entity entity);
}
