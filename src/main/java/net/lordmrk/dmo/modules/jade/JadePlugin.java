package net.lordmrk.dmo.modules.jade;

import net.lordmrk.dmo.block.DogBowl;
import net.lordmrk.dmo.block.entity.DogBowlEntity;
import net.lordmrk.dmo.entity.DoggoEntity;
import snownee.jade.api.*;

@WailaPlugin
public class JadePlugin implements IWailaPlugin {

    @Override
    public void register(IWailaCommonRegistration registration) {
        registration.registerBlockDataProvider(DoggoBowlComponentProvider.INSTANCE, DogBowlEntity.class);
        //registration.registerEntityDataProvider(DebugDoggoComponentProvider.INSTANCE, DoggoEntity.class);
    }

    @Override
    public void registerClient(IWailaClientRegistration registration) {
        registration.registerBlockComponent(DoggoBowlComponentProvider.INSTANCE, DogBowl.class);
        //registration.registerEntityComponent(DebugDoggoComponentProvider.INSTANCE, DoggoEntity.class);
    }
}
