package com.pandapulsestudios.amongusminecraft.VariableTests;

import com.pandapulsestudios.amongusminecraft.Enums.GameTypes;
import com.pandapulsestudios.pulsecore.Data.Interface.CustomVariableTest;
import com.pandapulsestudios.pulsecore.Data.Interface.PulseVariableTest;
import com.pandapulsestudios.pulsecore.Loops.PulseLoop;

import java.util.ArrayList;
import java.util.List;

@CustomVariableTest
public class GameTypesTest implements PulseVariableTest {
    @Override
    public boolean IsType(Object variable) {
        try{
            var test = GameTypes.valueOf(variable.toString());
            return true;
        }catch (IllegalArgumentException ignored){ return false; }
    }

    @Override
    public List<Class<?>> ClassTypes() {
        var data = new ArrayList<Class<?>>();
        data.add(GameTypes.class);
        data.add(GameTypes[].class);
        return data;
    }

    @Override
    public Object SerializeData(Object serializedData) {
        return serializedData.toString();
    }

    @Override
    public Object DeSerializeData(Object serializedData) { return GameTypes.valueOf(serializedData.toString());
    }

    @Override
    public Object SerializeBinaryData(Object serializedData) {
        return serializedData.toString();
    }

    @Override
    public Object DeSerializeBinaryData(Object serializedData) {
        return DeSerializeData(serializedData);
    }

    @Override
    public Object ReturnDefaultValue() { return GameTypes.values(); }

    @Override
    public void CUSTOM_CAST_AND_PLACE(List<Object> toAdd, int place, List<?> castedData, Class<?> arrayType) {
        toAdd.add(castedData.toArray(new GameTypes[0]));
    }
}
