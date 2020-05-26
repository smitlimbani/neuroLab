package com.example.neuro.services;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import com.example.neuro.beans.Master;
import com.example.neuro.services.MasterService;

class MasterServiceTest {
    MasterService masterService;

    @Test
    public void itShouldThrowNullPointerExceptionSinceNoSuchULIDExist() {
        assertThrows(NullPointerException.class,
                ()->{
                    masterService.doesULIDExistRest("5");
                });
    }
}
